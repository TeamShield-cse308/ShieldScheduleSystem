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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Evan Guby
 */
public class EditSectionController implements Initializable, ControlledScreen  {
    @FXML
    private TextField room;
    @FXML
    private TextField teacher;
    @FXML
    private TextField period;
    @FXML
    private TextField semester;
    @FXML
    private TextField size;

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
