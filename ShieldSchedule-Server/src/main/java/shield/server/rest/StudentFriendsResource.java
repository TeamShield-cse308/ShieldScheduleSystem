/*

 */
package shield.server.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    //Logger
    private static final Logger logger =
            Logger.getLogger(StudentFriendsResource.class.getName());

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
        GenericEntity<List<SimpleStudent>> wrapper =
                new GenericEntity<List<SimpleStudent>>(simpleFriendsList)
                {
                };
        logger.log(Level.INFO, "OK response");
        return Response.ok(wrapper).build();
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
        GenericEntity<List<SimpleFriendship>> wrapper =
                new GenericEntity<List<SimpleFriendship>>(simpleFriendRequests)
                {
                };
        logger.log(Level.INFO, "OK response");
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
            //if the recipient's email was specified, send a direct friendship request
            if (sf.recipientEmail != null)
            {
                studentFriendsBean.addFriendByEmail(sf.senderEmail,
                        sf.recipientEmail);
                logger.log(Level.INFO, "OK response");
                return Response.ok(sf).build();
            } else //otherwise we have to search by the recipients name
            {
                List<Student> recipients =
                        studentFriendsBean.addFriendByName(sf.senderEmail,
                                sf.recipientName);

                //if there was just one result then return OK
                if (recipients.size() == 1)
                {
                    logger.log(Level.INFO, "OK response");
                    return Response.ok(sf).build();
                } else
                {
                    //if there were multiple results then send the list of students with that name
                    //response code is set to SEE OTHER to disambiguate from a single result
                    //this is not standard use of SEE OTHER, but JAX-RS does not have MULTIPLE CHOICES code
                    List<SimpleStudent> simpleRecipients = new ArrayList<>(
                            recipients.size());
                    SimpleStudent ss;
                    for (Student s : recipients)
                    {
                        ss = new SimpleStudent();
                        ss.email = s.getEmail();
                        ss.name = s.getName();
                        simpleRecipients.add(ss);
                    }
                    GenericEntity<List<SimpleStudent>> wrapper =
                            new GenericEntity<List<SimpleStudent>>(simpleRecipients)
                            {
                            };
                    logger.log(Level.INFO, "SEE OTHER response");
                    return Response.status(Response.Status.SEE_OTHER).entity(wrapper).build();
                }
            }
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "Bad Request response");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * POST method for approving or denying friend requests.
     *
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
        } catch (NoResultException nrex)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("The friendship does not exist").build();
        }
    }

    /**
     * POST method for deleting friends.
     *
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
        } catch (NoResultException nrex)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("The friendship does not exit").build();
        }
    }
}
