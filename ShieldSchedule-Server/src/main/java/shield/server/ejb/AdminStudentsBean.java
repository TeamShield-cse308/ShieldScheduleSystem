/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.util.List;
import javax.ejb.Stateful;
import shield.server.entities.Student;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.persistence.EntityExistsException;
import shield.server.entities.School;
import shield.server.exceptions.AccountApprovedException;
import shield.server.util.DatabaseConnection;

/**
 *
 * @author Jeffrey Kabot, Phillip Elliot
 */
@Stateful
public class AdminStudentsBean
{

    //Logger
    private static final Logger logger =
            Logger.getLogger("sss.ejb.AdminSchoolsBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    /**
     * Retrieves a list of all Student Accounts in the database.
     *
     * @return The list of student accounts.
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
            logger.log(Level.INFO, "Retrieving all students in DB", students);
        } catch (Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
            throw ex;
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
        } catch (Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
            throw ex;
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
     */
    public void addStudent(String initName,
            String initEmail,
            String initPassword,
            String initSchool) throws NoResultException, EntityExistsException
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
        } catch (EntityExistsException eeex)
        {
            logger.log(Level.WARNING, "Student with email {0} already exists", initEmail);
            throw eeex;
        } catch (Exception ex)
        {
            //something terrible happened
            logger.log(Level.SEVERE, null, ex);
            throw ex;
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
            Student student = query.getSingleResult();
            em.getTransaction().begin();
            if (approved)
            {
                student.approve();
                logger.log(Level.INFO, "Student account {0} approved", student);
                //@TODO send message to student?
            } else
            {
                em.remove(student);
                logger.log(Level.INFO, "Student account {0} deleted", student);
                //@TODO send message to student?
            }
            em.getTransaction().commit();
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No such account with email {0} found in database", email);
            throw nrex;
        } catch (Exception ex)
        {
            //something terrible happened
            logger.log(Level.SEVERE, null, ex);
            throw ex;
        } finally
        {
            //close the Entity Manager
            em.close();
            em = null;
        }
    }
}
