/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import shield.client.main.CSE308GUI;
import shield.client.view.session.StudentSession;
import shield.shared.dto.SimpleSchedule;

/**
 * FXML Controller class
 *
 * @author evanguby
 */
public class SelectAcademicYearPageController implements Initializable, ControlledScreen
{

    ScreensController myController;

    @FXML
    private ComboBox<String> year;

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
    private void handleSelectYear(ActionEvent event)
    {
        if (year.getValue() != null)
        {
            StudentSession ss = (StudentSession) myController.getSession();
            ss.setScheduleYear(Integer.parseInt(year.getValue()));
            //ss.newAssignedSchedule();
            //ss.setAssignedScheduleAsString("");
            //SimpleSchedule sss = new SimpleSchedule();
            //sss.studentEmail = ss.getStudentAccount().email;
            //sss.year = ss.getScheduleYear();
            //Response rsp = createSchedule.request(sss);
            myController.loadScreen(CSE308GUI.CourseOfferingsPageID, CSE308GUI.CourseOfferingsPage);
            myController.setScreen(CSE308GUI.CourseOfferingsPageID);
        }
    }

    @FXML
    private void handleBack(ActionEvent event)
    {
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
        ObservableList<String> options =
                 FXCollections.observableArrayList(
                        "1",
                        "2",
                        "3",
                        "4"
                );
        year.setItems(options);
    }

}
