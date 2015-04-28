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
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleStudent;

/**
 * FXML Controller class
 *
 * @author Evan Guby, Jeffrey Kabot
 */
public class ManageStudentAccountsController implements Initializable, ControlledScreen
{

    @FXML
    private ListView<?> existing = new ListView<>();
    @FXML
    private ListView<?> requested = new ListView<>();

    private final ServerAccessPoint getApprovedStudents =
            new ServerAccessPoint(ServerResource.GET_APPROVED_STUDENTS_URL);

    private final ServerAccessPoint getPendingStudents =
            new ServerAccessPoint(ServerResource.GET_PENDING_STUDENTS_URL);

    private final ServerAccessPoint approveStudent =
            new ServerAccessPoint(ServerResource.APPROVE_STUDENT_URL);

    private final ServerAccessPoint deleteStudent =
            new ServerAccessPoint(ServerResource.DELETE_STUDENT_URL);

    private List<SimpleStudent> approvedStudents = null;
    private List<SimpleStudent> pendingStudents = null;

    ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb)
    {
        populateApprovedStudentField();
        populatePendingStudentField();
    }

    public void populateApprovedStudentField()
    {
        //get the list of students
        Response rsp = getApprovedStudents.request();
        //check response code
        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            //@TODO error handling
            int code = rsp.getStatus();
            if (code == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            {
                //something terrible happened
            }
        }
        GenericType<List<SimpleStudent>> gtlc = new GenericType<List<SimpleStudent>>()
        {
        };

        //read students from server response
        approvedStudents = rsp.readEntity(gtlc);

        //extract student names
        ArrayList<String> studentNames = new ArrayList();
        for (SimpleStudent sch : approvedStudents)
        {
            studentNames.add(sch.name);
        }

        //populate combobox
        Collection stus = studentNames;
        existing.getItems().clear();
        existing.getItems().addAll(stus);
    }

    public void populatePendingStudentField()
    {
        //get the list of students
        Response rsp = getPendingStudents.request();
        //check response code
        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            int code = rsp.getStatus();
            if (code == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            {
                //something terrible happened
            }
        }
        GenericType<List<SimpleStudent>> gtlc = new GenericType<List<SimpleStudent>>()
        {
        };

        //read students from server response
        pendingStudents = rsp.readEntity(gtlc);

        //extract student names
        ArrayList<String> studentNames = new ArrayList();
        for (SimpleStudent sch : pendingStudents)
        {
            studentNames.add(sch.name);
        }

        //populate combobox
        Collection stus = studentNames;
        requested.getItems().clear();
        requested.getItems().addAll(stus);
    }

    @FXML
    private void handleBack(ActionEvent event)
    {
        myController.setScreen(CSE308GUI.AdminViewID);
    }

    @FXML
    private void handleAcceptSelected(ActionEvent event)
    {
        int idx = requested.getSelectionModel().getSelectedIndex();
        SimpleStudent student = pendingStudents.get(idx);
        Response rsp = approveStudent.request(student);
        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            //@TODO error handling
            int code = rsp.getStatus();
            if (code == Response.Status.CONFLICT.getStatusCode())
            {
                //account approved already
            } else if (code == Response.Status.BAD_REQUEST.getStatusCode())
            {
                //account not exist
            } else if (code == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            {
                //something terrible happened
            }
        }
        populateApprovedStudentField();
        populatePendingStudentField();
    }

    @FXML
    private void handleRejectSelected(ActionEvent event)
    {
        int idx = requested.getSelectionModel().getSelectedIndex();
        SimpleStudent student = pendingStudents.get(idx);
        Response rsp = deleteStudent.request(student);
        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            //@TODO error handling
            int code = rsp.getStatus();
            if (code == Response.Status.BAD_REQUEST.getStatusCode())
            {
                //account not exist
            } else if (code == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            {
                //something terrible happened
            }
        }
        populateApprovedStudentField();
        populatePendingStudentField();
    }

    @FXML
    private void handleAcceptAll(ActionEvent event)
    {
        //@TODO modify server functionality to accept all pending
        Response rsp;
        for (SimpleStudent student : pendingStudents)
        {
            rsp = deleteStudent.request(student);
            if (rsp.getStatus() != Response.Status.OK.getStatusCode())
            {
                //@TODO error handling
                break;
            }
        }
        populateApprovedStudentField();
        populatePendingStudentField();
    }

    @FXML
    private void handleDeleteSelected(ActionEvent event)
    {
        int idx = existing.getSelectionModel().getSelectedIndex();
        SimpleStudent student = approvedStudents.get(idx);
        Response rsp = deleteStudent.request(student);
        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            //@TODO error handling
            int code = rsp.getStatus();
            if (code == Response.Status.BAD_REQUEST.getStatusCode())
            {
                //account not exist
            } else if (code == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            {
                //something terrible happened
            }
        }
        populateApprovedStudentField();
        populatePendingStudentField();
    }

    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }

    @Override
    public void populatePage()
    {
    }

}
