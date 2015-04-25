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
import shield.server.entities.Friendship;
import shield.server.entities.School;
import shield.server.entities.Student;
import shield.server.util.DatabaseConnection;

/**
 * Provides functionality for retrieving, creating, and responding to friend
 * requests as well as retrieving a list of friends for a particular student,
 * and deleting particular friends.
 *
 * @author Jeffrey Kabot
 */
@Stateless
public class StudentFriendsBean
{

    //Logger
    private static final Logger logger
            = Logger.getLogger("sss.ejb.StudentFriendsBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    /**
     * Retrieves the list of friends for a particular student.
     *
     * @param email the email of the student whose friends to retrieve.
     * @return the list of friends.
     */
    public List<Student> getAllFriends(String email)
    {
        em = DatabaseConnection.getEntityManager();
        
        TypedQuery<Student> query
                = em.createNamedQuery("Friendship.getFriends", Student.class);
        query.setParameter("email", email);

        logger.log(Level.INFO, "Retrieving list of friends for student {0}",
                email);

        List<Student> friendsList = null;
        try
        {
            friendsList = query.getResultList();
        } catch (Exception ex)
        {
            //@TODO catch more exceptions
            logger.log(Level.WARNING, "Terrible error occurred", ex);
            throw ex;
        } finally
        {
            em.close();
            em = null;
        }
        return friendsList;
    }

    /**
     * Get the incoming friend requests for a particular student.
     *
     * @param recipientEmail the email of the student with incoming friend
     * requests.
     * @return the list of friend requests.
     */
    public List<Friendship> getFriendRequests(String recipientEmail)
    {
        List<Friendship> friendRequests = null;

        //set up the entity manager and the query
        em = DatabaseConnection.getEntityManager();

        TypedQuery<Friendship> query
                = em.createNamedQuery("Friendship.getFriendRequests",
                        Friendship.class);
        query.setParameter("recipient", recipientEmail);

        logger.log(Level.INFO, "Retrieving friend requests for student {0}",
                recipientEmail);

        try
        {
            //get the list of friend requests
            friendRequests = query.getResultList();
            //@TODO catch exceptions
        } finally
        {
            //close the entity manager
            em.close();
            em = null;
        }

        return friendRequests;
    }

    /**
     * Create a new friend request associating the student sending it and the
     * student receiving it.
     *
     * @param senderEmail The student sending the friend request
     * @param recipientName The student receiving the friend request
     * @throws NoResultException When the student sending the request name does
     * not exist.
     */
    public void addFriend(String senderEmail,
            String recipientName) throws NoResultException
    {
        //set up the entity manager and the queries
        em = DatabaseConnection.getEntityManager();

        TypedQuery<Student> senderQuery
                = em.createNamedQuery("Student.findByEmail", Student.class);
        senderQuery.setParameter("email", senderEmail);
        TypedQuery<Student> recipientQuery
                = em.createNamedQuery("Student.findByNameAndSchool",
                        Student.class);
        recipientQuery.setParameter("name", recipientName);

        Student sender, recipient;
        School school;
        try
        {
            //get the sender and the sender's school
            sender = senderQuery.getSingleResult();
            school = sender.getSchool();

            //check the sender's school for the supplied student name
            recipientQuery.setParameter("school", school.getSchoolName());
            List<Student> recipients = recipientQuery.getResultList();

            //if there is just one match
            if (recipients.size() == 1)
            {
                recipient = recipients.get(0);
                Friendship f = new Friendship(sender, recipient);

                em.getTransaction().begin();
                em.persist(f);
                em.getTransaction().commit();

                logger.log(Level.INFO, "New friend request created {0}", f);
            } else
            {
                //@TODO when there is more than one matching student
            }
        } catch (NoResultException nrex)
        {
            //@TODO error handling
            logger.log(Level.WARNING,
                    "No student found with name {0} in School {1}", senderEmail);
            throw nrex;
        } finally
        {
            // close the entity manager
            em.close();
            em = null;
        }
    }

    /**
     * Accept or deny a received friend request.
     *
     * @param senderEmail the student who sent the friend request.
     * @param recipientEmail the student who received the friend request.
     * @param approved true if the friendship is approved, false if denied
     */
    public void approveFriend(String senderEmail,
            String recipientEmail, boolean approved)
    {
        //set up the entity manager and the query
        em = DatabaseConnection.getEntityManager();

        TypedQuery<Friendship> query
                = em.createNamedQuery("Friendship.findBySenderAndRecipient",
                        Friendship.class);
        query.setParameter("sender", senderEmail);
        query.setParameter("recipient", recipientEmail);

        try
        {
            //get the friendship
            Friendship f = query.getSingleResult();

            //approve or deny it
            em.getTransaction().begin();
            if (approved)
            {
                //accepting a friendship request automatically creates a new friendship in the opposite direction
                //this ensures symmetry on the friendship graph
                //for approved friendships, both students are the senders and recipieents
                f.approve();
                Friendship f_inverse = new Friendship(f.getRecipient(),
                        f.getSender());
                f_inverse.approve();
                logger.log(Level.INFO, "Friendship request {0} accepted", f);
            } else
            {
                em.remove(f);
                logger.log(Level.INFO, "Friendship request {0} deleted", f);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            //@TODO error handling
        } finally
        {
            //close the entity manager
            em.close();
            em = null;
        }
    }
}
