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
import javafx.scene.control.TextField;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import shield.client.view.session.StudentSession;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleFriendship;
import shield.shared.dto.SimpleStudent;

/**
 * FXML Controller class
 *
 * @author Evan Guby
 */
public class StudentViewController implements Initializable, ControlledScreen {

    ScreensController myController;
    
    @FXML
    private ListView<?> friendsListView = new ListView<>();
    
    @FXML
    private ListView<?> friendRequestsListView = new ListView<>();
    
    @FXML
    private TextField add;
    
    private final ServerAccessPoint GET_FRIEND_LIST = 
            new ServerAccessPoint(ServerResource.GET_FRIENDS_URL);
    
    private final ServerAccessPoint GET_FRIEND_REQUESTS = 
            new ServerAccessPoint(ServerResource.GET_FRIEND_REQUESTS_URL);
    
    private final ServerAccessPoint ADD_FRIEND = 
            new ServerAccessPoint(ServerResource.ADD_FRIEND_URL);
    
    private final ServerAccessPoint APPROVE_FRIEND_REQUEST = 
            new ServerAccessPoint(ServerResource.APPROVE_FRIEND_URL);
    
    private List<SimpleFriendship> pendingRequests = null;
    private List<SimpleStudent> friends = null;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populatePage();
    }    

    @FXML
    private void handleViewSchedules(ActionEvent event) {
    }

    @FXML
    private void handleAddCourses(ActionEvent event) {
        myController.setScreen(CSE308GUI.AddSchoolCoursesID);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        myController.setScreen(CSE308GUI.LoginPageID);
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
    private void handleAdd(ActionEvent event) {
        //get sender and receiver emails
        String recip = add.getText();
        StudentSession ses = (StudentSession) myController.getSession();
        String send = ses.getStudentAccount().email;
        
        SimpleFriendship fs = new SimpleFriendship();
        
        fs.recipientName = recip;
        fs.senderEmail = send;
        
        
        ADD_FRIEND.request(fs);
    }
    
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
        populateFriendRequestsListView();
        populateFriendsListView();
    }

    @Override
    public void populatePage() {
        
        
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
}
