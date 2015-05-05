/*

 */
package shield.client.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.main.CSE308GUI;
import shield.client.view.session.StudentSession;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleStudent;

/**
 * FXML Controller class
 *
 * @author Jeffrey Kabot
 */
public class DesignASchedulePageController implements Initializable, ControlledScreen
{

    @FXML
    private ComboBox<String> courseBox;
    @FXML
    private Label courses;

    ScreensController myController;

    private final ServerAccessPoint getSchoolCourses =
            new ServerAccessPoint(ServerResource.GET_SCHOOL_COURSES_URL);

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
    private void handleSelectCourse(ActionEvent event)
    {
        String course = courseBox.getValue().substring(0, courseBox.getValue().indexOf(","));
        StudentSession ss = (StudentSession) myController.getSession();

        ss.setCourseName(course);
        ss.setCourseIdentifier(courseBox.getValue().substring(courseBox.getValue().indexOf(",") + 2));

        myController.loadScreen(CSE308GUI.ChoosePreferredSectionsPageID, CSE308GUI.ChoosePreferredSectionsPage);
        myController.setScreen(CSE308GUI.ChoosePreferredSectionsPageID);
    }

    @FXML
    private void handleBack(ActionEvent event)
    {
        myController.loadScreen(CSE308GUI.SelectDesignScheduleYearPageID, CSE308GUI.SelectDesignScheduleYearPage);
        myController.setScreen(CSE308GUI.SelectDesignScheduleYearPageID);
    }

    @FXML
    private void handleFinished(ActionEvent event)
    {
        return;
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
        SimpleStudent student = ss.getStudentAccount();

        //request the list of courses
        Response rsp = getSchoolCourses.request(student);

        //check the response status code
        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            //in this interaction, there was some kind of error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rsp.getStatusInfo().getReasonPhrase());
            alert.setContentText(rsp.readEntity(String.class));
            alert.show();
            return;
        }
        
        //read the list of courses
        GenericType<List<SimpleCourse>> gtlc = new GenericType<List<SimpleCourse>>() {
        };
        
        List<SimpleCourse> courses = rsp.readEntity(gtlc);
        ss.setCourses(new ArrayList<>(courses));
        
        //copy the names
        ArrayList<String> courseNames = new ArrayList<>();
        for (SimpleCourse sc : courses)
        {
            courseNames.add(sc.name);
        }
        
        //fill the box
        courseBox.getItems().clear();
        courseBox.getItems().addAll(courseNames);
    }

}
