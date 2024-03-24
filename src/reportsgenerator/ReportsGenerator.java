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
                    generateReport(conn, ReportType.COURSE, reportData);
                    break;
                case 2:
                    generateReport(conn, ReportType.STUDENT, reportData);
                    break;
                case 3:
                    generateReport(conn, ReportType.LECTURER, reportData);
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
    
}