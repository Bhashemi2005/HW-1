import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.Scanner;

public class fileUtil {
    static void writeAdmin(Admin admin) {
        try {
            File file = new File("src/file/admin");
            if(!file.exists()) file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(admin.getPassword());
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Something went wrong. Please try again later");
        }
    }
    static Admin readAdmin() {
        try {
            File file = new File("src/file/admin");
            Scanner sc = new Scanner(file);
            return new Admin(sc.next());
        } catch (Exception e) {
            System.out.println("The admin file has been removed");
            return null;
        }
    }
    static void writeStudent(Student student) {
        try {
            File file = new File("src/file/students/" + student.getUsername());
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
    static Student readStudent(String code) throws Exception {
        try {
            File file = new File("src/file/students/" + code);
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
            throw new Exception(e);
        }
    }
    static Course readCourse(String s) {
        return null;
    }
}
