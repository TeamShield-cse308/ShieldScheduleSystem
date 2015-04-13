package sss.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sss.entities.Course;
import sss.entities.Student;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-13T18:53:41")
@StaticMetamodel(School.class)
public class School_ { 

    public static volatile SingularAttribute<School, Integer> numScheduleDays;
    public static volatile SingularAttribute<School, Integer> endingLunchPeriod;
    public static volatile SingularAttribute<School, Integer> numPeriods;
    public static volatile SingularAttribute<School, Integer> numSemesters;
    public static volatile SingularAttribute<School, String> name;
    public static volatile ListAttribute<School, Student> studentList;
    public static volatile SingularAttribute<School, Integer> startingLunchPeriod;
    public static volatile ListAttribute<School, Course> courseList;
    public static volatile SingularAttribute<School, Long> id;

}