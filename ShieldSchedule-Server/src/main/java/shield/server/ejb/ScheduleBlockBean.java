/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.ejb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import shield.server.entities.ScheduleBlock;
import shield.server.entities.School;
import shield.server.util.DatabaseConnection;
import shield.shared.dto.SimpleScheduleBlock;

/**
 *
 * @author evanguby
 */
@Stateful
public class ScheduleBlockBean {

    //Logger
    private static final Logger logger =
             Logger.getLogger("sss.ejb.CoursesBean");

    //reference to the perisstence layer
    @PersistenceContext
    private EntityManager em;

    public void addScheduleBlock(SimpleScheduleBlock ssb){
        em = DatabaseConnection.getEntityManager();
        TypedQuery<School> query =
                 em.createNamedQuery("School.findByName", School.class);
        query.setParameter("name", ssb.schoolName);
        try{
            School s = query.getSingleResult();
            ArrayList<Integer> al = new ArrayList<>();
            String days = ssb.scheduleDays;
            for(int i = 0; i < days.length(); i++){
                al.add(Integer.parseInt(days.substring(i,i+1)));
            }
            
            SortedSet<Integer> a = (SortedSet<Integer>)new TreeSet<>(al);
            ScheduleBlock sb = new ScheduleBlock(s, ssb.period,  a);
            
            em.getTransaction().begin();
            //@TODO check return of addCourse to see if it worked
            em.persist(sb);
            em.getTransaction().commit();
            logger.log(Level.INFO, "New ScheduleBlock added to school {0}", s);
        } finally
        {
            //Close the entity manager
            em.close();
            em = null;
        }
        
    }
}
