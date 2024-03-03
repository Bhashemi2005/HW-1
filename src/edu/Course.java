package edu;
import java.util.ArrayList;
public class Course {
    private ArrayList<String> studentList;
    private String teacher, name, code;
    private String department;
    private String finalExam;
    private String midtermExam;
    private int capacity, unit;
    private TimeTable timeTable;

    public void setUnit(int unit) {
        this.unit = unit;
    }
    public Course() {
        timeTable = new TimeTable();
        studentList = new ArrayList<>();
    }

    public int getUnit() {
        return unit;
    }

    public String getPath() {
        return "src/edu/file/departments/" + department + "/" + code;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getStudentList() {
        return studentList;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDepartment() {
        return department;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(String finalExam) {
        this.finalExam = finalExam;
    }

    public String getMidtermExam() {
        return midtermExam;
    }

    public void setMidtermExam(String midtermExam) {
        this.midtermExam = midtermExam;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    public void setStudentList(ArrayList<String> studentList) {
        this.studentList = studentList;
    }
    public String getType() {
        return "Course";
    }
    public void writeForUser() {
        Write.println("-".repeat(200), "yellow");
        Write.print("Type: ", "GREEN"); Write.println(getType(), "ORANGE");
        Write.print("Name: ", "GREEN"); Write.println(getName(), "ORANGE");
        Write.print("Teacher: ", "GREEN"); Write.println(getTeacher(), "ORANGE");
        Write.print("Final exam: ", "GREEN"); Write.println(getFinalExam(), "ORANGE");
        Write.print("Midterm exam: ", "GREEN"); Write.println(getMidtermExam(), "ORANGE");
        Write.print("Unit: ", "GREEN"); Write.println(getUnit() + "", "ORANGE");
        Write.print("Capacity: ", "GREEN"); Write.println(getCapacity() + "", (getCapacity() >= 10? "GRAY": "PINK"));
        System.out.println();
        getTimeTable().writeForUser();
        Write.println("-".repeat(200), "yellow");
    }
}
