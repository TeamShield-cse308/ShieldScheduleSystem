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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author evanguby
 */
public class StudentRegistrationController implements Initializable, ControlledScreen {
    @FXML
    private AnchorPane anchor;
    @FXML
    private TextField email;
    @FXML
    private TextField confirmedPassword;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmedEmail;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<?> school;

    ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleRegister(ActionEvent event) {
        
        System.out.println(name.getText());
        myController.setScreen(CSE308GUI.LoginPageID);
    }
    
    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }
    
}
