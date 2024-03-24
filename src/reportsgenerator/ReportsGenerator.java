/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reportsgenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    
      private static void exportReport(StringBuilder reportData) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select output format:");
        System.out.println("1. Console");
        System.out.println("2. TXT file");
        System.out.println("3. CSV file");
        int formatOption = scanner.nextInt();

        switch (formatOption) {
            case 1:
                System.out.println(reportData.toString());
                break;
            case 2:
                exportToTxt(reportData, "Report.txt");
                break;
            case 3:
                exportToCSV(reportData, "Report.csv");
                break;
            default:
                System.out.println("Invalid format option.");               
                break;
        }
    }
    
    private static void exportToTxt(StringBuilder reportData, String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"))) {
        writer.write(reportData.toString());
        System.out.println("Report exported to " + fileName + ".csv");
    } catch (IOException e) {
        System.out.println("Error exporting report to CSV file: " + e.getMessage());
    }
}

    private static void exportToCSV(StringBuilder reportData, String fileName) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".csv"))) {
        writer.write(reportData.toString());
        System.out.println("Report exported to " + fileName + ".csv");
    } catch (IOException e) {
        System.out.println("Error exporting report to CSV file: " + e.getMessage());
    }
}

    enum ReportType {
        COURSE, STUDENT, LECTURER
    }
}