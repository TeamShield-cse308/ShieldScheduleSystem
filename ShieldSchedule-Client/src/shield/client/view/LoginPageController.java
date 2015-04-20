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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.ws.rs.core.Response;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResources;
import shield.shared.dto.LoginCredentials;
import shield.shared.dto.SimpleStudent;

/**
 * FXML Controller class
 *
 * @author Evan Guby
 */
public class LoginPageController implements Initializable, ControlledScreen
{

    ScreensController myController;
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    private final ServerAccessPoint AUTHENTICATE =
            new ServerAccessPoint(ServerResources.AUTHENTICATION_URL);

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
        if (email.getCharacters().toString().equals("admin"))
        {
            myController.setScreen(CSE308GUI.AdminViewID);
        } else
        {
            LoginCredentials login = new LoginCredentials();
            login.username = email.getText();
            login.password = password.getText();
            Response rsp = AUTHENTICATE.request(login);
            if (rsp.getStatus() != Response.Status.OK.getStatusCode())
            {
                int code = rsp.getStatus();
                if (code == Response.Status.UNAUTHORIZED.getStatusCode())
                {
                    //wrong password
                    
                } else if (code == Response.Status.FORBIDDEN.getStatusCode())
                {
                    //account not yet approved
                } else if (code == Response.Status.CONFLICT.getStatusCode())
                {
                    //account is already active in another session
                } else if (code == Response.Status.BAD_REQUEST.getStatusCode())
                {
                    //no such username
                } else if (code == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                {
                    //something terrible happened
                }
                return;
            } else
            {
                SimpleStudent student = rsp.readEntity(SimpleStudent.class);
                myController.setScreen(CSE308GUI.StudentViewID);
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
