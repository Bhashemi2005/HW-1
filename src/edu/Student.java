package edu;
import java.util.ArrayList;

public class Student extends User {
    private int unit;
    int generalCount = 0;
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
        if (course instanceof General)
            generalCount++;
        course.addStudent(this);
        fileUtil.writeCourse(course);
        fileUtil.writeStudent(this);
    }
    public void writeCourse(Course course) {
        courseList.add(course);
        timeTable.addTable(course.getTimeTable());
        unit += course.getUnit();
        if (course instanceof General)
            generalCount++;
    }
    public boolean canAddCourse(Course course) {
        return (course.getUnit() + unit < 20) && timeTable.canAdd(course.getTimeTable())
                && (!(course instanceof General) || generalCount < 5);
    }
    public boolean hasCourse(String code) {
        for (Course course1: courseList)
            if (course1.getCode().equals(code))
                return true;
        return false;
    }
    public boolean removeCourse(Course course) { //Todo complete here
        for (int i = 0; i < courseList.size(); i++)
            if (courseList.get(i).getCode().equals(course.getCode())) {
                courseList.remove(i);
                timeTable.removeTable(course.getTimeTable());
                unit -= course.getUnit();
                if (course instanceof General)
                    generalCount--;
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
