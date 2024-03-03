package edu;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import static java.time.Clock.system;

public class Application {
    private static ArrayList<Page> functionSequence;
    static String currentUser;
    static String currentDepartment;
    private enum Page {
        firstPage, logIn, signUp, back, firstAdmin, firstStudent, adminDepartment
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
        Scanner sc = new Scanner(System.in);
        while (!fileUtil.hasDepartment(department)) {
            Write.print("Choose department ", "Green"); department = sc.next();
            if (department.equals("back")) {
                runFunction(Page.back);
                return;
            }
            if (fileUtil.hasDepartment(department))
                break;
            Write.println("invalid department", "pink");
        }
        currentDepartment = department;
        runFunction(Page.adminDepartment);
    }
    private static void onAdminDepartment() {

    }
    private static void onStudent() {

    }
    private static void onlogIn() {
        Scanner sc = new Scanner(System.in);
        Write.println("This is the log in page. If you want to go to the previous page, type \"back\" at any time", "Orange");
        Write.print("Please enter your StudentId: ", "Green");
        String id = sc.next();
        if (id.equals("back")) {
            runFunction(Page.back);
            return;
        }
        while (!fileUtil.hasStudent(id) && !id.equals("admin")) {
            Write.println("This username does not exit. please try another one", "pink");
            Write.print("Please enter your StudentId: ", "Green");
            id = sc.next();
            if (id.equals("back")) {
                runFunction(Page.back);
                return;
            }
        }
        String PASSWORD = (id.equals("admin")? fileUtil.readAdmin().getPassword(): fileUtil.readStudent(id).getPassword());
        Write.print("Please enter your password: ", "Green"); String password = sc.next();
        if (password.equals("back")) {
            runFunction(Page.back);
            return;
        }
        while (!password.equals(PASSWORD)) {
            Write.println("Wrong password. please try again", "pink");
            Write.print("Please enter your password: ", "Green"); password = sc.next();
            if (password.equals("back")) {
                runFunction(Page.back);
                return;
            }
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
        Write.print("Please enter your StudentId: ", "Green");
        String id = sc.next();
        if (id.equals("back")) {
            runFunction(Page.back);
            return;
        }
        while (fileUtil.hasStudent(id) || id.equals("admin")) {
            Write.println("This username already exits. please try another one", "pink");
            Write.print("Please enter your StudentId: ", "Green");
            id = sc.next();
            if (id.equals("back")) {
                runFunction(Page.back);
                return;
            }
        }
        Write.print("Please enter your password: ", "Green");
        String password = sc.next();
        if(password.equals("back")) {
            runFunction(Page.back);
            return;
        }
        Student student = new Student(id, password);
        fileUtil.writeStudent(student);
        Write.println("Your username has been successfully created", "Yellow");
        Write.println("You will be directed to the first page", "Yellow");
        runFunction(Page.back);
    }
    static public void runProgramme() {
        Write.println("Welcome", "Yellow");
        functionSequence = new ArrayList<>();
        runFunction(Page.firstPage);
    }
}
