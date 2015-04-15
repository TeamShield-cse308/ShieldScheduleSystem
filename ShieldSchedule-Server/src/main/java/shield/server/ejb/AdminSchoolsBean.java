/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.util.List;
import javax.ejb.Stateful;
import shield.server.entities.School;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * A javabean that provides functionality to add, delete, and edit schools
 * To be used on the server by administrators logged into the client
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
     * @param initName          the name of the school to add
     * @param initSemesters     the number of semesters the school has per school year
     * @param initScheduleDays  the number of days in a schedule cycle
     * @param initPeriods       the number of schedule blocks in the school day
     */
    public void addSchool(String initName, int initSemesters, int initPeriods, 
            int initScheduleDays, int initStartLunchPeriod, int initEndLunchPeriod)
    {
        School school = new School(initName, initSemesters, initPeriods, 
                initScheduleDays, initStartLunchPeriod, initEndLunchPeriod);
//        em.getTransaction().begin();
        em.persist(school);
//        em.getTransaction().commit();

        //Logging
        logger.log(Level.INFO, "New school added to database", school);
    }

    /**
     * Remove a school that is in the database
     * @param name  the named of the school to remove
     */
    public void deleteSchool(String name)
    {
        TypedQuery<School> query =
                em.createNamedQuery("School.findByName", School.class);
        try {
            School school = query.setParameter("name", name).getSingleResult();
//            em.getTransaction().begin();
            em.remove(school);
//            em.getTransaction().commit();

            //Logging
            logger.log(Level.INFO, "School removed from database", school);
        } catch (NoResultException noex) {
            //Logging
            logger.log(Level.WARNING, "No schools found for removal that match db query", name);
            //@TODO no such school
        } catch (Exception ex) {
            //@TODO generic catch
        }
    }

    
    //@TODO excessive parameters... package into a structure ??
    /**
     * Modify a school in the database
     * @param originalName the original identifier for the school
     */
    public void editSchool(String originalName, String newName, 
            int newSemesters, int newPeriods, int newScheduleDays, 
            int newStartLunchPeriod, int newEndLunchPeriod)
    {
        TypedQuery<School> query =
                em.createNamedQuery("School.findByName", School.class);
        try{
            //find the school to be edited
            School school = 
                    query.setParameter("name", originalName).getSingleResult();
            
            //update the school with the new info
            school.setName(newName);
            school.setNumSemesters(newSemesters);
            school.setNumScheduleDays(newScheduleDays);
            school.setNumPeriods(newPeriods);
            school.setStartingLunchPeriod(newStartLunchPeriod);
            school.setEndingLunchPeriod(newEndLunchPeriod);
            
            //School Updated
            //Now pass to database
//            em.getTransaction().begin();
            em.refresh(school);
//            em.getTransaction().commit();
           
           logger.log(Level.INFO, "School data changed in database", school);
            
        } catch(Exception ex) {
            
        }
    }

    /**
     * Retrieve a list of all schools in the database
     * @return  the list of all schools
     */
    public List<School> getAllSchools()
    {
        TypedQuery<School> query =
                em.createNamedQuery("School.findAll", School.class);
        //@TODO needs try / catch?
        List<School> schools = query.getResultList();
        //@TODO logging
        logger.log(Level.INFO, "Retrieving all schools in DB", schools);
        
        return schools;
    }
}