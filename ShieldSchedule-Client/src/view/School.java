/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;

/**
 * A class providing the client-side representation of a school
 * @author Jeffrey Kabot
 */
public class School implements Serializable
{
    private String schoolName;
    private String numPeriods;
    private String numSemesters;
    private String numScheduleDays;

    public String getSchoolName()
    {
        return schoolName;
    }

    public void setSchoolName(String name)
    {
        this.schoolName = name;
    }

    public String getNumPeriods()
    {
        return numPeriods;
    }

    public void setNumPeriods(String numPeriods)
    {
        this.numPeriods = numPeriods;
    }

    public String getNumSemesters()
    {
        return numSemesters;
    }

    public void setNumSemesters(String numSemesters)
    {
        this.numSemesters = numSemesters;
    }

    public String getNumScheduleDays()
    {
        return numScheduleDays;
    }

    public void setNumScheduleDays(String numScheduleDays)
    {
        this.numScheduleDays = numScheduleDays;
    }
}
