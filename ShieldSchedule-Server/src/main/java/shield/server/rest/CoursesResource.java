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
import shield.server.ejb.CoursesBean;
import shield.server.entities.Course;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleStudent;

/**
 *
 * @author evanguby
 */
@Path("courses")
@RequestScoped
public class CoursesResource
{

    //Logger
    private static final Logger logger = Logger.getLogger(AdminStudentsResource.class.getName());

    @Context
    private UriInfo context;

    @Inject
    private CoursesBean coursesBean;

    /**
     * Creates a new instance of CoursesREST
     */
    public CoursesResource()
    {
    }

    @POST
    @Path("/add")
    @Consumes("application/json")
    public Response addCourse(SimpleCourse course)
    {
        try
        {
            coursesBean.addCourse(course.identifier, course.name, course.school, course.year);
            logger.log(Level.INFO, "OK Response");
            return Response.ok(course).build();
        } catch (RollbackException rex)
        {
            logger.log(Level.WARNING, "BAD REQUEST");
            return Response.status(Response.Status.BAD_REQUEST).entity("Course with that identifier already exists").build();
        }
    }

    @POST
    @Path("/getSchoolCourses")
    @Consumes("application/json")
    public Response getSchoolCourses(SimpleStudent stu)
    {
        try
        {
            List<Course> courseList = coursesBean.getSchoolCourses(stu.school);

            List<SimpleCourse> simpleCourses = new ArrayList<>();
            SimpleCourse c;
            for (Course course : courseList)
            {
                c = new SimpleCourse();
                c.name = course.getName();
                c.identifier = course.getIdentifier();
                c.courseID = course.getId();
                c.year = course.getYear();
                simpleCourses.add(c);
            }
            //a wrapper for the list of courses
            GenericEntity<List<SimpleCourse>> wrapper =
                    new GenericEntity<List<SimpleCourse>>(simpleCourses)
                    {
                    };
            logger.log(Level.INFO, "Sending list of courses");
            return Response.ok(wrapper).build();
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "BAD REQUEST response, school doesn't exist");
            return Response.status(Response.Status.BAD_REQUEST).entity("School doesn't exist").build();
        }
    }

    @POST
    @Path("/getSchoolCoursesWithLunch")
    @Consumes("application/json")
    public Response getSchoolCoursesWithLunch(SimpleStudent stu)
    {
        try
        {
            List<Course> courseList = coursesBean.getSchoolCoursesWithLunch(stu.school);

            List<SimpleCourse> simpleCourses = new ArrayList<>();
            SimpleCourse c;
            for (Course course : courseList)
            {
                c = new SimpleCourse();
                c.name = course.getName();
                c.identifier = course.getIdentifier();
                c.courseID = course.getId();
                c.year = course.getYear();
                simpleCourses.add(c);
            }
            //a wrapper for the list of courses
            GenericEntity<List<SimpleCourse>> wrapper =
                    new GenericEntity<List<SimpleCourse>>(simpleCourses)
                    {
                    };
            logger.log(Level.INFO, "Sending list of courses");
            return Response.ok(wrapper).build();
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "BAD REQUEST response, school doesn't exist");
            return Response.status(Response.Status.BAD_REQUEST).entity("School doesn't exist").build();

        }
    }
}
