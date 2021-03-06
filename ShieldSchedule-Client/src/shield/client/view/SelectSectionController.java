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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.view.session.Session;
import shield.client.view.session.StudentSession;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleSection;

/**
 * FXML Controller class
 *
 * @author Evan Guby
 */
public class SelectSectionController implements Initializable, ControlledScreen {
    @FXML
    private ComboBox<String> section;
    ScreensController myController;
    
    private final ServerAccessPoint getCourseSections =
            new ServerAccessPoint(ServerResource.GET_COURSE_SECTIONS_URL);
    
    private final ServerAccessPoint addSectionToSchedule
            = new ServerAccessPoint(ServerResource.ADD_SECTION_TO_SCHEDULE_URL);
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleSelectSection(ActionEvent event) {
        StudentSession ss = (StudentSession)myController.getSession();
        String courseID = "" + ss.getCourse().courseID;
        int index = section.getSelectionModel().getSelectedIndex();
        String sectionID = "" + ss.getSections().get(index).sectionID;
        String toAdd = ss.getCourseName() + " " + section.getValue();
        ss.sectionToAddToString(toAdd);
        SimpleSection sec = new SimpleSection();
        sec.sectionID = Long.parseLong(sectionID);
        sec.studentEmail = ss.getStudentAccount().email;
        sec.year = ss.getScheduleYear();
        Response rsp = addSectionToSchedule.request(sec);
        myController.loadScreen(CSE308GUI.AddSchoolCoursesID,CSE308GUI.AddSchoolCourses);
        myController.setScreen(CSE308GUI.AddSchoolCoursesID);
    }

    @FXML
    private void handleNewSection(ActionEvent event) {
        myController.loadScreen(CSE308GUI.AddSectionID, CSE308GUI.AddSection);
        myController.setScreen(CSE308GUI.AddSectionID);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        myController.setScreen(CSE308GUI.AddSchoolCoursesID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void populatePage() {
        StudentSession s = (StudentSession)myController.getSession();
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
        try{
        List<SimpleSection> sections = rsp.readEntity(gtlc);
        s.setSections(sections);
        ArrayList<SimpleSection> sectionArray = new ArrayList<>();
        
        ArrayList<String> sectionNames = new ArrayList<>();
        
        for (SimpleSection section : sections) {
            String sectionDays = "";
            for(int i = 0; i<section.scheduleBlock.scheduleDays.length();i++){
                sectionDays += section.scheduleBlock.scheduleDays.substring(i,i+1) + ", ";
            }
            sectionNames.add(section.teacherName + " Period: " + section.scheduleBlock.period + 
                    " Days: " + sectionDays.substring(0,sectionDays.lastIndexOf(","))); //Add schedule block info
        }
        //ss.setCourses(coursesArray);
        //populate combobox
        section.getItems().clear();
        section.getItems().addAll(sectionNames);
        }
        catch (Exception e){
            
        }
    }
    
}
