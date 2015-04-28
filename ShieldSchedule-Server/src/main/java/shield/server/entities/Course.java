/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;

/**
 * Class representing a Course offered by a particular school.
 * Each course has a unique identifier and a name.
 * A course is available in some number of sections.
 *
 * @author Phillip Elliot
 */
@Entity

public class Course implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

<<<<<<< HEAD
<<<<<<< HEAD
    private String name;

    private int semester;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
=======
    //@TODO course has reference to school rather than school has list of courses?
    //not sure which is more efficient
>>>>>>> origin/master
    
    @Column(unique = true)
    private String identifier;

    private String name;

=======
    //@TODO course has reference to school rather than school has list of courses?
    //not sure which is more efficient
    
    @Column(unique = true)
    private String identifier;

    private String name;

>>>>>>> origin/master
    protected Course()
    {
    }

    
    /**
     * Create a new course.
     * @param identifier The unique identifier for the Course, e.g. PHY101
     * @param name The name of the Course, e.g. Intro to Physics
     */
    public Course(String identifier, String name)
    {
        this.identifier = identifier;
        this.name = name;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public String getName()
    {
        return name;
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
