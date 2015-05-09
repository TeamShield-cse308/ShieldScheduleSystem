/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import shield.server.entities.Schedule;

/**
 *
 * @author Weize
 */
public class ScheduleBeanTest {
    
    public ScheduleBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSchedule method, of class ScheduleBean.
     */
    @Test
    public void testGetSchedule() throws Exception {
        System.out.println("getSchedule");
        String studentEmail = "";
        int year = 0;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ScheduleBean instance = (ScheduleBean)container.getContext().lookup("java:global/classes/ScheduleBean");
        Schedule expResult = null;
        Schedule result = instance.getSchedule(studentEmail, year);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSectionToSchedule method, of class ScheduleBean.
     */
    @Test
    public void testAddSectionToSchedule() throws Exception {
        System.out.println("addSectionToSchedule");
        String studentEmail = "";
        int year = 0;
        long sectionID = 0L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ScheduleBean instance = (ScheduleBean)container.getContext().lookup("java:global/classes/ScheduleBean");
        boolean expResult = false;
        boolean result = instance.addSectionToSchedule(studentEmail, year, sectionID);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeSectionFromSchedule method, of class ScheduleBean.
     */
    @Test
    public void testRemoveSectionFromSchedule() throws Exception {
        System.out.println("removeSectionFromSchedule");
        String studentEmail = "";
        int year = 0;
        long sectionID = 0L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ScheduleBean instance = (ScheduleBean)container.getContext().lookup("java:global/classes/ScheduleBean");
        instance.removeSectionFromSchedule(studentEmail, year, sectionID);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
