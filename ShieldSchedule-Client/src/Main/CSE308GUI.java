/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.ManageSchoolsController;
import view.ScreensController;
/**
 *
 * @author Evan
 */
public class CSE308GUI extends Application {
    
    public static String LoginPage = "LoginPage.fxml";
    public static String LoginPageID = "LoginPage";
    public static String StudentRegistration = "StudentRegistration.fxml";
    public static String StudentRegistrationID = "StudentRegistration";
    public static String ForgotPassword = "ForgotPassword.fxml";
    public static String ForgotPasswordID = "ForgotPassword";
    public static String AdminView = "AdminView.fxml";
    public static String AdminViewID = "AdminView";
    public static String ManageSchools = "ManageSchools.fxml";
    public static String ManageSchoolsID = "ManageSchools";
    public static String NewSchool = "NewSchool.fxml";
    public static String NewSchoolID = "NewSchool";
    public static String EditSchool = "EditSchool.fxml";
    public static String EditSchoolID = "EditSchool";
    public static String EditSchoolInfo = "EditSchoolInfo.fxml";
    public static String EditSchoolInfoID = "EditSchoolInfo";
    public static String EditSchoolCourses = "EditSchoolCourses.fxml";
    public static String EditSchoolCoursesID = "EditSchoolCourses";
    public static String EditCourse = "EditCourse.fxml";
    public static String EditCourseID = "EditCourse";
    public static String AddCourse = "AddCourse.fxml";
    public static String AddCourseID = "AddCourse";
    public static String AddSection = "AddSection.fxml";
    public static String AddSectionID = "AddSection";
    public static String EditSection = "EditSection.fxml";
    public static String EditSectionID = "EditSection";
    public static String ManageStudentAccounts = "ManageStudentAccounts.fxml";
    public static String ManageStudentAccountsID = "ManageStudentAccounts";
    public static String StudentView = "StudentView.fxml";
    public static String StudentViewID = "StudentView";
    
    private Stage primaryStage;
    private AnchorPane rootLayout;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(CSE308GUI.LoginPageID, CSE308GUI.LoginPage);
        mainContainer.loadScreen(CSE308GUI.StudentRegistrationID, CSE308GUI.StudentRegistration);
        mainContainer.loadScreen(CSE308GUI.ForgotPasswordID, CSE308GUI.ForgotPassword);
        mainContainer.loadScreen(CSE308GUI.AdminViewID, CSE308GUI.AdminView);
        mainContainer.loadScreen(CSE308GUI.ManageSchoolsID, CSE308GUI.ManageSchools);
        mainContainer.loadScreen(CSE308GUI.NewSchoolID, CSE308GUI.NewSchool);
        mainContainer.loadScreen(CSE308GUI.EditSchoolID, CSE308GUI.EditSchool);
        mainContainer.loadScreen(CSE308GUI.EditSchoolInfoID, CSE308GUI.EditSchoolInfo);
        mainContainer.loadScreen(CSE308GUI.EditSchoolCoursesID, CSE308GUI.EditSchoolCourses);
        mainContainer.loadScreen(CSE308GUI.EditCourseID, CSE308GUI.EditCourse);
        mainContainer.loadScreen(CSE308GUI.AddCourseID, CSE308GUI.AddCourse);
        mainContainer.loadScreen(CSE308GUI.AddSectionID, CSE308GUI.AddSection);
        mainContainer.loadScreen(CSE308GUI.EditSectionID, CSE308GUI.EditSection);
        mainContainer.loadScreen(CSE308GUI.ManageStudentAccountsID, CSE308GUI.ManageStudentAccounts);
        mainContainer.loadScreen(CSE308GUI.StudentViewID, CSE308GUI.StudentView);
        
        mainContainer.setScreen(CSE308GUI.LoginPageID);
            
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        
//        //ManageSchoolsController msc = Loader.
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageSchools.fxml"));
//        ManageSchoolsController msc = loader.getController();
//        msc.populateSchoolsBox();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
