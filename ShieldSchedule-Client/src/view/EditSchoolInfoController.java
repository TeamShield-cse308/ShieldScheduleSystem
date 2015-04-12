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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Evan Guby
 */
public class EditSchoolInfoController implements Initializable, ControlledScreen {

    ScreensController myController;
    @FXML
    private TextField name;
    @FXML
    private TextField semesters;
    @FXML
    private TextField scheduleDays;
    @FXML
    private TextField periods;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
}
