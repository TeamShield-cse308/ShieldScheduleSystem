/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sss.ejb;

import java.util.List;
import javax.ejb.Stateful;
import sss.entities.Student;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;
import java.util.logging.Level;
import sss.entities.School;

/**
 *
 * @author Jeffrey Kabot
 */
@Stateful
public class AdminStudentsBean
{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    //Logger
    private static final Logger logger = Logger.getLogger("sss.ejb.AdminSchoolsBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    public void addStudent(String initName, String email, School school)
    {
        
    }
    public void deleteStudent(String email){
        
        TypedQuery<Student> query = em.createNamedQuery("Student.findByEMail", Student.class);
        
         try {
            Student stu = query.setParameter("Email", email).getSingleResult();
            em.getTransaction().begin();
            em.remove(stu);
            em.getTransaction().commit();

            //Logging
            logger.log(Level.INFO, "Student removed from database", stu);
        } catch (NoResultException noex) {
            //Logging
            logger.log(Level.WARNING, "No students found for removal that match db query", email);
            //@TODO no such school
        } catch (Exception ex) {
            //@TODO generic catch
        }
        
    }
}
