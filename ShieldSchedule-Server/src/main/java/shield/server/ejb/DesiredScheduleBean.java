/*
 */
package shield.server.ejb;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import shield.server.entities.GenerationCriteria;
import shield.server.entities.Schedule;
import shield.server.entities.Student;
import shield.server.util.DatabaseConnection;

/**
 *
 * @author Jeffrey Kabot
 */
@Stateless
public class DesiredScheduleBean
{

    //Logger
    private static final Logger logger
            = Logger.getLogger("sss.ejb.DesiredScheduleBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    public List<Schedule> generateSchedule(String studentEmail)
    {
        //Create the entity manager and set up the query for the student and his/her friends
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> queryStudent
                = em.createNamedQuery("Student.findByEmail", Student.class);
        queryStudent.setParameter("email", studentEmail);

        TypedQuery<Student> queryFriends
                = em.createNamedQuery("Friendship.getFriends", Student.class);
        queryFriends.setParameter("email", studentEmail);
        
        List<Schedule> acceptableSchedules = null;
        try
        {
            Student student = queryStudent.getSingleResult();
            List<Student> friends = queryFriends.getResultList();
            GenerationCriteria gc = student.getGenerationCriteria();
            acceptableSchedules = gc.generateSchedule(friends);
        } finally
        {
            em.close();
            em = null;
        }
        return acceptableSchedules;
    }
}
