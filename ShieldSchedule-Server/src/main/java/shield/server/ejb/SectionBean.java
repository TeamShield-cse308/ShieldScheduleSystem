/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import shield.server.entities.Course;
import shield.server.entities.ScheduleBlock;
import shield.server.entities.Section;
import shield.server.util.DatabaseConnection;

/**
 *
 * @author evanguby
 */
@Stateful
public class SectionBean
{

    //Logger

    private static final Logger logger =
            Logger.getLogger("sss.ejb.SectionBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    public List<Section> getCourseSections(String courseIdentifier,
            String school, int year)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Course> query =
                em.createNamedQuery("Course.findByIdentifierSchoolYear", Course.class);
        query.setParameter("identifier", courseIdentifier);
        query.setParameter("school", school);
        query.setParameter("year", year);
        List<Section> sectionList = null;
        try
        {
            Course course = query.getSingleResult();
            sectionList = new ArrayList<>(course.getSections());
            logger.log(Level.INFO, "Retrieving all sections from Course", course);
        } finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }
        return sectionList;
    }

    public void addSection(String school,
            int period,
            String days,
            String identifier,
            int year,
            String teacher,
            List<Integer> semesters)
    {

        em = DatabaseConnection.getEntityManager();
        TypedQuery<ScheduleBlock> query =
                em.createNamedQuery("ScheduleBlock.findBySchoolPeriodDay", ScheduleBlock.class);
        TypedQuery<Course> query2 =
                em.createNamedQuery("Course.findByIdentifierSchoolYear", Course.class);
        query.setParameter("school", school);
        query.setParameter("period", period);
        query.setParameter("days", days);

        query2.setParameter("identifier", identifier);
        query2.setParameter("school", school);
        query2.setParameter("year", year);
        try
        {
            ScheduleBlock sb = query.getSingleResult();
            Course c = query2.getSingleResult();
            

            SortedSet<Integer> semSet = new TreeSet<>();
            semSet.addAll(semesters);
            
            em.getTransaction().begin();
            c.addSection(teacher, sb, semSet);
            em.getTransaction().commit();
        } finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }
    }

}
