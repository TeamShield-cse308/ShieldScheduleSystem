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

/**
 *
 * @author Evan Guby
 */
public class AdminViewController implements Initializable, ControlledScreen {
    ScreensController myController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
    
    @FXML
    private void handleManageSchools(ActionEvent event) {
        myController.loadScreen(CSE308GUI.ManageSchoolsID, CSE308GUI.ManageSchools);
        myController.setScreen(CSE308GUI.ManageSchoolsID);
    }

    @FXML
    private void handleManageStudentAccounts(ActionEvent event) {
        myController.loadScreen(CSE308GUI.ManageStudentAccountsID, CSE308GUI.ManageStudentAccounts);
        myController.setScreen(CSE308GUI.ManageStudentAccountsID);
    }
    
    @FXML
    private void handleLogout(ActionEvent event) {
        myController.loadScreen(CSE308GUI.StudentRegistrationID, CSE308GUI.StudentRegistration);
        myController.setScreen(CSE308GUI.LoginPageID);
    }

    @Override
    public void populatePage() {
    }
}
