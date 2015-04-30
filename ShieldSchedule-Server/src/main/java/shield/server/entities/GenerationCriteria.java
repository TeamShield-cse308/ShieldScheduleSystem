/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    @OneToMany
    private Set<Course> courses;

    @OneToMany
    private List<Section> excludedSections;

    //@TODO by default the student does not have lunch any day
    private boolean[] hasLunch;

    //required by JPA
    protected GenerationCriteria()
    {
    }

    GenerationCriteria(School sch)
    {
        courses = new HashSet<>();
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

    //@TODO bundle preferred instructors into course store
    //@TODO overload for optional parameters?
    /**
     * Add a course to the desired schedule, optionally excluding
     *
     * @param c The course desired
     * @param exclusions The sections to exclude, null or empty if omitted
     * @param instructors The preferred instructors for this course
     * @return
     */
    public boolean addCourse(Course c, List<Section> exclusions, List<String> instructors)
    {
        if (!courses.add(c))
        {
            return false;
        }
        if (exclusions == null)
        {
            return true;
        }

        for (Section s : exclusions)
        {
            if (c.getSections().contains(s))
            {
                excludedSections.add(s);
            }
        }
        return true;
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
