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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import shield.server.ejb.StudentFriendRequestsBean;
import shield.server.entities.FriendRequest;
import shield.shared.dto.SimpleFriendRequest;
import shield.shared.dto.SimpleStudent;

/**
 * REST Web Service
 *
 * @author Jeffrey Kabot
 */
@Path("student/friendrequests")
@RequestScoped
public class StudentFriendRequestsResource
{

    @Context
    private UriInfo context;

    @Inject
    private StudentFriendRequestsBean studentFriendsBean;

    /**
     *
     * Creates a new instance of StudentFriendsResource
     */
    public StudentFriendRequestsResource()
    {
    }

    /**
     * Retrieves representation of an instance of
 shield.server.ejb.StudentFriendRequestsResource
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response retrieveFriendRequsets(
            SimpleStudent recipient)
    {
        List<FriendRequest> friendRequests = studentFriendsBean.getFriendRequests(recipient.email);
        List<SimpleFriendRequest> simpleFriendRequests = new ArrayList<>(friendRequests.size());
        SimpleFriendRequest sfr;
        for (FriendRequest fr : friendRequests)
        {
            sfr = new SimpleFriendRequest();
            sfr.senderName = fr.getSender().getName();
            sfr.senderEmail = fr.getSender().getEmail();
            sfr.recipientName = fr.getSender().getName();
            sfr.recipientEmail = fr.getSender().getName();
            simpleFriendRequests.add(sfr);
        }
        GenericEntity<List<SimpleFriendRequest>> wrapper =
                new GenericEntity<List<SimpleFriendRequest>>(simpleFriendRequests)
                {
                };
        return Response.ok(wrapper).build();
    }

    /**
     * POST method for updating or creating an instance of StudentFriendRequestsResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Path("/add")
    @Consumes("application/json")
    public Response addFriendRequest(SimpleFriendRequest sfr)
    {
        try
        {
            studentFriendsBean.createFriendRequest(sfr.senderEmail, sfr.recipientName);
            return Response.ok(sfr).build();
        } catch (NoResultException nrex)
        {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("/accept")
    @Consumes("application/json")
    public Response acceptFriendRequest(SimpleFriendRequest sfr)
    {
        try
        {
            studentFriendsBean.acceptFriendRequest(sfr.senderEmail, sfr.recipientEmail);
            return Response.ok(sfr).build();
        } catch (Exception ex)
        {
            return Response.serverError().build();
        }
    }
}
