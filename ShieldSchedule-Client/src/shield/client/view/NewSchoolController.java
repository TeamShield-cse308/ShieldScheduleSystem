/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import shield.client.main.CSE308GUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import shield.shared.dto.SimpleSchool;
import shield.client.web.MessageExchange;

/**
 * FXML Controller class jax-rs code adapted from
 * http://www.hascode.com/2013/12/jax-rs-2-0-rest-client-features-by-example/
 *
 * @author Evan Guby, Jeffrey Kabot
 */
public class NewSchoolController implements Initializable, ControlledScreen
{

    @FXML
    private TextField name;
    @FXML
    private TextField semesters;
    @FXML
    private TextField scheduleDays;
    @FXML
    private TextField periods;
    @FXML
    private TextField startingLunch;
    @FXML
    private TextField endingLunch;

    ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    @FXML
    private void handleCreateSchool(ActionEvent event)
    {
        //retrieve school data
        String initSchoolName = name.getText();
        String initSemesters = semesters.getText();
        String initScheduleDays = scheduleDays.getText();
        String initPeriods = periods.getText();

        String initStartingLunchPeriod = startingLunch.getText();
        String initEndingLunchPeriod = endingLunch.getText();

        SimpleSchool school = new SimpleSchool();
        school.name = initSchoolName;
        school.numSemesters = initSemesters;
        school.numScheduleDays = initScheduleDays;
        school.numPeriods = initPeriods;
        school.startingLunchPeriod = initStartingLunchPeriod;
        school.endingLunchPeriod = initEndingLunchPeriod;

        //connect to server
        WebTarget clientTarget;
        Client client = ClientBuilder.newClient();
        //@TODO register a json MessageBodyWriter
        client.register(JacksonJsonProvider.class);
        clientTarget = client.target(MessageExchange.ADD_SCHOOL_URL);
        System.out.println(clientTarget.getUri().toString());
        
        //send the new school request
        clientTarget.request().post(Entity.entity(school,
                MediaType.APPLICATION_JSON), SimpleSchool.class);

    }

    @FXML
    private void handleCancel(ActionEvent event)
    {
        myController.setScreen(CSE308GUI.ManageSchoolsID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }

}
