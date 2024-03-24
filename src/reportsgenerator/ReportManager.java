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
public class ReportManager {
    public static Connection connect() {
        //Using DBConnector to obtain the connection details
        DBConnector dbConnector = new DBConnector(); 
        try {
            //Try to stablish a connection with the database using the provided details
            return DriverManager.getConnection(dbConnector.getDbUrl(), dbConnector.getUser(), dbConnector.getPassword());
        } catch (SQLException e) {
            System.out.println("");
            return null;
        }
    }
    public static void generateCourseReport(Connection conn, StringBuilder reportData) {
        String sql = 
            "SELECT c.Name AS ModuleName, c.Program, COUNT(e.StudentID) AS StudentCount, " +
            "l.Name AS LecturerName, COALESCE(c.Room, 'online') AS Room " +
            "FROM Courses c " +
            "LEFT JOIN Enrollments e ON c.CourseID = e.CourseID " +
            "LEFT JOIN Lecturers l ON c.LecturerID = l.LecturerID " +
            "GROUP BY c.CourseID";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            reportData.append("Course Report:\n");
            while (rs.next()) {
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
    
    public static void generateStudentReport(Connection conn, StringBuilder reportData) {
        String sql = 
            "SELECT s.Name AS StudentName, s.StudentID, s.Program, " +
            "GROUP_CONCAT(DISTINCT CASE WHEN g.Status = 'In Progress' THEN c.Name END) AS EnrolledModules, " +
            "GROUP_CONCAT(DISTINCT CASE WHEN g.Status = 'Passed' THEN CONCAT(c.Name, ' (Grade: ', g.Score, ')') END) AS CompletedModules, " +
            "GROUP_CONCAT(DISTINCT CASE WHEN g.Status = 'Failed' THEN c.Name END) AS RepeatModules " +
            "FROM Students s " +
            "LEFT JOIN Enrollments e ON s.StudentID = e.StudentID " +
            "LEFT JOIN Courses c ON e.CourseID = c.CourseID " +
            "LEFT JOIN Grades g ON e.EnrollmentID = g.EnrollmentID " +
            "GROUP BY s.StudentID";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
    
}
