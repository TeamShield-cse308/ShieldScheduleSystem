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
import javafx.scene.control.TextField;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.view.session.Session;
import shield.client.view.session.StudentSession;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleSchool;
import shield.shared.dto.SimpleStudent;
/**
 * FXML Controller class
 *
 * @author Evan Guby
 */
public class AddCourseController implements Initializable, ControlledScreen {
    @FXML
    private TextField name;
//    @FXML
//    private TextField id;
    @FXML
    private TextField semesters;
    
    @FXML
    private ComboBox<String> courseBox;
    
    ScreensController myController;
    
    private final ServerAccessPoint newCourse =
            new ServerAccessPoint(ServerResource.ADD_COURSE_URL);
<<<<<<< HEAD

=======
>>>>>>> origin/master

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //populatePage();
    }    

    @FXML
    private void handleSaveCourse(ActionEvent event) {
        String courseName = name.getText();
        //String courseID = id.getText();
        String semester = semesters.getText();
        String sem[] = semester.split(",");
        int courseNum = sem.length;
        ArrayList <SimpleCourse> courses = new ArrayList<SimpleCourse>();
        for(int i = 0; i<courseNum; i++){
            SimpleCourse c = new SimpleCourse();
            c.name = courseName;
            c.semester = Integer.parseInt(sem[i]);
            Session s = myController.getSession();
            StudentSession ss = (StudentSession)s;
            c.school = ss.getStudentAccount().school;
            Response rsp = newCourse.request(c);
        }
        
        myController.setScreen(CSE308GUI.AddSchoolCoursesID);
    }

    @FXML
    private void handleBack(ActionEvent event) {
        myController.setScreen(CSE308GUI.AddSchoolCoursesID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void populatePage() {
        
    }
    
}
