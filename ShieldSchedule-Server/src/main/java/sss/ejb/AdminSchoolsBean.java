/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sss.ejb;

import java.util.List;
import javax.ejb.Stateful;
import sss.entities.School;
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
    private static final Logger logger = Logger.getLogger("sss.ejb.AdminSchoolsBean");

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
    public void addSchool(String initName, int initSemesters, int initScheduleDays, int initPeriods)
    {
        School sch = new School(initName, initSemesters, initScheduleDays, initPeriods);
        em.getTransaction().begin();
        em.persist(sch);
        em.getTransaction().commit();

        //Logging
        logger.log(Level.INFO, "New school added to database", sch);
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
            School sch = query.setParameter("name", name).getSingleResult();
            em.getTransaction().begin();
            em.remove(sch);
            em.getTransaction().commit();

            //Logging
            logger.log(Level.INFO, "School removed from database", sch);
        } catch (NoResultException noex) {
            //Logging
            logger.log(Level.WARNING, "No schools found for removal that match db query", name);
            //@TODO no such school
        } catch (Exception ex) {
            //@TODO generic catch
        }
    }

    //@TODO editing schools
    /**
     * Modify a school in the database
     * @param name 
     */
    public void editSchool(String name)
    {

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
