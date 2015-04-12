package sss.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sss.entities.Course;
import sss.entities.ScheduleBlock;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-12T18:20:00")
@StaticMetamodel(Section.class)
public class Section_ { 

    public static volatile SingularAttribute<Section, Integer> roomNumber;
    public static volatile SingularAttribute<Section, String> teacher;
    public static volatile SingularAttribute<Section, Course> course;
    public static volatile SingularAttribute<Section, Long> id;
    public static volatile SingularAttribute<Section, ScheduleBlock> schedBlock;
    public static volatile SingularAttribute<Section, Integer> sizeOfClass;

}