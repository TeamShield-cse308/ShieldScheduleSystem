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
import javax.persistence.NoResultException;
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
             Logger.getLogger(CoursesBean.class.getName());

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    /**
     * Add a new course to those offered by a school.
     *
     * @param identifier The new course unique identifier.
     * @param courseName The new course name.
     * @param schoolName The school to which the course is added.
     * @param year The year the course is valid for
     */
    public void addCourse(String identifier,
            String courseName,
            String schoolName,
            int year)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<School> query =
                 em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", schoolName);
        try
        {
            logger.log(Level.INFO, "Adding new course with identifier {0} at school {1}", new String[] {identifier, schoolName});
            
            School school = query.getSingleResult();
            //add the course
            em.getTransaction().begin();
            school.addCourse(identifier, courseName, year);
            em.getTransaction().commit();
            
            logger.log(Level.INFO, "New course created with identifier {0} at school {1}", new String[] {identifier, schoolName});
        } catch (RollbackException rex)
        {
            //a course at that school that id already exists database
            logger.log(Level.WARNING, "Course with identifier {0} already exists at school {1}", new String[] {identifier, schoolName});
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
            courseList = school.getCourses();
            logger.log(Level.INFO, "Retrieving all courses from school {0} in DB", school);
        } catch(NoResultException nrex)
        {
            logger.log(Level.WARNING, "No school exists with name {0}", schoolName);
            throw nrex;
        }
        finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }
        return courseList;
    }
    
    public List<Course> getSchoolCoursesWithLunch(String schoolName)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<School> query =
                em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", schoolName);
        List<Course> courseList = null;
        try
        {
            School school = query.getSingleResult();
            courseList = new ArrayList<>(school.getCoursesWithLunch());
            logger.log(Level.INFO, "Retrieving all courses and lunches from school {0} in DB", school);
        } catch(NoResultException nrex)
        {
            logger.log(Level.WARNING, "No school exists with name {0}", schoolName);
            throw nrex;
        } finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }
        return courseList;
    }
}
