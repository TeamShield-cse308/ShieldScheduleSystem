/*

 */
package shield.client.view.session;

import java.util.ArrayList;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleStudent;

/**
 * A session manager for student users.
 * @author Jeffrey Kabot
 */
public class StudentSession implements Session
{
    private SimpleStudent studentAccount;
    
    private ArrayList<SimpleCourse> courses;
    
    private SimpleCourse course;
    
    private String courseName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        for(SimpleCourse c : courses){
            if(c.name.equals(courseName))
                course = c;
        }
        this.courseName = courseName;
    }
    

    public void setCourses(ArrayList<SimpleCourse> courses) {
        this.courses = courses;
    }


    public StudentSession(SimpleStudent acct)
    {
        studentAccount = acct;
    }
    
    public SimpleStudent getStudentAccount()
    {
        return studentAccount;
    }
    
    public SimpleCourse getCourse() {
        return course;
    }

    public void setCourse(SimpleCourse course) {
        this.course = course;
    }
    
}
