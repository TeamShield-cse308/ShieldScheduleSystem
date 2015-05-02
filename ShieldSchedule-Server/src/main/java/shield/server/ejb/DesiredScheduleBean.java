/*
 */
package shield.server.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import shield.server.entities.Course;
import shield.server.entities.GenerationCriteria;
import shield.server.entities.Schedule;
import shield.server.entities.Section;
import shield.server.entities.Student;
import shield.server.util.DatabaseConnection;

/**
 * Enterprise JavaBean handling desired schedule business logic.
 *
 * @author Jeffrey Kabot
 */
@Stateless
public class DesiredScheduleBean
{

    //Logger
    private static final Logger logger =
            Logger.getLogger("sss.ejb.DesiredScheduleBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    /**
     * Set whether the student desires lunch on each day of the schedule week.
     *
     * @param studentEmail The student in reference
     * @param desiresLunch A boolean array indicating whether lunch is desired
     * on each day
     */
    public void setLunches(String studentEmail,
            boolean[] desiresLunch)
    {
        //Create the entity manager and set up the query for the student and his/her friends
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> queryStudent =
                em.createNamedQuery("Student.findByEmail", Student.class);
        queryStudent.setParameter("email", studentEmail);

        try
        {
            Student student = queryStudent.getSingleResult();
            GenerationCriteria gc = student.getGenerationCriteria();
            gc.setLunches(desiresLunch);
            //@TODO catch exceptions
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No such account with email {0} found in database", studentEmail);
            throw nrex;
        } finally
        {
            em.close();
            em = null;
        }
    }

    /**
     * Add a course to the schedule generation criteria.
     *
     * @param studentEmail
     * @param course
     * @param instructor
     * @param exclusions
     * @return True if the course could be added, false if otherwise (i.e., the
     * course is already among the generation criteria)
     */
    public boolean addCourse(String studentEmail,
            Course course,
            String instructor,
            List<Section> exclusions)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> queryStudent =
                em.createNamedQuery("Student.findByEmail", Student.class);
        queryStudent.setParameter("email", studentEmail);

        boolean success = false;
        try
        {
            Student student = queryStudent.getSingleResult();
            GenerationCriteria gc = student.getGenerationCriteria();
            success = gc.addCourse(course, exclusions, instructor);
            //@TODO catch exceptions
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No such account with email {0} found in database", studentEmail);
            throw nrex;
        } finally
        {
            em.close();
            em = null;
        }
        return success;
    }

    public void removeCourse(String studentEmail,
            Course course)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> queryStudent =
                em.createNamedQuery("Student.findByEmail", Student.class);
        queryStudent.setParameter("email", studentEmail);

        try
        {
            Student student = queryStudent.getSingleResult();
            GenerationCriteria gc = student.getGenerationCriteria();
            gc.removeCourse(course);
            //@TODO catch exceptions
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No such account with email {0} found in database", studentEmail);
            throw nrex;
        } finally
        {
            em.close();
            em = null;
        }
    }

    public GenerationCriteria getCriteria(String studentEmail)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> queryStudent =
                em.createNamedQuery("Student.findByEmail", Student.class);
        queryStudent.setParameter("email", studentEmail);

        GenerationCriteria gc = null;
        try
        {
            Student student = queryStudent.getSingleResult();
            gc = student.getGenerationCriteria();
            //@TODO catch exceptions
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No such account with email {0} found in database", studentEmail);
            throw nrex;
        } finally
        {
            em.close();
            em = null;
        }
        return gc;
    }

    public List<Schedule> generateSchedule(String studentEmail)
    {
        //Create the entity manager and set up the query for the student and his/her friends
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> queryStudent =
                em.createNamedQuery("Student.findByEmail", Student.class
                );
        queryStudent.setParameter(
                "email", studentEmail);

        TypedQuery<Student> queryFriends =
                em.createNamedQuery("Friendship.getFriends", Student.class);

        queryFriends.setParameter(
                "email", studentEmail);

        List<Schedule> acceptableSchedules = null;

        try
        {
            Student student = queryStudent.getSingleResult();
            List<Student> friends = queryFriends.getResultList();
            GenerationCriteria gc = student.getGenerationCriteria();
            acceptableSchedules = gc.generateSchedule(friends);
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No such account with email {0} found in database", studentEmail);
            throw nrex;
        } finally
        {
            em.close();
            em = null;
        }
        return acceptableSchedules;
    }
}
