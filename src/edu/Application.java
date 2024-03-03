package edu;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Scanner;

import static java.time.Clock.system;

public class Application {
    private static ArrayList<Page> functionSequence;
    static String currentUser;
    private enum Page {
        firstPage, logIn, signUp, back, admin, student
    }
    private static void runFunction(Page page) {
        if (page == Page.back) {
            functionSequence.removeLast();
            runFunction(functionSequence.getLast());
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
            case Page.admin:
                onAdmin();
                return;
            case Page.student:
                onStudent();
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
    private static void onAdmin() {

    }
    private static void onStudent() {

    }
    private static void onlogIn() {
        Scanner sc = new Scanner(System.in);
        Write.println("This is the log in page. If you want to go to the previous page, type \"back\" at any time", "Pink");
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
            runFunction(Page.admin);
            return;
        }
        else {
            currentUser = id;
            runFunction(Page.student);
            return;
        }
    }
    private static void onSignUp() {
        Scanner sc = new Scanner(System.in);
        Write.println("This is the sign up page. If you want to go to the previous page, type \"back\" at any time", "Pink");
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
