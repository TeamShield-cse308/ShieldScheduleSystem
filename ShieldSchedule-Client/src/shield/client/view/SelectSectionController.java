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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Evan Guby
 */
public class SelectSectionController implements Initializable, ControlledScreen {
    @FXML
    private ComboBox<?> section;
    ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleSelectSection(ActionEvent event) {
        myController.setScreen(CSE308GUI.StudentViewID);
    }

    @FXML
    private void handleNewSection(ActionEvent event) {
        myController.setScreen(CSE308GUI.AddSectionID);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        myController.setScreen(CSE308GUI.AddSchoolCoursesID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void populatePage() {
    }
    
}
