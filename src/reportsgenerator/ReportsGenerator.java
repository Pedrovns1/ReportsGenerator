/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reportsgenerator;

import java.sql.Connection;
import java.util.Scanner;

/**
 *
 * @author peuvi
 */
public class ReportsGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection conn = ReportManager.connect(); // Establish the connection using the ReportManager
        Scanner scanner = new Scanner(System.in);
        StringBuilder reportData = new StringBuilder();
        boolean running = true;

        while (running) {
            MainMenu(); // Shows the main menu
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    generateAndExportReport(conn, ReportType.COURSE);
                    break;
                case 2:
                    generateAndExportReport(conn, ReportType.STUDENT);
                    break;
                case 3:
                    generateAndExportReport(conn, ReportType.LECTURER);
                    break;
                case 4:
                    System.out.println("Exiting program.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void MainMenu() {
        System.out.println("\nChoose the report you want to generate:");
        System.out.println("1. Course Report");
        System.out.println("2. Student Report");
        System.out.println("3. Lecturer Report");
        System.out.println("4. Exit");
        System.out.print("Enter option: ");
    }
    
    private static void generateAndExportReport(Connection conn, ReportType type) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Generating " + type + " Report...");
        StringBuilder reportData = new StringBuilder();
        
        switch (type) {
            case COURSE:
                ReportManager.generateCourseReport(conn, reportData);
                break;
            case STUDENT:
                ReportManager.generateStudentReport(conn, reportData);
                break;
            case LECTURER:
                ReportManager.generateLecturerReport(conn, reportData);
                break;
        }
        
        System.out.println("Do you want to export the report? (Y/N)");
        String exportOption = scanner.next();
        
        if (exportOption.equalsIgnoreCase("Y")) {
            exportReport(reportData); // Variable "exportReportMethod"
        } else {
            System.out.println("Report not exported.");
        }
    }
    
  
}