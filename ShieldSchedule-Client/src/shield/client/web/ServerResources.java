/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.web;

/**
 * A class which collects the URLs for web resources hosted on the schedule server.
 * @author Jeffrey Kabot
 */
public final class ServerResources
{

    private static final String SERVER_BASE_URL =
            "http://localhost:8080/ShieldSchedule-Server";

    /**
     * The location of the web resource exposing the list of all schools.
     */
    public static final String GET_ALL_SCHOOLS_URL =
            SERVER_BASE_URL + "/webresources/admin/schools";
    /**
     * The location of the web resources accepting add school requests.
     */
    public static final String ADD_SCHOOL_URL =
            SERVER_BASE_URL + "/webresources/admin/schools/add";
    /**
     * The location of the web resource accepting delete school requests.
     */
    public static final String DELETE_SCHOOL_URL =
            SERVER_BASE_URL + "/webresources/admin/schools/delete";
    
    /**
     * The location of the web resource accepting delete school requests.
     */
    public static final String GET_SCHOOL_URL =
            SERVER_BASE_URL + "/webresources/admin/schools/getSchool";

    /**
     * The location of the web resource accepting delete school requests.
     */
    public static final String EDIT_SCHOOL_URL =
            SERVER_BASE_URL + "/webresources/admin/schools/edit";
    
    /**
     * The location of the web resource exposing the list of all students.
     */
    public static final String GET_APPROVED_STUDENTS_URL =
            SERVER_BASE_URL + "/webresources/admin/students/approved";
    /**
     * The location of the web resource exposing the list of pending students.
     */
    public static final String GET_PENDING_STUDENTS_URL =
            SERVER_BASE_URL + "/webresources/admin/students/pending";
    /**
     * The location of the web resource accepting add student requests.
     */
    public static final String ADD_STUDENT_URL =
            SERVER_BASE_URL + "/webresources/admin/students/add";
    
    public static final String APPROVE_STUDENT_URL = 
            SERVER_BASE_URL + "/webresources/admin/students/approve";
    
    public static final String DELETE_STUDENT_URL = 
            SERVER_BASE_URL + "/webresources/admin/students/delete";
    
    public static final String AUTHENTICATION_URL = 
            SERVER_BASE_URL + "/webresources/authentication";
    
    public static final String GET_FRIEND_REQUESTS_URL = 
            SERVER_BASE_URL + "/webresources/students/friendrequests";
    
    public static final String ADD_FRIEND_REQUEST_URL = 
            SERVER_BASE_URL + "/webresources/students/friendrequests/add";
    
    public static final String APPROVE_FRIEND_REQUEST_URL = 
            SERVER_BASE_URL + "/webresources/students/friendrequests/accept";
    
    public static final String GET_FRIENDS_URL = 
            SERVER_BASE_URL + "/webresources/students/friends";
}
