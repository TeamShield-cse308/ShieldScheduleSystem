/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.rest;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import shield.server.ejb.ScheduleBlockBean;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleScheduleBlock;

/**
 *
 * @author evanguby
 */
@Path("scheduleBlock")
@RequestScoped
public class ScheduleBlockResource {
        //Logger
    private static final Logger logger = Logger.getLogger(ScheduleBlockResource.class.getName());

    @Context
    private UriInfo context;

    @Inject
    private ScheduleBlockBean scheduleBlockBean;
    
    public ScheduleBlockResource()
    {
        
    }
    
    @POST
    @Path("/add")
    @Consumes("application/json")
    public Response addScheduleBlock(SimpleScheduleBlock scheduleBlock)
    {
         try
        {
            scheduleBlockBean.addScheduleBlock(scheduleBlock);
            logger.log(Level.INFO, "OK Response");
            return Response.ok(scheduleBlock).build();
        } catch (RollbackException rex)
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
}
