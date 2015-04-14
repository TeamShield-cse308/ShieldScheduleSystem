/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sss.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import sss.exceptions.AccountActiveException;
import sss.exceptions.AccountPendingException;

/**
 * Class for persisting Student user representations in the database
 * @author Phillip Elliot, Jeffrey Kabot
 */
@NamedQueries({
    @NamedQuery(name = "Student.findAllPending",
            query = "SELECT s FROM Student s WHERE s.state = sss.entities.StudentAccountState.PENDING"),
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
    
    private StudentAccountState state; 

    @ManyToMany
    List<Student> friendsList = new ArrayList<>();

    @OneToOne
    private AssignedSchedule myAssignedSchedule;

    @OneToOne
    private DesiredSchedule myGeneratedSchedule;
    
    //required by JPA
    protected Student() {};
    
    public Student(String initName, String initPassword, String initEmail, School initSchool) {
        name = initName;
        password = initPassword;
        email = initEmail;
        school = initSchool;
        
        state = StudentAccountState.PENDING;
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
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
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
     */
    public void approve() {
        if (state == StudentAccountState.PENDING)
            state = StudentAccountState.INACTIVE;
    }
    
    /**
     * Flags a student account as active
     * @throws AccountActiveException Indicates that the student account is already active
     * @throws AccountPendingException If the account has not yet been approved
     */
    public void activate() throws AccountActiveException, AccountPendingException {
        if (state == StudentAccountState.INACTIVE)
            state = StudentAccountState.ACTIVE;
        else if (state == StudentAccountState.ACTIVE)
            throw new AccountActiveException(email + " is already active.");
        else
            throw new AccountPendingException(email + " is not yet approved.");
    }
    
    /**
     * Flags a student account as inactive
     */
    public void deactivate() {
        if (state == StudentAccountState.ACTIVE)
            state = StudentAccountState.INACTIVE;
    }

    //@TODO all these methods
    public void addFriend(Long id)
    {

    }

    public void deleteFriend(Long id)
    {

    }

    public void sendFriendRequest(Long id)
    {

    }

    public void enterCourse(Course course)
    {

    }

    public void viewCourse()
    {

    }

    public void editCourse(Course course)
    {

    }

    public Course viewAllCourses(Long id, int year, String semester)
    {
        //@TODO valid return
        return null;
    }

    public void generateDesiredSchedule()
    {

    }

    public DesiredSchedule viewDesiredSchedule()
    {
        //@TODO valid return
        return null;
    }

    public DesiredSchedule exportDesiredSchedule()
    {
        //@TODO valid return
        return null;
    }

}
