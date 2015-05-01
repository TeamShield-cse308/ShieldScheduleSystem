/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

import java.lang.reflect.InvocationTargetException;
import shield.client.main.CSE308GUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javax.ws.rs.core.Response;
import shield.client.view.session.AdminSession;
import shield.client.web.ServerAccessPoint;

import shield.shared.dto.SimpleSchool;
import shield.client.web.ServerResource;

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
            new ServerAccessPoint(ServerResource.ADD_SCHOOL_URL);

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
        try
        {
            school.numSemesters = Integer.parseInt(initSemesters);
            school.numScheduleDays = Integer.parseInt(initScheduleDays);
            school.numPeriods = Integer.parseInt(initPeriods);
            school.startingLunchPeriod = Integer.parseInt(initStartingLunchPeriod);
            school.endingLunchPeriod = Integer.parseInt(initEndingLunchPeriod);
            if(school.numSemesters <1 || school.numSemesters > 4)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Semester Error");
                alert.setHeaderText("Bad Number Of Semesters");
                alert.setContentText("Semesters are in the range 1 - 4.");
                alert.show();
            }
            else if(school.numScheduleDays < 1 || school.numScheduleDays > 7)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Schedule Days Error");
                alert.setHeaderText("Bad Number Of Schedule Days");
                alert.setContentText("Schedule Days are in the range 1 - 7.");
                alert.show();
            }
            else if(school.numPeriods < 6 || school.numPeriods > 12)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Period Error");
                alert.setHeaderText("Bad Number Of Periods");
                alert.setContentText("Periods are in the range 6 - 12.");
                alert.show();
            }
            else if(school.startingLunchPeriod < 1 || school.startingLunchPeriod > school.endingLunchPeriod || school.startingLunchPeriod > school.numPeriods)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Lunch Error");
                alert.setHeaderText("Bad Starting Lunch");
                alert.setContentText("Lunch must start during or after 1st period and before endingLunchPeriod");
                alert.show();
            }
            else if(school.endingLunchPeriod < school.startingLunchPeriod || school.endingLunchPeriod < 1 || school.endingLunchPeriod > school.numPeriods)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Lunch Error");
                alert.setHeaderText("Bad Ending Lunch");
                alert.setContentText("Ending Lunch must be after Starting Lunch and before or during last period.");
                alert.show();
            }
            else
            {
        


        //transmit the school
                Response rsp = addSchool.request(school);
        //check server code
                if (rsp.getStatus() != Response.Status.OK.getStatusCode())
                {
            //@TODO error handling
                }else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success!");
                    alert.setHeaderText("School Added!");
                    alert.setContentText("School " + initSchoolName + " sucessfully added!");
                    alert.show();
                }
<<<<<<< Updated upstream
        
                myController.loadScreen(CSE308GUI.ManageSchoolsID, CSE308GUI.ManageSchools);
                myController.setScreen(CSE308GUI.ManageSchoolsID);
=======

        AdminSession sess = (AdminSession)myController.getSession();
        sess.setSchoolAdded(school);
        
        myController.loadScreen(CSE308GUI.ManageSchoolsID, CSE308GUI.ManageSchools);
        myController.loadScreen(CSE308GUI.SetScheduleBlocksID, CSE308GUI.SetScheduleBlocks);
        myController.setScreen(CSE308GUI.SetScheduleBlocksID);

>>>>>>> Stashed changes
        

        //System.out.println("");
            }
            }catch (Exception ex){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Bad Field Entries");
                alert.setContentText("Please only enter numerical values for \n"
                    + "Semesters, Schedule Days, Periods, and the Lunch Periods");
                alert.show();
            }
        
        

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
