/*

 */
package shield.server.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
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
 * Enterprise Bean for validating user log in credentials
 * @author Jeffrey Kabot
 */
@Stateless
public class AuthenticationBean
{

    //Logger
    private static final Logger logger =
            Logger.getLogger("sss.ejb.AuthenticationBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

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

        //Student users log in using their email
        try
        {
            if (username.indexOf('@') != -1)
            {
                //set up the query
                TypedQuery<Student> query =
                        em.createNamedQuery("Student.findByEmail", Student.class);
                query.setParameter("email", username);

                Student student = query.getSingleResult();

                //if correct password
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
                //setup the query
                TypedQuery<Administrator> query =
                        em.createNamedQuery("Administrator.findByUserame", Administrator.class);
                query.setParameter("username", username);

                Administrator admin = query.getSingleResult();

                //if correct password
                if (admin.getPassword().equals(password))
                {
                    //@TODO successfully log in
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
            logger.log(Level.WARNING, "No such account with username {0} found in database", username);
            throw nrex;
        } catch (Exception ex)
        {
            //something terrible happened
            logger.log(Level.SEVERE, null, ex);
            throw ex;
        } finally
        {
            //close the entity manager
            em.close();
            em = null;
        }
    }
}
