package shield.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
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
    @OneToMany
    private Map<Course, String> courses;

    @OneToMany
    private List<Course> lunches;

    //The list of excluded sections, aggregated for every course desired
    @OneToMany
    private List<Section> excludedSections;

    @OneToOne
    private Schedule rootSchedule;

    @Transient
    private int bestScore;

    //required by JPA
    protected GenerationCriteria()
    {
    }

    GenerationCriteria(School sch,
            int sem)
    {
        courses = new HashMap<>();
        excludedSections = new ArrayList<>();
        lunches = sch.getLunches();
        rootSchedule = new Schedule(sch, sem);
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
     * selecting instructors
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
        if (courses.containsKey(c))
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

    /**
     * Retrieve the set of desired courses.
     *
     * @return The set of desired courses.
     */
    @Deprecated
    public Set<Course> getCourses()
    {
        return courses.keySet();
    }

    /**
     * Perform a combinatorial search on the Schedule Space for a valid (i.e.,
     * no conflicting sections) schedule optimally meeting the requirements
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

        bestScore = 0;
        backtrackSchedule(rootSchedule, new HashSet<>(courses.keySet()), 0, friends,
                acceptableSchedules, nearSchedules);

        if (!acceptableSchedules.isEmpty())
        {
            Collections.sort(acceptableSchedules);
            return acceptableSchedules;
        } else
        {
            Collections.sort(nearSchedules);
            return nearSchedules;
        }
    }

    /**
     * Recursive backtracking method for searching Schedule Space.
     *
     * @param sch The schedule being worked on.
     * @param remaining The set of courses desired in the schedule.
     * @param omissions The number of desired courses the search has omitted.
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
        //If we had to omit two or more courses, stop early
        if (omissions > 1)
        {
            return;
        }
        //If there are no more courses to add, save the schedule
        if (remaining.isEmpty())
        {
            if (omissions == 0 && sch.getScore() > bestScore)
            {
                bestScore = sch.getScore();
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
        for (Course c : localRemaining)
        {
            //pop the course from the set of courses to add
            localRemaining.remove(c);

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
            if (!success) //we had to omit this course
            {
                omissions += 1;
            }

        }
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
