/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javax.ws.rs.core.Response;
import shield.client.main.CSE308GUI;
import shield.client.view.session.AdminSession;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleScheduleBlock;
import shield.shared.dto.SimpleSchool;

/**
 * FXML Controller class
 *
 * @author evanguby
 */
public class SetScheduleBlocksController implements Initializable, ControlledScreen {
    @FXML
    private ComboBox<String> periods;
    @FXML
    private CheckBox day1;
    @FXML
    private CheckBox day3;
    @FXML
    private CheckBox day4;
    @FXML
    private CheckBox day5;
    @FXML
    private CheckBox day6;
    @FXML
    private CheckBox day7;
    @FXML
    private CheckBox day2;
    @FXML
    private Label blocksAdded;
    
    private String blocks;
    
    private ServerAccessPoint addScheduleBlock = 
            new ServerAccessPoint(ServerResource.ADD_SCHEDULE_BLOCK_URL);

    ScreensController myController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleFinish(ActionEvent event) {
        myController.setScreen(CSE308GUI.AdminViewID);
    }

    @FXML
    private void handleAddScheduleBlock(ActionEvent event) {
        String period = periods.getValue();
        String days = "";
        String scheduleDays = "";
        if(day1.isSelected()){
            scheduleDays+="1";
            days += "1, ";
        }
        if(day2.isSelected()){
            scheduleDays+="2";
            days += "2, ";
        }
        if(day3.isSelected()){
            scheduleDays+="3";
            days += "3, ";
        }
        if(day4.isSelected()){
            scheduleDays+="4";
            days += "4, ";
        }
        if(day5.isSelected()){
            scheduleDays+="5";
            days += "5, ";
        }
        if(day6.isSelected()){
            scheduleDays+="6";
            days += "6, ";
        }
        if(day7.isSelected()){
            scheduleDays+="7";
            days += "7, ";
        }
        AdminSession as = (AdminSession)myController.getSession();
        SimpleScheduleBlock ssb = new SimpleScheduleBlock();
        ssb.period = Integer.parseInt(period);
        ssb.scheduleDays = scheduleDays;
        ssb.schoolName = as.getSchoolAdded().name;
        
        Response rsp = addScheduleBlock.request(ssb);
        //TODO error handling if schedule block was not added
        if (rsp.getStatus() == Response.Status.OK.getStatusCode())
        {
            blocks += "Period: " + period + " Days: " + days.substring(0,days.lastIndexOf(",")) + "\n";
            blocksAdded.setText(blocks);
        }
        //ScheduleBlock added
        periods.setValue(null);
        day1.setSelected(false);
        day2.setSelected(false);
        day3.setSelected(false);
        day4.setSelected(false);
        day5.setSelected(false);
        day6.setSelected(false);
        day7.setSelected(false);
    }
    
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void populatePage() {
        ArrayList<CheckBox> cb = new ArrayList<>();
        cb.add(day1);
        cb.add(day2);
        cb.add(day3);
        cb.add(day4);
        cb.add(day5);
        cb.add(day6);
        cb.add(day7);
        AdminSession sess = (AdminSession)myController.getSession();
        SimpleSchool sch = sess.getSchoolAdded();
        for(int i = 1; i <= sch.numPeriods; i++){
            periods.getItems().add(""+i);
        }
        for(int i = 0; i < sch.numScheduleDays; i++){
            cb.get(i).setVisible(true);
        }
        blocks = "";
    }
    
}
