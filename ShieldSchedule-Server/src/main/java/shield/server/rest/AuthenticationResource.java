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
import shield.server.entities.Administrator;
import shield.server.entities.GenericUser;
import shield.server.entities.Student;
import shield.server.exceptions.AccountActiveException;
import shield.server.exceptions.AccountPendingException;
import shield.server.exceptions.WrongPasswordException;
import shield.shared.dto.SimpleStudent;
import shield.shared.dto.LoginCredentials;
import shield.shared.dto.SimpleAdmin;

/**
 * Exposes functionality of authenticationBean. Gateway to log-in requests on
 * server.
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
     * POST method for validating user credentials
     *
     * @param login The bundle of login information sent from the client
     * @return an HTTP response with the user's account info or an error code.
     */
    @POST
    @Consumes("application/json")
    public Response authenticateUser(LoginCredentials login)
    {
        GenericUser user;
        try
        {
            user = authenticationBean.authenticate(login.username, login.password);
            logger.log(Level.INFO, "Authentication success, OK response");
            if (user instanceof Student)
            {
                SimpleStudent acct = new SimpleStudent();
                acct.email = ((Student) user).getEmail();
                acct.school = ((Student) user).getSchool().getSchoolName();
                acct.name = user.getName();
                acct.password = user.getPassword();
                return Response.ok(acct).build();
            } else
            {
                SimpleAdmin acct = new SimpleAdmin();
                acct.username = ((Administrator) user).getUsername();
                acct.name = user.getName();
                acct.password = user.getPassword();
                return Response.ok(acct).build();
            }
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
            //logger.log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
}
