/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.util.List;
import java.util.Properties;
import javax.ejb.Stateful;
import shield.server.entities.Student;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.persistence.EntityExistsException;
import javax.persistence.RollbackException;
import shield.server.entities.School;
import shield.server.exceptions.AccountApprovedException;
import shield.server.util.DatabaseConnection;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Provides functionality to add, approve, and delete students. Exposes a list
 * of approved and pending student accounts.
 *
 * @author Jeffrey Kabot, Phillip Elliot
 */
@Stateful
public class AdminStudentsBean
{

    //Logger
    private static final Logger logger =
            Logger.getLogger(AdminStudentsBean.class.getName());

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    /**
     * Retrieves a list of approved students in the database.
     *
     * @return The list of approved student accounts.
     */
    public List<Student> getApprovedStudents()
    {
        List<Student> students = null;

        //Create the entity manager and set up the query for all students
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> query =
                em.createNamedQuery("Student.findAllApproved", Student.class);

        try
        {
            students = query.getResultList();
            logger.log(Level.INFO, "Retrieving all approved students in DB", students);
        } finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }

        return students;
    }

    /**
     * Retrieves a list of all pending student accounts. Pending accounts are
     * those which have been requested but not yet approved.
     *
     * @return The list of pending accounts.
     */
    public List<Student> getPendingStudents()
    {
        List<Student> pendingStudents = null;

        //Create the Entity Manager and set up the query for pending students
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> query =
                em.createNamedQuery("Student.findAllPending", Student.class);
        try
        {
            pendingStudents = query.getResultList();
            logger.log(Level.INFO, "Retrieving all pending student account requests");
        } finally
        {
            //Close the Entity Manager
            em.close();
            em = null;
        }

        return pendingStudents;
    }

    /**
     * Insert a new student account into the database.
     *
     * @param initName The student's name.
     * @param initEmail The email associated with the account.
     * @param initPassword The account password.
     * @param initSchool The school the student attends.
     * @throws NoResultException when there is school with the name supplied.
     * @throws RollbackException when a student with that email already exists.
     */
    public void addStudent(String initName,
            String initEmail,
            String initPassword,
            String initSchool) throws NoResultException, RollbackException
    {
        //Create the Entity Manager and set up the query for school
        em = DatabaseConnection.getEntityManager();
        TypedQuery<School> query =
                em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", initSchool);

        try
        {
            //find the appropriate school
            School school = query.getSingleResult();
            Student student = new Student(initName, initEmail, initPassword, school);
            em.getTransaction().begin();
            em.persist(student); //insert the student
            em.getTransaction().commit();
            logger.log(Level.INFO, "New student inserted to database {0}", student);
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No school found with name {0}", initSchool);
            throw nrex;
        } catch (RollbackException rex)
        {
            logger.log(Level.WARNING, "Student account with email {0} already exists", initEmail);
            throw rex;
        } finally
        {
            //close the Entity Manager
            em.close();
            em = null;
        }
    }

    /**
     * Allows a student account to be approved or denied. Student accounts are
     * addressed by email.
     *
     * @param email The ID of the student account
     * @param approved true if the student is approved, false if deleted
     * @throws AccountApprovedException when an account has already been
     * approved
     * @throws NoResultException when no student by that email exists.
     */
    public void approveStudent(String email,
            boolean approved) throws AccountApprovedException, NoResultException
    {
        //Create the Entity Manager and set up the query for the student
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> query =
                em.createNamedQuery("Student.findByEmail", Student.class);
        query.setParameter("email", email);

        try
        {
            //query the email and approve or delete the student
            logger.log(Level.INFO, "Retrieving student with email {0}", email);
            Student student = query.getSingleResult();
            em.getTransaction().begin();
            if (approved)
            {
                student.approve();
                logger.log(Level.INFO, "Student account {0} approved", student);
                //@TODO send email message to student?
                
                final String from = "ShieldEmailService@gmail.com";
                final String pass = "shield123";
                String host = "localhost";
                
                Properties props = new Properties();
                
                
                props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
                Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, pass);
			}
		  });
                
                try{
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(email));
                    message.setSubject("Account has been approved!");
                    message.setText("Dear " + email + ","
				+ "\n\n Your account has been succesfully approved by our admin."
                            + " You may now log in at any time!");
 
			Transport.send(message);
                    
                    System.out.println("Sent message successfully....");
                    
                }catch(MessagingException mex){
                    mex.printStackTrace();
                }
     
                
            } else
            {
                em.remove(student);
                logger.log(Level.INFO, "Student account {0} deleted", student);
                //@TODO send email message to student?
                final String from = "ShieldEmailService@gmail.com";
                final String pass = "shield123";
                String host = "localhost";
                
                Properties props = new Properties();
                
                
                props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
                Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, pass);
			}
		  });
                
                try{
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(email));
                    message.setSubject("Account has been deleted.");
                    message.setText("Dear " + email + ","
				+ "\n\n Your account has been deleted by our admin."
                            + " You may no longer log in, feel free to register again!");
 
			Transport.send(message);
                    
                    System.out.println("Sent message successfully....");
                    
                }catch(MessagingException mex){
                    mex.printStackTrace();
                }
            }
            em.getTransaction().commit();
        } catch (AccountApprovedException aae)
        {
            logger.log(Level.WARNING, "Student account {0} is already approved!", email);
            throw aae;
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No account found with email {0}", email);
            throw nrex;
        } finally
        {
            //close the Entity Manager
            em.close();
            em = null;
        }
    }
}
