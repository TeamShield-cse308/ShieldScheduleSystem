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
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import shield.client.web.MessageExchange;
import shield.shared.dto.SimpleStudent;

/**
 * FXML Controller class
 *
 * @author Evan Guby
 */
public class ManageStudentAccountsController implements Initializable, ControlledScreen {
    @FXML
    private ListView<?> existing;
    @FXML
    private ListView<?> requested;
    
    ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateStudentField();
    }    
    public void populateStudentField()
    {
         //adapted from oracle javafx / javaee tutorial
        //connect to shield schedule server
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        client.register(JacksonJsonProvider.class);
        clientTarget = client.target(MessageExchange.GET_ALL_STUDENTS_URL);
        GenericType<List<SimpleStudent>> gtlc = new GenericType<List<SimpleStudent>>()
        {
        };

        //get a list of all schools in database, transmitted from server in JSON
        List<SimpleStudent> students = clientTarget.request("application/json").get(gtlc);

        //extract school names from schools
        ArrayList<String> studentNames = new ArrayList();
        for (SimpleStudent sch : students) {
            studentNames.add(sch.name);
        }

        //populate combobox
        Collection stus = studentNames;
        existing.getItems().clear();
        existing.getItems().addAll(stus);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        myController.setScreen(CSE308GUI.AdminViewID);
    }

    @FXML
    private void handleAcceptSelected(ActionEvent event) {
    }

    @FXML
    private void handleRejectSelected(ActionEvent event) {
    }

    @FXML
    private void handleAcceptAll(ActionEvent event) {
    }

    @FXML
    private void handleDeleteSelected(ActionEvent event) {
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }
    
}
