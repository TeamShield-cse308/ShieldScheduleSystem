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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import shield.shared.dto.SimpleStudent;
import shield.client.web.MessageExchange;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import shield.shared.dto.SimpleSchool;
//import com.fasterxml.jackson.jaxrs.

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
            SimpleStudent student = new SimpleStudent();
            student.state = "Pending";
            student.email = email.getText();
            student.name = name.getText();
            student.password = password.getText();
            student.school = school.getValue();

            WebTarget clientTarget;
            Client client = ClientBuilder.newClient();
            clientTarget = client.target(MessageExchange.ADD_STUDENT_URL);
            clientTarget.request().post(Entity.entity(student, MediaType.APPLICATION_JSON));
            
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
            if (email.getText().indexOf('@') == -1) {
                // Alert alert = new Alert(AlertType.INFORMATION);
                //alert.setTitle("Input Error");
                //alert.setHeaderText("Invalid Email");
                //alert.setContentText("Please enter a valid email");
                // alert.show();
            } else if (!(email.getText().equals(confirmedEmail.getText()))) {
                // Alert alert = new Alert(AlertType.INFORMATION);
                // alert.setTitle("Input Error");
                // alert.setHeaderText("Check Emails");
                // alert.setContentText("Entered email addressed do not match.");
                // alert.show();
            } else if (!(password.getText().equals(confirmedPassword.getText()))) {
                // Alert alert = new Alert(AlertType.INFORMATION);
                // alert.setTitle("Input Error");
                // alert.setHeaderText("Check Passwords");
                // alert.setContentText("Entered passwords do not match.");
                //alert.show();
            }
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @FXML
    public void handleBack(ActionEvent event) {
        email.clear();
        confirmedEmail.clear();
        password.clear();
        confirmedPassword.clear();
        name.clear();
        //Cant figure out how to reset school box
        myController.setScreen(CSE308GUI.LoginPageID);
    }

    public void populateSchoolsBox() {
        //adapted from oracle javafx / javaee tutorial
        //connect to shield schedule server
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();

        //Register a message body reader provider
        client.register(JacksonJsonProvider.class);

        //target the web resource with the list of all chools
        clientTarget = client.target(MessageExchange.GET_ALL_SCHOOLS_URL);

        GenericType<List<SimpleSchool>> gtlc = new GenericType<List<SimpleSchool>>() {
        };

        //get a list of all schools in database, transmitted from server in JSON
        List<SimpleSchool> schools = clientTarget.request("application/json").get(gtlc);

        //extract school names from schools
        ArrayList<String> schoolNames = new ArrayList<>();
        for (SimpleSchool sch : schools) {
            schoolNames.add(sch.name);
        }

        //populate combobox
        school.getItems().clear();
        school.getItems().addAll(schoolNames);
    }

}
