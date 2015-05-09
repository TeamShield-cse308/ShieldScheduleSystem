/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import shield.server.entities.GenerationCriteria;
import shield.server.entities.Schedule;

/**
 *
 * @author Weize
 */
public class DesiredScheduleBeanTest {
    
    public DesiredScheduleBeanTest() {
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
     * Test of setLunches method, of class DesiredScheduleBean.
     */
    @Test
    public void testSetLunches() throws Exception {
        System.out.println("setLunches");
        String studentEmail = "";
        int year = 0;
        boolean[] desiresLunch = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        DesiredScheduleBean instance = (DesiredScheduleBean)container.getContext().lookup("java:global/classes/DesiredScheduleBean");
        instance.setLunches(studentEmail, year, desiresLunch);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCourse method, of class DesiredScheduleBean.
     */
    @Test
    public void testAddCourse() throws Exception {
        System.out.println("addCourse");
        String studentEmail = "";
        int year = 0;
        long courseID = 0L;
        String instructor = "";
        List<Long> excludedSectionIDs = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        DesiredScheduleBean instance = (DesiredScheduleBean)container.getContext().lookup("java:global/classes/DesiredScheduleBean");
        boolean expResult = false;
        boolean result = instance.addCourse(studentEmail, year, courseID, instructor, excludedSectionIDs);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeCourse method, of class DesiredScheduleBean.
     */
    @Test
    public void testRemoveCourse() throws Exception {
        System.out.println("removeCourse");
        String studentEmail = "";
        int year = 0;
        long courseID = 0L;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        DesiredScheduleBean instance = (DesiredScheduleBean)container.getContext().lookup("java:global/classes/DesiredScheduleBean");
        instance.removeCourse(studentEmail, year, courseID);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCriteria method, of class DesiredScheduleBean.
     */
    @Test
    public void testGetCriteria() throws Exception {
        System.out.println("getCriteria");
        String studentEmail = "";
        int year = 0;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        DesiredScheduleBean instance = (DesiredScheduleBean)container.getContext().lookup("java:global/classes/DesiredScheduleBean");
        GenerationCriteria expResult = null;
        GenerationCriteria result = instance.getCriteria(studentEmail, year);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateSchedule method, of class DesiredScheduleBean.
     */
    @Test
    public void testGenerateSchedule() throws Exception {
        System.out.println("generateSchedule");
        String studentEmail = "";
        int year = 0;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        DesiredScheduleBean instance = (DesiredScheduleBean)container.getContext().lookup("java:global/classes/DesiredScheduleBean");
        List<Schedule> expResult = null;
        List<Schedule> result = instance.generateSchedule(studentEmail, year);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
