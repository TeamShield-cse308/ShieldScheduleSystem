package shield.client.web;

/**
 * A class which collects the URLs for web resources hosted on the shield application server.
 * @author Jeffrey Kabot
 */
public final class ServerResources
{
    /**
     * The base url of webresources on the shield application server.
     */
    private static final String SERVER_BASE_URL =
            "http://localhost:8080/ShieldSchedule-Server/webresources/";

    /**
     * The location of the web resource exposing the list of all schools.
     */
    public static final String GET_ALL_SCHOOLS_URL =
            SERVER_BASE_URL + "admin/schools";
    /**
     * The location of the web resources accepting add school requests.
     */
    public static final String ADD_SCHOOL_URL =
            SERVER_BASE_URL + "admin/schools/add";
    /**
     * The location of the web resource accepting delete school requests.
     */
    public static final String DELETE_SCHOOL_URL =
            SERVER_BASE_URL + "admin/schools/delete";
    /**
     * The location of the web resource accepting edit school requests.
     */
    public static final String EDIT_SCHOOL_URL =
            SERVER_BASE_URL + "admin/schools/edit";
    
    /**
     * The location of the web resource exposing the list of approved students.
     */
    public static final String GET_APPROVED_STUDENTS_URL =
            SERVER_BASE_URL + "admin/students/approved";
    /**
     * The location of the web resource exposing the list of pending students.
     */
    public static final String GET_PENDING_STUDENTS_URL =
            SERVER_BASE_URL + "admin/students/pending";
    /**
     * The location of the web resource accepting add student requests.
     */
    public static final String ADD_STUDENT_URL =
            SERVER_BASE_URL + "admin/students/add";
    /**
     * The location of the web resource accepting approve student requests.
     */
    public static final String APPROVE_STUDENT_URL = 
            SERVER_BASE_URL + "admin/students/approve";
    /**
     * The location of the web resource accepting delete student requests.
     */
    public static final String DELETE_STUDENT_URL = 
            SERVER_BASE_URL + "admin/students/delete";
    
    /**
     * The location of the web resource accepting log in requests.
     */
    public static final String AUTHENTICATION_URL = 
            SERVER_BASE_URL + "authentication";
    
    /**
     * The location of the web resource friend exposing lists of friend requests.
     */
    public static final String GET_FRIEND_REQUESTS_URL = 
            SERVER_BASE_URL + "students/friendrequests";
    /**
     * The location of the web resource accepting requests for the creation of new friend requests.
     */
    public static final String ADD_FRIEND_REQUEST_URL = 
            SERVER_BASE_URL + "students/friendrequests/add";
    /**
     * The location of the web resource accepting requests for approve friend requests.
     */
    public static final String APPROVE_FRIEND_REQUEST_URL = 
            SERVER_BASE_URL + "students/friendrequests/accept";
    
    /**
     * The location of the web resource exposing lists of friends.
     */
    public static final String GET_FRIENDS_URL = 
            SERVER_BASE_URL + "students/friends";
}
