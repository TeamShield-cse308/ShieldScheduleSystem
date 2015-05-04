/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.shared.dto;

import java.util.List;

/**
 *
 * @author evanguby
 */
public class SimpleSection
{
    public SimpleCourse course;
    public String teacherName;
    public SimpleScheduleBlock scheduleBlock;
    public List<Integer> semesters;
    
    public long sectionID;

    public String school;
    public String studentEmail;
    public int year;
    public int studentsEnrolled;

    public void setScheduleBlock(int period,
            String days)
    {
        scheduleBlock = new SimpleScheduleBlock();
        scheduleBlock.period = period;
        scheduleBlock.scheduleDays = days;
    }
    
    public void setCourse(String identifier, String name)
    {
        course = new SimpleCourse();
        course.identifier = identifier;
        course.name = name;
    }
}
