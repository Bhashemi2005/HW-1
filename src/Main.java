import java.io.File;
import java.io.FileWriter;
import java.sql.Time;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Course course = fileUtil.readCourse("22815");
        course.writeForUser();
    }
}