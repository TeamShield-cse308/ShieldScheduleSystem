
import java.util.Scanner;
import shield.server.ejb.AdminSchoolsBeanTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Weize
 */
public class FunctionsTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       int input;
       Scanner sc=new Scanner(System.in);
       System.out.println("Choose the test you want now(input the number before the Test)"); 
       System.out.println("1.AdminSChoolsBeanTest");
       System.out.println("2.AdminStudentsBeanTest");
       System.out.println("3.AUthenticationBeanTest");
       System.out.println("4.CoursesBeanTest");
       System.out.println("5.DesiredScheduleBeanTest");
       System.out.println("6.ScheduleBeanTest");
       System.out.println("7.ScheduleBlockBeanTest");
       System.out.println("8.SectionBeanTest");
       System.out.println("9.StudentFrienedsBeanTest");
       //read the int input from console
       input=sc.nextInt();
       // check the valid input!
       while(input<1||input>9)
       {       
       System.out.println("Choose the test you want now(input the number before the Test)"); 
       System.out.println("1.AdminSchoolsBeanTest");
       System.out.println("2.AdminStudentsBeanTest");
       System.out.println("3.AUthenticationBeanTest");
       System.out.println("4.CoursesBeanTest");
       System.out.println("5.DesiredScheduleBeanTest");
       System.out.println("6.ScheduleBeanTest");
       System.out.println("7.ScheduleBlockBeanTest");
       System.out.println("8.SectionBeanTest");
       System.out.println("9.StudentFrienedsBeanTest");
       //read the int input from console
       input=sc.nextInt();}
       //Test function of admin and school
       if(input==1) {
       //create the test Object
       AdminSchoolsBeanTest AschoolTest=new AdminSchoolsBeanTest();
       AschoolTest.testAddSchool();
       };
       if(input==2)
          {}
       if(input==3)
          {}
       if(input==4)
          {}
       if(input==5)
          {}
       if(input==6)
          {}
       if(input==7)
          {}
       if(input==8)
          {}
       if(input==9)
          {}
      
       
    }
    
}
