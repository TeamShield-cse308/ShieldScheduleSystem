/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sss.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;

/**
 * Class representing Schools in the database
 * @author Phillip Elliot, Jeffrey Kabot
 */
@Entity
//database queries for retrieving all or certain schools
@NamedQueries({
    @NamedQuery(name = "School.findAll", 
            query = "SELECT s FROM School s"),
    @NamedQuery(name = "School.findByName", 
            query = "SELECT s FROM School s WHERE s.name = :name"),
//    @TODO resolve compilation error here
//    @NamedQuery(name = "School.findAllCourses",
//            query = "SELECT c FROM School s WHERE")
})
public class School implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //annotations below________________
    private String name;
    private int numSemesters;
    private int numScheduleDays;
    private int numPeriods;
    private int startingLunchPeriod;
    private int endingLunchPeriod;

    @OneToMany
    private List<Course> courseList;

    @OneToMany
    private List<Student> studentList;
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    
    //required by JPA
    protected School(){}

    public School(String initName, int initSemesters, int initScheduleDays, int initPeriods)
    {
        name = initName;
        //@TODO enfroce # semesters from 1 to 4
        numSemesters = initSemesters;
        numScheduleDays = initScheduleDays;
        numPeriods = initPeriods;
    }

    
    /*
     * Getters and Setters
     */
    public String getSchoolName()
    {
        return name;
    }
    public void setSchoolName(String schoolName)
    {
        this.name = schoolName;
    }
    public int getNumSemesters()
    {
        return numSemesters;
    }
    //@TODO enforce validity
    public void setNumSemesters(int numSemesters)
    {
        this.numSemesters = numSemesters;
    }
    public int getNumScheduleDays()
    {
        return numScheduleDays;
    }
    public void setNumScheduleDays(int numScheduleDays)
    {
        this.numScheduleDays = numScheduleDays;
    }
    public int getNumPeriods()
    {
        return numPeriods;
    }
    public void setNumPeriods(int numPeriods)
    {
        this.numPeriods = numPeriods;
    }
    public int getStartingLunchPeriod()
    {
        return startingLunchPeriod;
    }
    public void setStartingLunchPeriod(int period)
    {
        this.startingLunchPeriod = period;
    }
    public int getEndingLunchPeriod()
    {
        return endingLunchPeriod;
    }
    public void setEndingLunchPeriod(int period)
    {
        this.endingLunchPeriod = period;
    }
    public List<Course> getCourseList()
    {
        return courseList;
    }

    public List<Student> getStudentList()
    {
        return studentList;
    }
    
    

    public void addCourse(Course c)
    {
        courseList.add(c);
    }
    
    public void removeCourse(Course c)
    {
        courseList.remove(c);
    }
    
    public void addStudent(Student s)
    {
        studentList.add(s);
    }
    
    public void removeStudent(Student s)
    {
        studentList.remove(s);
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
        if (!(object instanceof School)) {
            return false;
        }
        School other = (School) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "cse308.School[ id=" + id + " ]";
    }
}
