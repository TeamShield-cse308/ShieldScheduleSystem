/*

 */
package shield.client.view.session;

import java.util.ArrayList;
import java.util.List;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleScheduleBlock;
import shield.shared.dto.SimpleSchool;
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
    
    private List<SimpleScheduleBlock> scheduleBlocks;
    
    private SimpleSchool school;

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

    public SimpleSchool getSchool() {
        return school;
    }

    public void setSchool(SimpleSchool school) {
        this.school = school;
    }
    
    

    public List<SimpleScheduleBlock> getScheduleBlocks() {
        return scheduleBlocks;
    }

    public void setScheduleBlocks(List<SimpleScheduleBlock> scheduleBlocks) {
        this.scheduleBlocks = scheduleBlocks;
    }
    
    
}
