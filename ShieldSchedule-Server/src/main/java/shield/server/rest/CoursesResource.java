/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.rest;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import shield.server.ejb.AdminSchoolsBean;
import shield.server.ejb.CoursesBean;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleStudent;

/**
 *
 * @author evanguby
 */
@Path("courses")
@RequestScoped
public class CoursesResource {

    //Logger
    private static final Logger logger = Logger.getLogger(AdminStudentsResource.class.getName());

    @Context
    private UriInfo context;

    @Inject
    private CoursesBean coursesBean;
    
//    @Inject
//    private AdminSchoolsBean adminSchoolsBean;
    
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
            coursesBean.addCourse(course.name,course.school,course.semester);
            //adminSchoolsBean.addCourse()
            logger.log(Level.INFO, "OK Response");
            return Response.ok(course).build();
        } catch (EntityExistsException eeex)
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

}