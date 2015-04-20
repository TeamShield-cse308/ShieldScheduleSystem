/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import static java.util.jar.Pack200.Packer.PASS;
import javax.ejb.Stateful;
import shield.server.entities.School;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.persistence.Persistence;
import static javax.ws.rs.Priorities.USER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.*;
import java.util.Map;
import java.util.HashMap;
import javax.persistence.EntityExistsException;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import javax.persistence.EntityManagerFactory;
import shield.server.util.DatabaseConnection;

/**
 * A javabean that provides functionality to add, delete, and edit schools To be
 * used on the server by administrators logged into the client
 *
 * @author Jeffrey Kabot
 */
@Stateful
public class AdminSchoolsBean
{

    //Logger
    private static final Logger logger =
            Logger.getLogger("sss.ejb.AdminSchoolsBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    /**
     * Add a new school to the database
     *
     * @param initName the name of the school to add
     * @param initSemesters the number of semesters the school has per school
     * year
     * @param initScheduleDays the number of days in a schedule cycle
     * @param initPeriods the number of schedule blocks in the school day
     * @param initStartLunchPeriod the starting lunch period
     * @param initEndLunchPeriod the ending lunchperiod
     */
    public void addSchool(String initName,
            int initSemesters,
            int initPeriods,
            int initScheduleDays,
            int initStartLunchPeriod,
            int initEndLunchPeriod)
    {
        //create the entity manager
        em = DatabaseConnection.getEntityManager();

        //new school entity
        School school = new School(initName, initSemesters, initPeriods,
                initScheduleDays, initStartLunchPeriod, initEndLunchPeriod);

        try {
            //add the school
            em.persist(school);
            logger.log(Level.INFO, "New school added to database {0}", school);
        } catch (EntityExistsException eeex) {
            logger.log(Level.WARNING, "Collision on school ID within database");
            //@TODO message to client
        } catch (Exception ex) {
            //@TODO collision on school name?
            logger.log(Level.SEVERE, null, ex);
        } finally {
            //close the entity manager
            em.close();
            em = null;
        }

        //        try {
        //            Class.forName("com.mysql.jdbc.Driver").newInstance();
        //        } catch (Exception ex) {
        //            logger.log(Level.SEVERE, null, ex);
        //        }
        //        School school = new School(initName, initSemesters, initPeriods,
        //                initScheduleDays, initStartLunchPeriod, initEndLunchPeriod);
        ////        em.getTransaction().begin();
        //        Connection conn = null;
        //        Statement stmt = null;
        //
        //        try {
        //            conn = DriverManager.getConnection("jdbc:mysql://mysql2.cs.stonybrook.edu:3306/eguby", "eguby", "108555202");
        //
        //            stmt = conn.createStatement();
        //            String sql = "INSERT INTO School (SCHOOLNAME,SEMESTERS,SCHEDULEDAYS,PERIODS,STARTINGLUNCH,ENDINGLUNCH) VALUES (\'" + initName + "\', " + initSemesters + ", "
        //                    + initPeriods + ", " + initScheduleDays + ", " + initStartLunchPeriod + ", "
        //                    + initEndLunchPeriod + ")";
        //            stmt.executeUpdate(sql);
        //        } catch (SQLException ex) {
        //            logger.log(Level.SEVERE, null, ex);
        //        }
    }

    /**
     * Remove a school that is in the database
     *
     * @param name the named of the school to remove
     */
    public void deleteSchool(String name)
    {
        //Create the entity manager and set up the query by school name
        em = DatabaseConnection.getEntityManager();
        TypedQuery<School> query =
                em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", name);

        try {
            //search the school and remove it from database
            School school = query.getSingleResult();
            em.remove(school);
            logger.log(Level.INFO, "School {0} removed from database", school);
        } catch (NoResultException noex) {
            logger.log(Level.WARNING, "No such school with name {0} found in database", name);
            //@TODO no such school message to client
        } catch (Exception ex) {
            //@TODO generic catch
        } finally {
            //Close the entity manager
            em.close();
            em = null;
        }
    }

    //@TODO excessive parameters?... package into a structure ??
    /**
     * Modify a school in the database
     *
     * @param originalName the original identifier for the school
     */
    public void editSchool(String originalName,
            String newName,
            int newSemesters,
            int newPeriods,
            int newScheduleDays,
            int newStartLunchPeriod,
            int newEndLunchPeriod)
    {

        //Create the entity manager and set up the query by school name
        em = DatabaseConnection.getEntityManager();
        TypedQuery<School> query =
                em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", originalName);
        try {
            //execute query
            School school = query.getSingleResult();
            //update the school with the new info
            school.setSchoolName(newName);
            school.setSemesters(newSemesters);
            school.setScheduleDays(newScheduleDays);
            school.setPeriods(newPeriods);
            school.setStartingLunch(newStartLunchPeriod);
            school.setEndingLunch(newEndLunchPeriod);
            
            //@TODO change this log?
            logger.log(Level.INFO, "School data changed in database for school with ID: {0}", school.getID());

        } catch (NoResultException noex) {
            logger.log(Level.WARNING, "No such school with name {0} found in database", originalName);
            //@TODO message back to client
        } finally {
            //close the entity manager
            em.close();
            em = null;
        }
    }

    /**
     * Retrieve a list of all schools in the database
     *
     * @return the list of all schools
     */
    public List<School> getAllSchools()
    {
        List<School> schools = null;

        // Create the entity manager and set up the query for all schools
        em = DatabaseConnection.getEntityManager();
        TypedQuery<School> query =
                em.createNamedQuery("School.findAll", School.class);
        try {
            schools = query.getResultList();
            logger.log(Level.INFO, "Retrieving all schools in DB", schools);
        } catch (Exception ex) {
            //@TODO
        } finally {
            //Close the entity manager
            em.close();
            em = null;
        }
        return schools;
    }
}
