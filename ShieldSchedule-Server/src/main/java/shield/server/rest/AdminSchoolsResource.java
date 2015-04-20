/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.rest;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
//import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import shield.server.ejb.AdminSchoolsBean;
import shield.server.entities.School;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import shield.shared.dto.SimpleSchool;

/**
 * REST Web Service Exposes the functionality of the AdminSchoolsBean to the
 * client program
 * 
 * Some code adapted from http://www.studytrails.com/java/json/java-jackson-json-tree-parsing.jsp
 *
 * @author Jeffrey Kabot
 */
@Path("admin/schools") //the url at which this web service's resources are accessed
@RequestScoped
public class AdminSchoolsResource
{

    //test

    @Context
    private UriInfo context;

    //link the bean whose functionallity we expose
    @Inject
    private AdminSchoolsBean adminSchoolsBean;

    //Logger
    private static final Logger logger = 
            Logger.getLogger("sss.rest.AdminSchoolsREST");
    
    //the reader for JSON messages
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Creates a new instance of AdminSchoolsREST
     */
    public AdminSchoolsResource()
    {
    }

    /**
     * Retrieves representation of an instance of sss.rest.AdminSchoolsREST This
     * default GET retrieves all the schools in the system's database
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public List<SimpleSchool> getAllSchools()
    {
        List<School> allSchools = adminSchoolsBean.getAllSchools();
        List<SimpleSchool> translatedSchools = new ArrayList<>();
        SimpleSchool s;
        for (School school : allSchools)
        {
            s = new SimpleSchool();
            s.name = school.getSchoolName();
            s.numPeriods = "" + school.getPeriods();
            s.numSemesters = "" + school.getSemesters();
            s.startingLunchPeriod = "" + school.getStartingLunch();
            s.endingLunchPeriod = "" + school.getEndingLunch();
            s.numScheduleDays = "" + school.getScheduleDays();
            translatedSchools.add(s);
        }
        return translatedSchools;
    }

    /**
     * POST method for creating a school
     *
     * @param content representation for the resource
//     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Path("/add")
    @Consumes("application/json")
    public void addSchool(String content)
    {
        try {
            //@TODO ensure correct JSON keys
            JsonNode node = mapper.readTree(content);
            String name = node.get("name").asText();
            int numSemesters = node.get("numSemesters").asInt();
            int numPeriods = node.get("numPeriods").asInt();
            int numScheduleDays = node.get("numScheduleDays").asInt();
            int startLunch = node.get("startingLunchPeriod").asInt();
            int endLunch = node.get("endingLunchPeriod").asInt();
            
            adminSchoolsBean.addSchool(name, numSemesters, numPeriods, 
                    numScheduleDays, startLunch, endLunch);
            
            //@TODO logging
            
            //@TODO error handling
        } catch (IOException ioex) {
            Logger.getLogger(AdminSchoolsResource.class.getName()).log(Level.SEVERE, null, ioex);
        }
    }

    /**
     * Resource for editing a particular school
     *
     * @param content
     */
    @POST
    @Path("/edit")
    @Consumes("application/json")
    public void editSchool(String content)
    {
        try {
            //@TODO ensure correct JSON keys
            JsonNode node = mapper.readTree(content);
            String oldName = node.get("oldName").asText();
            String newName = node.get("newName").asText();
            int newSemesters = node.get("newSemesters").asInt();
            int newPeriods = node.get("newPeriods").asInt();
            int newScheduleDays = node.get("newScheduleDays").asInt();
            int newStartLunch = node.get("newStartingLunchPeriod").asInt();
            int newEndLunch = node.get("newEndingLunchPeriod").asInt();
            
            adminSchoolsBean.editSchool(oldName, newName, newSemesters, 
                    newPeriods, newScheduleDays, newStartLunch, newEndLunch);
        } 
        catch(IOException ioex) {
            Logger.getLogger(AdminSchoolsResource.class.getName()).log(Level.SEVERE, null, ioex);
        }
    }

    //@TODO POST or DELETE?
    /**
     * Resource for removing a school
     * @param content 
     */
    @POST
    @Path("/delete")
    @Consumes("application/json")
    public void deleteSchool(String content)
    {
        //try {
           // JsonNode node = mapper.readTree(content);

            ///@TODO ensure correct JSON key
           // String name = node.get("name").asText();
            String name = content;
            adminSchoolsBean.deleteSchool(name);
            
            //@TODO logging
            //@TODO error handling
       // } catch (IOException ex) {
          //  Logger.getLogger(AdminSchoolsResource.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }

}
