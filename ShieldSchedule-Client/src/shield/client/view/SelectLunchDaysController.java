/*

 */
package shield.client.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javax.ws.rs.core.Response;
import shield.client.main.CSE308GUI;
import shield.client.view.session.StudentSession;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleCriteria;

/**
 * FXML Controller class
 *
 * @author Jeffrey Kabot
 */
public class SelectLunchDaysController implements Initializable, ControlledScreen
{
    @FXML
    private Label welcome;
    @FXML
    private CheckBox lunchDay1;
    @FXML
    private CheckBox lunchDay2;
    @FXML
    private CheckBox lunchDay3;
    @FXML
    private CheckBox lunchDay4;
    @FXML
    private CheckBox lunchDay5;
    @FXML
    private CheckBox lunchDay6;
    @FXML
    private CheckBox lunchDay7;
    
    ScreensController myController;
    
    private final ServerAccessPoint setDesiredLunches = 
            new ServerAccessPoint(ServerResource.SET_DESIRED_LUNCHES_URL);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void handleFinishSchedule(ActionEvent event)
    {
        StudentSession ss = (StudentSession) myController.getSession();
        SimpleCriteria sc = new SimpleCriteria();
        boolean[] lunches = new boolean[ss.getSchool().numScheduleDays];
        CheckBox[] checkboxes = { lunchDay1, lunchDay2, lunchDay3, lunchDay4, lunchDay5, lunchDay6, lunchDay7};
        for (int i = 0; i < ss.getSchool().numScheduleDays; i++)
        {
            lunches[i] = checkboxes[i].selectedProperty().get();
        }
        sc.studentEmail = ss.getStudentAccount().email;
        sc.year = ss.getScheduleYear();
        sc.hasLunches = lunches;
        
        Response rsp = setDesiredLunches.request(sc);
        
        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            //@TODO error handling  
//            return;
        }
        
        myController.loadScreen(CSE308GUI.StudentViewID, CSE308GUI.StudentView);
        myController.setScreen(CSE308GUI.StudentViewID);
        
        
    }

    @Override
    public void setScreenParent(ScreensController screenPage)
    {
        myController = screenPage;
    }

    @Override
    public void populatePage()
    {
        StudentSession ss = (StudentSession) myController.getSession();
        int days = ss.getSchool().numScheduleDays;
        CheckBox[] checkboxes = { lunchDay1, lunchDay2, lunchDay3, lunchDay4, lunchDay5, lunchDay6, lunchDay7};
        for (int i = 0; i < days; i++)
        {
            checkboxes[i].setVisible(true);
        }
    }
    
}
