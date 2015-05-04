/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import shield.server.entities.Schedule;
import shield.server.entities.School;
import shield.server.entities.Section;
import shield.server.entities.Student;
import shield.server.util.DatabaseConnection;
import shield.shared.dto.SimpleSchedule;
import shield.shared.dto.SimpleScheduleBlock;

/**
 *
 * @author evanguby
 */
@Stateful
public class ScheduleBean {

    //Logger
    private static final Logger logger
            = Logger.getLogger("sss.ejb.ScheduleBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    public void setAssignedSchedule(SimpleSchedule schedule) {
//        em = DatabaseConnection.getEntityManager();
//        TypedQuery<Student> query
//                = em.createNamedQuery("Student.findByEmail", Student.class);
//        query.setParameter("email", schedule.studentEmail);
//        try {
//            Student s = query.getSingleResult();
//            s.createSchedule(schedule.year);
//            Schedule assigned = s.getSchedule(schedule.year);
//            for (String sID : schedule.sectionIDs) {
//                TypedQuery<Section> query2
//                        = em.createNamedQuery("Section.findByID", Section.class);
//                query2.setParameter("id", Long.parseLong(sID));
//                Section sec = query2.getSingleResult();
//                assigned.addSection(sec);
//                
//            }
//            em.getTransaction().begin();
//            em.persist(assigned);
//            em.getTransaction().commit();
//        } finally {
//            //Close the entity manager
//            em.close();
//            em = null;
//        }
    }

}
