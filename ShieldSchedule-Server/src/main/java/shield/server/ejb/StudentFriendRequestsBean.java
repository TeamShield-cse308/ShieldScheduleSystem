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
 * Provides functionality for retrieving, creating, approving, and denying
 * friend requests.
 *
 * @author Jeffrey Kabot
 */
@Stateless
public class StudentFriendRequestsBean
{

    //Logger
    private static final Logger logger =
            Logger.getLogger("sss.ejb.StudentFriendRequestsBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    /**
     * Get the incoming friend requests for a particular student.
     *
     * @param recipientEmail the email of the student with incoming friend requests.
     * @return the list of friend requests.
     */
    public List<FriendRequest> getFriendRequests(String recipientEmail)
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
        } finally
        {
            //close the entity manager
            em.close();
            em = null;
        }

        return friendRequests;
    }

    /**
     * Accept a received friend request, adding the students involved to each
     * other's friends lists.
     *
     * @param senderEmail the student who sent the friend request.
     * @param recipientEmail the student who received the friend request.
     */
    public void acceptFriendRequest(String senderEmail,
            String recipientEmail)
    {
        //set up the entity manager and the query
        em = DatabaseConnection.getEntityManager();

        TypedQuery<FriendRequest> query =
                em.createNamedQuery("FriendRequest.findBySenderAndRecipient", FriendRequest.class);
        query.setParameter("sender", senderEmail);
        query.setParameter("recipient", recipientEmail);

        try
        {
            //get the sender and recipient
            FriendRequest fr = query.getSingleResult();
            Student sender = fr.getSender();
            Student recipient = fr.getRecipient();

            //add each student to each other's friend's lists
            //and delete the friend request
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
            //close the entity manager
            em.close();
            em = null;
        }

    }

    /**
     * Create a new friend request associating the student sending it and the
     * student receiving it.
     *
     * @param senderEmail The student sending the friend request
     * @param recipientName the student receiving the friend request
     * @throws NoResultException When there is no student with the supplied name.
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
                FriendRequest fr = new FriendRequest(sender, recipient);

                em.getTransaction().begin();
                em.persist(fr);
                em.getTransaction().commit();

                logger.log(Level.INFO, "New friend request created {0}", fr);
            } else
            {
                //@TODO when there is more than one matching student
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
