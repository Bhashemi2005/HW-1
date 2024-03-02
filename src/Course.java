import java.io.File;
import java.io.FileWriter;
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
        return "src/file/departments/" + department + "/" + code;
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
        System.out.println("----------------------------------------");
        System.out.println("Type: " + getType());
        System.out.println("Name: " + getName());
        System.out.println("Teacher: " + getTeacher());
        System.out.println("Final exam: " + getFinalExam());
        System.out.println("Midterm exam: " + getMidtermExam());
        System.out.println("Capacity: " + getCapacity());
        System.out.println("Unit: " + getUnit());
        getTimeTable().writeForUser();
        System.out.println("----------------------------------------");
    }
}
