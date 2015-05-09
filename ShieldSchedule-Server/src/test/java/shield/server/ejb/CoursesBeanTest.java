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
import shield.server.entities.Course;

/**
 *
 * @author Weize
 */
public class CoursesBeanTest {
    
    public CoursesBeanTest() {
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
     * Test of addCourse method, of class CoursesBean.
     */
    @Test
    public void testAddCourse() throws Exception {
        System.out.println("addCourse");
        String identifier = "";
        String courseName = "";
        String schoolName = "";
        int year = 0;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        CoursesBean instance = (CoursesBean)container.getContext().lookup("java:global/classes/CoursesBean");
        instance.addCourse(identifier, courseName, schoolName, year);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSchoolCourses method, of class CoursesBean.
     */
    @Test
    public void testGetSchoolCourses() throws Exception {
        System.out.println("getSchoolCourses");
        String schoolName = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        CoursesBean instance = (CoursesBean)container.getContext().lookup("java:global/classes/CoursesBean");
        List<Course> expResult = null;
        List<Course> result = instance.getSchoolCourses(schoolName);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSchoolCoursesWithLunch method, of class CoursesBean.
     */
    @Test
    public void testGetSchoolCoursesWithLunch() throws Exception {
        System.out.println("getSchoolCoursesWithLunch");
        String schoolName = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        CoursesBean instance = (CoursesBean)container.getContext().lookup("java:global/classes/CoursesBean");
        List<Course> expResult = null;
        List<Course> result = instance.getSchoolCoursesWithLunch(schoolName);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
