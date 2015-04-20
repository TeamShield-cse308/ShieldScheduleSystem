/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;
import shield.server.entities.Student;
import javax.inject.Inject;
import shield.server.ejb.AdminStudentsBean;
import javax.ws.rs.POST;
import javax.enterprise.context.RequestScoped;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import shield.server.exceptions.AccountApprovedException;
import shield.shared.dto.SimpleStudent;

/**
 * REST Web Service
 *
 * @author Jeffrey Kabot
 */
@Path("admin/students")
@RequestScoped
public class AdminStudentsResource
{

    private static final Logger logger = Logger.getLogger(AdminStudentsResource.class.getName());

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
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public Response getStudents()
    {
        List<Student> students = adminStudentsBean.getAllStudents();

        List<SimpleStudent> simpleStudents = new ArrayList<>();
        SimpleStudent s;
        for (Student student : students)
        {
            s = new SimpleStudent();
            s.name = student.getName();
            s.email = student.getEmail();

            simpleStudents.add(s);
        }
        GenericEntity<List<SimpleStudent>> wrapper =
                new GenericEntity<List<SimpleStudent>>(simpleStudents)
                {
                };
        return Response.ok(wrapper).build();
    }

    @GET
    @Path("/pending")
    @Produces("application/json")
    public Response getPendingStudents()
    {
        List<Student> pendingStudents = adminStudentsBean.getPendingStudents();

        List<SimpleStudent> simpleStudents = new ArrayList<>();
        SimpleStudent s;
        for (Student student : pendingStudents)
        {
            s = new SimpleStudent();
            s.name = student.getName();
            s.email = student.getEmail();

            simpleStudents.add(s);
        }
        GenericEntity<List<SimpleStudent>> wrapper =
                new GenericEntity<List<SimpleStudent>>(simpleStudents)
                {
                };
        return Response.ok(wrapper).build();
    }

    /**
     * POST method for adding a student
     *
     * @param content representation for the resource // * @return an HTTP
     * response with content of the updated or created resource.
     */
    @POST
    @Path("/add")
    @Consumes("application/json")
    public Response addStudent(SimpleStudent student)
    {
        try
        {
            adminStudentsBean.addStudent(student.name, student.email, student.password, student.school);
            logger.log(Level.INFO, "OK Response");
            return Response.ok(student).build();
        } catch (EntityExistsException eeex)
        {
            logger.log(Level.WARNING, "BAD REQUEST");
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "BAD REQUEST");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * POST method for approving a student
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Path("/approve")
    @Consumes("application/json")
    public Response approveStudent(SimpleStudent student)
    {
        try
        {
            adminStudentsBean.approveStudent(student.email, true);
            logger.log(Level.INFO, "OK response");
            return Response.ok(student).build();
        } catch (AccountApprovedException aaex)
        {
            logger.log(Level.WARNING, "Account Approved, CONFLICT response", aaex);
            return Response.status(Response.Status.CONFLICT).build();
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No such account, BAD REQUEST response", nrex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }

    /**
     * POST method for deleting a student
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Path("/delete")
    @Consumes("application/json")
    public Response deleteStudent(SimpleStudent student)
    {
        try
        {
            adminStudentsBean.approveStudent(student.email, false);
            logger.log(Level.INFO, "OK response");
            return Response.ok(student).build();
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No such account, BAD REQUEST response", nrex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
}
