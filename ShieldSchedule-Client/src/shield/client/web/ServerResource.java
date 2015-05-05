package shield.client.web;

/**
 * ServerResources enclose URLs for web resources hosted on the shield
 * application server. ServerResources cannot be created dynamically by the
 * client. Instead all the usable ServerResources are statically pre-defined and
 * accessed here.
 *
 * @author Jeffrey Kabot
 */
public final class ServerResource
{

    /**
     * The base url of web resources on the shield application server.
     */
    private static final String SERVER_BASE_URL =
            "http://localhost:8080/ShieldSchedule-Server/webresources/";

    /**
     * The location of the web resource exposing the list of all schools.
     */
    public static final ServerResource GET_ALL_SCHOOLS_URL = new ServerResource(
            SERVER_BASE_URL + "admin/schools");
    /**
     * The location of the web resources accepting add school requests.
     */
    public static final ServerResource ADD_SCHOOL_URL = new ServerResource(
            SERVER_BASE_URL + "admin/schools/add");
    /**
     * The location of the web resource accepting delete school requests.
     */
    public static final ServerResource DELETE_SCHOOL_URL = new ServerResource(
            SERVER_BASE_URL + "admin/schools/delete");
    /**
     * The location of the web resource accepting edit school requests.
     */
    public static final ServerResource EDIT_SCHOOL_URL = new ServerResource(
            SERVER_BASE_URL + "admin/schools/edit");

    public static final ServerResource GET_SCHOOL_URL = new ServerResource(
            SERVER_BASE_URL + "admin/schools/getSchool");

    /**
     * The location of the web resource exposing the list of approved students.
     */
    public static final ServerResource GET_APPROVED_STUDENTS_URL = new ServerResource(
            SERVER_BASE_URL + "admin/students/approved");
    /**
     * The location of the web resource exposing the list of pending students.
     */
    public static final ServerResource GET_PENDING_STUDENTS_URL = new ServerResource(
            SERVER_BASE_URL + "admin/students/pending");
    /**
     * The location of the web resource accepting add student requests.
     */
    public static final ServerResource ADD_STUDENT_URL = new ServerResource(
            SERVER_BASE_URL + "admin/students/add");
    /**
     * The location of the web resource accepting approve student requests.
     */
    public static final ServerResource APPROVE_STUDENT_URL = new ServerResource(
            SERVER_BASE_URL + "admin/students/approve");
    /**
     * The location of the web resource accepting delete student requests.
     */
    public static final ServerResource DELETE_STUDENT_URL = new ServerResource(
            SERVER_BASE_URL + "admin/students/delete");

    /**
     * The location of the web resource accepting log in requests.
     */
    public static final ServerResource AUTHENTICATION_URL = new ServerResource(
            SERVER_BASE_URL + "authentication");

    /**
     * The location of the web resource friend exposing lists of friend
     * requests.
     */
    public static final ServerResource GET_FRIEND_REQUESTS_URL = new ServerResource(
            SERVER_BASE_URL + "student/friends/requests");
    /**
     * The location of the web resource accepting requests for the creation of
     * new friend requests.
     */
    public static final ServerResource ADD_FRIEND_URL = new ServerResource(
            SERVER_BASE_URL + "student/friends/add");
    /**
     * The location of the web resource accepting requests for approving friend
     * invites.
     */
    public static final ServerResource APPROVE_FRIEND_URL = new ServerResource(
            SERVER_BASE_URL + "student/friends/approve");

    /**
     * The location of the web resource exposing lists of friends.
     */
    public static final ServerResource GET_FRIENDS_URL = new ServerResource(
            SERVER_BASE_URL + "student/friends");

    /**
     * The location of the web resource accepting requests for deleting friends.
     */
    public static final ServerResource DELETE_FRIEND_URL = new ServerResource(
            SERVER_BASE_URL + "student/friends/delete");

    /**
     * The location of the web resource accepting requests to create new
     * courses.
     */
    public static final ServerResource ADD_COURSE_URL = new ServerResource(
            SERVER_BASE_URL + "courses/add");

    /**
     * The location of the web resource to get a Schools courselist
     */
    public static final ServerResource GET_SCHOOL_COURSES_URL = new ServerResource(
            SERVER_BASE_URL + "courses/getSchoolCourses");

    public static final ServerResource GET_SCHOOL_COURSES_WITH_LUNCH_URL = new ServerResource(
            SERVER_BASE_URL + "courses/getSchoolCoursesWithLunch");

    /**
     * The location of the web resource to get a Courses sectionList
     */
    public static final ServerResource GET_COURSE_SECTIONS_URL = new ServerResource(
            SERVER_BASE_URL + "section/getCourseSections");

    public static final ServerResource ADD_SCHEDULE_BLOCK_URL = new ServerResource(
            SERVER_BASE_URL + "scheduleBlock/add");

    public static final ServerResource GET_SCHOOL_SCHEDULE_BLOCKS = new ServerResource(
            SERVER_BASE_URL + "scheduleBlock/getSchoolScheduleBlocks");

    public static final ServerResource ADD_SECTION_URL = new ServerResource(
            SERVER_BASE_URL + "section/add");

    public static final ServerResource SET_ASSIGNED_SCHEDULE_URL = new ServerResource(
            SERVER_BASE_URL + "schedule/setAssignedSchedule");

    public static final ServerResource ADD_SECTION_TO_SCHEDULE_URL = new ServerResource(
            SERVER_BASE_URL + "schedule/addSectionToSchedule");

    public static final ServerResource CREATE_SCHEDULE_URL = new ServerResource(
            SERVER_BASE_URL + "schedule/createSchedule");

    public static final ServerResource ADD_DESIRED_COURSE_URL = new ServerResource(
            SERVER_BASE_URL + "desiredschedule/add");

    public static final ServerResource SET_DESIRED_LUNCHES_URL = new ServerResource(
            SERVER_BASE_URL + "desiredschedule/lunch");

    public static final ServerResource GET_STUDENTS_ASSIGNED_SCHEDULE = new ServerResource(
            SERVER_BASE_URL + "schedule/getSchedule");

    public static final ServerResource GENERATE_SCHEDULE_URL = new ServerResource(
            SERVER_BASE_URL + "desiredschedule/generate");

    private String resourceUrl;

    public String getUrl()
    {
        return resourceUrl;
    }

    private ServerResource(String url)
    {
        resourceUrl = url;
    }
}
