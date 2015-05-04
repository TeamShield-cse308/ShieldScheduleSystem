/*

 */
package shield.shared.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeffrey Kabot
 */
public class SimpleCriteria
{
    public String studentEmail;
    public int year;
    public boolean[] hasLunches;
    
    public List<SimpleCriteriaCourse> courses;
    
    public void addCourse(SimpleCourse course, String teacher, List<SimpleSection> sections)
    {
        if (courses == null)
            courses = new ArrayList<>();
        
        SimpleCriteriaCourse scc = new SimpleCriteriaCourse();
        scc.course = course;
        scc.teacher = teacher;
        scc.sections = sections;
        courses.add(scc);
    }
    
}
