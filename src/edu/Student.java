package edu;
import java.util.ArrayList;

public class Student extends User {
    private int unit;
    private ArrayList<Course> courseList;
    private TimeTable timeTable;
    public Student (String username, String password) {
        super(username, password);
        unit = 0;
        courseList = new ArrayList<>();
        timeTable = new TimeTable();
    }
    public void addCourse(Course course) {
        courseList.add(course);
        timeTable.addTable(course.getTimeTable());
        unit += course.getUnit();
        course.addStudent(this);
        fileUtil.writeCourse(course);
        fileUtil.writeStudent(this);
    }
    public boolean removeCourse(Course course) { //Todo complete here
        for (int i = 0; i < courseList.size(); i++)
            if (courseList.get(i).getCode().equals(course.getCode())) {
                courseList.remove(i);
                timeTable.removeTable(course.getTimeTable());
                unit -= course.getUnit();
                course.removeStudent(this);
                fileUtil.writeCourse(course);
                fileUtil.writeStudent(this);
                return true;
            }
        return false;
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
