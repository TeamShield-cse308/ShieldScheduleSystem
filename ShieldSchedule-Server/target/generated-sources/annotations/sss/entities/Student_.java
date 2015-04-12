package sss.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sss.entities.AssignedSchedule;
import sss.entities.DesiredSchedule;
import sss.entities.School;
import sss.entities.Student;
import sss.entities.Student.AccountState;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-12T18:20:00")
@StaticMetamodel(Student.class)
public class Student_ { 

    public static volatile SingularAttribute<Student, School> school;
    public static volatile ListAttribute<Student, Student> friendsList;
    public static volatile SingularAttribute<Student, AssignedSchedule> myAssignedSchedule;
    public static volatile SingularAttribute<Student, DesiredSchedule> myGeneratedSchedule;
    public static volatile SingularAttribute<Student, Long> id;
    public static volatile SingularAttribute<Student, AccountState> state;
    public static volatile SingularAttribute<Student, String> email;

}