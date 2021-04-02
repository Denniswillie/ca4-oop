package com.dkit.oop.sd2.server.DTOs;

import java.util.*;

public class CourseChoice
{

    private static Scanner kb = new Scanner(System.in);



    // reference to constructor injected studentManager
    private StudentManager studentManager;

    // reference to constructor injected courseManager
    private CourseManager courseManager;

    private int student;
    private List<String>courses =new ArrayList<>();
    private String course;


    private HashMap<Integer, List<String>> selectedChoices = new HashMap<>();


    public CourseChoice(int student, String course) {
        this.student = student;
        this.course = course;
    }

    public CourseChoice(int studentId, List<String>courseId) {
        this.student = studentId;
        this.courses = courseId;

    }

    public int getStudent() {
        return student;
    }

    public void setStudent(int student) {
        this.student = student;
    }

    public List<String> getCourses() {
        return courses;
    }

    public String getCourse() {
        return course;
    }

    //    public void updateChoicesInput()
//    {
//        String ans = "y";
//        String courseId = "";
//        boolean flag = false;
//        String choices = new ArrayList<>();
//        System.out.print("Please input your caoNumber: ");
//        int caoNumber = kb.nextInt();
//        while(ans.equals("y"))
//        {
//
//            System.out.print("Input the courseId with DK + 3 digits : ");
//            courseId = kb.next();
//
//            choices.add(courseId);
//            System.out.println("Do you want to add another course? Please enter 'y' if you want to add another again.");
//            ans=kb.next();
//
//
//        }
//
//
//        selectedChoices.put(caoNumber,choices);
//    }

    public List<Course> getAllCourses()
    {
        return courseManager.getAllCourse();
    }

    boolean login()
    {
        int input =0;
        String dateBirth ="";
        String password ="";

        System.out.print("Input your caoNumber");
        input = kb.nextInt();

        System.out.print("Input your birth of date like '1980-01-01' : ");
        dateBirth = kb.next();

        System.out.print("Please enter your password like: ");
        password = kb.next();

        try
        {
            if(studentManager.getStudent(input).getCaoNumber() == input && studentManager.getStudent(input).getDayOfBirth().equals(dateBirth )&& studentManager.getStudent(input).getPassword().equals(password))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(NullPointerException e)
        {
            return false;
        }


    }

    boolean login(int input,String dateBirth,String password)
    {

        try
        {
            if(studentManager.getStudent(input).getCaoNumber() == input && studentManager.getStudent(input).getDayOfBirth().equals(dateBirth )&& studentManager.getStudent(input).getPassword().equals(password))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(NullPointerException e)
        {
            return false;
        }


    }

//    protected void loadCourseChoiceFromFile()
//    {
//        try(Scanner courseChoiceFile = new Scanner(new BufferedReader(new FileReader("courseChoice.txt"))))
//        {
//
//            String input;
//            while (courseChoiceFile.hasNextLine())
//            {
//                input = courseChoiceFile.nextLine();
//                String [] data = input.split(",");
//                int caoNumber = Integer.parseInt(data[0]);
//                List<String> choices = new ArrayList<>();
//                for (int i = 1; i < data.length; i++)  //continue to end of data line
//                {
//                    choices.add((data[i]));
//                }
//
//                this.selectedChoices.put(caoNumber,choices);
//
//            }
//        }
//        catch(FileNotFoundException fne)
//        {
//            System.out.println("Could not load booking.If this is " +
//                    "the first time running the app this might fine");
//        }
//    }

//    public void saveCourseChoicesToFile()
//    {
//        try(BufferedWriter courseChoiceFile = new BufferedWriter(new FileWriter("courseChoice.txt") ))
//        {
//
//
//            for (Map.Entry<Integer, List<String>> entry : selectedChoices.entrySet())
//            {
//                courseChoiceFile.write(entry.getKey()+","+entry.getValue());
//                courseChoiceFile.write("\n");
//            }
//        }
//        catch(IOException ioe)
//        {
//            System.out.println( "Could not save students.");
//        }
 //   }

}
