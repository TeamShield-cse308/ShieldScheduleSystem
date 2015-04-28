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
import javafx.scene.control.ListView;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;

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
    
    private final ServerAccessPoint GET_FRIEND_LIST = 
            new ServerAccessPoint(ServerResource.GET_FRIENDS_URL);
    
    private final ServerAccessPoint GET_FRIEND_REQUESTS = 
            new ServerAccessPoint(ServerResource.GET_FRIEND_REQUESTS_URL);
    
    private final ServerAccessPoint ADD_FRIEND = 
            new ServerAccessPoint(ServerResource.ADD_FRIEND_URL);
    
    private final ServerAccessPoint APPROVE_FRIEND_REQUEST = 
            new ServerAccessPoint(ServerResource.APPROVE_FRIEND_URL);
    
    
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
    }

    @FXML
    private void handleAdd(ActionEvent event) {
    }
    
    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void populatePage() {
        populateFriendsListView();
        populateFriendRequestsListView();
    }
    
    private void populateFriendsListView()
    {
        
    }
    private void populateFriendRequestsListView()
    {
        
    }
}
