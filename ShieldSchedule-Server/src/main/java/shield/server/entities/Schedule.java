package shield.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * Entity class representing a Schedule that a Student has entered.
 *
 * <p>
 * A schedule is a list of sections, satisfying the two constraints that each
 * course is unique and that schedule blocks do not overlap. Schedules are per
 * each academic year.
 *
 * @author Jeffrey Kabot
 */
@Entity
@SuppressWarnings("ValidAttributes")
public class Schedule implements Serializable, Comparable<Schedule>
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private Set<Course> courses;

    @OneToMany
    private List<Section> sections;

    private boolean[][][] scheduleSlots;

    private int year;

    private int scheduleDays;

    //Score is the measure of how many friends are in each class summed over every class.
    //Used only by generated schedules.
    @Transient
    private int score = 0;

    //Flag indicating whether this schedule meets all generation criteria.
    //Used only by generated schedules.
    @Transient
    private boolean perfect = false;

    protected Schedule()
    {
    }

    /**
     * Create a fresh new schedule.
     *
     * @param school The school the schedule is for
     * @param year The year the schedule is for
     */
    Schedule(School school,
            int year)
    {
        this.year = year;
        scheduleDays = school.getScheduleDays();

        //need to add 1 since semesters, periods and schedule days are 1-indexed
        scheduleSlots = new boolean[school.getSemesters() + 1][school.getPeriods() + 1][school.getScheduleDays() + 1];
        courses = new HashSet<>();
        sections = new ArrayList<>();
    }

    /**
     * Copy constructor, creates a schedule that is a clone of the schedule
     * passed.
     *
     * @param s The schedule to copy
     */
    Schedule(Schedule s)
    {
        this.scheduleSlots = s.scheduleSlots.clone();
        this.courses = (HashSet) ((HashSet) s.courses).clone();
        this.sections = (ArrayList) ((ArrayList) s.sections).clone();

        this.year = s.year;
        this.scheduleDays = s.scheduleDays;
        this.score = s.score;
    }

    //@TODO convert boolean returns to thrown exceptions for more information?
    /**
     * Add a section of a course to this schedule.
     *
     * A section can only be added if it is offered during the semester this
     * schedule is for, and uses doesn't use a schedule block that conflicts
     * with those of other sections already in the schedule.
     *
     * @param s The section to add
     * @return True if the section could be added to the schedule. False if
     * otherwise.
     */
    public boolean addSection(Section s)
    {
        //If the section isn't offered this year then it can't be added.
        if (!(s.getCourse().getYear() == year))
        {
            return false;
        }

        //Check if the section fits in with the schedule
        boolean[][][] sandbox = fillScheduleSlots(s, true);
        if (sandbox == null)
        {
            return false;
        }

        //Try adding the course and section to the schedule
        //We can only add the section if the course is not already in the schedule
        //Adding the section can't fail
        //If successful, update matrix of filled time slots
        boolean success;
        if (success = (courses.add(s.getCourse()) && sections.add(s)))
        {
            scheduleSlots = sandbox;
        }
        return success;
    }

    public boolean removeSection(Section s)
    {
        //Try to remove the section and its course from the schedule.
        //Should only fail when the section isn't in the schedule.
        boolean success = courses.remove(s.getCourse()) && sections.remove(s);
        if (!success)
        {
            return false;
        }

        //Clear the schedule slots.
        boolean[][][] sandbox = fillScheduleSlots(s, false);
        scheduleSlots = sandbox;
        return success;
    }

    /**
     * Helper method for filling in or clearing schedule slots when adding or
     * removing a section from the schedule, respectively.
     *
     * <p>
     * The method returns unsuccessfully when there is a conflict in adding this
     * section to the schedule.
     *
     * @param s The section being added or removed from the schedule.
     * @param fill Whether we are filling or clearing schedule blocks.
     * @return If successful, returns the new matrix of schedule slots,
     * otherwise returns null
     */
    private boolean[][][] fillScheduleSlots(Section s,
            boolean fill)
    {
        //Create a locally scoped copy of the boolean matrix
        boolean[][][] sandbox = scheduleSlots.clone();

        //Extract the period slot for the section
        int block = s.getScheduleBlock().getPeriod();

        //Get the semesters
        Set<Integer> semesters = s.getSemesters();
        //Get the Days
        Set<Integer> days = s.getScheduleBlock().getDays();

        //Iterate over the semesters and days
        for (int sem : semesters)
        {
            for (int day : days)
            {
                //If we are adding the section to the schedule, we need to check for conflicts
                if (fill && sandbox[sem][block][day])
                {
                    return null;
                } else
                {
                    sandbox[sem][block][day] = fill;
                }
            }
        }
        return sandbox;
    }

    public List<Section> getSections()
    {
        return sections;
    }

    /**
     * Increase the score of this schedule
     *
     * @param gain The amount to increase the score
     */
    void incrementScore(int gain)
    {
        score += gain;
    }

    /**
     * Flag this schedule as perfect (i.e. meets all generation criteria)
     */
    void setPerfect()
    {
        perfect = true;
    }

    /**
     * Queries whether this schedule is perfect. Should only be used on
     * generated schedules.
     *
     * @return True if this schedule is perfect, false if otherwise.
     */
    public boolean isPerfect()
    {
        return perfect;
    }

    /**
     * Compare one schedule's score to another
     *
     * @param sch The schedule to compare to
     * @return A positive number of this schedule has a higher score, a negative
     * number if it has a lower score, zero if the scores are equal
     */
    @Override
    public int compareTo(Schedule sch)
    {
        return this.score - sch.score;
    }

    int getScheduleDays()
    {
        return scheduleDays;
    }

    int getScore()
    {
        return score;
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
        if (!(object instanceof Schedule))
        {
            return false;
        }
        Schedule other = (Schedule) object;
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
        return "shield.server.entities.Schedule[ id=" + id + " ]";
    }

}
