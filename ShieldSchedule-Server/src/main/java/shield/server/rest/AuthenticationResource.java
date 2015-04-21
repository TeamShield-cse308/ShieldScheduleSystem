/*

 */
package shield.server.rest;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import shield.server.ejb.AuthenticationBean;
import shield.server.entities.GenericUser;
import shield.server.exceptions.AccountActiveException;
import shield.server.exceptions.AccountPendingException;
import shield.server.exceptions.WrongPasswordException;
import shield.shared.dto.LoginCredentials;

/**
 * REST Web Service
 *
 * @author Jeffrey Kabot
 */
@Path("authentication")
@RequestScoped
public class AuthenticationResource
{

    //Logger
    private static final Logger logger = Logger.getLogger(AuthenticationResource.class.getName());

    @Context
    private UriInfo context;

    @Inject
    private AuthenticationBean authenticationBean;

    /**
     * Creates a new instance of AuthenticationResource
     */
    public AuthenticationResource()
    {
    }

    /**
     * Retrieves representation of an instance of
     * shield.server.rest.AuthenticationResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson()
    {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of AuthenticationResource
     *
     * @param login The bundle of login information sent from the client
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    public Response authenticateUser(LoginCredentials login)
    {
        GenericUser user;
        try
        {
            user = authenticationBean.authenticate(login.username, login.password);
            //@TODO convert user to json format
            logger.log(Level.INFO, "Authentication success, OK response");
            return Response.ok(user).build();
        } catch (WrongPasswordException wpex)
        {
            logger.log(Level.WARNING, "UNAUTHORIZED resposne");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (AccountPendingException apax)
        {
            logger.log(Level.WARNING, "Account pending, FORBIDDEN response");
            return Response.status(Response.Status.FORBIDDEN).build();
        } catch (AccountActiveException aaex)
        {
            logger.log(Level.WARNING, "Account already active, CONFLICT resposne");
            return Response.status(Response.Status.CONFLICT).build();
        } catch (NoResultException nrex)
        {
            logger.log(Level.WARNING, "No such account, BAD REQUEST response");
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception ex)
        {
            logger.log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
}