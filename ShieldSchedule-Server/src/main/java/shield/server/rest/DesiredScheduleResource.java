/*

 */
package shield.server.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import shield.server.ejb.DesiredScheduleBean;
import shield.server.entities.Course;
import shield.server.entities.GenerationCriteria;
import shield.server.entities.Schedule;
import shield.server.entities.Section;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleCriteria;
import shield.shared.dto.SimpleCriteriaCourse;
import shield.shared.dto.SimpleSchedule;
import shield.shared.dto.SimpleSection;

/**
 * REST Web Service
 *
 * @author Jeffrey Kabot
 */
@Path("desiredschedule")
@RequestScoped
public class DesiredScheduleResource
{

    @Context
    private UriInfo context;

    @Inject
    private DesiredScheduleBean desiredScheduleBean;

    /**
     * Creates a new instance of DesiredScheduleResource
     */
    public DesiredScheduleResource()
    {
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response getCriteria(SimpleCriteria sc)
    {
        try
        {
            GenerationCriteria gc = desiredScheduleBean.getCriteria(sc.studentEmail, sc.year);
            Map<Course, String> courses = gc.getCourses();
            for (Course course : courses.keySet())
            {
                Set<Section> keptSections = new HashSet<>(course.getSections());
                keptSections.removeAll(gc.getExlcusions());

                SimpleCourse c = new SimpleCourse();
                c.identifier = course.getIdentifier();
                c.name = course.getName();

                List<SimpleSection> keptSimpleSections = new ArrayList<>();
                for (Section section : keptSections)
                {
                    SimpleSection ss = new SimpleSection();
                    ss.setScheduleBlock(section.getScheduleBlock().getPeriod(), section.getScheduleBlock().getDaysString());
                    ss.teacherName = section.getTeacher();
                    ss.course = c;
                    keptSimpleSections.add(ss);
                }

                sc.addCourse(c, courses.get(course), keptSimpleSections);
            }
            return Response.ok(sc).build();
        } catch (NoResultException nrex)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response setLunches(SimpleCriteria sc)
    {
        try
        {
            desiredScheduleBean.setLunches(sc.studentEmail, sc.year, sc.hasLunches);
            return Response.ok(sc).build();
        } catch (NoResultException nrex)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addCourse(SimpleCriteriaCourse scc)
    {
        List<Long> exclusions = new ArrayList<>();
        for (SimpleSection ss : scc.sections)
        {
            exclusions.add(ss.sectionID);
        }

        if (desiredScheduleBean.addCourse(scc.studentEmail, scc.year,
                scc.course.courseID, scc.teacher, exclusions))
        {
            return Response.ok(scc).build();
        } else
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response removeCourse(SimpleCriteriaCourse scc)
    {
        desiredScheduleBean.removeCourse(scc.studentEmail, scc.year, scc.course.courseID);
        return Response.ok(scc).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response generateSchedule(SimpleCriteria sc)
    {
        try
        {
            List<Schedule> schedules =
                    desiredScheduleBean.generateSchedule(sc.studentEmail, sc.year);

            if (schedules.isEmpty())
            {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else if (schedules.get(0).isPerfect())
            {
                Schedule schedule = schedules.get(0);

                //build schedule dto
                SimpleSchedule ss = new SimpleSchedule();
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

            } else
            {
                //list of schedule dtos
                List<SimpleSchedule> ssList = new ArrayList<>();
                for (Schedule schedule : schedules)
                {
                    SimpleSchedule ss = new SimpleSchedule();
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
                    ssList.add(ss);
                }
                GenericEntity<List<SimpleSchedule>> wrapper =
                        new GenericEntity<List<SimpleSchedule>>(ssList)
                        {
                        };
                return Response.status(Response.Status.SEE_OTHER).entity(wrapper).build();
            }

        } catch (NoResultException nrex)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
