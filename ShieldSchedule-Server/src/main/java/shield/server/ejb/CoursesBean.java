/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import shield.server.entities.Course;
import shield.server.entities.School;
import shield.server.util.DatabaseConnection;

/**
 *
 * @author evanguby
 */
@Stateful
public class CoursesBean
{

    //Logger
    private static final Logger logger =
             Logger.getLogger("sss.ejb.CoursesBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    /**
     * Add a new course to those offered by a school.
     *
     * @param identifier The new course unique identifier.
     * @param courseName The new course name.
     * @param schoolName The school to which the course is added.
     */
    public void addCourse(String identifier,
            String courseName,
            String schoolName)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<School> query =
                 em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", schoolName);
        try
        {
            School school = query.getSingleResult();
            //add the course
            em.getTransaction().begin();
            //@TODO check return of addCourse to see if it worked
            school.addCourse(identifier, courseName);
            em.getTransaction().commit();
            logger.log(Level.INFO, "New course added to database {0}", identifier);
        } catch (RollbackException rex)
        {
            //a course with that id already exists in database
            logger.log(Level.WARNING, "Collision on course ID within database");
            throw rex;
        } finally
        {
            //close the entity manager
            em.close();
            em = null;
        }
    }

    public List<Course> getSchoolCourses(String schoolName)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<School> query =
                em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", schoolName);
        List<Course> courseList = null;
        try
        {
            School school = query.getSingleResult();
            courseList = new ArrayList<>(school.getCourses());
            logger.log(Level.INFO, "Retrieving all courses from school in DB", school);
        } finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }
        return courseList;
    }
}
