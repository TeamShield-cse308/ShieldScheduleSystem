/*

 */
package shield.client.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import shield.client.main.CSE308GUI;
import shield.client.view.session.StudentSession;
import shield.client.web.ServerAccessPoint;
import shield.client.web.ServerResource;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleCriteriaCourse;
import shield.shared.dto.SimpleSection;

/**
 * FXML Controller class
 *
 * @author Jeffrey Kabot
 */
public class ChoosePreferredSectionsController implements Initializable, ControlledScreen
{

    ScreensController myController;

    @FXML
    private ListView<String> sections;
    @FXML
    private ListView<String> notWantedSections;
    @FXML
    private ComboBox<String> preferredProfessor;

    private final ServerAccessPoint getCourseSections =
            new ServerAccessPoint(ServerResource.GET_COURSE_SECTIONS_URL);

    private final ServerAccessPoint addDesiredCourse =
            new ServerAccessPoint(ServerResource.ADD_DESIRED_COURSE_URL);

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
    private void handleRemoveSection(ActionEvent event)
    {

    }

    @FXML
    private void handleSavePreferences(ActionEvent event)
    {
        StudentSession ss = (StudentSession) myController.getSession();
        SimpleCriteriaCourse scc = new SimpleCriteriaCourse();
        List<SimpleSection> sectionList = new ArrayList<>();

        if (!sections.getSelectionModel().getSelectedItem().equals("No Preference"))
        {
            ObservableList<Integer> indices = sections.getSelectionModel().getSelectedIndices();

            for (int i : indices)
            {
                sectionList.add(ss.getSections().get(i));
            }
        }

        scc.course = ss.getCourse();
        if (preferredProfessor.getSelectionModel().isEmpty() || preferredProfessor.getSelectionModel().getSelectedItem().equals("No Preference"))
        {
            scc.teacher = null;
        } else
        {
            scc.teacher = preferredProfessor.getSelectionModel().getSelectedItem();
        }
        scc.studentEmail = ss.getStudentAccount().email;
        scc.year = ss.getScheduleYear();
        scc.sections = sectionList;

        Response rsp = addDesiredCourse.request(scc);

        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            //@TODO error happened
            return;
        }

        myController.loadScreen(CSE308GUI.DesignASchedulePageID, CSE308GUI.DesignASchedulePage);
        myController.setScreen(CSE308GUI.DesignASchedulePageID);
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
        SimpleCourse sc = ss.getCourse();

        sc.school = ss.getSchool().name;
        sc.year = ss.getScheduleYear();

        Response rsp = getCourseSections.request(sc);

        if (rsp.getStatus() != Response.Status.OK.getStatusCode())
        {
            return;
        }

        GenericType<List<SimpleSection>> gtlc = new GenericType<List<SimpleSection>>()
        {
        };

        List<SimpleSection> sectionList = rsp.readEntity(gtlc);
        ss.setSections(sectionList);

        ArrayList<String> sectionNames = new ArrayList<>();
        Set<String> teacherNames = new HashSet<>();

        for (SimpleSection s : sectionList)
        {
            String sectionDays = "";
            for (int i = 0; i < s.scheduleBlock.scheduleDays.length(); i++)
            {
                sectionDays += s.scheduleBlock.scheduleDays.substring(i, i + 1) + ", ";
            }
            sectionNames.add(s.teacherName + " Period: " + s.scheduleBlock.period +
                    " Days: " + sectionDays.substring(0, sectionDays.lastIndexOf(",")));
            teacherNames.add(s.teacherName);
        }

        sectionNames.add("No Preference");
        teacherNames.add("No Preference");

        sections.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        sections.getItems().clear();
        sections.getItems().addAll(sectionNames);

        preferredProfessor.getItems().clear();
        preferredProfessor.getItems().addAll(teacherNames);
    }

}
