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
import shield.server.entities.FriendRequest;
import shield.server.entities.School;
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

    /**
     * Get the incoming friend requests for a particular student.
     *
     * @param recipientEmail
     * @return
     * @throws NoResultException
     */
    public List<FriendRequest> getFriendRequests(String recipientEmail) throws
            NoResultException
    {
        List<FriendRequest> friendRequests = null;

        //set up the entity manager and the query
        em = DatabaseConnection.getEntityManager();

        TypedQuery<FriendRequest> query =
                em.createNamedQuery("FriendRequest.findByRecpient", FriendRequest.class);
        query.setParameter("recipient", recipientEmail);

        try
        {
            //get the list of friend requests
            friendRequests = query.getResultList();
            logger.log(Level.INFO, "Retrieving friend requests for student {0}", recipientEmail);
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No friend requests for recipient {0}", recipientEmail);
            throw nrex;
        } finally
        {
            em.close();
            em = null;
        }

        return friendRequests;
    }

    /**
     * Accept a received friend request, adding the students involved to each
     * other's friends lists.
     *
     * @param senderEmail
     * @param recipientEmail
     */
    public void acceptFriendRequest(String senderEmail,
            String recipientEmail)
    {
        //set up the entity manager and the queries
        em = DatabaseConnection.getEntityManager();

        TypedQuery<FriendRequest> query =
                em.createNamedQuery("FriendRequest.findBySenderAndRecpient", FriendRequest.class);
        query.setParameter("sender", senderEmail);
        query.setParameter("recipient", recipientEmail);

        try
        {
            FriendRequest fr = query.getSingleResult();
            Student sender = fr.getSender();
            Student recipient = fr.getRecipient();
            
            em.getTransaction().begin();
            sender.addFriend(recipient);
            recipient.addFriend(sender);
            em.remove(fr);
            em.getTransaction().commit();
            
            logger.log(Level.INFO, "Friend Request {0} accepted", fr);
        } catch (Exception ex)
        {
            //@TODO error handling
        } finally
        {
            em.close();
            em = null;
        }

    }

    /**
     * Create a new friend request associating the student sending it and the
     * student receiving it.
     *
     * @param senderEmail
     * @param recipientName
     * @throws NoResultException
     */
    public void createFriendRequest(String senderEmail,
            String recipientName) throws NoResultException
    {
        //set up the entity manager and the queries
        em = DatabaseConnection.getEntityManager();

        TypedQuery<Student> senderQuery =
                em.createNamedQuery("Student.findByEmail", Student.class);
        senderQuery.setParameter("email", senderEmail);
        TypedQuery<Student> recipientQuery =
                em.createNamedQuery("Student.findByNameAndSchool", Student.class);
        recipientQuery.setParameter("name", recipientName);

        Student sender, recipient;
        School school;
        try
        {
            sender = senderQuery.getSingleResult();
            school = sender.getSchool();
            recipientQuery.setParameter("school", school.getSchoolName());
            List<Student> recipients = recipientQuery.getResultList();

            //if there is just one match
            if (recipients.size() == 1)
            {
                recipient = recipients.get(0);
                FriendRequest fr = new FriendRequest(sender, recipient);
                
                em.getTransaction().begin();
                em.persist(fr);
                em.getTransaction().commit();
                
                logger.log(Level.INFO, "New friend request created {0}", fr);
            } else
            {
                //@TODO return to client
            }
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No student found with name {0} in School {1}", recipientName);
            throw nrex;
        } finally
        {
            // close the entity manager
            em.close();
            em = null;
        }
    }
}
