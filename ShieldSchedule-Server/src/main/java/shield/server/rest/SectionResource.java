/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import shield.server.ejb.SectionBean;
import shield.server.entities.Course;
import shield.server.entities.ScheduleBlock;
import shield.server.entities.Section;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleSection;

/**
 *
 * @author evanguby
 */
@Path("section")
@RequestScoped
public class SectionResource {
    //Logger
    private static final Logger logger = Logger.getLogger(SectionResource.class.getName());

    @Context
    private UriInfo context;

    @Inject
    private SectionBean sectionBean;
    
        /**
     * Creates a new instance of SectionREST
     */
    public SectionResource()
    {
    }
    
    @POST
    @Path("/add")
    @Consumes("application/json")
    public Response addSection(SimpleSection section)
    {
         try
        {
            sectionBean.addSection(section);
            logger.log(Level.INFO, "OK Response");
            return Response.ok(section).build();
        } catch (RollbackException rex)
        {
            logger.log(Level.WARNING, "BAD REQUEST");
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (NoResultException nrex)
        {
            //@TODO disambiguate errors
            logger.log(Level.WARNING, "BAD REQUEST");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("/getCourseSections")
    @Consumes("application/json")
    public Response getCourseSections(SimpleCourse course)
    {
        List<Section> sectionList = sectionBean.getCourseSections(course.identifier, course.school);
        
        List<SimpleSection> simpleSections = new ArrayList<>();
        SimpleSection s;
        for (Section section : sectionList)
        {
            s = new SimpleSection();
            s.teacherName = section.getTeacher();
            ScheduleBlock sb = section.getScheduleBlock();
            s.scheduleBlockPeriod = sb.getPeriod();
            s.scheduleBlockDays = sb.getDaysString();
            s.sectionID = section.getId();
            //s.scheduleBlockPeriod = section.g
            
            simpleSections.add(s);
        }
        //a wrapper for the list of students
        GenericEntity<List<SimpleSection>> wrapper =
                new GenericEntity<List<SimpleSection>>(simpleSections)
                {
                };
        logger.log(Level.INFO, "Sending list of sections");
        return Response.ok(wrapper).build();
    }

}


