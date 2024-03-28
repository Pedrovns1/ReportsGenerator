/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportsgenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author peuvi
 */

/*
 * This class serves as a utility for managing and generating various reports within the ReportsGenerator application
 * It utilizes JDBC to connect to and interact with a database, fetching and aggregating data required for generating specific reports
 * such as course reports, student reports, and lecturer reports. The methods within this class demonstrate JDBC operations
 * including establishing connections, executing SQL queries, and processing ResultSet objects to compile the report data
 */
public class ReportManager {

    // Establishes a connection to the database using the connection details provided by the DBConnector class
    public static Connection connect() {
        //Using DBConnector to obtain the connection details
        DBConnector dbConnector = new DBConnector(); // Creates an instance of DBConnector to retrieve connection details
        try {
            //Try to stablish a connection with the database using the provided details
            return DriverManager.getConnection(dbConnector.getDbUrl(), dbConnector.getUser(), dbConnector.getPassword());
        } catch (SQLException e) {
            System.out.println("");
            return null; // Returns null if the connection attempt fails
        }
    }

    // Generates a report containing information on courses, including module name, program, student count, lecturer name, and room
    public static void generateCourseReport(Connection conn, StringBuilder reportData) {
        String sql // MySQL query to retrieve data from the database
                = "SELECT c.Name AS ModuleName, c.Program, COUNT(e.StudentID) AS StudentCount, "
                + "l.Name AS LecturerName, COALESCE(c.Room, 'online') AS Room "
                + "FROM Courses c "
                + "LEFT JOIN Enrollments e ON c.CourseID = e.CourseID "
                + "LEFT JOIN Lecturers l ON c.LecturerID = l.LecturerID "
                + "GROUP BY c.CourseID";

        try ( Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            reportData.append("Course Report:\n");
            while (rs.next()) {
                // This block constructs a string for each row in the ResultSet by appending column values
                reportData.append("Module Name: ").append(rs.getString("ModuleName"))
                        .append(", Programme: ").append(rs.getString("Program"))
                        .append(", Enrolled Students: ").append(rs.getInt("StudentCount"))
                        .append(", Lecturer: ").append(rs.getString("LecturerName"))
                        .append(", Room: ").append(rs.getString("Room")).append("\n");
            }
        } catch (SQLException e) {
            System.out.println("Error generating course report:");
        }
    }

    // Generates a report containing detailed information on students, including their name, ID, program, enrolled modules, completed modules, and modules to repeat
    public static void generateStudentReport(Connection conn, StringBuilder reportData) {
        String sql // MySQL query to retrieve data from the database
                = "SELECT s.Name AS StudentName, s.StudentID, s.Program, "
                + "GROUP_CONCAT(DISTINCT CASE WHEN g.Status = 'In Progress' THEN c.Name END) AS EnrolledModules, "
                + "GROUP_CONCAT(DISTINCT CASE WHEN g.Status = 'Passed' THEN CONCAT(c.Name, ' (Grade: ', g.Score, ')') END) AS CompletedModules, "
                + "GROUP_CONCAT(DISTINCT CASE WHEN g.Status = 'Failed' THEN c.Name END) AS RepeatModules "
                + "FROM Students s "
                + "LEFT JOIN Enrollments e ON s.StudentID = e.StudentID "
                + "LEFT JOIN Courses c ON e.CourseID = c.CourseID "
                + "LEFT JOIN Grades g ON e.EnrollmentID = g.EnrollmentID "
                + "GROUP BY s.StudentID";

        try ( Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            reportData.append("Student Report:\n");
            while (rs.next()) {
                reportData.append("Student Name: ").append(rs.getString("StudentName"))
                        .append(", Student Number: ").append(rs.getString("StudentID"))
                        .append(", Programme: ").append(rs.getString("Program"))
                        .append(", Currently Enrolled Modules: ").append(rs.getString("EnrolledModules"))
                        .append(", Completed Modules (with Grades): ").append(rs.getString("CompletedModules"))
                        .append(", Modules to Repeat: ").append(rs.getString("RepeatModules")).append("\n");
            }
        } catch (SQLException e) {
            System.out.println("Error generating student report:");
        }
    }

    // Generates a report detailing lecturers, including their names, roles, modules they teach, student count for each module, and courses they can teach
    public static void generateLecturerReport(Connection conn, StringBuilder reportData) {
        String sql // MySQL query to retrieve data from the database
                = "SELECT l.Name AS LecturerName, l.Role, c.Name AS ModuleName, COUNT(e.StudentID) AS StudentCount, "
                + "GROUP_CONCAT(DISTINCT IFNULL(c.Program, 'Unknow')) AS CourseTypes "
                + "FROM Lecturers l "
                + "LEFT JOIN Courses c ON l.LecturerID = c.LecturerID "
                + "LEFT JOIN Enrollments e ON c.CourseID = e.CourseID "
                + "GROUP BY l.LecturerID, c.Name";

        try ( Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            reportData.append("Lecturer Report:\n");
            while (rs.next()) {
                reportData.append("Lecturer Name: ").append(rs.getString("LecturerName"))
                        .append(", Role: ").append(rs.getString("Role"))
                        .append(", Teaching Module: ").append(rs.getString("ModuleName"))
                        .append(", Number of Students in Module: ").append(rs.getInt("StudentCount"))
                        .append(", Can Teach: ").append(rs.getString("CourseTypes")).append("\n");
            }
        } catch (SQLException e) {
            System.out.println("Error generating lecturer report:");
        }
    }

}
