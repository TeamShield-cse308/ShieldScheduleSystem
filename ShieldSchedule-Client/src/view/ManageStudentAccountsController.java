/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Main.CSE308GUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Evan
 */
public class ManageStudentAccountsController implements Initializable, ControlledScreen {
    @FXML
    private ListView<?> existing;
    @FXML
    private ListView<?> requested;
    
    ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleBack(ActionEvent event) {
        myController.setScreen(CSE308GUI.AdminViewID);
    }

    @FXML
    private void handleAcceptSelected(ActionEvent event) {
    }

    @FXML
    private void handleRejectSelected(ActionEvent event) {
    }

    @FXML
    private void handleAcceptAll(ActionEvent event) {
    }

    @FXML
    private void handleDeleteSelected(ActionEvent event) {
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
    
}
