/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sss.ejb;

import shield.server.ejb.AdminStudentsBean;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import shield.server.entities.Student;

/**
 *
 * @author Weize
 */
public class AdminStudentsBeanTest {
    
    public AdminStudentsBeanTest() {
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
     * Test of getAllStudents method, of class AdminStudentsBean.
     */
    @Test
    public void testGetAllStudents() throws Exception {
        System.out.println("getAllStudents");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        AdminStudentsBean instance = (AdminStudentsBean)container.getContext().lookup("java:global/classes/AdminStudentsBean");
        List<Student> expResult = null;
        List<Student> result = instance.getAllStudents();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPendingStudents method, of class AdminStudentsBean.
     */
    @Test
    public void testGetPendingStudents() throws Exception {
        System.out.println("getPendingStudents");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        AdminStudentsBean instance = (AdminStudentsBean)container.getContext().lookup("java:global/classes/AdminStudentsBean");
        List<Student> expResult = null;
        List<Student> result = instance.getPendingStudents();
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of approveStudent method, of class AdminStudentsBean.
     */
    @Test
    public void testApproveStudent() throws Exception {
        System.out.println("approveStudent");
        String email = "";
        boolean approved = false;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        AdminStudentsBean instance = (AdminStudentsBean)container.getContext().lookup("java:global/classes/AdminStudentsBean");
        instance.approveStudent(email, approved);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteStudent method, of class AdminStudentsBean.
     */
    @Test
    public void testDeleteStudent() throws Exception {
        System.out.println("deleteStudent");
        String email = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        AdminStudentsBean instance = (AdminStudentsBean)container.getContext().lookup("java:global/classes/AdminStudentsBean");
        instance.deleteStudent(email);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
