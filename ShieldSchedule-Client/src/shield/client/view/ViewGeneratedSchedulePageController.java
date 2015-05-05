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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.main.CSE308GUI;
import shield.client.view.session.StudentSession;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleCriteria;
import shield.shared.dto.SimpleSchedule;
import shield.shared.dto.SimpleSection;

/**
 * FXML Controller class
 *
 * @author evanguby
 */
public class ViewGeneratedSchedulePageController implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    private ListView<?> day1Table;
    @FXML
    private ListView<?> day2Table;
    @FXML
    private ListView<?> day3Table;
    @FXML
    private ListView<?> day4Table;
    @FXML
    private ListView<?> day5Table;
    @FXML
    private ListView<?> day6Table;
    @FXML
    private ListView<?> day7Table;
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
    @FXML
    private Button nextSchedule;
    
    private final ServerAccessPoint generateSchedule =
            new ServerAccessPoint(ServerResource.GENERATE_SCHEDULE_URL);
    
    

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
    
    @FXML
    private void handleNextSchedule(ActionEvent event) {
        StudentSession ss = (StudentSession)myController.getSession();
        List<SimpleSchedule> list = ss.getSchedules();
        int index = ss.getCurrentScheduleIndex();
        SimpleSchedule schedule = null;
        if(index == list.size() -1){
            schedule = list.get(0);
            ss.setCurrentScheduleIndex(0);
        }
        else{
            schedule = list.get(index + 1);
            ss.setCurrentScheduleIndex(index+1);
        }
        List<SimpleSection> sections = schedule.sections;
        int days = ss.getSchool().numScheduleDays;
        int periods = ss.getSchool().numPeriods;
        if(sections == null)
            sections = new ArrayList<SimpleSection>();
        ArrayList<ListView> tableDays = new ArrayList<>();
        tableDays.add(day1Table);
        tableDays.add(day2Table);
        tableDays.add(day3Table);
        tableDays.add(day4Table);
        tableDays.add(day5Table);
        tableDays.add(day6Table);
        tableDays.add(day7Table);
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
                    if(j >= ss.getSchool().startingLunchPeriod && j <= ss.getSchool().endingLunchPeriod)
                        tableDays.get(i - 1).getItems().add("Lunch");
                    else
                        tableDays.get(i - 1).getItems().add("Study Hall");
                    
                }
            }
        }
        
    }
    

    @Override
    public void populatePage() {
        StudentSession ss = (StudentSession)myController.getSession();
        SimpleCriteria sc = new SimpleCriteria();
        sc.studentEmail = ss.getStudentAccount().email;
        sc.year = ss.getScheduleYear();
        
        Response rsp = generateSchedule.request(sc);
        GenericType<SimpleSchedule> gtlc = new GenericType<SimpleSchedule>()
        {
        };
        GenericType<List<SimpleSchedule>> gtlc2 = new GenericType<List<SimpleSchedule>>()
        {
        };
        SimpleSchedule schedule = null;
        List<SimpleSchedule> schedList;
        if(rsp.getStatus() == 200){
            nextSchedule.setVisible(false);
            schedule = rsp.readEntity(gtlc);
        }
        if(rsp.getStatus() == 204){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Schedule Error");
            alert.setHeaderText("No Schedule Found");
            alert.setContentText("Could not generate a schedule based off criteria");
            alert.show();
            myController.getScene().getWindow().setWidth(720);
            myController.setScreen(CSE308GUI.ViewSchedulesPageID);
            return;
        }
        if(rsp.getStatus() == 303){
            nextSchedule.setVisible(true);
            schedList = rsp.readEntity(gtlc2);
            schedule = schedList.get(0);
            ss.setSchedules(schedList);
            ss.setCurrentScheduleIndex(0);
        }
        
        myController.getScene().getWindow().setWidth(900);
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
        List<SimpleSection> sections = schedule.sections;
        if(sections == null)
            sections = new ArrayList<SimpleSection>();
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
                    if(j >= ss.getSchool().startingLunchPeriod && j <= ss.getSchool().endingLunchPeriod)
                        tableDays.get(i - 1).getItems().add("Lunch");
                    else
                        tableDays.get(i - 1).getItems().add("Study Hall");
                    
                }
            }
        }
        }

}
