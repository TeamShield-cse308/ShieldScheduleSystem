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
import javafx.scene.control.ComboBox;
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
    private TextField confirmedPassword;
    @FXML
    private TextField password;
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
        System.out.println(name.getText());
        myController.setScreen(CSE308GUI.LoginPageID);
    }
    
    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }

    public void populateSchoolsBox()
    {
        //adapted from oracle javafx / javaee tutorial
        //connect to shield schedule server
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        client.register(SchoolsBodyReader.class);
        clientTarget = client.target("http://localhost:8080/ShieldSchedule-Server/webresources/admin-schools");
        GenericType<List<School>> gtlc = new GenericType<List<School>>()
        {
        };
        
        //get a list of all schools in database, transmitted from server in JSON
        List<School> schools = clientTarget.request("application/json").get(gtlc);
        
        //extract school names from schools
        ArrayList<String> schoolNames = new ArrayList<>();
        for (School sch : schools)
        {
            schoolNames.add(sch.getSchoolName());
        }
        
        //populate combobox
        school.getItems().clear();
        school.getItems().addAll(schoolNames);
    }
    
}
