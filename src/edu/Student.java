package edu;
import java.util.ArrayList;

public class Student extends User {
    private int unit;
    private ArrayList<Course> courseList;
    private TimeTable timeTable;
    public Student (String username, String password) {
        super(username, password);
        courseList = new ArrayList<>();
        timeTable = new TimeTable();
    }
    public ArrayList<Course> getCourseList() {
        return courseList;
    }
    public TimeTable getTimeTable() {
        return timeTable;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getUnit() {
        return unit;
    }

    public Student() {
    }
}
