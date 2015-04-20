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
            @NamedQuery(name = "Student.findByEmail",
                    query = "SELECT s FROM Student s WHERE s.email = :email"),
        })
@Entity
public class Student extends GenericUser implements Serializable
{

    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //annotations go here_________________________________________

    @ManyToOne
    private School school;

    @Id
    private String email;

    private StudentAccountState accountState;

    @ManyToMany
    List<Student> friendsList = new ArrayList<>();

    @OneToOne
    private AssignedSchedule myAssignedSchedule;

    @OneToOne
    private DesiredSchedule myGeneratedSchedule;

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
    }

    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
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
        if (!(object instanceof Student))
        {
            return false;
        }
        Student other = (Student) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "cse308.Student[ id=" + id + " ]";
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
    public void activate() throws AccountActiveException, AccountPendingException
    {
        if (accountState == StudentAccountState.INACTIVE)
        {
            accountState = StudentAccountState.ACTIVE;
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

    //@TODO all these methods
    public void addFriend(Long id)
    {

    }

    public void deleteFriend(Long id)
    {

    }

    public void addFriendRequest(Long id)
    {

    }

    public Course viewAllCourses(Long id,
            int year,
            String semester)
    {
        //@TODO valid return
        return null;
    }

    public void generateDesiredSchedule()
    {

    }

    public AssignedSchedule viewAssignedSchedule()
    {
        return myAssignedSchedule;
    }

    public DesiredSchedule viewDesiredSchedule()
    {
        return myGeneratedSchedule;
    }

}
