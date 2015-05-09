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
import shield.server.entities.Section;

/**
 *
 * @author Weize
 */
public class SectionBeanTest {
    
    public SectionBeanTest() {
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
     * Test of getCourseSections method, of class SectionBean.
     */
    @Test
    public void testGetCourseSections() throws Exception {
        System.out.println("getCourseSections");
        String courseIdentifier = "";
        String school = "";
        int year = 0;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SectionBean instance = (SectionBean)container.getContext().lookup("java:global/classes/SectionBean");
        List<Section> expResult = null;
        List<Section> result = instance.getCourseSections(courseIdentifier, school, year);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSection method, of class SectionBean.
     */
    @Test
    public void testAddSection() throws Exception {
        System.out.println("addSection");
        String school = "";
        int period = 0;
        String days = "";
        String identifier = "";
        int year = 0;
        String teacher = "";
        List<Integer> semesters = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        SectionBean instance = (SectionBean)container.getContext().lookup("java:global/classes/SectionBean");
        instance.addSection(school, period, days, identifier, year, teacher, semesters);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
