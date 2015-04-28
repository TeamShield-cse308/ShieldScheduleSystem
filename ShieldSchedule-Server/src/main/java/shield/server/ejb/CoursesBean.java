/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import shield.server.entities.Course;
import shield.server.entities.School;
import shield.server.util.DatabaseConnection;

/**
 *
 * @author evanguby
 */
@Stateful
public class CoursesBean {
        //Logger
    private static final Logger logger =
            Logger.getLogger("sss.ejb.CoursesBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;
    
    public void addCourse(String courseName, String schoolName, int semester){
        em = DatabaseConnection.getEntityManager();
        Course c = new Course(courseName, semester);
        TypedQuery<School> query =
                em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", schoolName);
        try
        {
            School school = query.getSingleResult();
            //add the school
            em.getTransaction().begin();
            school.addCourse(c);
            em.persist(c);
            em.getTransaction().commit();
            logger.log(Level.INFO, "New course added to database {0}", c);
        } catch (EntityExistsException eeex)
        {
            //a school with that id already exists in database
            logger.log(Level.WARNING, "Collision on course ID within database");
            throw eeex;
        } finally
        {
            //close the entity manager
            em.close();
            em = null;
        }
    }
}
