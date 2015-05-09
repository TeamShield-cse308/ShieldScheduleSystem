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
import shield.server.entities.ScheduleBlock;
import shield.shared.dto.SimpleScheduleBlock;

/**
 *
 * @author Weize
 */
public class ScheduleBlockBeanTest {
    
    public ScheduleBlockBeanTest() {
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
     * Test of addScheduleBlock method, of class ScheduleBlockBean.
     */
    @Test
    public void testAddScheduleBlock() throws Exception {
        System.out.println("addScheduleBlock");
        SimpleScheduleBlock ssb = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ScheduleBlockBean instance = (ScheduleBlockBean)container.getContext().lookup("java:global/classes/ScheduleBlockBean");
        instance.addScheduleBlock(ssb);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSchoolScheduleBlocks method, of class ScheduleBlockBean.
     */
    @Test
    public void testGetSchoolScheduleBlocks() throws Exception {
        System.out.println("getSchoolScheduleBlocks");
        String schoolName = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        ScheduleBlockBean instance = (ScheduleBlockBean)container.getContext().lookup("java:global/classes/ScheduleBlockBean");
        List<ScheduleBlock> expResult = null;
        List<ScheduleBlock> result = instance.getSchoolScheduleBlocks(schoolName);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
