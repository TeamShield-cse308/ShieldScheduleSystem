/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse308;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;

/**
 *
 * @author Snapadragon
 */
@Entity
public class Student extends Guest implements Serializable  {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //annotations go here_________________________________________
    @ManyToOne
    private School mySchool;
    
    @ManyToMany
    ArrayList<Student> friendsList = new ArrayList<Student>();
    
    @OneToOne
    private AssignedSchedule myAssignedSchedule;
    
    @OneToOne
    private DesiredSchedule myGeneratedSchedule;
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
    public String toString() {
        return "cse308.Student[ id=" + id + " ]";
    }
    
    public void addFriend(Long id){
        
    }
    
    public void deleteFriend(Long id){
        
    }
    
    public void sendFriendRequest(Long id){
        
    }
    
    public void enterCourse(Course course){
        
    }
    
    public void viewCourse(){
        
    }
    
    public void editCourse(Course course){
        
    }
    
    public Course viewAllCourses(Long id, int year, String semester){
        //@TODO valid return
        return null;
    }
    
    public void generateDesiredSchedule(){
        
    }
    
    public DesiredSchedule viewDesiredSchedule(){
        //@TODO valid return
        return null;
    }
    
    public DesiredSchedule exportDesiredSchedule(){
        //@TODO valid return
        return null;
    }
    
}
