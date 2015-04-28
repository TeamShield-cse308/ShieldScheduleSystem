/*

 */
package shield.server.rest;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
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
import shield.server.entities.Friendship;
import shield.server.entities.Student;
import shield.shared.dto.SimpleFriendship;
import shield.shared.dto.SimpleStudent;

/**
 * REST Web Service Resource for accessing a list of a student's friends.
 *
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
     *
     * @param student the student whose friends list to retrieve.
     * @return the friends list.
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response getFriendsList(SimpleStudent student)
    {
        List<Student> friendsList = studentFriendsBean.getAllFriends(
                student.email);
        List<SimpleStudent> simpleFriendsList = new ArrayList<>(
                friendsList.size());
        SimpleStudent ss;
        for (Student s : friendsList)
        {
            ss = new SimpleStudent();
            ss.email = s.getEmail();
            ss.name = s.getName();
            simpleFriendsList.add(ss);
        }
        GenericEntity<List<SimpleStudent>> wrapper
                = new GenericEntity<List<SimpleStudent>>(simpleFriendsList)
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

    /**
     * Retrieves the list of friend requests for a student
     *
     * @param student the student whose friend requests to retrieve.
     * @return the friends requests.
     */
    @POST
    @Path("/requests")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getFriendRequests(SimpleStudent student)
    {
        List<Friendship> friendRequests = studentFriendsBean.getFriendRequests(
                student.email);
        List<SimpleFriendship> simpleFriendRequests = new ArrayList<>(
                friendRequests.size());
        SimpleFriendship sf;
        for (Friendship f : friendRequests)
        {
            sf = new SimpleFriendship();
            sf.senderName = f.getSender().getName();
            sf.senderEmail = f.getSender().getEmail();
            sf.recipientName = f.getRecipient().getName();
            sf.recipientEmail = f.getRecipient().getEmail();
            simpleFriendRequests.add(sf);
        }
        GenericEntity<List<SimpleFriendship>> wrapper
                = new GenericEntity<List<SimpleFriendship>>(simpleFriendRequests)
                {
                };
        return Response.ok(wrapper).build();
    }

    /**
     * POST method for creating a new friendship
     *
     * @param sf the friendship to add
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Path("/add")
    @Consumes("application/json")
    public Response addFriend(SimpleFriendship sf)
    {
        try
        {
            studentFriendsBean.addFriend(sf.senderEmail,
                    sf.recipientName);
            return Response.ok(sf).build();
        } catch (NoResultException nrex)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * POST method for approving or denying friend requests.
     * @param sf The Friendship message sent by the client.
     * @return 
     */
    @POST
    @Path("/approve")
    @Consumes("application/json")
    public Response approveFriendRequest(SimpleFriendship sf)
    {
        try
        {
            studentFriendsBean.approveFriend(sf.senderEmail,
                    sf.recipientEmail, sf.approved);
            return Response.ok(sf).build();
        } catch (Exception ex)
        {
            return Response.serverError().build();
        }
    }
    
    /**
     * POST method for deleting friends.
     * @param sf The Friendship message sent by the client.
     * @return 
     */
    @POST
    @Path("/delete")
    @Consumes("application/json")
    public Response deleteFriend(SimpleFriendship sf)
    {
        try
        {
            studentFriendsBean.approveFriend(sf.senderEmail,
                    sf.recipientEmail, false);
            return Response.ok(sf).build();
        } catch (Exception ex)
        {
            return Response.serverError().build();
        }
    }
}
