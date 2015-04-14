/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sss.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import java.util.List;
import sss.entities.Student;
import javax.inject.Inject;
import sss.ejb.AdminStudentsBean;
import javax.ws.rs.POST;

/**
 * REST Web Service
 *
 * @author Jeffrey Kabot
 */
@Path("admin/students")
public class AdminStudentsResource
{

    @Context
    private UriInfo context;
    
    @Inject
    private AdminStudentsBean adminStudentsBean;

    /**
     * Creates a new instance of AdminStudentsREST
     */
    public AdminStudentsResource()
    {
    }

    /**
     * Retrieves representation of an instance of sss.ejb.AdminStudentsREST
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public List<Student> getStudents()
    {
        return adminStudentsBean.getAllStudents();
    }
    
    @GET
    @Path("/pending")
    @Produces("application/json")
    public List<Student> getPendingStudents()
    {
        return adminStudentsBean.getPendingStudents();
    }

    /**
     * PUT method for updating or creating an instance of AdminStudentsREST
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Path("/approve")
    @Consumes("application/json")
    public void approveStudent(String content)
    {
        //@TODO parse JSON content string
        adminStudentsBean.approveStudent("a@b.com", true);
    }
}
