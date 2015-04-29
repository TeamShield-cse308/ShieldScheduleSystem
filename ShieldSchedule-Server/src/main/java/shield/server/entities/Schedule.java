/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entity class representing a Schedule that a Student has entered.
 *
 * A schedule is a set of sections, satisfying the two constraints that each
 * course is unique and that schedule blocks do not overlap.
 *
 * @author Jeffrey Kabot
 */
@Entity
public class Schedule implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private Set<Section> sections;

    private boolean[][] scheduleSlots;

    private int semester;

    public Schedule(School sch,
            int sem)
    {
        scheduleSlots = new boolean[sch.getPeriods()][sch.getScheduleDays()];
        semester = sem;
        sections = new HashSet<>();
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
        if (!s.getSemesters().contains(semester))
        {
            return false;
        }
        //Create a locally scoped copy of the boolean matrix
        boolean[][] sandbox = scheduleSlots.clone();

        //Extract the period
        int p = s.getScheduleBlock().getPeriod();
        //Get the days
        Set<Integer> days = s.getScheduleBlock().getDays();
        //Iterate over the days
        Iterator<Integer> iter = days.iterator();
        while (iter.hasNext())
        {
            int q = iter.next();
            if (sandbox[p][q]) //If there is already a Section using this spot
            {
                return false;
            } else
            {
                sandbox[p][q] = true;
            }
        }
        
        //Update the boolean matrix
        scheduleSlots = sandbox;
        return sections.add(s);
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
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
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
