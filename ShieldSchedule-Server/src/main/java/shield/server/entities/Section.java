/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Class to persist Sections of classes in the database
 *
 * @author Phillip Elliot, Jeffrey Kabot
 */
@Entity
@NamedQueries(
        {
            @NamedQuery(name = "Section.findByID",
                    query = "SELECT s FROM Section s WHERE s.id = :id"),
            @NamedQuery(name = "Section.batchFindByID",
                    query = "SELECT s FROM Section s WHERE s.id IN :ids")
        }
)
@Table(uniqueConstraints =
        @UniqueConstraint(columnNames =
                {
                    "COURSE_ID", "SCHEDULE_BLOCK_ID", "SEMESTERS",
        })
)
@SuppressWarnings("ValidPrimaryTableName")
public class Section implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "SCHEDULE_BLOCK_ID")
    private ScheduleBlock scheduleBlock;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    @Column(name = "SEMESTERS")
    private String semesters;

    private String teacherName;

    @OneToMany
    private Set<Student> enrolledStudents;

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
    Section(Course initCourse,
            String teacher,
            ScheduleBlock sb,
            SortedSet<Integer> initSemesters) throws IllegalArgumentException
    {
        course = initCourse;
        teacherName = teacher;
        semesters = "";
        if (initSemesters.first() < 1 || initSemesters.last() > initCourse.getSchool().getSemesters())
        {
            throw new IllegalArgumentException("The set of semesters for this section must be in the valid range defined by the school");
        }
        for (int sem : initSemesters)
        {
            semesters += sem;
        }
        scheduleBlock = sb;
        enrolledStudents = new HashSet<>();
    }

    public Course getCourse()
    {
        return course;
    }

    public SortedSet<Integer> getSemesters()
    {
        SortedSet<Integer> semSet = new TreeSet<>();
        for (int i = 0; i < semesters.length(); i++)
        {
            semSet.add(Integer.parseInt("" + semesters.charAt(i)));
        }
        return semSet;
    }

    public String getTeacher()
    {
        return teacherName;
    }

    public ScheduleBlock getScheduleBlock()
    {
        return scheduleBlock;
    }

    public Set<Student> getEnrolledStudents()
    {
        return enrolledStudents;
    }

    /**
     * Enroll a student in the section.
     *
     * @param s
     * @return
     */
    public boolean addStudent(Student s)
    {
        return enrolledStudents.add(s);
    }

    /**
     * Drop a student from the section.
     *
     * @param s
     * @return
     */
    public boolean removeStudent(Student s)
    {
        return enrolledStudents.remove(s);
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
