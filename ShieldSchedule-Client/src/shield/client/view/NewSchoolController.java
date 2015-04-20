/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

import shield.client.main.CSE308GUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javax.ws.rs.core.Response;
import shield.client.web.ServerAccessPoint;

import shield.shared.dto.SimpleSchool;
import shield.client.web.ServerResources;

/**
 * FXML Controller class 
 *
 * @author Evan Guby, Jeffrey Kabot
 */
public class NewSchoolController implements Initializable, ControlledScreen
{

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
    
    private ServerAccessPoint addSchool = 
            new ServerAccessPoint(ServerResources.ADD_SCHOOL_URL);

    ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    @FXML
    private void handleCreateSchool(ActionEvent event)
    {
        //retrieve school data
        //@TODO ensure user can't enter non-integers for integer fields
        String initSchoolName = name.getText();
        String initSemesters = semesters.getText();
        String initScheduleDays = scheduleDays.getText();
        String initPeriods = periods.getText();

        String initStartingLunchPeriod = startingLunch.getText();
        String initEndingLunchPeriod = endingLunch.getText();

        SimpleSchool school = new SimpleSchool();
        school.name = initSchoolName;
        school.numSemesters = Integer.parseInt(initSemesters);
        school.numScheduleDays = Integer.parseInt(initScheduleDays);
        school.numPeriods = Integer.parseInt(initPeriods);
        school.startingLunchPeriod = Integer.parseInt(initStartingLunchPeriod);
        school.endingLunchPeriod = Integer.parseInt(initEndingLunchPeriod);


        //transmit the school
        Response rsp = addSchool.request(school);
        //check server code
        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            //@TODO error handling
        }
        
        myController.loadScreen(CSE308GUI.ManageSchoolsID, CSE308GUI.ManageSchools);
        myController.setScreen(CSE308GUI.ManageSchoolsID);
        

        //System.out.println("");

    }

    @FXML
    private void handleCancel(ActionEvent event)
    {
        myController.setScreen(CSE308GUI.ManageSchoolsID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }

    @Override
    public void populatePage() {
    }

}
