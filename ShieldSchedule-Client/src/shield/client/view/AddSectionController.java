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
import shield.shared.dto.SimpleStudent;

/**
 * FXML Controller class
 *
 * @author evanguby
 */
public class AddSectionController implements Initializable, ControlledScreen {
    @FXML
    private TextField teacher;
    @FXML
    private ComboBox<String> scheduleBlockBox;

    ScreensController myController;
    
    private final ServerAccessPoint getSchoolScheduleBlocks
            = new ServerAccessPoint(ServerResource.GET_SCHOOL_SCHEDULE_BLOCKS);
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleSaveSection(ActionEvent event) {
        myController.setScreen(CSE308GUI.AddCourseID);
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
            toAdd = "Period: " + ssb.period + " Days: " + ssb.scheduleDays;
            scheduleBlockBox.getItems().add(toAdd);
        }
    }
    
}
