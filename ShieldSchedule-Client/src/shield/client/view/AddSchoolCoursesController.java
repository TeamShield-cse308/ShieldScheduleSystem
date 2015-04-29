/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

import shield.client.main.CSE308GUI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.view.session.Session;
import shield.client.view.session.StudentSession;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleStudent;

/**
 * FXML Controller class
 *
 * @author Evan Guby
 */
public class AddSchoolCoursesController implements Initializable, ControlledScreen {

    @FXML
    private ComboBox<String> courseBox;

    private final ServerAccessPoint getSchoolCourses
            = new ServerAccessPoint(ServerResource.GET_SCHOOL_COURSES_URL);

    ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleSelectCourse(ActionEvent event) {
        myController.setScreen(CSE308GUI.SelectSectionID);
    }

    @FXML
    private void handleAddCourse(ActionEvent event) {
        myController.setScreen(CSE308GUI.AddCourseID);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        myController.setScreen(CSE308GUI.StudentViewID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void populatePage() {
        Session s = myController.getSession();
        StudentSession ss = (StudentSession) s;
        //String schoolName = ss.getStudentAccount().school;
        SimpleStudent stu = ss.getStudentAccount();
        //request list of courses
        Response rsp = getSchoolCourses.request(stu);

        //check the response status code
        if (rsp.getStatus() != Response.Status.OK.getStatusCode()) {
            //@TODO error handling   
        }
        GenericType<List<SimpleCourse>> gtlc = new GenericType<List<SimpleCourse>>() {
        };
        //read courses from http response
        List<SimpleCourse> courses = rsp.readEntity(gtlc);

        //extract course names from schools
        ArrayList<String> courseNames = new ArrayList<>();
        for (SimpleCourse course : courses) {
            courseNames.add(course.name + ", " + course.identifier);
        }

        //populate combobox
        courseBox.getItems().clear();
        courseBox.getItems().addAll(courseNames);
    }

}
