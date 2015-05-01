/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.rest;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import shield.server.ejb.AdminSchoolsBean;
import shield.server.entities.School;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import shield.shared.dto.SimpleSchool;

/**
 * REST Web Service Exposes the functionality of the AdminSchoolsBean to the
 * client program
 *
 * @author Jeffrey Kabot
 */
@Path("admin/schools") //the url at which this web service's resources are accessed
@RequestScoped
public class AdminSchoolsResource
{

    @Context
    private UriInfo context;

    //link the bean whose functionallity we expose
    @Inject
    private AdminSchoolsBean adminSchoolsBean;

    //Logger
    private static final Logger logger =
            Logger.getLogger(AdminSchoolsResource.class.getName());

    /**
     * Creates a new instance of AdminSchoolsREST
     */
    public AdminSchoolsResource()
    {
    }

    /**
     * Responds to GET requests at this resources base path. Retrieves all the
     * schools in the system's database
     *
     * @return an HTTP response containing the list of all schools or an error
     * code.
     */
    @GET
    @Produces("application/json")
    public Response getAllSchools()
    {
        List<School> allSchools = adminSchoolsBean.getAllSchools();
        
        //convert the School entities to a stripped down version readable by the client
        List<SimpleSchool> simpleSchools = new ArrayList<>();
        SimpleSchool s;
        for (School school : allSchools)
        {
            s = new SimpleSchool();
            s.name = school.getSchoolName();
            s.numPeriods = school.getPeriods();
            s.numSemesters = school.getSemesters();
            s.startingLunchPeriod = school.getStartingLunch();
            s.endingLunchPeriod = school.getEndingLunch();
            s.numScheduleDays = school.getScheduleDays();
            simpleSchools.add(s);
        }
        GenericEntity<List<SimpleSchool>> wrapper =
                new GenericEntity<List<SimpleSchool>>(simpleSchools)
                {
                };
        return Response.ok(wrapper).build();
    }

    /**
     * Responds to POST requests at the /add extension of this resource. Adds a
     * school with the supplied information to the database.
     *
     * @param school the struct received from the client.
     * @return HTTP response to client
     */
    @POST
    @Path("/add")
    @Consumes("application/json")
    public Response addSchool(SimpleSchool school)
    {
        try
        {
            //add the school
            adminSchoolsBean.addSchool(school.name, school.numSemesters, school.numPeriods,
                    school.numScheduleDays, school.startingLunchPeriod, school.endingLunchPeriod);
            logger.log(Level.INFO, "OK response");
            return Response.ok(school).build();
        } catch (EntityExistsException eeex)
        {
            logger.log(Level.WARNING, "BAD REQUEST response");
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (NoResultException eeex)
        {
            //@TODO disambiguate with previous exception
            logger.log(Level.WARNING, "BAD REQUEST response");
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }

    /**
     * Responds to POST requests at the /edit extension of this resource. Edits
     * a school in the database with the supplied information.
     *
     * @param school struct with the new school information
     * @return HTTP response to client
     */
    @POST
    @Path("/edit")
    @Consumes("application/json")
    public Response editSchool(SimpleSchool school)
    {
        try
        {
            //edit the school
            adminSchoolsBean.editSchool(school.name, school.numSemesters, school.numPeriods,
                    school.numScheduleDays, school.startingLunchPeriod, school.endingLunchPeriod);
            return Response.ok(school).build();
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "BAD REQUEST response", nrex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }

    /**
     * Responds to POST requests at the /delete extension of this resource.
     * Deletes a school with the supplied information to the database.
     *
     * @param content
     */
    @POST
    @Path("/delete")
    @Consumes("application/json")
    public Response deleteSchool(SimpleSchool school)
    {
        try
        {
            //delete the school
            adminSchoolsBean.deleteSchool(school.name);
            return Response.ok(school).build();
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "BAD REQUEST response", nrex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/getSchool")
    @Produces("application/json")
    public Response getSchool(String school)
    {
        try
        {
            SimpleSchool ss = adminSchoolsBean.getSchool(school);
            GenericEntity<SimpleSchool> wrapper =
                new GenericEntity<SimpleSchool>(ss)
                {
                };
        return Response.ok(wrapper).build();
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "BAD REQUEST response", nrex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }

}
