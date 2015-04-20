/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.ejb.Stateful;
import shield.server.entities.Student;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.persistence.Query;
import shield.server.entities.School;
import shield.server.entities.StudentAccountState;
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
    public List<Student> getAllStudents()
    {
        List<Student> students = null;

        //Create the entity manager and set up the query for all students
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> query =
                em.createNamedQuery("Student.findAll", Student.class);

        try {
            students = query.getResultList();
            logger.log(Level.INFO, "Retrieving all students in DB", students);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            //@TODO logging
        } finally {
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
        try {
            pendingStudents = query.getResultList();
            logger.log(Level.INFO, "Retrieving all pending student account requests");
        } catch (Exception ex) {
            //@TODO logging
        } finally {
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
            String initSchool)
    {
        //Create the Entity Manager and set up the query for school
        em = DatabaseConnection.getEntityManager();
        TypedQuery<School> query =
                em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", initSchool);

        try {
            //find the appropriate school
            School school = query.getSingleResult();
            Student student = new Student(initName, initEmail, initPassword, school);
            em.getTransaction().begin();
            em.persist(student); //insert the student
            em.getTransaction().commit();
            logger.log(Level.INFO, "New student inserted to database {0}", student);
        } catch (NoResultException noex) {
            logger.log(Level.WARNING, "No school found with name {0}", initSchool);
            //@TODO more errors?
            //@TODO message back to client
        } finally {
            //close the Entity Manager
            em.close();
            em = null;
        }
//        School schoolE = query.getSingleResult();
//        long id = schoolE.getID();
//        try {
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//        } catch (Exception ex) {
//            logger.log(Level.SEVERE, null, ex);
//        }
//        Student st = new Student(initName, password, email, schoolE);

//        Connection conn = null;
//        Statement stmt = null;
//
//        try {
//            conn = DriverManager.getConnection("jdbc:mysql://mysql2.cs.stonybrook.edu:3306/eguby", "eguby", "108555202");
//
//            stmt = conn.createStatement();
//            String sql = "INSERT INTO Student (email,password,SchoolID,State, name) VALUES (\'" + email + "\', \'" + password + "\', " +
//                    id + ",\'" + StudentAccountState.PENDING + "\',\'" + initName + "\')";
//            logger.log(Level.INFO, sql, st);
//            stmt.executeUpdate(sql);
//        } catch (SQLException ex) {
//            logger.log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * Allows a student account to be approved or denied. Student accounts are
     * addressed by email.
     *
     * @param email The ID of the student account
     * @param approved true if the student is approved, false if deleted
     */
    public void approveStudent(String email,
            boolean approved)
    {
        //Create the Entity Manager and set up the query for the student
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> query =
                em.createNamedQuery("Student.findByEmail", Student.class);
        query.setParameter("email", email);

        try {
            //query the email and approve or delete the student
            Student student = query.getSingleResult();
            em.getTransaction().begin();
            if (approved) {
                student.approve();
                logger.log(Level.INFO, "Student account {0} approved", student);
                //@TODO send message to student?
            } else {
                em.remove(student);
                logger.log(Level.INFO, "Student account {0} deleted", student);
                //@TODO send message to student?
            }
            em.getTransaction().commit();
        } catch (NoResultException noex) {
            logger.log(Level.WARNING, "No such account with email {0} found in database", email);
        } finally {
            //close the Entity Manager
            em.close();
            em = null;
        }
//        if (approved) {
//
//            Student student = query.getSingleResult(); //@TODO error handling?
//            student.approve();
////            em.getTransaction().begin();
//            em.refresh(student); //update the student account status
////            em.getTransaction().commit();
//            //@TODO send message to student
//        } else {
//            deleteStudent(email);
//            //@TODO send message to student
//        }
    }

    //
    /**
     * Deletes a student account from the database.
     *
     * @param email
     * @deprecated Use {@link approveStudent(String, boolean)} instead
     */
    @Deprecated
    public void deleteStudent(String email)
    {

        TypedQuery<Student> query =
                em.createNamedQuery("Student.findByEMail", Student.class);

        try {
            Student student = query.setParameter("email", email).getSingleResult();
//            em.getTransaction().begin();
            em.remove(student);
//            em.getTransaction().commit();

            //Logging
            logger.log(Level.INFO, "Student removed from database", student);
        } catch (NoResultException noex) {
            //Logging
            logger.log(Level.WARNING, "No students found for removal that match db query", email);
            //@TODO no such school
        } catch (Exception ex) {
            //@TODO generic catch
        }

    }
}
