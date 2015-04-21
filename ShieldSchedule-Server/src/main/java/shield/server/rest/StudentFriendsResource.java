/*

 */
package shield.server.rest;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import shield.server.ejb.StudentFriendsBean;
import shield.server.entities.Student;
import shield.shared.dto.SimpleFriendRequest;
import shield.shared.dto.SimpleStudent;

/**
 * REST Web Service
 * Resource for accessing a list of a student's friends.
 * @author Jeffrey Kabot
 */
@Path("student/friends")
@RequestScoped
public class StudentFriendsResource
{

    @Context
    private UriInfo context;

    @Inject
    private StudentFriendsBean studentFriendsBean;

    /**
     * Creates a new instance of StudentFriendsResource
     */
    public StudentFriendsResource()
    {
    }

    /**
     * Retrieves a the friends list for a student
     * @param student the student whose friends list to retrieve.
     * @return the friends list.
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response getFriendsList(SimpleStudent student)
    {
        List<Student> friendsList = studentFriendsBean.getAllFriends(student.email);
        List<SimpleStudent> simpleFriendsList = new ArrayList<>(friendsList.size());
        SimpleStudent ss;
        for (Student s : friendsList)
        {
            ss = new SimpleStudent();
            ss.email = s.getEmail();
            ss.name = s.getName();
            simpleFriendsList.add(ss);
        }
        GenericEntity<List<SimpleStudent>> wrapper =
                new GenericEntity<List<SimpleStudent>>(simpleFriendsList)
                {
                };
        return Response.ok(wrapper).build();
    }

    /**
     * PUT method for updating or creating an instance of StudentFriendsResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content)
    {
        throw new UnsupportedOperationException();
    }
}
