/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResources;
import shield.shared.dto.SimpleStudent;

/**
 * FXML Controller class
 *
 * @author Evan Guby, Jeffrey Kabot
 */
public class ManageStudentAccountsController implements Initializable, ControlledScreen
{

    @FXML
    private ListView<?> existing;
    @FXML
    private ListView<?> requested;

    private ServerAccessPoint getAllStudents =
            new ServerAccessPoint(ServerResources.GET_ALL_STUDENTS_URL);

    ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb)
    {
        populateStudentField();
    }

    public void populateStudentField()
    {
        //get the list of students
        Response rsp = getAllStudents.request();
        //check response code
        if (rsp.getStatus() != Response.Status.OK.getStatusCode()) {
            //@TODO error handling
        }
        GenericType<List<SimpleStudent>> gtlc = new GenericType<List<SimpleStudent>>()
        {
        };

        //read students from server response
        List<SimpleStudent> students = rsp.readEntity(gtlc);

        //extract student names
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
    private void handleBack(ActionEvent event)
    {
        myController.setScreen(CSE308GUI.AdminViewID);
    }

    @FXML
    private void handleAcceptSelected(ActionEvent event)
    {
    }

    @FXML
    private void handleRejectSelected(ActionEvent event)
    {
    }

    @FXML
    private void handleAcceptAll(ActionEvent event)
    {
    }

    @FXML
    private void handleDeleteSelected(ActionEvent event)
    {
    }

    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }

}
