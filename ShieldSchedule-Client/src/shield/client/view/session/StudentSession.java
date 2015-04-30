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

    private SimpleCourse courseSelected;
    
    private ArrayList<SimpleCourse> courses;
    
    private SimpleCourse course;
    
    public SimpleCourse getCourses() {
        return courseSelected;
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
