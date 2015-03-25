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
import sss.ejb.AdminSchoolsBean;
import sss.entities.School;

/**
 * REST Web Service
 * Exposes the functionality of the AdminSchoolsBean to the client program
 * @author Jeffrey Kabot
 */
@Path("admin-schools") //the url at which this web service's resources are accessed
@RequestScoped
public class AdminSchoolsREST
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
    public AdminSchoolsREST()
    {
    }

    /**
     * Retrieves representation of an instance of sss.rest.AdminSchoolsREST
     * This default GET retrieves all the schools in the system's database
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public List<School> getJson()
    {
        return adminSchoolsBean.getAllSchools();
    }

    /**
     * PUT method for updating or creating an instance of AdminSchoolsREST
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content)
    {
        //parse content 
        
        //adminSchoolsBean.addSchool();
    }
}
