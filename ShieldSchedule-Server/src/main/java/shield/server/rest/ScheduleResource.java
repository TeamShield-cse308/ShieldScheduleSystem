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
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import shield.server.ejb.ScheduleBean;
import shield.server.entities.Schedule;
import shield.server.entities.Section;
import shield.shared.dto.SimpleSchedule;
import shield.shared.dto.SimpleSection;

/**
 *
 * @author evanguby
 */
@Path("schedule")
@RequestScoped
public class ScheduleResource
{

    private static final Logger logger = Logger.getLogger(ScheduleResource.class.getName());

    @Context
    private UriInfo context;

    @Inject
    private ScheduleBean scheduleBean;

    public ScheduleResource()
    {
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response getSchedule(SimpleSchedule ss)
    {
        String studentEmail = ss.studentEmail;
        int year = ss.year;
        Schedule schedule = scheduleBean.getSchedule(studentEmail, year);
        List<Section> sections = schedule.getSections();
        for (Section section : sections)
        {
            SimpleSection s = new SimpleSection();
            s.teacherName = section.getTeacher();
            s.setScheduleBlock(section.getScheduleBlock().getPeriod(), section.getScheduleBlock().getDaysString());
            s.setCourse(section.getCourse().getIdentifier(), section.getCourse().getName());
            s.semesters = new ArrayList<>(section.getSemesters());
            ss.addSection(s);
        }
        return Response.ok(ss).build();
    }

    @POST
    @Path("/addSectionToSchedule")
    @Consumes("application/json")
    public Response addSection(SimpleSection section)
    {
        String studentEmail = section.studentEmail;
        int year = section.year;
        long sectionID = section.sectionID;
        try
        {
            if (scheduleBean.addSectionToSchedule(studentEmail, year, sectionID))
            {
                logger.log(Level.INFO, "OK Response");
                return Response.ok(section).build();
            } else
            {
                logger.log(Level.INFO, "BAD REQUEST, schedule conflict");
                return Response.ok(section).entity("The section conflicts with a course or section already in the schedule.").build();
            }
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "BAD REQUEST");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/removeSectionFromSchedule")
    @Consumes("application/json")
    public Response removeSection(SimpleSection section)
    {
        String studentEmail = section.studentEmail;
        int year = section.year;
        long sectionID = section.sectionID;
        try
        {
            scheduleBean.removeSectionFromSchedule(studentEmail, year, sectionID);
            logger.log(Level.INFO, "OK Response");
            return Response.ok(section).build();
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "BAD REQUEST", nrex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
