package edu;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class fileUtil {
    public static void writeAdmin(Admin admin) {
        try {
            File file = new File("src/edu/file/admin");
            if(!file.exists()) file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(admin.getPassword());
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again later");
        }
    }
    public static Admin readAdmin() {
        try {
            File file = new File("src/edu/file/admin");
            Scanner sc = new Scanner(file);
            return new Admin(sc.next());
        } catch (Exception e) {
            System.out.println("The admin file has been removed");
            return null;
        }
    }
    public static void writeStudent(Student student) {
        try {
            File file = new File("src/edu/file/students/" + student.getUsername());
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            // write password
            fileWriter.write(student.getPassword() + "\n");
            // write unit
            fileWriter.append(student.getUnit() + "\n");
            // write timeTable
            TimeTable timeTable = student.getTimeTable();
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 24; j++)
                    for (int k = 0; k < 2; k++)
                        fileWriter.append((timeTable.getCell(i, j, k)? "1 ": "0 "));
                fileWriter.append("\n");
            }
            // write courseList
            for (Course course: student.getCourseList())
                fileWriter.append(course.getPath() + "\n");
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again later");
        }
    }
    public static Student readStudent(String code) {
        try {
            File file = new File("src/edu/file/students/" + code);
            if (!file.exists()) {
                System.out.println("This username does not exist!");
                return null;
            }
            Scanner sc = new Scanner(file);
            Student student = new Student(code, sc.next());
            student.setUnit(sc.nextInt());
            for (int i = 0; i < 7; i++)
                for (int j = 0; j < 24; j++)
                    for (int k = 0; k < 2; k++)
                        student.getTimeTable().setCell(i, j, k, (sc.nextInt() == 1));
            while (sc.hasNext())
                student.getCourseList().add(readCourse(sc.next()));
            return student;
        } catch (Exception e) {
            System.out.println("something went wrong. please try again later");
            return null;
        }
    }
    public static Set<String> listData(String dir) {
        return Stream.of(new File(dir).listFiles())
                .map(File::getName)
                .collect(Collectors.toSet());
    }
    public static boolean hasStudent(String code) {
        for (String s: listData("src/edu/file/students"))
            if (code.equals(s))
                return true;
        return false;
    }
    public static String findDepartment(String code) {
        String current = "src/edu/file/departments";
        Set<String> departments = listData(current);
        for (String department: departments) {
            Set<String> courseList = listData(current + "/" + department);
            for (String s: courseList)
                if (s.equals(code))
                    return department;
        }
        return null;
    }
    public static Course readCourse(String s) {
        try {
            String department = findDepartment(s);
            File file = new File("src/edu/file/departments/" + department + "/" + s);
            Scanner sc = new Scanner(file);
            String type = sc.next();
            Course course = (type.equals("General")? new General(): new Specialized());
            course.setDepartment(department);
            course.setCode(s);
            course.setName(sc.next());
            course.setTeacher(sc.next());
            course.setFinalExam(sc.next());
            course.setMidtermExam(sc.next());
            course.setCapacity(sc.nextInt());
            course.setUnit(sc.nextInt());
            for (int day = 0; day < 7; day++)
                for (int hour = 0; hour < 24; hour++)
                    for (int i = 0; i < 2; i++)
                        course.getTimeTable().setCell(day, hour, i, (sc.nextInt() == 1));
            while (sc.hasNext())
                course.getStudentList().add(sc.next());
            return course;
        } catch (Exception e) {
            System.out.println("Something went wrong. please try again later");
            return null;
        }
    }
    public static void writeCourse(Course course) throws IOException {
        try {
            File file = new File(course.getPath());
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(course.getType() + "\n");
            fileWriter.append(course.getName() + "\n");
            fileWriter.append(course.getTeacher() + "\n");
            fileWriter.append(course.getFinalExam() + "\n");
            fileWriter.append(course.getMidtermExam() + "\n");
            fileWriter.append(course.getCapacity() + "\n");
            fileWriter.append(course.getUnit() + "\n");
            fileWriter.append(course.getTimeTable().toString());
            for (String student: course.getStudentList())
                fileWriter.append(student + "\n");
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("something went wrong please try again later");
        }
    }
}
