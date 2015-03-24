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

/**
 * FXML Controller class
 *
 * @author Evan
 */
public class EditCourseController implements Initializable, ControlledScreen {
    @FXML
    private ChoiceBox<?> section;
    ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleEditSection(ActionEvent event) {
        myController.setScreen(CSE308GUI.EditSectionID);
    }

    @FXML
    private void handleAddSection(ActionEvent event) {
        myController.setScreen(CSE308GUI.AddSectionID);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        myController.setScreen(CSE308GUI.EditSchoolCoursesID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
    
}
