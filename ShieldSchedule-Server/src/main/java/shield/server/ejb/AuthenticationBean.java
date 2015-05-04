/*

 */
package shield.server.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import shield.server.entities.Administrator;
import shield.server.entities.GenericUser;
import shield.server.entities.Student;
import shield.server.exceptions.AccountActiveException;
import shield.server.exceptions.AccountPendingException;
import shield.server.exceptions.WrongPasswordException;
import shield.server.util.DatabaseConnection;

/**
 * Provides functionality for validating user log-in credentials
 *
 * @author Jeffrey Kabot
 */
@Stateless
public class AuthenticationBean
{

    //Logger
    private static final Logger logger =
            Logger.getLogger(AuthenticationBean.class.getName());

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    /**
     * Checks validity of the supplied log-in credentials.
     * 
     * @param username The username for the account
     * @param password The password for the account
     * @return The account matching the supplied credentials.
     * @throws AccountActiveException When the Student account is already logged in somewhere.
     * @throws AccountPendingException When the Student account is not yet approved.
     * @throws WrongPasswordException When the supplied password does not match the one on file.
     * @throws NoResultException When there is no account with the supplied username.
     */
    public GenericUser authenticate(String username,
            String password)
            throws AccountActiveException,
            AccountPendingException,
            WrongPasswordException,
            NoResultException
    {
        //Create the entity manager
        em = DatabaseConnection.getEntityManager();
        logger.log(Level.INFO, "User attempts log in with name {0}", username);

        try
        {
            if (username.indexOf('@') != -1) //Student users log in using their email
            {
                //set up the query
                TypedQuery<Student> query =
                        em.createNamedQuery("Student.findByEmail", Student.class);
                query.setParameter("email", username);

                //find the right account with that email
                Student student = query.getSingleResult();

                if (student.getPassword().equals(password))
                {
                    student.activate();
                    logger.log(Level.INFO, "Student {0} logged in", username);
                    return student;
                } else
                {
                    logger.log(Level.WARNING, "Incorrect password for Student {0}", username);
                    throw new WrongPasswordException();
                }
            } else //Administrators don't have @ symbol in their username
            {
                //set up the query
                TypedQuery<Administrator> query =
                        em.createNamedQuery("Administrator.findByUsername", Administrator.class);
                query.setParameter("username", username);

                //find the admin with that username
                Administrator admin = query.getSingleResult();

                if (admin.getPassword().equals(password))
                {
                    logger.log(Level.INFO, "Admin {0} logged in", username);
                    return admin;
                } else
                {
                    logger.log(Level.WARNING, "Incorrect password for Admin {0}", username);
                    throw new WrongPasswordException();
                }
            }
        } catch (NoResultException nrex)
        {
            //no matching account
            logger.log(Level.WARNING, "No such account with username {0} found in database", username);
            throw nrex;
        } finally
        {
            //close the entity manager
            em.close();
            em = null;
        }
    }
}
