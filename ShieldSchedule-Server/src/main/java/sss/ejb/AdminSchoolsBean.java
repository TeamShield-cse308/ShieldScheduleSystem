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

/**
 *
 * @author Jeffrey Kabot
 */
@Stateful
public class AdminSchoolsBean
{
    //@TODO logging
    //private static final Logger logger = Logger.get;

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    public void addSchool(String initName, int initSemesters, int initScheduleDays, int initPeriods)
    {
        School sch = new School(initName, initSemesters, initScheduleDays, initPeriods);
        em.getTransaction().begin();
        em.persist(sch);
        em.getTransaction().commit();

        //@TODO logging
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void deleteSchool(String name)
    {
        TypedQuery<School> query =
                em.createNamedQuery("School.findByName", School.class);
        try {
            School sch = query.setParameter("name", name).getSingleResult();
            em.getTransaction().begin();
            em.remove(sch);
            em.getTransaction().commit();

            //@TODO logging
        } catch (NoResultException noex) {
            //@TODO no such school
        } catch (Exception ex) {
            //@TODOgeneric catch
        }
    }

    public void editSchool()
    {

    }

    public List<School> getAllSchools()
    {
        TypedQuery<School> query =
                em.createNamedQuery("School.findAll", School.class);
        //@TODO needs try / catch?
        List<School> schools = query.getResultList();
        //@TODO logging
        return schools;
    }
}
