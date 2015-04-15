/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.util.List;
import javax.ejb.Stateful;
import shield.server.entities.Student;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;
import java.util.logging.Level;
import shield.server.entities.School;

/**
 *
 * @author Jeffrey Kabot, Phillip Elliot
 */
@Stateful
public class AdminStudentsBean
{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    //Logger
    private static final Logger logger = 
            Logger.getLogger("sss.ejb.AdminSchoolsBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;
    
    public List<Student> getAllStudents()
    {
        TypedQuery<Student> query = 
                em.createNamedQuery("Student.findAll", Student.class);
        
        //@TODO needs try/catch?
        List<Student> students = query.getResultList();
        
        //@TODO logging
        
        return students;
    }

    /**
     * Retrieves a list of all pending student accounts.
     * Pending accounts are those which have been requested but not yet approved.
     * @return 
     */
    public List<Student> getPendingStudents()
    {
        TypedQuery<Student> query =
                em.createNamedQuery("Student.findAllPending", Student.class);
        
        //@TODO needs try catch?
        List<Student> pendingStudents = query.getResultList();
        
        //@TODO logging
        
        return pendingStudents;
    }
    
    /**
     * Allows a student account to be approved or denied.
     * Student accounts are addressed by email.
     * @param email
     * @param approved true if the student is approved, false if otherwise
     */
    public void approveStudent(String email, boolean approved)
    {

        if (approved) {
            TypedQuery<Student> query =
                    em.createNamedQuery("Student.findByName", Student.class);
            query.setParameter("email", email);
            Student student = query.getSingleResult(); //@TODO error handling?
            student.approve();
//            em.getTransaction().begin();
            em.refresh(student); //update the student account status
//            em.getTransaction().commit();
            //@TODO send message to student
        }
        else {
            deleteStudent(email);
            //@TODO send message to student
        }
    }
    
    /**
     * Deletes a student account from the database.
     * @param email 
     */
    public void deleteStudent(String email){
        
        TypedQuery<Student> query = 
                em.createNamedQuery("Student.findByEMail", Student.class);
        
         try {
            Student student = query.setParameter("email", email).getSingleResult();
//            em.getTransaction().begin();
            em.remove(student);
//            em.getTransaction().commit();

            //Logging
            logger.log(Level.INFO, "Student removed from database", student);
        } catch (NoResultException noex) {
            //Logging
            logger.log(Level.WARNING, "No students found for removal that match db query", email);
            //@TODO no such school
        } catch (Exception ex) {
            //@TODO generic catch
        }
        
    }
}
