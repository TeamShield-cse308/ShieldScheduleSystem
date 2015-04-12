/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Main.CSE308GUI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

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
    private PasswordField confirmedPassword;
    @FXML
    private PasswordField password;
    @FXML
    private TextField confirmedEmail;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<String> school;

    ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateSchoolsBox();
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        //Check if email already registered/ add to database 
        //Set account to inactive
        if (email.getText().indexOf('@') != -1 && email.getText().equals(confirmedEmail.getText()) && password.getText().equals(confirmedPassword.getText())) {
            //Add account to DB / Check if it already exists
            
            
            //CLEAR FIELDS
            email.clear();
            confirmedEmail.clear();
            password.clear();
            confirmedPassword.clear();
            name.clear();
            //Cant figure out how to reset school box
            myController.setScreen(CSE308GUI.LoginPageID);
        } else {
            //Check to see if it email for password not correct
            if(email.getText().indexOf('@') == -1){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Input Error");
                alert.setHeaderText("Invalid Email");
                alert.setContentText("Please enter a valid email");
                alert.show();
            }
            else if (!(email.getText().equals(confirmedEmail.getText()))) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Input Error");
                alert.setHeaderText("Check Emails");
                alert.setContentText("Entered email addressed do not match.");
                alert.show();
            } else if (!(password.getText().equals(confirmedPassword.getText()))) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Input Error");
                alert.setHeaderText("Check Passwords");
                alert.setContentText("Entered passwords do not match.");
                alert.show();
            }
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    public void populateSchoolsBox() {
        //adapted from oracle javafx / javaee tutorial
        //connect to shield schedule server
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        client.register(SchoolsBodyReader.class);
        clientTarget = client.target("http://localhost:8080/ShieldSchedule-Server/webresources/admin-schools");
        GenericType<List<School>> gtlc = new GenericType<List<School>>() {
        };

        //get a list of all schools in database, transmitted from server in JSON
        List<School> schools = clientTarget.request("application/json").get(gtlc);

        //extract school names from schools
        ArrayList<String> schoolNames = new ArrayList<>();
        for (School sch : schools) {
            schoolNames.add(sch.getSchoolName());
        }

        //populate combobox
        school.getItems().clear();
        school.getItems().addAll(schoolNames);
    }

}
