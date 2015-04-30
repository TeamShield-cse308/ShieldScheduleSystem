/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import shield.server.entities.Course;
import shield.server.entities.School;
import shield.server.entities.Section;
import shield.server.util.DatabaseConnection;

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
}
