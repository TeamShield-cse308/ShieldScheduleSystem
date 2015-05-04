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
    private static final Logger logger =
            Logger.getLogger(StudentFriendsBean.class.getName());

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

        TypedQuery<Student> query =
                em.createNamedQuery("Friendship.getFriends", Student.class);
        query.setParameter("email", email);

        logger.log(Level.INFO, "Retrieving friends for student {0}",
                email);

        List<Student> friendsList = null;
        try
        {
            friendsList = query.getResultList();
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

        TypedQuery<Friendship> query =
                em.createNamedQuery("Friendship.getFriendRequests",
                        Friendship.class);
        query.setParameter("email", recipientEmail);

        logger.log(Level.INFO, "Retrieving friend requests for student {0}",
                recipientEmail);

        try
        {
            //get the list of friend requests
            friendRequests = query.getResultList();
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
    public List<Student> addFriendByName(String senderEmail,
            String recipientName) throws NoResultException
    {
        //set up the entity manager and the queries
        em = DatabaseConnection.getEntityManager();

        TypedQuery<Student> senderQuery =
                em.createNamedQuery("Student.findByEmail", Student.class);
        senderQuery.setParameter("email", senderEmail);
        TypedQuery<Student> recipientQuery =
                em.createNamedQuery("Student.findByNameAndSchool",
                        Student.class);
        recipientQuery.setParameter("name", recipientName);

        Student sender, recipient;
        School school;

        List<Student> recipients = null;
        try
        {
            logger.log(Level.INFO, "Student {0} requests friendship for {1}", new String[]
            {
                senderEmail, recipientName
            });

            //get the sender and the sender's school
            sender = senderQuery.getSingleResult();
            school = sender.getSchool();

            //check the sender's school for the supplied student name
            recipientQuery.setParameter("school", school.getSchoolName());
            recipients = recipientQuery.getResultList();

            if (recipients.isEmpty())
            {
                throw new NoResultException();
            }
            if (recipients.size() == 1)
            {
                recipient = recipients.get(0);
                Friendship f = new Friendship(sender, recipient);

                logger.log(Level.INFO, "Single result for name {0}", recipientName);

                em.getTransaction().begin();
                em.persist(f);
                em.getTransaction().commit();

                logger.log(Level.INFO, "New friendship created {0}", f);
            } else
            {
                logger.log(Level.INFO, "Multiple results for name {0}", recipientName);
            }
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING,
                    "No student found with name {0} in same school as {1}", new String[]
                    {
                        recipientName, senderEmail
                    });
            throw nrex;

        } finally
        {
            // close the entity manager
            em.close();
            em = null;
        }
        return recipients;
    }

    /**
     * Create a new friend request associating the student sending it and the
     * student receiving it.
     *
     * @param senderEmail The student sending the friend request
     * @param recipientEmail The student receiving the friend request
     * @throws NoResultException When the student sending the request name does
     * not exist.
     */
    public void addFriendByEmail(String senderEmail,
            String recipientEmail) throws NoResultException
    {
        //set up the entity manager and the queries
        em = DatabaseConnection.getEntityManager();

        TypedQuery<Student> senderQuery =
                em.createNamedQuery("Student.findByEmail", Student.class);
        senderQuery.setParameter("email", senderEmail);
        TypedQuery<Student> recipientQuery =
                em.createNamedQuery("Student.findByEmail",
                        Student.class);
        recipientQuery.setParameter("email", recipientEmail);

        Student sender, recipient;

        try
        {
            logger.log(Level.INFO, "Student {0} requests friendship for {1}", new String[]
            {
                senderEmail, recipientEmail
            });

            //get the sender and receiver
            sender = senderQuery.getSingleResult();
            recipient = recipientQuery.getSingleResult();

            Friendship f = new Friendship(sender, recipient);

            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();

            logger.log(Level.INFO, "New friendship created {0}", f);
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING,
                    "Not both students exist found with email {0}, {1}", new String[]
                    {
                        senderEmail, recipientEmail
                    });
            throw nrex;

        } finally
        {
            // close the entity manager
            em.close();
            em = null;
        }
    }

    /**
     * Approve or delete a friendship.
     *
     * @param senderEmail the student who sent the friend request.
     * @param recipientEmail the student who received the friend request.
     * @param approved true if the friendship is approved, false if denied
     */
    public void approveFriend(String senderEmail,
            String recipientEmail,
            boolean approved)
    {
        //set up the entity manager and the query
        em = DatabaseConnection.getEntityManager();

        TypedQuery<Friendship> query =
                em.createNamedQuery("Friendship.findBySenderAndRecipient",
                        Friendship.class);
        query.setParameter("sender", senderEmail);
        query.setParameter("recipient", recipientEmail);

        try
        {
            //get the friendship
            logger.log(Level.INFO, "Modification on friendship {0}<->{1}", new String[]
            {
                senderEmail, recipientEmail
            });
            Friendship f = query.getSingleResult();

            //approve or delete it
            em.getTransaction().begin();
            if (approved)
            {
                f.approve();
                logger.log(Level.INFO, "Friendship request {0} approved", f);
            } else
            {
                em.remove(f);
                logger.log(Level.INFO, "Friendship {0} deleted", f);
            }
            em.getTransaction().commit();
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No friendship found with sender {0} and recipient {1}", new String[]
            {
                senderEmail, recipientEmail
            });
            throw nrex;
        } finally
        {
            //close the entity manager
            em.close();
            em = null;
        }
    }
}
