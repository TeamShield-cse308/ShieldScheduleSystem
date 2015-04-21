/*

 */
package shield.server.ejb;

import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import shield.server.entities.Student;
import shield.server.util.DatabaseConnection;

/**
 *
 * @author Jeffrey Kabot
 */
@Stateless
public class StudentFriendsBean
{

    //Logger
    private static final Logger logger =
            Logger.getLogger("sss.ejb.StudentFriendsBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    public List<Student> getAllFriends(String email)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> query =
                em.createNamedQuery("Student.findByEmail", Student.class);
        query.setParameter("email", email);

        List<Student> friendsList;
        try
        {
            Student student = query.getSingleResult();
            friendsList = student.getFriendsList();
        } finally
        {
            em.close();
            em = null;
        }
        return friendsList;
    }
}
