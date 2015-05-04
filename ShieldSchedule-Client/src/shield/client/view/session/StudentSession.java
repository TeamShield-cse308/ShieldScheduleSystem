/*

 */
package shield.client.view.session;

import java.util.ArrayList;
import java.util.List;
import shield.shared.dto.SimpleCourse;
import shield.shared.dto.SimpleSchedule;
import shield.shared.dto.SimpleScheduleBlock;
import shield.shared.dto.SimpleSchool;
import shield.shared.dto.SimpleSection;
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

    private String courseIdentifier;
    
    private SimpleSchedule assignedSchedule;
    
    private List<SimpleSection> sections;
    
    private String assignedScheduleAsString = "";
    
    private int scheduleYear;
    
    public String getCourseName() {
        return courseName;
    }
    
//    public void newAssignedSchedule(){
//        assignedSchedule = new SimpleSchedule();
//        assignedSchedule.courseIDs = new ArrayList<String>();
//        assignedSchedule.sectionIDs = new ArrayList<String>();
//    }
    
    public void setCourseName(String courseName) {
        for(SimpleCourse c : courses){
            if(c.name.equals(courseName))
                course = c;
        }
        this.courseName = courseName;
    }

    public String getCourseIdentifier() {
        return courseIdentifier;
    }

    public void setCourseIdentifier(String courseIdentifier) {
        this.courseIdentifier = courseIdentifier;
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

    public List<SimpleSection> getSections() {
        return sections;
    }

    public void setSections(List<SimpleSection> sections) {
        this.sections = sections;
    }
    
    

    public List<SimpleScheduleBlock> getScheduleBlocks() {
        return scheduleBlocks;
    }

    public void setScheduleBlocks(List<SimpleScheduleBlock> scheduleBlocks) {
        this.scheduleBlocks = scheduleBlocks;
    }

//    public void addCourseID(String courseID) {
//        assignedSchedule.courseIDs.add(courseID);
//    }
//
//    public void addSectionID(String sectionID) {
//        assignedSchedule.sectionIDs.add(sectionID);
//    }

    public String getAssignedScheduleAsString() {
        return assignedScheduleAsString;
    }
    
    public void setAssignedScheduleAsString(String toSet) {
        assignedScheduleAsString = toSet;
    }
    
    public void sectionToAddToString(String add){
        assignedScheduleAsString += add + "\n";
    }

    public int getScheduleYear() {
        return scheduleYear;
    }

    public void setScheduleYear(int scheduleYear) {
        this.scheduleYear = scheduleYear;
    }

    public SimpleSchedule getAssignedSchedule() {
        return assignedSchedule;
    }

    public void setAssignedSchedule(SimpleSchedule assignedSchedule) {
        this.assignedSchedule = assignedSchedule;
    }
    
    
    
}
