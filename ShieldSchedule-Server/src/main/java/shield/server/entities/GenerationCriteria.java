package shield.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * An object which stores the criteria a student specifies for their desired
 * schedule.
 *
 * <p>
 * Provides functionality for generating valid or near-valid schedules optimally
 * meeting those criteria and maximizing overlap with friends' schedules.
 *
 * @author Jeffrey Kabot
 */
@Entity
public class GenerationCriteria implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //The set of desired courses.  The uniqueness of Keys in a Map prevents duplicate courses.
    //Courses point to their preferred instructor.  The target value is null if no instructor is preferred.
    private Map<Course, String> courses;

    @OneToMany
    private List<Course> lunches;

    //The list of excluded sections, aggregated for every course desired
    @OneToMany
    private List<Section> excludedSections;

    @OneToOne(cascade = CascadeType.ALL)
    private Schedule rootSchedule;

    private int year;

    @Transient
    private int bestScore;

    //required by JPA
    protected GenerationCriteria()
    {
    }

    /**
     * Create a new set of generation criteria.
     *
     * @param school The school this generation criteria applies to.
     * @param sem The year the desired schedule is for.
     */
    GenerationCriteria(School school,
            int year)
    {
        courses = new HashMap<>();
        excludedSections = new ArrayList<>();
        lunches = school.getLunches(year);
        rootSchedule = new Schedule(school, year);
        this.year = year;
    }

    /**
     * Set whether the student wants to have a lunch, chosen for each day in the
     * schedule cycle.
     *
     * @param desiresLunch
     */
    public void setLunches(boolean[] desiresLunch)
    {
        if (desiresLunch.length != lunches.size())
        {
            throw new IndexOutOfBoundsException(
                    "Desired Lunch array must match the number of Schedule Days in the school's schedule!");
        }

        for (int i = 0; i < desiresLunch.length; i++)
        {
            //if the student wants lunch on that day, add lunch to the set of desired courses
            if (desiresLunch[i])
            {
                courses.put(lunches.get(i), null);
            } else //otherwise remove it if it was already present
            {
                courses.remove(lunches.get(i));
            }
        }
    }

    /**
     * Add a course to the desired schedule, optionally excluding sections or
     * selecting instructors.
     *
     * @param c The course desired
     * @param exclusions The sections to exclude, EMPTY if omitted
     * @param instructor The preferred instructor, NULL if omitted
     * @return True if the course could be added, false if otherwise
     */
    public boolean addCourse(Course c,
            List<Section> exclusions,
            String instructor)
    {
        //don't add if the course is already in the schedule
        if (c.getYear() != year || courses.containsKey(c))
        {
            return false;
        }
        //map the course to the instructor
        //the same instructor can be preferred for different courses
        //a map to a null instructor means there is no preferred instructor
        courses.put(c, instructor);

        //store the specified exclusions
        for (Section s : exclusions)
        {
            if (c.getSections().contains(s))
            {
                excludedSections.add(s);
            }
        }
        return true;
    }

    public void removeCourse(Course c)
    {
        courses.remove(c);
        excludedSections.removeAll(c.getSections());
    }

    /**
     * Perform a combinatorial search on the Schedule Space for a valid (i.e.,
     * with no conflicting sections) schedule optimally meeting the requirements
     * specified in this generation criteria object and maximizing the overlap
     * with friends' assigned schedules.
     *
     * @param friends The set of students to maximize overlap with.
     * @return A list of optimal schedules, sorted by overlap with friends'
     * schedules. All the schedules will have all the courses desired, or all
     * will omit one course (including lunches) from those desired.
     */
    public List<Schedule> generateSchedule(List<Student> friends)
    {
        List<Schedule> acceptableSchedules = new ArrayList<>();
        List<Schedule> nearSchedules = new ArrayList<>();

        this.bestScore = -1;
        //perform the search
        backtrackSchedule(rootSchedule, new HashSet<>(courses.keySet()), 0, friends,
                acceptableSchedules, nearSchedules);

        if (!acceptableSchedules.isEmpty()) //the search found perfect schedules
        {
            //acceptableSchedules are already sorted by ascending friends overlap
            //When we have found any perfect schedules, we only want to return the best one
            int idx = acceptableSchedules.size() - 1;
            return acceptableSchedules.subList(idx, idx + 1);
        } else //the search only found nearly-perfect schedules
        {
            //Sort by maximum overlap with friends' schedules
            Collections.sort(nearSchedules);
            return nearSchedules;
        }
    }

    /**
     * Recursive backtracking method for searching Schedule Space.
     *
     * @param sch The schedule being worked on.
     * @param remaining The set of courses desired in the schedule.
     * @param omissions The weighted sum of criteria omissions. Lunches are
     * worth 1 point. Courses are worth d points, where d is the number of days
     * in the schedule-week. A schedule search path is short-circuited when more
     * than d points of criteria are omitted.
     * @param friends The list of friends.
     * @param acceptableSchedules The list of perfect schedules (i.e., contain
     * all the desired courses)
     * @param nearSchedules The list of nearly perfect schedules (i.e., contain
     * all but one desired course)
     */
    private void backtrackSchedule(Schedule sch,
            Set<Course> remaining,
            int omissions,
            List<Student> friends,
            List<Schedule> acceptableSchedules,
            List<Schedule> nearSchedules)
    {
        //If we omit too many criteria, stop early
        if (omissions > sch.getScheduleDays())
        {
            return;
        }
        //If there are no more courses to add, save the schedule
        if (remaining.isEmpty())
        {
            if (omissions == 0 && sch.getScore() > bestScore)
            {
                bestScore = sch.getScore();
                sch.setPerfect();
                acceptableSchedules.add(sch);
            } else
            {
                nearSchedules.add(sch);
            }
            return;
        }
        //local set of remaining courses to add
        Set<Course> localRemaining = new HashSet<>(remaining);

        //for each course desired
        for (Iterator<Course> iter = localRemaining.iterator(); iter.hasNext();)
        {
            Course c = iter.next();
            //pop the course from the set of courses to add
            iter.remove();

            //track whether we were able to add any section of the course to the schedule
            boolean success = false;

            //find a section of the course
            for (Section s : c.getSections())
            {
                //only try to add it if this section has not been excluded
                //or if its teacher is the preferred teacher (if specified)
                if ((!excludedSections.contains(s)) &&
                        (courses.get(c) == null || courses.get(c).equals(
                                s.getTeacher())))
                {
                    Schedule localSch = new Schedule(sch);
                    //try adding this section to the schedule
                    if (success = localSch.addSection(s))
                    {

                        //Compute the amount of overlap with friends schedules
                        Set<Student> intersection = new HashSet<>();
                        intersection.addAll(s.getEnrolledStudents());
                        intersection.retainAll(friends);
                        localSch.incrementScore(intersection.size());

                        //Recursive backtracking
                        backtrackSchedule(localSch, localRemaining, omissions,
                                friends, acceptableSchedules, nearSchedules);
                    }
                }
            }
            if (!success) //we had to omit this course, add to the omission score
            {
                //@TODO @WARNING this condition is sensitive to changes in the way lunch courses are created
                //add method for detecting lunch courses, boolean isLunch() ??
                if (c.getIdentifier().startsWith("LUNCH"))
                {
                    omissions += 1;
                } else
                {
                    omissions += sch.getScheduleDays();
                }
            }

        }
    }

    public Map<Course, String> getCourses()
    {
        return new HashMap<>(courses);
    }
    
    public List<Section> getExlcusions()
    {
        return excludedSections;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GenerationCriteria))
        {
            return false;
        }
        GenerationCriteria other = (GenerationCriteria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(
                other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "shield.server.entities.GenerationCriteria[ id=" + id + " ]";
    }
}
