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
import javafx.scene.control.TextField;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResources;
import shield.shared.dto.SimpleSchool;

/**
 * FXML Controller class
 *
 * @author Evan Guby
 */
public class EditSchoolInfoController implements Initializable, ControlledScreen {

    private ServerAccessPoint getSchool =
            new ServerAccessPoint(ServerResources.GET_SCHOOL_URL);
    
    ScreensController myController;
    @FXML
    private TextField name;
    @FXML
    private TextField semesters;
    @FXML
    private TextField scheduleDays;
    @FXML
    private TextField periods;
    @FXML
    private TextField startingLunch;
    @FXML
    private TextField endingLunch;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //populateSchoolInfo();
    }    

    @FXML
    private void handleSaveSchool(ActionEvent event) {
        myController.setScreen(CSE308GUI.ManageSchoolsID);
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        myController.setScreen(CSE308GUI.ManageSchoolsID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    public void populatePage() {
        String school = myController.getSchool();
        List<SimpleSchool> schools = myController.getSchools();
        SimpleSchool sch = null;
        for(SimpleSchool s : schools){
            if(s.name.equals(school)){
                sch = s;
                break;
            }
        }
        name.setText(sch.name);
        semesters.setText("" + sch.numSemesters);
        scheduleDays.setText("" + sch.numScheduleDays);
        periods.setText("" + sch.numPeriods);
        startingLunch.setText("" + sch.startingLunchPeriod);
        endingLunch.setText("" + sch.endingLunchPeriod);
    }
    
}
