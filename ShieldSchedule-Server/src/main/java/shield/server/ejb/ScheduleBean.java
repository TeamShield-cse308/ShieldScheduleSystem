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
import shield.server.entities.Section;
import shield.server.entities.Student;
import shield.server.util.DatabaseConnection;
import shield.shared.dto.SimpleSchedule;
import shield.shared.dto.SimpleSection;

/**
 *
 * @author evanguby
 */
@Stateful
public class ScheduleBean
{

    //Logger
    private static final Logger logger =
            Logger.getLogger(ScheduleBean.class.getName());

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    public Schedule getSchedule(String studentEmail,
            int year)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> query =
                em.createNamedQuery("Student.findByEmail", Student.class);
        query.setParameter("email", studentEmail);

        Schedule sch = null;
        try
        {
            Student s = query.getSingleResult();
            sch = s.getAssignedSchedule(year);
        } finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }
        return sch;
    }

    public boolean addSectionToSchedule(String studentEmail,
            int year,
            long sectionID)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> query =
                em.createNamedQuery("Student.findByEmail", Student.class);
        query.setParameter("email", studentEmail);

        TypedQuery<Section> query2 =
                em.createNamedQuery("Section.findByID", Section.class);
        query2.setParameter("id", sectionID);

        boolean success = false;
        try
        {
            Student s = query.getSingleResult();
            Schedule assigned = s.getAssignedSchedule(year);

            Section sec = query2.getSingleResult();

            em.getTransaction().begin();
            success = assigned.addSection(sec);
            if (success)
            {
                sec.addStudent(s);
            }
            em.getTransaction().commit();
        } finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }
        return success;
    }

    public void removeSectionFromSchedule(String studentEmail,
            int year,
            long sectionID)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Student> query =
                em.createNamedQuery("Student.findByEmail", Student.class);
        query.setParameter("email", studentEmail);

        TypedQuery<Section> query2 =
                em.createNamedQuery("Section.findByID", Section.class);
        query2.setParameter("id", sectionID);

        try
        {
            Student s = query.getSingleResult();
            Schedule assigned = s.getAssignedSchedule(year);

            Section sec = query2.getSingleResult();

            em.getTransaction().begin();
            assigned.removeSection(sec);
            sec.removeStudent(s);
            em.getTransaction().commit();

        } finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }
    }
}
