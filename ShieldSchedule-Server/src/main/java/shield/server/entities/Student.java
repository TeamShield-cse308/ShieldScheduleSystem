/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import shield.server.exceptions.AccountActiveException;
import shield.server.exceptions.AccountApprovedException;
import shield.server.exceptions.AccountPendingException;

/**
 * Class for persisting Student user representations in the database
 *
 * @author Phillip Elliot, Jeffrey Kabot
 */
@NamedQueries(
        {
            @NamedQuery(name = "Student.findAll",
                    query = "SELECT s FROM Student s"),
            @NamedQuery(name = "Student.findAllPending",
                    query = "SELECT s FROM Student s WHERE s.accountState = shield.server.entities.StudentAccountState.PENDING"),
            @NamedQuery(name = "Student.findAllApproved",
                    query = "SELECT s FROM Student s WHERE NOT s.accountState = shield.server.entities.StudentAccountState.PENDING"),
            @NamedQuery(name = "Student.findByEmail",
                    query = "SELECT s FROM Student s WHERE s.email = :email"),
            @NamedQuery(name = "Student.findByNameAndSchool",
                    query = "SELECT s FROM Student s WHERE s.name = :name AND s.school.name = :school")
        })
@Entity
public class Student extends GenericUser implements Serializable
{

    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    //annotations go here_________________________________________

    @ManyToOne
    private School school;

    @Id
    private String email;

    private StudentAccountState accountState;

    //a schedule for each semester in the academic year
    @OneToMany
    private List<Schedule> assignedSchedule;
    
    @OneToOne
    private GenerationCriteria desiredSchedule;

    //required by JPA
    protected Student()
    {
    }

    public Student(String initName,
            String initEmail,
            String initPassword,
            School initSchool)
    {
        name = initName;
        email = initEmail;
        password = initPassword;
        school = initSchool;

        accountState = StudentAccountState.PENDING;
        assignedSchedule = new ArrayList<>(school.getSemesters());
        desiredSchedule = new GenerationCriteria(school);
    }

    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public String getId()
    {
        return email;
    }

    public void setId(String id)
    {
        this.email = id;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student))
        {
            return false;
        }
        Student other = (Student) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(
                other.email)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "cse308.Student[ id=" + email + " ]";
    }

    /**
     * Changes an account from pending to approved
     *
     * @throws AccountApprovedException Indicates that an account is already
     * approved
     */
    public void approve() throws AccountApprovedException
    {
        if (accountState == StudentAccountState.PENDING)
        {
            accountState = StudentAccountState.INACTIVE;
        } else
        {
            throw new AccountApprovedException(email + " is already approved.");
        }
    }

    /**
     * Flags a student account as active
     *
     * @throws AccountActiveException Indicates that the student account is
     * already active
     * @throws AccountPendingException If the account has not yet been approved
     */
    public void activate() throws AccountActiveException,
            AccountPendingException
    {
        if (accountState == StudentAccountState.INACTIVE)
        {
            //@TODO uncomment when deactivate is hooked to client
            //accountState = StudentAccountState.ACTIVE;
        } else if (accountState == StudentAccountState.ACTIVE)
        {
            throw new AccountActiveException(email + " is already active.");
        } else
        {
            throw new AccountPendingException(email + " is not yet approved.");
        }
    }

    /**
     * Flags a student account as inactive
     */
    public void deactivate()
    {
        if (accountState == StudentAccountState.ACTIVE)
        {
            accountState = StudentAccountState.INACTIVE;
        }
    }

    public String getEmail()
    {
        return email;
    }

    public School getSchool()
    {
        return school;
    }

    public Course viewAllCourses(Long id,
            int year,
            String semester)
    {
        //@TODO valid return
        return null;
    }
    

    public Schedule getSchedule(int semester)
    {
        return assignedSchedule.get(semester);
    }

    public void createSchedule(int semester)
    {
        //if there is already a schedule for that semester, then we assume that it's being replaced
        if (assignedSchedule.get(semester) != null)
        {
            assignedSchedule.remove(semester);
        }
        assignedSchedule.add(semester, new Schedule(this.school, semester));
    }

}
