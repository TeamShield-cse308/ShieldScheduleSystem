/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;

/**
 * Class representing a Course offered by a particular school.
 * Each course has a unique identifier and a name.
 * A course is available in some number of sections.
 *
 * @author Phillip Elliot, Jeffrey Kabot
 */
@Entity

public class Course implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true)
    private String identifier;

    private String name;
    
    @OneToMany(mappedBy="course")
    private Set<Section> sections;

    protected Course()
    {
    }

    
    /**
     * Create a new course.
     * 
     * @param identifier The unique identifier for the Course, e.g. PHY101
     * @param name The name of the Course, e.g. Intro to Physics
     */
    public Course(String identifier, String name)
    {
        this.identifier = identifier;
        this.name = name;
        this.sections = new HashSet<>();
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public String getName()
    {
        return name;
    }
    
    public Set<Section> getSections()
    {
        return sections;
    }
    
    /**
     * Adds a new section to this course.
     * Automatically called when creating a new section.
     * 
     * @param s the section added.
     * @return 
     */
    boolean addSection(Section s)
    {
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
