/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.main.CSE308GUI;
import shield.client.view.session.StudentSession;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleFriendship;
import shield.shared.dto.SimpleStudent;

/**
 * FXML Controller class
 *
 * @author evanguby
 */
public class StudentViewController implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    private Label welcome;
    @FXML
    private TextField add;
    @FXML
    private ListView<?> friendsListView;
    @FXML
    private ListView<?> friendRequestsListView;

    private final ServerAccessPoint GET_FRIEND_LIST = 
            new ServerAccessPoint(ServerResource.GET_FRIENDS_URL);
    
    private final ServerAccessPoint GET_FRIEND_REQUESTS = 
            new ServerAccessPoint(ServerResource.GET_FRIEND_REQUESTS_URL);
    
    private final ServerAccessPoint ADD_FRIEND = 
            new ServerAccessPoint(ServerResource.ADD_FRIEND_URL);
    
    private final ServerAccessPoint APPROVE_FRIEND_REQUEST = 
            new ServerAccessPoint(ServerResource.APPROVE_FRIEND_URL);
    
    private final ServerAccessPoint DELETE_FRIEND = 
            new ServerAccessPoint(ServerResource.DELETE_FRIEND_URL);
    
    private List<SimpleFriendship> pendingRequests = null;
    private List<SimpleStudent> friends = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleViewSchedules(ActionEvent event) {
        myController.loadScreen(CSE308GUI.ViewSchedulesPageID, CSE308GUI.ViewSchedulesPage);
        myController.setScreen(CSE308GUI.ViewSchedulesPageID);
    }

    @FXML
    private void handleDesignASchedule(ActionEvent event) {
        
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        String recip = add.getText();
        StudentSession ses = (StudentSession) myController.getSession();
        String send = ses.getStudentAccount().email;
        
        SimpleFriendship fs = new SimpleFriendship();
        
        fs.recipientName = recip;
        fs.senderEmail = send;
        
        
        Response rsp = ADD_FRIEND.request(fs);
        
        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            //@TODO error handling
            int code = rsp.getStatus();
            if (code == Response.Status.CONFLICT.getStatusCode())
            {
                
            } else if (code == Response.Status.BAD_REQUEST.getStatusCode())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("No Such Person");
                alert.setContentText("The person you are trying to add does not exist.");
                alert.show();
            } else if (code == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("No Such Person");
                alert.setContentText("The person you are trying to add does not exist.");
                alert.show();
            }
           
        }else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("Request Sent");
            alert.setContentText(recip + " has been sent a friend request!");
            alert.show();
        }
    }

    @FXML
    private void handleAcceptSelected(ActionEvent event) {
        int idx = friendRequestsListView.getSelectionModel().getSelectedIndex();
        
        SimpleFriendship sf = pendingRequests.get(idx);
        sf.approved = true;
        
        
        Response rsp =  APPROVE_FRIEND_REQUEST.request(sf);
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
        
        populateFriendRequestsListView();
        populateFriendsListView();
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        myController.setScreen(CSE308GUI.LoginPageID);
    }

    @FXML
    private void handleEnterAssignedSchedule(ActionEvent event) {
        
        myController.loadScreen(CSE308GUI.SelectScheduleYearPageID, CSE308GUI.SelectScheduleYearPage);
        myController.setScreen(CSE308GUI.SelectScheduleYearPageID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
        populateFriendRequestsListView();
        populateFriendsListView();
    }

    @Override
    public void populatePage() {
        StudentSession ss = (StudentSession)myController.getSession();
        welcome.setText("Welcome " + ss.getStudentAccount().name + "!");
    }
    
    private void populateFriendsListView()
    {
        StudentSession ses = (StudentSession) myController.getSession();
        SimpleStudent stu = ses.getStudentAccount();
        
       // String loggedInAs = myController.getSessionEmail();
        
        Response rsp = GET_FRIEND_LIST.request(stu);
        
        GenericType<List<SimpleStudent>> gtlc = new GenericType<List<SimpleStudent>>()
        {
        };
        
        friends = rsp.readEntity(gtlc);
        
        ArrayList<String> friendNames = new ArrayList();
        
        for(SimpleStudent ss : friends)
        {
            friendNames.add(ss.email);
        }
        
        Collection names = friendNames;
        
        friendsListView.getItems().clear();
        friendsListView.getItems().addAll(names);
        
    }
    private void populateFriendRequestsListView()
    {
        StudentSession ses = (StudentSession) myController.getSession();
        SimpleStudent stu = ses.getStudentAccount();
        
       // String loggedInAs = myController.getSessionEmail();
        
        Response rsp = GET_FRIEND_REQUESTS.request(stu);
        
        GenericType<List<SimpleFriendship>> gtlc = new GenericType<List<SimpleFriendship>>()
        {
        };
        
        pendingRequests = rsp.readEntity(gtlc);
        
        ArrayList<String> requestNames = new ArrayList();
        
        for(SimpleFriendship sf : pendingRequests)
        {
            requestNames.add(sf.senderEmail);
        }
        
        Collection names = requestNames;
        
        friendRequestsListView.getItems().clear();
        friendRequestsListView.getItems().addAll(names);
        
    }
    @FXML
    public void handleDeleteSelectedFriend(ActionEvent event){
        int idx = friendsListView.getSelectionModel().getSelectedIndex();
        
        SimpleStudent ss = friends.get(idx);
        StudentSession ses = (StudentSession) myController.getSession();
        SimpleStudent stu = ses.getStudentAccount();
        SimpleFriendship sf = new SimpleFriendship();
        sf.recipientEmail = stu.email;
        sf.senderEmail = ss.email;
        
        
        
        Response rsp =  DELETE_FRIEND.request(sf);
        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            //@TODO error handling
            int code = rsp.getStatus();
            if (code == Response.Status.CONFLICT.getStatusCode())
            {
                
            } else if (code == Response.Status.BAD_REQUEST.getStatusCode())
            {
                
            } else if (code == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            {
                
            }
        }else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("Friend Deleted");
            alert.setContentText("You have deleted");
            alert.show();
        }
    }

}
