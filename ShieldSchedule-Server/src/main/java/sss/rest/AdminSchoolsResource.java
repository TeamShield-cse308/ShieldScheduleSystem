/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sss.rest;

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
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import sss.ejb.AdminSchoolsBean;
import sss.entities.School;

/**
 * REST Web Service
 * Exposes the functionality of the AdminSchoolsBean to the client program
 * @author Jeffrey Kabot
 */
@Path("admin/schools") //the url at which this web service's resources are accessed
@RequestScoped
public class AdminSchoolsResource
{
    //test
    @Context
    private UriInfo context;

    //link the bean whose functionallity we expose
    @Inject
    private AdminSchoolsBean adminSchoolsBean;
    
    //Logger
    private static final Logger logger = Logger.getLogger("sss.rest.AdminSchoolsREST");
    
    
    /**
     * Creates a new instance of AdminSchoolsREST
     */
    public AdminSchoolsResource()
    {
    }

    /**
     * Retrieves representation of an instance of sss.rest.AdminSchoolsREST
     * This default GET retrieves all the schools in the system's database
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public List<School> getAllSchools()
    {
        return adminSchoolsBean.getAllSchools();
    }

    /**
     * POST method for creating a school
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Path("/add")
    @Consumes("application/json")
    public void addSchool(String content)
    {
        //@TODO parse JSON content string
//        String name = "TEST_SCHOOL_NAME";
//        int semesters = 4;
//        int periods = 9;
//        int scheduleDays = 5;
//        int startLunch = 3;
//        int endLunch = 6;
        //@TODO no placeholder
        adminSchoolsBean.addSchool("TEST_SCHOOL_NAME", 4, 9, 5,
                6, 3);
    }
    
    /**
     * Resource for editing a particular school
     * @param content 
     */
    @POST
    @Path("/edit")
    @Consumes("application/json")
    public void editSchool(String content)
    {
        //@TODO parse JSON content string
        //@TODO no placeholder
        adminSchoolsBean.editSchool("TEST_SCHOOL_NAME", "NEW_SCHOOL_NAME 2 5 2 2 4");
    }
    //@TODO POST or DELETE?
    @POST
    @Path("/delete")
    @Consumes("application/json")
    public void deleteSchool(String content)
    {
        //@TODO parse JSON content string
        //@TODO no placeholder
        adminSchoolsBean.deleteSchool("TEST_SCHOOL_NAME");
    }
    
}
