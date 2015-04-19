/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import java.util.List;
import shield.server.entities.Student;
import javax.inject.Inject;
import shield.server.ejb.AdminStudentsBean;
import javax.ws.rs.POST;
import javax.enterprise.context.RequestScoped;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * REST Web Service
 *
 * @author Jeffrey Kabot
 */
@Path("admin/students")
@RequestScoped
public class AdminStudentsResource
{

    @Context
    private UriInfo context;
    
    @Inject
    private AdminStudentsBean adminStudentsBean;
    
    //The parser for incoming JSON messages
    ObjectMapper mapper = new ObjectMapper();

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
        try {
            JsonNode node = mapper.readTree(content);
            //@TODO ensure correct JSON keys
            String email = node.get("email").asText();
            boolean approved = node.get("approved").asBoolean();
            adminStudentsBean.approveStudent(email, approved);
            //@TODO error handling
            
            //@TODO logging
        } catch (IOException ex) {
            Logger.getLogger(AdminStudentsResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * POST method for adding a student
     *
     * @param content representation for the resource
//     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Path("/add")
    @Consumes("application/json")
    public void addStudent(String content)
    {
        try {
            //@TODO ensure correct JSON keys
            JsonNode node = mapper.readTree(content);
            String name = node.get("name").asText();
            String email = node.get("email").asText();
            String state = node.get("state").asText();
            String password = node.get("password").asText();
            String school = node.get("school").asText();
            
            adminStudentsBean.addStudent(name, email, password, school);
            
            //@TODO logging
            
            //@TODO error handling
        } catch (IOException ioex) {
            Logger.getLogger(AdminSchoolsResource.class.getName()).log(Level.SEVERE, null, ioex);
        }
    }
}
