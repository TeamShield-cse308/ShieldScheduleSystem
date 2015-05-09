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
import shield.server.entities.GenericUser;

/**
 *
 * @author Weize
 */
public class AuthenticationBeanTest {
    
    public AuthenticationBeanTest() {
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
     * Test of authenticate method, of class AuthenticationBean.
     */
    @Test
    public void testAuthenticate() throws Exception {
        System.out.println("authenticate");
        String username = "";
        String password = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        AuthenticationBean instance = (AuthenticationBean)container.getContext().lookup("java:global/classes/AuthenticationBean");
        GenericUser expResult = null;
        GenericUser result = instance.authenticate(username, password);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
