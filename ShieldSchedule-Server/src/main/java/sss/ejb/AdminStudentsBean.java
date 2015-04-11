/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sss.ejb;

import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sss.entities.School;
import sss.entities.Student;

/**
 *
 * @author Jeffrey Kabot
 */
@Stateful
public class AdminStudentsBean
{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    //Logger
    private static final Logger logger = Logger.getLogger("sss.ejb.AdminSchoolsBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    public void addStudent(String initName, String email, School school)
    {
        Student student = new Student();
    }
}
