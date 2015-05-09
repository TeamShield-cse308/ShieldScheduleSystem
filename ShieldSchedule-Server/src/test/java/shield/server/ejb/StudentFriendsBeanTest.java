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
import shield.server.entities.Friendship;
import shield.server.entities.Student;

/**
 *
 * @author Weize
 */
public class StudentFriendsBeanTest {
    
    public StudentFriendsBeanTest() {
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
     * Test of getAllFriends method, of class StudentFriendsBean.
     */
    @Test
    public void testGetAllFriends() throws Exception {
        System.out.println("getAllFriends");
        String email = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        StudentFriendsBean instance = (StudentFriendsBean)container.getContext().lookup("java:global/classes/StudentFriendsBean");
        List<Student> expResult = null;
        List<Student> result = instance.getAllFriends(email);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFriendRequests method, of class StudentFriendsBean.
     */
    @Test
    public void testGetFriendRequests() throws Exception {
        System.out.println("getFriendRequests");
        String recipientEmail = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        StudentFriendsBean instance = (StudentFriendsBean)container.getContext().lookup("java:global/classes/StudentFriendsBean");
        List<Friendship> expResult = null;
        List<Friendship> result = instance.getFriendRequests(recipientEmail);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addFriendByName method, of class StudentFriendsBean.
     */
    @Test
    public void testAddFriendByName() throws Exception {
        System.out.println("addFriendByName");
        String senderEmail = "";
        String recipientName = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        StudentFriendsBean instance = (StudentFriendsBean)container.getContext().lookup("java:global/classes/StudentFriendsBean");
        List<Student> expResult = null;
        List<Student> result = instance.addFriendByName(senderEmail, recipientName);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addFriendByEmail method, of class StudentFriendsBean.
     */
    @Test
    public void testAddFriendByEmail() throws Exception {
        System.out.println("addFriendByEmail");
        String senderEmail = "";
        String recipientEmail = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        StudentFriendsBean instance = (StudentFriendsBean)container.getContext().lookup("java:global/classes/StudentFriendsBean");
        instance.addFriendByEmail(senderEmail, recipientEmail);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of approveFriend method, of class StudentFriendsBean.
     */
    @Test
    public void testApproveFriend() throws Exception {
        System.out.println("approveFriend");
        String senderEmail = "";
        String recipientEmail = "";
        boolean approved = false;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        StudentFriendsBean instance = (StudentFriendsBean)container.getContext().lookup("java:global/classes/StudentFriendsBean");
        instance.approveFriend(senderEmail, recipientEmail, approved);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
