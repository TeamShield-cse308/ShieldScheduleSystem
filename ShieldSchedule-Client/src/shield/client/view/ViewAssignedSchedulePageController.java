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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.main.CSE308GUI;
import shield.client.view.session.StudentSession;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleFriendship;
import shield.shared.dto.SimpleSchedule;
import shield.shared.dto.SimpleSection;

/**
 * FXML Controller class
 *
 * @author evanguby
 */
public class ViewAssignedSchedulePageController implements Initializable, ControlledScreen {

    ScreensController myController;
    
    @FXML
    private ListView<String> day1Table;
    @FXML
    private ListView<String> day2Table;
    @FXML
    private ListView<String> day3Table;
    @FXML
    private ListView<String> day4Table;
    @FXML
    private ListView<String> day5Table;
    @FXML
    private ListView<String> day6Table;
    @FXML
    private ListView<String> day7Table;
    @FXML
    private Label period1;
    @FXML
    private Label period2;
    @FXML
    private Label period3;
    @FXML
    private Label period4;
    @FXML
    private Label period5;
    @FXML
    private Label period6;
    @FXML
    private Label period7;
    @FXML
    private Label period8;
    @FXML
    private Label period9;
    @FXML
    private Label period10;
    @FXML
    private Label period11;
    @FXML
    private Label period12;
    @FXML
    private Label day1;
    @FXML
    private Label day2;
    @FXML
    private Label day3;
    @FXML
    private Label day4;
    @FXML
    private Label day5;
    @FXML
    private Label day6;
    @FXML
    private Label day7;

    private final ServerAccessPoint getStudentsAssignedSchedule = 
            new ServerAccessPoint(ServerResource.GET_STUDENTS_ASSIGNED_SCHEDULE);
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleBack(ActionEvent event) {
        myController.getScene().getWindow().setWidth(720);
        myController.setScreen(CSE308GUI.ViewSchedulesPageID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void populatePage() {
        myController.getScene().getWindow().setWidth(900);
        StudentSession ss = (StudentSession)myController.getSession();
        int days = ss.getSchool().numScheduleDays;
        int periods = ss.getSchool().numPeriods;
        ArrayList<Label> schedDays = new ArrayList<>();
        ArrayList<Label> schPeriods = new ArrayList<>();
        ArrayList<ListView> tableDays = new ArrayList<>();
        schedDays.add(day1);
        schedDays.add(day2);
        schedDays.add(day3);
        schedDays.add(day4);
        schedDays.add(day5);
        schedDays.add(day6);
        schedDays.add(day7);
        schPeriods.add(period1);
        schPeriods.add(period2);
        schPeriods.add(period3);
        schPeriods.add(period4);
        schPeriods.add(period5);
        schPeriods.add(period6);
        schPeriods.add(period7);
        schPeriods.add(period8);
        schPeriods.add(period9);
        schPeriods.add(period10);
        schPeriods.add(period11);
        schPeriods.add(period12);
        tableDays.add(day1Table);
        tableDays.add(day2Table);
        tableDays.add(day3Table);
        tableDays.add(day4Table);
        tableDays.add(day5Table);
        tableDays.add(day6Table);
        tableDays.add(day7Table);
        for(int i = 0; i < days; i++){
            schedDays.get(i).setVisible(true);
            tableDays.get(i).setVisible(true);
        }
        for(int i = 0; i < periods; i++){
            schPeriods.get(i).setVisible(true);
        }
        SimpleSchedule sched = new SimpleSchedule();
        sched.studentEmail = ss.getStudentAccount().email;
        sched.year = ss.getScheduleYear();
        
        Response rsp = getStudentsAssignedSchedule.request(sched);
        GenericType<SimpleSchedule> gtlc = new GenericType<SimpleSchedule>()
        {
        };
        
        SimpleSchedule schedule = rsp.readEntity(gtlc);
        List<SimpleSection> sections = schedule.sections;
        for(int i = 1; i <= days; i++){
            for(int j = 1; j <= periods; j++){
                boolean added = false;
                for(SimpleSection section : sections){
                    if(section.scheduleBlock.period == j && section.scheduleBlock.scheduleDays.contains("" + i)){
                        tableDays.get(i - 1).getItems().add(section.course.name + " with " + section.teacherName);
                        added = true;
                    }
                }
                if(!added){
                    tableDays.get(i - 1).getItems().add("Study Hall");
                }
            }
        }
        
    }
    
}
