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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Evan Guby
 */
public class EditSchoolController implements Initializable, ControlledScreen {
    @FXML
    private TextField periods;
    @FXML
    private TextField scheduleDays;
    @FXML
    private TextField semesters;
    @FXML
    private TextField name;

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
        myController.setScreen(CSE308GUI.ManageSchoolsID);
    }

    @FXML
    private void handleEditInfo(ActionEvent event) {
        myController.setScreen(CSE308GUI.EditSchoolInfoID);
    }
    
    @FXML
    private void handleEditCourse(ActionEvent event) {
        myController.setScreen(CSE308GUI.EditSchoolCoursesID);
    }
    
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
}
