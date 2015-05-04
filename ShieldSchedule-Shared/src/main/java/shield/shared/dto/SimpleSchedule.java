/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.shared.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author evanguby
 */
public class SimpleSchedule {
    public String studentEmail;
    public int year;
    public List<SimpleSection> sections;
    
    public void addSection(SimpleSection section)
    {
        if (sections == null)
            sections = new ArrayList<>();
        
        sections.add(section);
    }
}
