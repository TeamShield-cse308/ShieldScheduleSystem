/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Class representing Schools in the database
 *
 * @author Phillip Elliot, Jeffrey Kabot
 */
@Entity
//database queries for retrieving all or certain schools
@NamedQueries(
        {
            @NamedQuery(name = "School.findAll",
                    query = "SELECT s FROM School s"),
            @NamedQuery(name = "School.findByName",
                    query = "SELECT s FROM School s WHERE s.name = :name")
        })
public class School implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    private String name;

    @Min(1)
    @Max(4)
    private int semesters;

    @Min(1)
    @Max(7)
    private int scheduleDays;

    @Min(6)
    @Max(12)
    private int periods;

    @Min(1)
    @Max(12)
    private int startingLunch;

    @Min(1)
    @Max(12)
    private int endingLunch;

    @OneToMany
    private List<Course> courseList;

    //required by JPA
    protected School()
    {
    }

    public School(String initName,
            int initSemesters,
            int initScheduleDays,
            int initPeriods,
            int initStartLunchPeriod,
            int initEndLunchPeriod)
    {
        name = initName;

        semesters = initSemesters;
        scheduleDays = initScheduleDays;
        periods = initPeriods;

        if (initStartLunchPeriod > periods || initEndLunchPeriod > periods)
            throw new IllegalArgumentException("Can't have a lunch after the last period in the school day");

        startingLunch = initStartLunchPeriod;

        if (initEndLunchPeriod < initStartLunchPeriod)
            throw new IllegalArgumentException("Lunch can't end before it begins.");
        
        endingLunch = initEndLunchPeriod;

        courseList = new ArrayList<>();
    }

    /*
     * Getters and Setters
     */
    public String getSchoolName()
    {
        return name;
    }

    public void setSchoolName(String name)
    {
        this.name = name;
    }

    public int getSemesters()
    {
        return semesters;
    }

    public void setSemesters(int numSemesters)
    {
        this.semesters = numSemesters;
    }

    public int getScheduleDays()
    {
        return scheduleDays;
    }

    public void setScheduleDays(int numScheduleDays)
    {
        this.scheduleDays = numScheduleDays;
    }

    public int getPeriods()
    {
        return periods;
    }

    public void setPeriods(int numPeriods)
    {
        this.periods = numPeriods;
    }

    public int getStartingLunch()
    {
        return startingLunch;
    }

    public void setStartingLunch(int period)
    {
        this.startingLunch = period;
    }

    public int getEndingLunch()
    {
        return endingLunch;
    }

    public void setEndingLunch(int period)
    {
        this.endingLunch = period;
    }

    public List<Course> getCourseList()
    {
        return courseList;
    }

    public void addCourse(Course c)
    {
        courseList.add(c);
    }

    public String getId()
    {
        return name;
    }

    public void setId(String id)
    {
        this.name = id;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the schoolName fields are not set
        if (!(object instanceof School))
        {
            return false;
        }
        School other = (School) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "cse308.School[ schoolName=" + name + " ]";
    }
}
