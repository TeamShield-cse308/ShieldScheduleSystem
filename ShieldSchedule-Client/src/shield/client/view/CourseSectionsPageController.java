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
import shield.shared.dto.SimpleSection;

/**
 * FXML Controller class
 *
 * @author evanguby
 */
public class CourseSectionsPageController implements Initializable, ControlledScreen {

    //The instance manager for the client
    ScreensController myController;
    @FXML
    private Label welcome;
    @FXML
    private ListView<String> sections;
    
    private final ServerAccessPoint getCourseSections =
            new ServerAccessPoint(ServerResource.GET_COURSE_SECTIONS_URL);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleBack(ActionEvent event) {
        myController.loadScreen(CSE308GUI.CourseOfferingsPageID, CSE308GUI.CourseOfferingsPage);
        myController.setScreen(CSE308GUI.CourseOfferingsPageID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void populatePage() {
        StudentSession s = (StudentSession) myController.getSession();
        SimpleCourse course = s.getCourse();
        course.school = s.getSchool().name;
        course.year = s.getScheduleYear();
        Response rsp = getCourseSections.request(course);

        //check the response status code
        if (rsp.getStatus() != Response.Status.OK.getStatusCode()) {
            //@TODO error handling   
        }
        GenericType<List<SimpleSection>> gtlc = new GenericType<List<SimpleSection>>() {
        };
        try {
            List<SimpleSection> sectionsList = rsp.readEntity(gtlc);
            s.setSections(sectionsList);
            ArrayList<SimpleSection> sectionArray = new ArrayList<>();

            ArrayList<String> sectionNames = new ArrayList<>();

            for (SimpleSection section : sectionsList) {
                String sectionDays = "";
                for (int i = 0; i < section.scheduleBlock.scheduleDays.length(); i++) {
                    sectionDays += section.scheduleBlock.scheduleDays.substring(i, i + 1) + ", ";
                }
                sectionNames.add(section.teacherName + " Period: " + section.scheduleBlock.period
                        + " Days: " + sectionDays.substring(0, sectionDays.lastIndexOf(",")) + " Number of Students Enrolled: " + section.studentsEnrolled); //Add schedule block info
            }
            sections.getItems().clear();
            sections.getItems().setAll(sectionNames);
        //ss.setCourses(coursesArray);
            //populate combobox
            
        } catch (Exception e) {

        }
    }

}
