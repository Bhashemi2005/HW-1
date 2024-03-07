package edu;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class fileUtil {
    public static void writeAdmin(Admin admin) {
        try {
            File file = new File("src/edu/file/admin");
            if(!file.exists()) file.createNewFile();
            writeAdmin(admin, file);
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again later");
        }
    }
    public static void writeAdmin(Admin admin, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(admin.getPassword());
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again later");
        }
    }
    public static Admin readAdmin() {
        File file = new File("src/edu/file/admin");
        return readAdmin(file);
    }
    public static Admin readAdmin(File file) {
        Scanner sc = new Scanner(System.in);
        try {
            sc = new Scanner(file);
            String s = sc.next();
            sc.close();
            return new Admin(s);
        } catch (Exception e) {
            System.out.println("The admin file has been removed");
            sc.close();
            return null;
        }
    }
    public static void writeStudent(Student student) {
        try {
            File file = new File("src/edu/file/students/" + student.getUsername());
            file.createNewFile();
            writeStudent(student, file);
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again later");
        }
    }
    public static void writeStudent(Student student, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            // write password
            fileWriter.write(student.getPassword() + "\n");
            // write courseList
            for (Course course: student.getCourseList())
                fileWriter.append(course.getCode() + "\n");
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again later");
        }
    }
    public static Set<String> readDepartmentList() {
        return listData("src/edu/file/departments");
    }
    public static Set<String> readCourseList(String department) {
        Set<String> courses = listData("src/edu/file/departments/" + department);
        Set<String> result = new HashSet<>();
        for (String s: courses) {
            result.add("[" + s + ": " + readCourse(s).getName() + "]");
        }
        return result;
    }
    public static Set<String> listCourses(String department) {
        return listData("src/edu/file/departments/" + department);
    }
    public static Set<String> listData(String dir) {
        return Stream.of(new File(dir).listFiles())
                .map(File::getName)
                .collect(Collectors.toSet());
    }
    public static Set<String> listStudents() {
        return listData("src/edu/file/students");
    }
    public static void removeCourse(String department, String code) {
        File file = new File("src/edu/file/departments/" + department + "/" + code);
        file.delete();
    }
    public static boolean hasCourse(String code) {
        Set<String> departments = listData("src/edu/file/departments");
        for (String s: departments)
            if (hasCourse(s, code))
                return true;
        return false;
    }
    public static boolean hasStudent(String code) {
        if (code.length() == 0)
            return false;
        File file = new File("src/edu/file/students/" + code);
        return file.exists();
    }
    public static boolean hasDepartment(String department) {
        if (department.length() == 0)
            return false;
        File file = new File("src/edu/file/departments/" + department);
        return file.exists();
    }
    public static boolean hasCourse(String department, String code) {
        if (department.length() == 0 || code.length() == 0)
            return false;
        File file = new File("src/edu/file/departments/" + department + "/" + code);
        return file.exists();
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
        String department = findDepartment(s);
        File file = new File("src/edu/file/departments/" + department + "/" + s);
        return readCourse(s, file);
    }
    public static Course readCourse(String s, File file) {
        Scanner sc = new Scanner(System.in);
        try {
            sc = new Scanner(file);
            String type = sc.next();
            Course course = (type.equals("General")? new General(): new Specialized());
            course.setDepartment(findDepartment(s));
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
            sc.close();
            return course;
        } catch (Exception e) {
            System.out.println("Something went wrong. please try again later");
            sc.close();
            return null;
        }
    }
    public static Student readStudent(String code) {
        File file = new File("src/edu/file/students/" + code);
        return readStudent(code, file);
    }
    public static Student readStudent(String code, File file) {
        Scanner sc = new Scanner(System.in);
        try {
            if (!file.exists()) {
                System.out.println("This username does not exist!");
                sc.close();
                return null;
            }
            sc = new Scanner(file);
            Student student = new Student(code, sc.next());
            while (sc.hasNext())
                student.addCourse(readCourse(sc.next()));
            sc.close();
            return student;
        } catch (Exception e) {
            System.out.println("something went wrong. please try again later");
            sc.close();
            return null;
        }
    }
    public static void writeCourse(Course course) {
        try {
            File file = new File("src/edu/file/departments/" + course.getDepartment() + "/" + course.getCode());
            file.createNewFile();
            writeCourse(course, file);
        } catch (Exception e) {
            Write.println("Your course has invalid data", "pink");
        }
    }
    public static void writeCourse(Course course, File file) {
        try {
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
            Write.println("Your course has invalid data", "pink");
        }
    }
    // for import export files
}
