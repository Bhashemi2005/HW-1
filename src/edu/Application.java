package edu;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import static java.time.Clock.system;

public class Application {
    private static ArrayList<Page> functionSequence;
    static Scanner sc = new Scanner(System.in);
    static String currentUser;
    static String currentDepartment;
    private enum Page {
        firstPage, logIn, signUp, back, firstAdmin, firstStudent,
        adminDepartment, addCourse, removeCourse,
        myCourses, allCourses, studentDepartments
    }
    private static void runFunction(Page page) {
        if (page == Page.back) {
            functionSequence.removeLast();
            Page p = functionSequence.getLast();
            functionSequence.removeLast();
            runFunction(p);
            return;
        }
        Write.println("-".repeat(100), "Yellow");
        functionSequence.add(page);
        switch (page) {
            case Page.firstPage:
                onFirstPage();
                return;
            case Page.signUp:
                onSignUp();
                return;
            case Page.logIn:
                onlogIn();
                return;
            case Page.firstAdmin:
                onFirstAdmin();
                return;
            case Page.firstStudent:
                onStudent();
                return;
            case Page.adminDepartment:
                onAdminDepartment();
                return;
            case Page.addCourse:
                onAddCourse();
                return;
            case Page.removeCourse:
                onRemoveCourse();
                return;
            case Page.myCourses:
                onMyCourses();
                return;
            case Page.studentDepartments:
                onStudentDepartments();
                return;
            case Page.allCourses:
                onAllCourses();
                return;
        }
    }
    private static void onRemoveCourse() {
        try {
            Write.println("This is the remove course page. If you want to go to the previous page, type \"back\" at any time", "Orange");
            Set<String> courseList = fileUtil.readCourseList(currentDepartment);
            Write.println("-".repeat(25) + " Course List " + "-".repeat(25), "Green");
            for (String s: courseList) System.out.println(" ".repeat(32 - s.length() / 2) + s);
            Write.println("-".repeat(25) + " Course List " + "-".repeat(25), "Green");
            while (true) {
                Write.print("To remove a course write it's code ", "green");
                String course = next();
                if (!fileUtil.hasCourse(currentDepartment, course)) {
                    Write.println("Invalid code", "Pink");
                    continue;
                }
                // modify all students
                Set<String> students = fileUtil.listStudents();
                Course course1 = fileUtil.readCourse(course);
                for (String code: students) {
                    Student student = fileUtil.readStudent(code);
                    student.removeCourse(course1);
                }
                // modifying finished
                Write.println("Course has been removed!", "Yellow");
                fileUtil.removeCourse(currentDepartment, course);
            }
        } catch (Exception e) {
            Write.println("Something went wrong. please try again later", "pink");
            runFunction(Page.back);
        }
    }
    private static void onMyCourses() {
        //todo compelete here
    }
    private static void onAllCourses() {
        Write.println("This is the add course page. If you want to go to the previous page, type \"back\" at any time", "Orange");
        Student student = fileUtil.readStudent(currentUser);
        // This part has been added for debug
        System.out.println(student.getUsername());
        Set<String> courseList = fileUtil.listCourses(currentDepartment);
       for (String course: courseList)
           fileUtil.readCourse(course).writeForUser();
       String code = "";
       while (true) {
           Write.print("To add a course type it's code: ", "green");  code = next();
           if (!fileUtil.hasCourse(currentDepartment, code)) {
               Write.println("Invalid course code!", "Pink");
               continue;
           }
           Course course = fileUtil.readCourse(code);
           if (!student.getTimeTable().canAdd(course.getTimeTable())) {
               Write.println("This course has time interference with previous courses", "Pink");
               continue;
           }
           if (course.getCapacity() == 0) {
               Write.println("Course capacity is 0!", "Pink");
               continue;
           }
           student.addCourse(course);
           course.setCapacity(course.getCapacity() - 1);
           fileUtil.writeCourse(course);
           Write.println("Course has been successfully added to your course list :)", "Yellow");
       }
    }
    private static void onStudentDepartments() {
        Write.println("This is the department page. If you want to go to the previous page, type \"back\" at any time", "Orange");
        Write.println("*".repeat(25) + "Department list" + "*".repeat(25), "Green");
        Set<String> departmentList = fileUtil.readDepartmentList();
        for (String s: departmentList) System.out.println(" ".repeat(33 - s.length() / 2) + s);
        Write.println("*".repeat(65), "Green");
        String department = "";
        while (!fileUtil.hasDepartment(department)) {
            Write.print("Choose department ", "Green"); department = next();
            if (fileUtil.hasDepartment(department))
                break;
            Write.println("invalid department", "pink");
        }
        currentDepartment = department;
        runFunction(Page.allCourses);
    }
    private static String next() {
        String s = sc.next();
        if (s.equals("back")) {
            runFunction(Page.back);
            return null;
        }
        return s;
    }
    private static void onAddCourse() { // Fellan add va modifie yek karo mikonan
        try {
            Write.println("This is the add course page. If you want to go to the previous page, type \"back\" at any time", "Orange");
            Course course;
            do {
                Write.print("course type:(General/Specialized) ", "green");
                String type = next();
                if (type.toLowerCase().equals("general")) {
                    course = new General();
                    break;
                } else if (type.toLowerCase().equals("specialized")) {
                    course = new Specialized();
                    break;
                } else
                    Write.println("type not found!", "pink");
            } while (true);
            Write.print("course code: ", "green");
            course.setCode(next());
            course.setDepartment(currentDepartment);
            Write.print("course teacher: ", "green");
            course.setTeacher(next());
            Write.print("course name: ", "green");
            course.setName(next());
            Write.print("course unit: ", "green");
            course.setUnit(Integer.valueOf(next()));
            Write.print("course capacity: ", "green");
            course.setCapacity(Integer.valueOf(next()));
            Write.print("course final exam: ", "green");
            course.setFinalExam(next());
            Write.print("course midterm exam: ", "green");
            course.setMidtermExam(next());
            course.setStudentList(new ArrayList<>());
            // todo get time intervals
            String command = "";
            Write.println("to add a time you should follow this format: \"day hour minute\"", "orange");
            Write.println("days: saturday, sunday, monday, tuesday, wednesday, thursday, friday", "orange");
            Write.println("hourse: 0, 1, 2, 3, ..., 23", "orange");
            Write.println("minutes: 0-> start from hour:00 1-> start from hour:30", "orange");
            while (true) {
                Write.print("to add time interval, type \"add\" and to break cycly, type \"break\" ", "green");
                command = next();
                if (command.equals("break")) break;
                if (!command.equals("add")) {
                    Write.println("Command not found!", "pink");
                    continue;
                }
                Write.print("please enter the interval opening: ", "green");
                String day1 = next();
                int hour1 = Integer.valueOf(next());
                int minute1 = Integer.valueOf(next());
                Write.print("please enter the interval closing: ", "green");
                String day2 = next();
                int hour2 = Integer.valueOf(next());
                int minute2 = Integer.valueOf(next());
                course.getTimeTable().addInterval(day1, hour1, minute1, day2, hour2, minute2);
            }
            fileUtil.writeCourse(course);
            Write.println("Course has been successfully made", "Yellow");
            runFunction(Page.back);
        } catch (Exception e) {
            Write.println("something went wrong. please try again later", "pink");
            runFunction(Page.back);
        }
    }
    private static void onFirstPage() {
        Write.print("to log in please type \"log-in\" and to sign up, please type \"sign-up\" ", "Green");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        if (s.equals("log-in"))
            runFunction(Page.logIn);
        else if (s.equals("sign-up"))
            runFunction(Page.signUp);
        else {
            Write.println("command not found!", "Pink");
            runFunction(Page.firstPage);
        }
    }
    private static void onFirstAdmin() {
        Write.println("This is the admin page. If you want to go to the previous page, type \"back\" at any time", "Orange");
        Write.println("*".repeat(25) + "Department list" + "*".repeat(25), "Green");
        Set<String> departmentList = fileUtil.readDepartmentList();
        for (String s: departmentList) System.out.println(" ".repeat(33 - s.length() / 2) + s);
        Write.println("*".repeat(65), "Green");
        String department = "";
        while (!fileUtil.hasDepartment(department)) {
            Write.print("Choose department ", "Green"); department = next();
            if (fileUtil.hasDepartment(department))
                break;
            Write.println("invalid department", "pink");
        }
        currentDepartment = department;
        runFunction(Page.adminDepartment);
    }
    private static void onAdminDepartment() {
        Write.println("This is the add/remove course page. If you want to go to the previous page, type \"back\" at any time", "Orange");
        Write.print("to add a course write \"add\" and to remove a course write \"remove\" ", "green");
        String type = next();
        if (type.equals("add"))
            runFunction(Page.addCourse);
        else if (type.equals("remove")) {
            runFunction(Page.removeCourse);
        }
        else {
            Write.println("Command not found!", "Pink");
            runFunction(Page.adminDepartment);
        }
    }
    private static void onStudent() {
        Write.println("This is the student page. If you want to go to the previuse page, type \"back\"", "orange");
        while (true) {
            Write.print("To see your courses type \"myCourses\" and to see all Department courses type \"allCourses\"" , "green");
            String type = next();
            if (type.equals("myCourses")) {
                runFunction(Page.myCourses);
                return;
            }
            if (type.equals("allCourses")) {
                runFunction(Page.studentDepartments);
                return;
            }
            Write.println("Command not found!", "pink");
        }
    }
    private static void onlogIn() {
        Scanner sc = new Scanner(System.in);
        Write.println("This is the log in page. If you want to go to the previous page, type \"back\" at any time", "Orange");
        Write.print("Please enter your Id: ", "Green");
        String id = next();
        while (!fileUtil.hasStudent(id) && !id.equals("admin")) {
            Write.println("This username does not exit. please try another one", "pink");
            Write.print("Please enter your Id: ", "Green");
            id = next();
        }
        String PASSWORD = (id.equals("admin")? fileUtil.readAdmin().getPassword(): fileUtil.readStudent(id).getPassword());
        Write.print("Please enter your password: ", "Green"); String password = next();
        while (!password.equals(PASSWORD)) {
            Write.println("Wrong password. please try again", "pink");
            Write.print("Please enter your password: ", "Green"); password = next();
        }
        if (id.equals("admin")) {
            currentUser = "admin";
            runFunction(Page.firstAdmin);
            return;
        }
        else {
            currentUser = id;
            runFunction(Page.firstStudent);
            return;
        }
    }

    private static void onSignUp() {
        Scanner sc = new Scanner(System.in);
        Write.println("This is the sign up page. If you want to go to the previous page, type \"back\" at any time", "Orange");
        Write.print("Please enter your Id: ", "Green");
        String id = next();
        while (fileUtil.hasStudent(id) || id.equals("admin")) {
            Write.println("This username already exits. please try another one", "pink");
            Write.print("Please enter your Id: ", "Green");
            id = next();
        }
        Write.print("Please enter your password: ", "Green");
        String password = next();
        Student student = new Student(id, password);
        fileUtil.writeStudent(student);
        Write.println("Your username has been successfully created", "Yellow");
        Write.println("You will be directed to the first page", "Yellow");
        runFunction(Page.back);
    }
    static public void runProgramme() {
        Write.println("Welcome", "Yellow");
        Write.println("To use our programme you should not write white space in any line only the ones we determine", "pink");
        functionSequence = new ArrayList<>();
        runFunction(Page.firstPage);
    }
}
