/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.util.List;
import java.util.TreeSet;
import javax.ejb.Stateful;
import shield.server.entities.School;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.persistence.EntityExistsException;
import javax.persistence.RollbackException;
import shield.server.entities.Course;
import shield.server.entities.ScheduleBlock;
import shield.server.util.DatabaseConnection;
import shield.shared.dto.SimpleSchool;

/**
 * A javabean that provides functionality to add, delete, and edit schools. To
 * be used on the server by administrators logged into the client
 *
 * @author Jeffrey Kabot
 */
@Stateful
public class AdminSchoolsBean
{

    //Logger
    private static final Logger logger
            = Logger.getLogger("sss.ejb.AdminSchoolsBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    /**
     * Add a new school to the database.
     *
     * @param initName the name of the school to add
     * @param initSemesters the number of semesters the school has per school
     * year
     * @param initScheduleDays the number of days in a schedule cycle
     * @param initPeriods the number of schedule blocks in the school day
     * @param initStartLunchPeriod the starting lunch period
     * @param initEndLunchPeriod the ending lunchperiod
     * @throws RollbackException if a school with that name already exists.
     */
    public void addSchool(String initName,
            int initSemesters,
            int initPeriods,
            int initScheduleDays,
            int initStartLunchPeriod,
            int initEndLunchPeriod) throws RollbackException
    {
        //create the entity manager
        em = DatabaseConnection.getEntityManager();

        //new school entity
        School school = new School(initName, initSemesters, initScheduleDays,
                initPeriods, initStartLunchPeriod, initEndLunchPeriod);

        try
        {
            em.getTransaction().begin();
            //add the school
            em.persist(school);

            //create a Lunch course for each day of the week
            for (int i = 1; i <= school.getScheduleDays(); i++)
            {
                Course c = school.addLunch(i);
                TreeSet<Integer> daySet = new TreeSet<>();
                daySet.add(i);

                //Create a schedule block and a lunch section for each period
                for (int j = school.getStartingLunch(); j <= school.getEndingLunch(); j++)
                {
                    ScheduleBlock sb = new ScheduleBlock(school, j, daySet, true);
                    em.persist(sb);
                    TreeSet<Integer> semSet = new TreeSet<>();
                    for (int k = 1; k <= school.getSemesters(); k++)
                    {
                        semSet.add(k);
                    }
                    c.addSection("STAFF", sb, semSet);
                }
            }
            em.getTransaction().commit();
            logger.log(Level.INFO, "New school added to database {0}", school);
        } catch (RollbackException rex)
        {
            //a school with that id already exists in database
            logger.log(Level.WARNING, "Collision on school ID within database");
            throw rex;
        } finally
        {
            //close the entity manager
            em.close();
            em = null;
        }
    }

    /**
     * Remove a school in the database.
     *
     * @param name the name of the school to remove
     * @throws NoResultException when there is no school with that name.
     */
    public void deleteSchool(String name) throws NoResultException
    {
        //Create the entity manager and set up the query by school name
        em = DatabaseConnection.getEntityManager();
        TypedQuery<School> query
                = em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", name);

        try
        {
            //search the school and remove it from database
            School school = query.getSingleResult();
            em.getTransaction().begin();
            em.remove(school);
            em.getTransaction().commit();
            logger.log(Level.INFO, "School {0} removed from database", school);
        } catch (NoResultException noex)
        {
            //school not found
            logger.log(Level.WARNING,
                    "No such school with name {0} found in database", name);
            throw noex;
        } finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }
    }

    /**
     * Modify a school in the database.
     *
     * @param name
     * @param newSemesters
     * @param newPeriods
     * @param newScheduleDays
     * @param newStartLunchPeriod
     * @param newEndLunchPeriod
     * @throws NoResultException
     */
    public void editSchool(String name,
            int newSemesters,
            int newPeriods,
            int newScheduleDays,
            int newStartLunchPeriod,
            int newEndLunchPeriod) throws NoResultException
    {
        //Create the entity manager and set up the query by school name
        em = DatabaseConnection.getEntityManager();
        TypedQuery<School> query
                = em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", name);
        try
        {
            //execute query and update school info
            School school = query.getSingleResult();
            em.getTransaction().begin();
            school.setSemesters(newSemesters);
            school.setScheduleDays(newScheduleDays);
            school.setPeriods(newPeriods);
            school.setStartingLunch(newStartLunchPeriod);
            school.setEndingLunch(newEndLunchPeriod);
            em.getTransaction().commit();
            logger.log(Level.INFO, "Updated properties for school: {0}", name);

        } catch (NoResultException nrex)
        {
            //school not found
            logger.log(Level.WARNING,
                    "No school with name {0} found in database", name);
            throw nrex;
        } finally
        {
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
        TypedQuery<School> query
                = em.createNamedQuery("School.findAll", School.class);
        try
        {
            schools = query.getResultList();
            logger.log(Level.INFO, "Retrieving all schools in DB", schools);
        } finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }
        return schools;
    }

    /**
     * Return a single school.
     *
     * @param name
     * @return
     * @deprecated
     */
    @Deprecated
    public SimpleSchool getSchool(String name)
    {
        School sch = null;
        em = DatabaseConnection.getEntityManager();
        TypedQuery<School> query
                = em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", name);
        try
        {
            sch = query.getSingleResult();
            logger.log(Level.INFO, "Retrieving school from DB", sch);
        } catch (Exception ex)
        {
            //@TODO
        } finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }
        SimpleSchool ss = new SimpleSchool();
        ss.endingLunchPeriod = sch.getEndingLunch();
        ss.name = sch.getSchoolName();
        ss.startingLunchPeriod = sch.getStartingLunch();
        ss.numSemesters = sch.getSemesters();
        ss.numScheduleDays = sch.getScheduleDays();
        ss.numPeriods = sch.getPeriods();
        return ss;
    }
}
