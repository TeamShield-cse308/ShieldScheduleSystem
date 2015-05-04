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
import shield.server.ejb.ScheduleBean;
import shield.server.ejb.ScheduleBlockBean;
import shield.shared.dto.SimpleSchedule;
import shield.shared.dto.SimpleScheduleBlock;

/**
 *
 * @author evanguby
 */
@Path("schedule")
@RequestScoped
public class ScheduleResource {
    private static final Logger logger = Logger.getLogger(ScheduleResource.class.getName());

    @Context
    private UriInfo context;

    @Inject
    private ScheduleBean scheduleBean;
    
    public ScheduleResource()
    {
        
    }
    
    @POST
    @Path("/setAssignedSchedule")
    @Consumes("application/json")
    public Response addScheduleBlock(SimpleSchedule scheduleBlock)
    {
        try
        {
            scheduleBean.setAssignedSchedule(scheduleBlock);
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
