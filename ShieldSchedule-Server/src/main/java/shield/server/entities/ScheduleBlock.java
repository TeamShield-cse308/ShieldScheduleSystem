/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.entities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.SortedSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Class representing valid schedule blocks for schools in the database.
 * Each schedule block is unique up to the combination of its school, the period
 * during which it takes place, and which days of the schedule it uses.
 * 
 * Within the schedule block the days is privately encoded as a string.
 * For example a schedule block using days 1, 2 and 4 will privately encode this 
 * as "124".
 *
 * @author Jeffrey Kabot
 */
@Entity
@NamedQueries(
        {
            @NamedQuery(
                    name = "ScheduleBlock.findBySchool",
                    query = "SELECT sb FROM ScheduleBlock sb WHERE sb.school.name = :school")
        }
)
@Table(uniqueConstraints =
        @UniqueConstraint(columnNames =
                {
                    "SCHOOL_ID, PERIOD_SLOT", "DAYS",
        })
)
public class ScheduleBlock implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    //We need a reference to the owning school here to enforce unique constraint
    @ManyToOne
    @JoinColumn(name = "SCHOOL_ID")
    private School school;

    @Column(name = "PERIOD_SLOT")
    private int period;

    //We use a string so we can use the set of days as a unique constraint
    @Column(name = "DAYS")
    private String days;

    protected ScheduleBlock()
    {
    }

    //@TODO check boundaries on scheduleDays?
    /**
     * Creates a new schedule block for a school. Each schedule block is unique
     * up to the combination of its school, period and which days it uses.
     *
     * @param initSchool The school owning this schedule block
     * @param initPeriod The period during which the block takes place
     * @param scheduleDays A sorted set of integers representing the combination
     * of days used by this schedule block
     */
    public ScheduleBlock(School initSchool, int initPeriod,
            SortedSet<Integer> scheduleDays)
    {
        school = initSchool;
        period = initPeriod;
        
        if (scheduleDays.first() < 1 || scheduleDays.last() > school.getScheduleDays())
            throw new IllegalArgumentException("Schedule days must be within the valid range specified by the school");
        for (int day: scheduleDays)
        {
            days += day;
        }
    }
    
    //@TODO check boundaries on scheduleDays?
    //@TODO check unique days?
    /**
     * Creates a new schedule block for a school. Each schedule block is unique
     * up to the combination of its school, period and which days it uses.
     *
     * @param initSchool The school owning this schedule block
     * @param initPeriod The period during which the block takes place
     * @param scheduleDays The schedule days used by this schedule block
     */
    @Deprecated
    public ScheduleBlock(School initSchool, int initPeriod,
            int... scheduleDays)
    {
        school = initSchool;
        period = initPeriod;
        days = "";
        Arrays.sort(scheduleDays);
        for (int day : scheduleDays)
        {
            days += day;
        }
    }

    //@TODO check boundaries on scheduleDays?
    //@TODO check unique days?
    /**
     * Creates a new schedule block for a school. Each schedule block is unique
     * up to the combination of its school, period and which days it uses.
     *
     * @param initSchool The school owning this schedule block
     * @param initPeriod The period during which the block takes place
     * @param scheduleDays A string representing the combination of days used by
     * this schedule block
     * @throws IllegalArgumentException when the scheduleDays string is invalid
     */
    @Deprecated
    public ScheduleBlock(School initSchool, int initPeriod,
            String scheduleDays) throws IllegalArgumentException
    {
        school = initSchool;
        period = initPeriod;
        if (!scheduleDays.matches("\\d+") || scheduleDays.contains("0"))
        {
            throw new IllegalArgumentException("Schedule Days string can't have zeroes or non-digit characters");
        } else
        {
            days = scheduleDays;
        }
    }
    
    public School getSchool()
    {
        return school;
    }

    /**
     * Retrieve the period for this schedule block.
     *
     * @return
     */
    public int getPeriod()
    {
        return period;
    }

    /**
     * Retrieve the set of schedule days as a string.
     *
     * @return
     */
    public String getDaysString()
    {
        return days;
    }

    /**
     * Retrieve the set of schedule days as an array of day numbers.
     *
     * @return
     */
    public int[] getDays()
    {
        int[] scheduleDays = new int[days.length()];
        for (int i = 0; i < scheduleDays.length; i++)
        {
            scheduleDays[i] = Integer.parseInt("" + days.charAt(i));
        }
        return scheduleDays;
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
        if (!(object instanceof ScheduleBlock))
        {
            return false;
        }
        ScheduleBlock other = (ScheduleBlock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "cse308.scheduleBlock[ id=" + id + " ]";
    }
}
