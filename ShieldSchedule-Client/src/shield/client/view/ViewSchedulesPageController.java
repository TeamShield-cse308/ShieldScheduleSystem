/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import shield.client.main.CSE308GUI;

/**
 * FXML Controller class
 *
 * @author evanguby
 */
public class ViewSchedulesPageController implements Initializable, ControlledScreen {

    ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleViewAssignedSchedule(ActionEvent event) {
        myController.loadScreen(CSE308GUI.SelectAssignedScheduleYearPageID, CSE308GUI.SelectAssignedScheduleYearPage);
        myController.setScreen(CSE308GUI.SelectAssignedScheduleYearPageID);
    }

    @FXML
    private void handleViewGeneratedSchedule(ActionEvent event) {
        myController.loadScreen(CSE308GUI.SelectGeneratedSchedulePageID, CSE308GUI.SelectGeneratedSchedulePage);
        myController.setScreen(CSE308GUI.SelectGeneratedSchedulePageID);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        myController.setScreen(CSE308GUI.StudentViewID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void populatePage() {

    }
    
}
