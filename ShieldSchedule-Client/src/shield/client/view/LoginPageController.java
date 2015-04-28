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
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.ws.rs.core.Response;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.LoginCredentials;
import shield.shared.dto.SimpleAdmin;
import shield.shared.dto.SimpleStudent;

/**
 * FXML Controller class
 *
 * @author Evan Guby, Jeffrey Kabot
 */
public class LoginPageController implements Initializable, ControlledScreen
{

    ScreensController myController;
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    private final ServerAccessPoint AUTHENTICATE =
            new ServerAccessPoint(ServerResource.AUTHENTICATION_URL);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb)
    {
        // @TODO
    }

    public void handleLogin(ActionEvent event)
    {
        //create a login credentials structure
        LoginCredentials login = new LoginCredentials();
        login.username = email.getText();
        login.password = password.getText();
        
        //transmit the login credentials to the server
        Response rsp = AUTHENTICATE.request(login);
        
        //if response codei indicates error then inform the client and return
        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            int code = rsp.getStatus();
            if (code == Response.Status.UNAUTHORIZED.getStatusCode()) //wrong password
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Error");
                alert.setHeaderText("Incorrect Password");
                alert.setContentText("Username exists but incorrect password.");
                alert.show();

            } else if (code == Response.Status.FORBIDDEN.getStatusCode()) //account not yet approved
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Error");
                alert.setHeaderText("Account Not Approved");
                alert.setContentText("Your account has not been approved by an admin.");
                alert.show();
            } else if (code == Response.Status.CONFLICT.getStatusCode()) //account is already active in another session
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Error");
                alert.setHeaderText("Already Logged In");
                alert.setContentText("This account is already logged in on a different client.");
                alert.show();
            } else if (code == Response.Status.BAD_REQUEST.getStatusCode()) //no such username
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Error");
                alert.setHeaderText("Incorrect Username");
                alert.setContentText("Username does not exist");
                alert.show();
            } else if (code == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) //something terrible happened
            {
               
            }
            return;
        }
        //successful response
        else
        {
            //students authenticate with an email
            if (login.username.indexOf('@') != -1)
            {
                SimpleStudent studentAcct = rsp.readEntity(SimpleStudent.class);
                myController.createStudentSession(studentAcct);
                myController.setScreen(CSE308GUI.StudentViewID);
            } 
            //administrators have no @ symbols in their username
            else
            {
                SimpleAdmin adminAcct = rsp.readEntity(SimpleAdmin.class);
                myController.createAdminSession(adminAcct);
                myController.setScreen(CSE308GUI.AdminViewID);
            }

        }

    }

    public void handleNewUser(ActionEvent event)
    {
        myController.setScreen(CSE308GUI.StudentRegistrationID);
    }

    public void handleForgotPassword(ActionEvent event)
    {
        myController.setScreen(CSE308GUI.ForgotPasswordID);
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
