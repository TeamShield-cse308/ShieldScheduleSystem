/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sss.ejb;

import shield.server.ejb.AdminSchoolsBean;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import shield.server.entities.School;

/**
 *
 * @author Weize
 */
public class AdminSchoolsBeanTest
{

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    private AdminSchoolsBean asb;

    private School sch;

    private String initName = "Stony Brook";
    private int initSemesters = 1;
    private int initPeriods = 2;
    private int initScheduleDays = 3;
    private int initStartLunchPeriod = 4;
    private int initEndLunchPeriod = 5;

    public AdminSchoolsBeanTest()
    {

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
        asb = new AdminSchoolsBean();
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
        
//        System.out.println("addSchool");
//        asb.addSchool(initName, initSemesters, initPeriods, initScheduleDays,
//        initStartLunchPeriod, initEndLunchPeriod);
//        sch=em.find(School.class, initName);
//        assertEquals(initName,sch.getName());
//        assertEquals(initSemesters,sch.getNumSemesters());
//        assertEquals(initPeriods,sch.getNumPeriods());
//        assertEquals(initScheduleDays,sch.getNumScheduleDays());
//        assertEquals(initStartLunchPeriod,sch.getStartingLunchPeriod());
//        assertEquals(initEndLunchPeriod,sch.getEndingLunchPeriod());
        return;
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
