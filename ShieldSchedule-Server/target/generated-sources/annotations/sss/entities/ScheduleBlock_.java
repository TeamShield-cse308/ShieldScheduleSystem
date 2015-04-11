package sss.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sss.entities.Course;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-10T18:48:01")
@StaticMetamodel(ScheduleBlock.class)
public class ScheduleBlock_ { 

    public static volatile SingularAttribute<ScheduleBlock, Integer> period;
    public static volatile SingularAttribute<ScheduleBlock, Integer> semester;
    public static volatile ListAttribute<ScheduleBlock, Course> courseList;
    public static volatile SingularAttribute<ScheduleBlock, Long> id;

}