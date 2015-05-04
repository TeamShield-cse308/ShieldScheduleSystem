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
import java.util.SortedSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Class representing a Course offered by a particular school. Each course has a
 * unique identifier and a name. A course is available in some number of
 * sections.
 *
 * @author Phillip Elliot, Jeffrey Kabot
 */
@Entity
@Table(uniqueConstraints
        = @UniqueConstraint(columnNames =
                {
                    "SCHOOL_ID", "IDENTIFIER", "YEAR" 
        })
)
@NamedQueries(
        {
            @NamedQuery(name = "Course.findByIdentifierSchool",
                    query = "SELECT c FROM Course c WHERE c.identifier = :identifier AND c.school.name = :school"),
            @NamedQuery(name = "Course.findByID",
                    query = "SELECT c FROM Course c WHERE c.id = :id")
        })
public class Course implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SCHOOL_ID")
    private School school;

    @Column(name = "IDENTIFIER")
    private String identifier;

    private String name;

    @Column(name = "YEAR")
    private int year;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Section> sections;

    protected Course()
    {
    }

    /**
     * Create a new course.
     *
     * @param school The school offering this course
     * @param identifier The unique identifier for the Course, e.g. PHY101
     * @param name The name of the Course, e.g. Intro to Physics
     */
    Course(School school, String identifier,
            String name, int year)
    {
        this.school = school;
        this.identifier = identifier;
        this.name = name;
        this.year = year;
        this.sections = new ArrayList<>();
    }
    
    public int getYear() {
        return year;
    }
    
    public School getSchool()
    {
        return school;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public String getName()
    {
        return name;
    }

    public List<Section> getSections()
    {
        return sections;
    }

    /**
     * Create a new section and add it to this course.
     *
     * @param teacher The instructor for the course.
     * @param sb The schedule block during which the section occurs.
     * @param initSemesters The set of semesters the section exists for.
     */
    public void addSection(String teacher,
            ScheduleBlock sb,
            SortedSet<Integer> initSemesters)
    {
        Section s = new Section(this, teacher, sb, initSemesters);
        sections.add(s);
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
        if (!(object instanceof Course))
        {
            return false;
        }
        Course other = (Course) object;
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
        return "cse308.Course[ id=" + id + " ]";
    }

}
