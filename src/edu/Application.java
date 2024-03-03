package edu;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Scanner;

import static java.time.Clock.system;

public class Application {
    private static ArrayList<Page> functionSequence;
    private enum Page {
        firstPage, logIn, signUp, back
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
    private static void onlogIn() {

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
