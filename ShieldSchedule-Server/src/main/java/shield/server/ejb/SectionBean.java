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
import shield.server.entities.School;
import shield.server.entities.Section;
import shield.server.util.DatabaseConnection;
import shield.shared.dto.SimpleSection;

/**
 *
 * @author evanguby
 */
@Stateful
public class SectionBean {
    //Logger
    private static final Logger logger =
             Logger.getLogger("sss.ejb.SectionBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;
    
    public List<Section> getCourseSections(String courseIdentifier)
    {
        em = DatabaseConnection.getEntityManager();
        TypedQuery<Course> query =
                em.createNamedQuery("Course.findByIdentifier", Course.class);
        query.setParameter("identifier", courseIdentifier);
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

    public void addSection(SimpleSection section) {
        
        em = DatabaseConnection.getEntityManager();
        TypedQuery<ScheduleBlock> query =
                 em.createNamedQuery("ScheduleBlock.findBySchoolPeriodDay", ScheduleBlock.class);
        TypedQuery<Course> query2 =
                 em.createNamedQuery("Course.findByIdentifier", Course.class);
        query.setParameter("school",section.school);
        query.setParameter("period",section.scheduleBlockPeriod);
        query.setParameter("days",section.scheduleBlockDays);
        query2.setParameter("identifier", section.courseIdentifier);
        try{
            ScheduleBlock sb = query.getSingleResult();
            Course c = query2.getSingleResult();
            ArrayList<Integer> al = new ArrayList<>();
            for(int i = 0; i < section.semesters.length(); i++){
                al.add(Integer.parseInt(section.semesters.substring(i,i+1)));
            }
            SortedSet<Integer> a = (SortedSet<Integer>)new TreeSet<>(al);
            Section toAdd = new Section(c,section.teacherName,sb,a);
            em.getTransaction().begin();
            em.persist(toAdd);
            em.getTransaction().commit();
        }finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }
    }
    
}
