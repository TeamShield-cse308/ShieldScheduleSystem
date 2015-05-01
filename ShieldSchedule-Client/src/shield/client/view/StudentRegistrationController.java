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

import shield.shared.dto.SimpleStudent;
import shield.client.web.ServerResource;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.web.ServerAccessPoint;
import shield.shared.dto.SimpleSchool;
//import com.fasterxml.jackson.jaxrs.

/**
 * FXML Controller class
 *
 * @author Evan Guby, Jeffrey Kabot
 */
public class StudentRegistrationController implements Initializable, ControlledScreen
{

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
    private ComboBox<String> schoolsBox;

    private final ServerAccessPoint newStudent =
            new ServerAccessPoint(ServerResource.ADD_STUDENT_URL);
    private final ServerAccessPoint getSchools =
            new ServerAccessPoint(ServerResource.GET_ALL_SCHOOLS_URL);

    ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb)
    {
        populateSchoolsBox();
    }

    @FXML
    private void handleRegister(ActionEvent event)
    {
        //Check if email already registered/ add to database 
        //Set account to inactive
        if (email.getText().indexOf('@') != -1 && email.getText().equals(confirmedEmail.getText()) && password.getText().equals(confirmedPassword.getText()))
        {
            //create a student DTO
            SimpleStudent student = new SimpleStudent();
            student.email = email.getText();
            student.name = name.getText();
            student.password = password.getText();
            student.school = schoolsBox.getValue();

            //transmit new student form to server
            Response rsp = newStudent.request(student);
            //check response code
            if (rsp.getStatus() != Response.Status.OK.getStatusCode())
            {
                //@TODO handle error codes
            }

            //CLEAR FIELDS
            email.clear();
            confirmedEmail.clear();
            password.clear();
            confirmedPassword.clear();
            name.clear();
            //Cant figure out how to reset school box
            myController.setScreen(CSE308GUI.LoginPageID);
        } else
        {
            //Check to see if it email for password not correct
            if (email.getText().indexOf('@') == -1)
            {
                 Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Input Error");
                alert.setHeaderText("Invalid Email");
                alert.setContentText("Please enter a valid email");
                 alert.show();
            } else if (!(email.getText().equals(confirmedEmail.getText())))
            {
                 Alert alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("Input Error");
                 alert.setHeaderText("Check Emails");
                 alert.setContentText("Entered email addressed do not match.");
                 alert.show();
            } else if (!(password.getText().equals(confirmedPassword.getText())))
            {
                 Alert alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("Input Error");
                 alert.setHeaderText("Check Passwords");
                 alert.setContentText("Entered passwords do not match.");
                alert.show();
            }
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }

    @FXML
    public void handleBack(ActionEvent event)
    {
        email.clear();
        confirmedEmail.clear();
        password.clear();
        confirmedPassword.clear();
        name.clear();
        //Cant figure out how to reset school box
        myController.setScreen(CSE308GUI.LoginPageID);
    }

    public void populateSchoolsBox()
    {
        //request list of schools
        Response rsp = getSchools.request();

        //check the response status code
        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            //@TODO error handling   
        }
        GenericType<List<SimpleSchool>> gtlc = new GenericType<List<SimpleSchool>>()
        {
        };
        //read schools from http response
        List<SimpleSchool> schools = rsp.readEntity(gtlc);
        
        //extract school names from schools
        ArrayList<String> schoolNames = new ArrayList<>();
        for (SimpleSchool sch : schools)
        {
            schoolNames.add(sch.name);
        }

        //populate combobox
        schoolsBox.getItems().clear();
        schoolsBox.getItems().addAll(schoolNames);
    }

    @Override
    public void populatePage() {
    }

}
