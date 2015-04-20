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
            query = "SELECT s FROM School s WHERE s.schoolName = :name"),
    @NamedQuery(name = "School.findSchoolIDByName", 
            query = "SELECT s.id FROM School s WHERE s.schoolName = :name"),
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
    private String schoolName;
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
        schoolName = initName;
        
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
        return schoolName;
    }
    public void setSchoolName(String name)
    {
        this.schoolName = name;
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
    public long getID(){
        return id;
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
