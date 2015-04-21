/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.client.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import shield.client.main.CSE308GUI;
import shield.client.view.session.AdminSession;
import shield.client.view.session.Session;
import shield.client.view.session.StudentSession;
import shield.shared.dto.AbstractAccount;
import shield.shared.dto.SimpleSchool;
import shield.shared.dto.SimpleStudent;

/**
 *
 * @author Evan Guby
 */
public class ScreensController extends StackPane
{

    private HashMap<String, Node> screens = new HashMap<>();
    private String school;
    private List<SimpleSchool> schools;

    private Session session;

    public ScreensController()
    {
        super();
    }

    public void addScreen(String name,
            Node screen)
    {
        screens.put(name, screen);
    }

    public Node getScreen(String name)
    {
        return screens.get(name);
    }

    public boolean loadScreen(String name,
            String resource)
    {
        try
        {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/shield/client/view/" + resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControlledScreen myScreenController = ((ControlledScreen) myLoader.getController());
            myScreenController.setScreenParent(this);
            myScreenController.populatePage();
            addScreen(name, loadScreen);
            return true;
        } catch (IOException ex)
        {
            System.out.println("Screen not loaded:" + name + ex);
            return false;
        }
    }

    public boolean setScreen(final String name)
    {
        if (screens.get(name) != null)
        {
            final DoubleProperty opacity = opacityProperty();
            if (!getChildren().isEmpty())
            {

                getChildren().remove(0);
                getChildren().add(0, screens.get(name));

            } else
            {

                getChildren().add(screens.get(name));

            }
            return true;
        } else
        {
            System.out.println("Screen has not loaded!");
            return false;
        }
    }

    public boolean unloadScreen(String name)
    {
        if (screens.remove(name) == null)
        {
            System.out.println("Screen doesn't exist");
            return false;

        } else
        {
            return true;
        }
    }

    public Session getSession()
    {
        return session;
    }
    public void createSession(AbstractAccount account)
    {
        if (account instanceof SimpleStudent)
            session = new StudentSession((SimpleStudent)account);
        else
            session = new AdminSession(); //@TODO pass admin parameters
    }

    public String getSchool()
    {
        return school;
    }

    public void setSchool(String school)
    {
        this.school = school;
    }

    public List<SimpleSchool> getSchools()
    {
        return schools;
    }

    public void setSchools(List<SimpleSchool> schools)
    {
        this.schools = schools;
    }

    public void loadSchoolInfoScreen()
    {
        try
        {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/shield/client/view/" + CSE308GUI.EditSchoolInfo));
            Parent loadScreen = (Parent) myLoader.load();
            EditSchoolInfoController myScreenController = ((EditSchoolInfoController) myLoader.getController());
            myScreenController.setScreenParent(this);
            myScreenController.populatePage();
            addScreen(CSE308GUI.EditSchoolInfoID, loadScreen);
            return;
        } catch (IOException ex)
        {
            System.out.println("Screen not loaded:" + CSE308GUI.EditSchoolInfoID + ex);
            return;
        }
    }
}
