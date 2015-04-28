/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.Vector;

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
            query = "SELECT s FROM School s WHERE s.name = :name")
//    @TODO resolve compilation error here
//    @NamedQuery(name = "School.findAllCourses",
//            query = "SELECT c FROM School s WHERE")
})
public class School implements Serializable
{

    private static final long serialVersionUID = 1L;

    //annotations below________________
    @Id
    private String name;
    private int semesters;
    private int scheduleDays;
    private int periods;
    private int startingLunch;
    private int endingLunch;

    @OneToMany
    private List<Course> courseList;

    @OneToMany
    private List<Student> studentList;
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    
    //required by JPA
    protected School(){}

    public School(String initName, int initSemesters, int initScheduleDays, 
            int initPeriods, int initStartLunchPeriod, int initEndLunchPeriod)
    {
        name = initName;
        
        //@TODO enforce constraints
        semesters = initSemesters;
        scheduleDays = initScheduleDays;
        periods = initPeriods;
        
        startingLunch = initStartLunchPeriod;
        endingLunch = initEndLunchPeriod;
        
        courseList = new Vector<Course>();
        studentList = new Vector<Student>();
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
    //@TODO enforce constraints
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
        if (!(object instanceof School)) {
            return false;
        }
        School other = (School) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
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
