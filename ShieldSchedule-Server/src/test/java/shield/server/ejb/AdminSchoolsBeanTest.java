/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import shield.server.ejb.AdminSchoolsBean;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import shield.server.entities.School;
import shield.server.util.DatabaseConnection;

/**
 *
 * @author Weize
 */
public class AdminSchoolsBeanTest
{
    private String initName; 
    private int initSemesters ;
    private int initPeriods;
    private int initScheduleDays ;
    private int initStartLunchPeriod ;
    private int initEndLunchPeriod ;
    private EntityManager em;

    public AdminSchoolsBeanTest()
    {
      AdminSchoolsBean asb=new AdminSchoolsBean();
       //create the entity manager
     em = DatabaseConnection.getEntityManager();
       asb.addSchool(initName, initSemesters, initPeriods, initScheduleDays,
       initStartLunchPeriod, initEndLunchPeriod);   
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    //@TODO resolve NullPointerExceptions
    
    /**
     * Test of addSchool method, of class AdminSchoolsBean.
     */
    @org.junit.Test
    public void testAddSchool() throws NullPointerException
    {
        
        System.out.println("addSchool");

               //Create the entity manager and set up the query by school name
        TypedQuery<School> query =
                em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", initName);
                    //search the school 
        School sch = query.getSingleResult();
        //to test if the those information added to databasee
        
        assertEquals(initName,sch.getSchoolName());
        assertEquals(initSemesters,sch.getSemesters());
        assertEquals(initPeriods,sch.getPeriods());
        assertEquals(initScheduleDays,sch.getScheduleDays());
        assertEquals(initStartLunchPeriod,sch.getStartingLunch());
        assertEquals(initEndLunchPeriod,sch.getEndingLunch());
        // return any fail message
        fail("The function does not work correctly with the input!");
    }

    /**
     * Test of deleteSchool method, of class AdminSchoolsBean.
     */
    @org.junit.Test
    public void testDeleteSchool() throws NullPointerException
    {
//        System.out.println("deleteSchool");
//        asb.deleteSchool(initName);
//        sch = em.find(School.class, initName);
//        if (sch == null) {
//            System.out.println("successful deleted");
//        }
//        return;
    }

    /**
     * Test of editSchool method, of class AdminSchoolsBean.
     */
    @org.junit.Test
    public void testEditSchool() throws NullPointerException
    {
//        System.out.println("editSchool");
//        asb.editSchool(initName, initName, initSemesters, initPeriods,
//                initScheduleDays, initStartLunchPeriod, initEndLunchPeriod);
//        sch = em.find(School.class, initName);
//        assertEquals(initName, sch.getName());
//        assertEquals(initSemesters, sch.getNumSemesters());
//        assertEquals(initPeriods, sch.getNumPeriods());
//        assertEquals(initScheduleDays, sch.getNumScheduleDays());
//        assertEquals(initStartLunchPeriod, sch.getStartingLunchPeriod());
//        assertEquals(initEndLunchPeriod, sch.getEndingLunchPeriod());
//
//        return;
    }

    /**
     * Test of getAllSchools method, of class AdminSchoolsBean.
     */
    @org.junit.Test
    public void testGetAllSchools() throws NullPointerException
    {
//        System.out.println("getAllSchools");
//        List<School> schList = asb.getAllSchools();
//        for (int n = 0; n < schList.size(); n++) {
//            System.out.println(schList.get(n).toString());
//        }
//        return;

    }
}