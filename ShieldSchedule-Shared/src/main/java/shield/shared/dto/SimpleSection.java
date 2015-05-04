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
    public String teacherName;
    public SimpleScheduleBlock scheduleBlock;
    public String school;
    public List<Integer> semesters;
    public String courseIdentifier;
    public long sectionID;
    public String studentEmail;
    public int year;
    
    public void setScheduleBlock(int period, String days)
    {
        scheduleBlock = new SimpleScheduleBlock();
        scheduleBlock.period = period;
        scheduleBlock.scheduleDays = days;
    }
}
