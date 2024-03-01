import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Student student = new Student("402110576", "0250461293");
        fileUtil.writeStudent(student);
        student = fileUtil.readStudent("402110576");
        System.out.println(student.getUsername());
        System.out.println(student.getPassword());
        System.out.println(student.getUnit());
    }
}