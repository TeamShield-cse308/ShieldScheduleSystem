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
 * Exposes the functionality of the AdminStudentsBean to the client.
 *
 * @author Jeffrey Kabot
 */
@Path("admin/students")
@RequestScoped
public class AdminStudentsResource
{

    //Logger
    private static final Logger logger = Logger.getLogger(AdminStudentsResource.class.getName());

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
     * Responds to GET requests at the /approved extension of this resource.
     * Retrieves the list of approved Students
     *
     * @return the list of approved students.
     */
    @GET
    @Path("/approved")
    @Produces("application/json")
    public Response getApprovedStudents()
    {
        List<Student> students = adminStudentsBean.getApprovedStudents();

        //convert the list of Student entities to a stripped down version readable by the client
        List<SimpleStudent> simpleStudents = new ArrayList<>();
        SimpleStudent s;
        for (Student student : students)
        {
            s = new SimpleStudent();
            s.name = student.getName();
            s.email = student.getEmail();

            simpleStudents.add(s);
        }
        //a wrapper for the list of students
        GenericEntity<List<SimpleStudent>> wrapper =
                new GenericEntity<List<SimpleStudent>>(simpleStudents)
                {
                };
        return Response.ok(wrapper).build();
    }

    /**
     * Responds to GET requests at the /pending extension of this resource.
     * Retrieves the list of pending students.
     *
     * @return the list of pending students.
     */
    @GET
    @Path("/pending")
    @Produces("application/json")
    public Response getPendingStudents()
    {
        List<Student> pendingStudents = adminStudentsBean.getPendingStudents();

        //convert the student entities to a stripped down version readable by the client
        List<SimpleStudent> simpleStudents = new ArrayList<>();
        SimpleStudent s;
        for (Student student : pendingStudents)
        {
            s = new SimpleStudent();
            s.name = student.getName();
            s.email = student.getEmail();
            

            simpleStudents.add(s);
        }
        //a wrapper for the list of students
        GenericEntity<List<SimpleStudent>> wrapper =
                new GenericEntity<List<SimpleStudent>>(simpleStudents)
                {
                };
        return Response.ok(wrapper).build();
    }

    /**
     * Responds to POST requests at the /add extension of this resource. Adds a
     * student to the database.
     *
     * @param student struct with student info passed by client.
     * @return
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
            //@TODO disambiguate errors
            logger.log(Level.WARNING, "BAD REQUEST");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Responds to POST requests at /approve extension. Approves a pending
     * student account.
     *
     * @param student the struct of info about the student to approve, passed by
     * client
     * @return
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
     * Delete a student.
     *
     * @param student the client-supplied info about the student to delete.
     * @return
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
