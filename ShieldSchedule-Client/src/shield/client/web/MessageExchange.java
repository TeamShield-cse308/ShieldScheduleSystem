/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.web;

/**
 *
 * @author Jeffrey Kabot
 */
public class MessageExchange
{
    /**
     * The location of the web resource exposing the list of all schools.
     */
    public static final String GET_ALL_SCHOOLS_URL = 
            "http://localhost:8080/ShieldSchedule-Server/webresources/admin/schools";
    
    public static final String ADD_SCHOOL_URL = 
            "http://localhost:8080/ShieldSchedule-Server/webresources/admin/schools/add";
    
}
