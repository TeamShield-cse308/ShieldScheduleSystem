package shield.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    //The list of excluded sections, aggregated for every course desired
    @OneToMany
    private List<Section> excludedSections;

    private boolean[] hasLunch;

    //required by JPA
    protected GenerationCriteria()
    {
    }

    GenerationCriteria(School sch)
    {
        courses = new HashMap<>();
        excludedSections = new ArrayList<>();
        hasLunch = new boolean[sch.getScheduleDays()];
    }

    /**
     * Set whether the student wants to have a lunch, chosen for each day in the
     * schedule cycle.
     *
     * @param desiredLunch
     */
    public void setLunches(boolean[] desiredLunch)
    {
        if (desiredLunch.length != hasLunch.length)
        {
            throw new IndexOutOfBoundsException(
                    "Desired Lunch array must match the number of Schedule Days in the school's schedule!");
        }

        hasLunch = desiredLunch;
    }

    /**
     * Add a course to the desired schedule, optionally excluding
     *
     * @param c The course desired
     * @param exclusions The sections to exclude, EMPTY if omitted
     * @param instructor The preferred instructor, NULL if omitted
     * @return True if the course could be added, false if otherwise
     */
    public boolean addCourse(Course c, List<Section> exclusions,
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
    public Set<Course> getCourses()
    {
        return courses.keySet();
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
