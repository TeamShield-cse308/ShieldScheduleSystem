/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.entities;

import java.io.Serializable;
import java.util.SortedSet;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Class to persist Sections of classes in the database
 *
 * @author Phillip Elliot, Jeffrey Kabot
 */
@Entity
@SuppressWarnings("ValidPrimaryTableName")
public class Section implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private ScheduleBlock scheduleBlock;

    @ManyToOne
    private Course course;

    private SortedSet<Integer> semesters;
    
    private String teacherName;

    protected Section()
    {
    }

    /**
     * Create a new section of a course.
     *
     * @param initCourse the course that this section is an instance of
     * @param teacher the instructor of this section
     * @param sb the block during which this section occurs
     * @param initSemesters the set of semesters this section exists for
     * @throws IllegalArgumentException when the set of semesters provided is
     * not valid for the school
     */
    public Section(Course initCourse,
            String teacher,
            ScheduleBlock sb,
            SortedSet<Integer> initSemesters) throws IllegalArgumentException
    {
        course = initCourse;
        teacherName = teacher;
        if (initSemesters.first() < 1 || initSemesters.last() > initCourse.getSchool().getSemesters())
        {
            throw new IllegalArgumentException("The set of semesters for this section must be in the valid range defined by the school");
        }
        semesters = initSemesters;
        scheduleBlock = sb;
    }

    public Course getCourse()
    {
        return course;
    }

    public SortedSet<Integer> getSemesters()
    {
        return semesters;
    }

    public String getTeacher()
    {
        return teacherName;
    }

    public ScheduleBlock getScheduleBlock()
    {
        return scheduleBlock;
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
        if (!(object instanceof Section))
        {
            return false;
        }
        Section other = (Section) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "cse308.Section[ id=" + id + " ]";
    }

}
