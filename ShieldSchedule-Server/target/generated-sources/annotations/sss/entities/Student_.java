package sss.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sss.entities.AssignedSchedule;
import sss.entities.DesiredSchedule;
import sss.entities.School;
import sss.entities.Student;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-10T18:48:01")
@StaticMetamodel(Student.class)
public class Student_ extends GenericUser_ {

    public static volatile ListAttribute<Student, Student> friendsList;
    public static volatile SingularAttribute<Student, AssignedSchedule> myAssignedSchedule;
    public static volatile SingularAttribute<Student, School> mySchool;
    public static volatile SingularAttribute<Student, DesiredSchedule> myGeneratedSchedule;

}