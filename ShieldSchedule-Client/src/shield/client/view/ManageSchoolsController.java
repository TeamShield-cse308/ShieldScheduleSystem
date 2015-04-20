/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

import shield.client.main.CSE308GUI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResources;
import shield.shared.dto.SimpleSchool;

/**
 * FXML Controller class Some code adapted from
 *
 * @author Evan Guby, Jeffrey Kabot
 */
public class ManageSchoolsController implements Initializable, ControlledScreen
{

    //Box listing schools in the database
    @FXML
    public ComboBox<String> schoolsBox;

    ScreensController myController;

    private final ServerAccessPoint getSchools =
            new ServerAccessPoint(ServerResources.GET_ALL_SCHOOLS_URL);
    private final ServerAccessPoint deleteSchool =
            new ServerAccessPoint(ServerResources.DELETE_SCHOOL_URL);

    private List<SimpleSchool> schoolsList = null;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb)
    {
        //populateSchoolsBox();
    }

    public void populatePage()
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
        schoolsList = rsp.readEntity(gtlc);
        //extract school names from schools
        ArrayList<String> schoolNames = new ArrayList<>();
        for (SimpleSchool sch : schoolsList)
        {
            schoolNames.add(sch.name);
        }
        myController.setSchools(schoolsList);
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
    private void handleDeleteSchool(ActionEvent event)
    {
        //get the school to delete
        int idx = schoolsBox.getSelectionModel().getSelectedIndex();
        SimpleSchool school = schoolsList.get(idx);

        //send it to the server
        Response rsp = deleteSchool.request(school);

        //check response status code
        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            //@TODO handle error code
            int code = rsp.getStatus();
            if (code == Response.Status.BAD_REQUEST.getStatusCode())
            {
                //invalid school name
            } else if (code == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            {
                //something terrible happenedF
            }
        }

        myController.loadScreen(CSE308GUI.ManageSchoolsID, CSE308GUI.ManageSchools);
        myController.setScreen(CSE308GUI.ManageSchoolsID);
    }

    @FXML
    private void handleEditSchool(ActionEvent event)
    {
        String content = schoolsBox.getValue().toString();
        myController.setSchool(content);
//        myController.updateSchoolInfoScreen();
        myController.loadSchoolInfoScreen();
        myController.setScreen(CSE308GUI.EditSchoolInfoID);

    }

    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }

}
