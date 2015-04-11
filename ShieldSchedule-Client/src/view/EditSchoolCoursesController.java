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
 * @author Evan Guby
 */
public class EditSchoolCoursesController implements Initializable, ControlledScreen {
    @FXML
    private ChoiceBox<?> course;
    ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleEditCourse(ActionEvent event) {
        myController.setScreen(CSE308GUI.EditCourseID);
    }

    @FXML
    private void handleAddCourse(ActionEvent event) {
        myController.setScreen(CSE308GUI.AddCourseID);
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        myController.setScreen(CSE308GUI.EditSchoolID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
    
}
