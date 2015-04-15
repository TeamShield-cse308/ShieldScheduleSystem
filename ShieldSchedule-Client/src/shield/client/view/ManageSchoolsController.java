/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import shield.client.main.CSE308GUI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import shield.client.web.MessageExchange;
import shield.shared.dto.SimpleSchool;


/**
 * FXML Controller class 
 * Some code adapted from
 * http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/javafx_json_tutorial/javafx_javaee7_json_tutorial.html#section2s1
 *
 * @author Evan Guby, Jeffrey Kabot
 */
public class ManageSchoolsController implements Initializable, ControlledScreen
{

    //Box listing schools in the database
    @FXML
    public ComboBox<String> schoolsBox;

    ScreensController myController;


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        populateSchoolsBox();
    }

    public void populateSchoolsBox()
    {
        //adapted from oracle javafx / javaee tutorial
        //connect to shield schedule server
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        client.register(JacksonJsonProvider.class);
        clientTarget = client.target(MessageExchange.GET_ALL_SCHOOLS_URL);
        GenericType<List<SimpleSchool>> gtlc = new GenericType<List<SimpleSchool>>()
        {
        };

        //get a list of all schools in database, transmitted from server in JSON
        List<SimpleSchool> schools = clientTarget.request("application/json").get(gtlc);

        //extract school names from schools
        ArrayList<String> schoolNames = new ArrayList<>();
        for (SimpleSchool sch : schools) {
            schoolNames.add(sch.name);
        }

        //populate combobox
        schoolsBox.getItems().clear();
        schoolsBox.getItems().addAll(schoolNames);
    }

    @FXML
    private void handleGoBack(ActionEvent event)
    {
        myController.setScreen(CSE308GUI.AdminViewID);
    }

    @FXML
    private void handleNewSchool(ActionEvent event)
    {
        myController.setScreen(CSE308GUI.NewSchoolID);
    }

    @FXML
    private void handleEditSchool(ActionEvent event)
    {
        myController.setScreen(CSE308GUI.EditSchoolInfoID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }

}
