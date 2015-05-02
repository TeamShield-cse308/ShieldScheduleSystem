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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.main.CSE308GUI;
import shield.client.view.session.Session;
import shield.client.view.session.StudentSession;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleScheduleBlock;
import shield.shared.dto.SimpleSchool;
import shield.shared.dto.SimpleSection;
import shield.shared.dto.SimpleStudent;

/**
 * FXML Controller class
 *
 * @author evanguby
 */
public class AddSectionController implements Initializable, ControlledScreen {
    private TextField teacher;
    private ComboBox<String> scheduleBlockBox;

    ScreensController myController;
    
    @FXML
    private CheckBox semester1;
    @FXML
    private CheckBox semester2;
    @FXML
    private CheckBox semester3;
    @FXML
    private CheckBox semester4;
    
    private final ServerAccessPoint getSchoolScheduleBlocks
            = new ServerAccessPoint(ServerResource.GET_SCHOOL_SCHEDULE_BLOCKS);
    
    private final ServerAccessPoint addSection
            = new ServerAccessPoint(ServerResource.ADD_SECTION_URL);
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private void handleSaveSection(ActionEvent event) {
        String secTeacher = teacher.getText();
        int index = scheduleBlockBox.getSelectionModel().getSelectedIndex();
        StudentSession ss = (StudentSession)myController.getSession();
        SimpleScheduleBlock ssb = ss.getScheduleBlocks().get(index);
        SimpleSection section = new SimpleSection();
        section.teacherName = secTeacher;
        section.scheduleBlockDays = ssb.scheduleDays;
        section.scheduleBlockPeriod = ssb.period;
        section.school = ss.getStudentAccount().school;
        String semestersPicked = "";
        if(semester1.isSelected()){
            semestersPicked+="1";
        }
        if(semester2.isSelected()){
            semestersPicked+="2";
        }
        if(semester3.isSelected()){
            semestersPicked+="3";
        }
        if(semester4.isSelected()){
            semestersPicked+="4";
        }
        section.semesters = semestersPicked;
        section.courseIdentifier = ss.getCourseIdentifier();
        Response rsp = addSection.request(section);
        //TODO error handling
        myController.loadScreen(CSE308GUI.SelectSectionID,CSE308GUI.SelectSection);
        myController.setScreen(CSE308GUI.SelectSectionID);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        myController.setScreen(CSE308GUI.AddCourseID);
    }
    
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void populatePage() {
        
        Session s = myController.getSession();
        StudentSession ss = (StudentSession) s;
        SimpleStudent stu = ss.getStudentAccount();
        SimpleSchool school = ss.getSchool();
        Response rsp = getSchoolScheduleBlocks.request(stu);
        
         if (rsp.getStatus() != Response.Status.OK.getStatusCode()) {
            //@TODO error handling   
        }
        GenericType<List<SimpleScheduleBlock>> gtlc = new GenericType<List<SimpleScheduleBlock>>() {
        };
        //read courses from http response
        List<SimpleScheduleBlock> scheduleBlocks = rsp.readEntity(gtlc);
        //ArrayList<SimpleScheduleBlock> sbArray = new ArrayList<>();
        ss.setScheduleBlocks(scheduleBlocks);
        for(SimpleScheduleBlock ssb : scheduleBlocks){
            String toAdd = "";
            String days = ssb.scheduleDays.substring(0,1);
            for(int i = 1; i<ssb.scheduleDays.length();i++){
               days += ", " + ssb.scheduleDays.substring(i,i+1);
            }
            toAdd = "Period: " + ssb.period + " Days: " + days;
            scheduleBlockBox.getItems().add(toAdd);
        }
        
        ArrayList<CheckBox> cb = new ArrayList<>();
        cb.add(semester1);
        cb.add(semester2);
        cb.add(semester3);
        cb.add(semester4);
        for(int i = 0; i< school.numSemesters; i++){
            cb.get(i).setVisible(true);
        }
        
    }

    @FXML
    private void handleViewAssignedSchedule(ActionEvent event) {
    }

    @FXML
    private void handleViewDesignedSchedule(ActionEvent event) {
    }
    
}
