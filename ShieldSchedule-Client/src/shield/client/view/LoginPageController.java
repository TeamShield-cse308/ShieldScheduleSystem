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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Evan Guby
 */
public class LoginPageController implements Initializable, ControlledScreen {
    
    ScreensController myController;
    @FXML private TextField email;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void handleLogin(ActionEvent event){
        if(email.getCharacters().toString().equals("admin")){
            myController.setScreen(CSE308GUI.AdminViewID);
        } else if(email.getCharacters().toString().equals("student")) {
            myController.setScreen(CSE308GUI.StudentViewID);
        }
    }
    public void handleNewUser(ActionEvent event) {
        myController.setScreen(CSE308GUI.StudentRegistrationID);
    }
    
    public void handleForgotPassword(ActionEvent event) {
        myController.setScreen(CSE308GUI.ForgotPasswordID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
    
}
