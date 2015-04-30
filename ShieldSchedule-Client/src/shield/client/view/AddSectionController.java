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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import shield.client.main.CSE308GUI;

/**
 * FXML Controller class
 *
 * @author evanguby
 */
public class AddSectionController implements Initializable, ControlledScreen {
    @FXML
    private TextField teacher;
    @FXML
    private ComboBox<String> scheduleBlockBox;

    ScreensController myController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleSaveSection(ActionEvent event) {
        myController.setScreen(CSE308GUI.AddCourseID);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        myController.setScreen(CSE308GUI.AddCourseID);
    }
    
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void populatePage() {
    }
    
}
