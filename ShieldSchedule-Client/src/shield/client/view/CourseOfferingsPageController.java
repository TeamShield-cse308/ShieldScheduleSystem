/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.main.CSE308GUI;
import shield.client.view.session.StudentSession;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleStudent;

/**
 * FXML Controller class
 *
 * @author evanguby
 */
public class CourseOfferingsPageController implements Initializable, ControlledScreen {
    
    //The instance manager for the client
    ScreensController myController;
    
    @FXML
    private Label welcome;
    @FXML
    private ListView<String> courses;

    
    private final ServerAccessPoint getSchoolCoursesWithLunch
            = new ServerAccessPoint(ServerResource.GET_SCHOOL_COURSES_WITH_LUNCH_URL);
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleBack(ActionEvent event) {
        myController.setScreen(CSE308GUI.StudentViewID);
    }

    @FXML
    private void handleViewSection(ActionEvent event) {
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void populatePage() {
        StudentSession ss = (StudentSession)myController.getSession();

        //String schoolName = ss.getStudentAccount().school;
        SimpleStudent stu = ss.getStudentAccount();
        //request list of courses
        Response rsp = getSchoolCoursesWithLunch.request(stu);

        //check the response status code
        if (rsp.getStatus() != Response.Status.OK.getStatusCode()) {
            //@TODO error handling   
        }
        GenericType<List<SimpleCourse>> gtlc = new GenericType<List<SimpleCourse>>() {
        };
        //read courses from http response
        List<SimpleCourse> coursesList = rsp.readEntity(gtlc);
        ArrayList<SimpleCourse> coursesArray = new ArrayList<>();
        //extract course names from schools;
        //extract course names from schools
        ArrayList<String> courseNames = new ArrayList<>();
        //List<String> list = ss.getAssignedSchedule().courseIDs;
        //List<SimpleCourses> list = ss.
        for (SimpleCourse course : coursesList) {
            SimpleCourse c = new SimpleCourse();
            c.identifier = course.identifier;
            c.name = course.name;
            c.school = course.school;
            c.courseID = course.courseID;
//            boolean toAdd = true;
//            for(String cid : list){
//                if(cid.equals("" + course.courseID))
//                    toAdd = false;
//            }
            if((course.year == ss.getScheduleYear()) && !course.name.contains("Lunch")){
                courseNames.add(course.name + ", " + course.identifier);
            }
            
            coursesArray.add(c);
        }
        ss.setCourses(coursesArray);
        //populate combobox
        courses.getItems().clear();
        courses.getItems().setAll(courseNames);
    }
    
}
