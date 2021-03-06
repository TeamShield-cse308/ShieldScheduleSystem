/*
 */
package shield.server.ejb;

import java.util.ArrayList;
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
            Logger.getLogger(DesiredScheduleBean.class.getName());

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    /**
     * Set whether the student desires lunch on each day of the schedule week.
     *
     * @param studentEmail The student in reference
     * @param year
     * @param desiresLunch A boolean array indicating whether lunch is desired
     * on each day
     */
    public void setLunches(String studentEmail,
            int year,
            boolean[] desiresLunch)
    {
        //Create the entity manager and set up the query for the student and his/her friends
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> queryStudent =
                em.createNamedQuery("Student.findByEmail", Student.class);
        queryStudent.setParameter("email", studentEmail);

        try
        {
            logger.log(Level.INFO, "Setting preferred lunch days for student {0} for year {1}", new Object[]
            {
                studentEmail, year
            });
            Student student = queryStudent.getSingleResult();
            GenerationCriteria gc = student.getGenerationCriteria(year);
            gc.setLunches(desiresLunch);
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
     *
     * @param studentEmail
     * @param year
     * @param courseID
     * @param instructor
     * @param excludedSectionIDs
     * @return
     */
    public boolean addCourse(String studentEmail,
            int year,
            long courseID,
            String instructor,
            List<Long> excludedSectionIDs)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> queryStudent =
                em.createNamedQuery("Student.findByEmail", Student.class);
        queryStudent.setParameter("email", studentEmail);

        TypedQuery<Course> queryCourse =
                em.createNamedQuery("Course.findByID", Course.class);
        queryCourse.setParameter("id", courseID);

        TypedQuery<Section> querySections =
                em.createNamedQuery("Section.batchFindByID", Section.class);
        querySections.setParameter("ids", excludedSectionIDs);

        boolean success = false;
        try
        {
            logger.log(Level.INFO, "Adding course to desired schedule for student {0} for year {1}", new Object[]
            {
                studentEmail, year
            });

            Student student = queryStudent.getSingleResult();
            GenerationCriteria gc = student.getGenerationCriteria(year);

            Course course = queryCourse.getSingleResult();
            List<Section> exclusions;
            if (excludedSectionIDs.isEmpty())
            {
                exclusions = new ArrayList<>();
            } else
            {
                exclusions = querySections.getResultList();
            }
            success = gc.addCourse(course, exclusions, instructor);
        } finally
        {
            em.close();
            em = null;
        }
        return success;
    }

    public void removeCourse(String studentEmail,
            int year,
            long courseID)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> queryStudent =
                em.createNamedQuery("Student.findByEmail", Student.class);
        queryStudent.setParameter("email", studentEmail);

        TypedQuery<Course> queryCourse =
                em.createNamedQuery("Course.findByID", Course.class);
        queryCourse.setParameter("id", courseID);

        try
        {
            logger.log(Level.INFO, "Removing course from desired schedule for student {0} for year {1}", new Object[]
            {
                studentEmail, year
            });

            Student student = queryStudent.getSingleResult();
            Course course = queryCourse.getSingleResult();
            GenerationCriteria gc = student.getGenerationCriteria(year);
            gc.removeCourse(course);

        } finally
        {
            em.close();
            em = null;
        }
    }

    public GenerationCriteria getCriteria(String studentEmail,
            int year)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> queryStudent =
                em.createNamedQuery("Student.findByEmail", Student.class);
        queryStudent.setParameter("email", studentEmail);

        GenerationCriteria gc = null;
        try
        {
            logger.log(Level.INFO, "Retrieving generation criteria for student {0} for year {1}", new Object[]
            {
                studentEmail, year
            });

            Student student = queryStudent.getSingleResult();
            gc = student.getGenerationCriteria(year);

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

    public List<Schedule> generateSchedule(String studentEmail,
            int year)
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

        List<Schedule> generatedSchedules = null;

        try
        {
            logger.log(Level.INFO, "Generating desired schedule for student {0} for year {1}", new Object[]
            {
                studentEmail, year
            });

            Student student = queryStudent.getSingleResult();
            List<Student> friends = queryFriends.getResultList();
            GenerationCriteria gc = student.getGenerationCriteria(year);
            generatedSchedules = gc.generateSchedule(friends);
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No such account with email {0} found in database", studentEmail);
            throw nrex;
        } finally
        {
            em.close();
            em = null;
        }
        return generatedSchedules;
    }
}
