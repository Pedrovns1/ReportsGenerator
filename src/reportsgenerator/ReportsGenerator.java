/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reportsgenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author peuvi
 */
public class ReportsGenerator {

    /**
     * @param args the command line arguments
     */
    private static final Map<String, User> users = new HashMap<>();

    static {
        // It will ensure the only user available at the start is the Admin
        users.put("admin", new Admin("admin", "java"));
    }

    public static void main(String[] args) {
        Connection conn = ReportManager.connect(); // Establish the connection using the ReportManager
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the Reports Generator. Please log in.");
        User currentUser = UserAuthenticator.authenticateUser(users);
        if (currentUser == null) {
            System.out.println("Authentication failed. Exiting program.");
            return;
        }
        System.out.println("Login successful. Welcome, " + currentUser.getUsername() + "!");
        // Loop to keep the program running until the user decides to exit
        while (running) {
            // Check if the current user is an admin
            if (currentUser instanceof Admin) {
                // Admin menu options
                System.out.println("\nAdmin Menu:");
                System.out.println("1. Add User");
                System.out.println("2. Modify User");
                System.out.println("3. Delete User");
                System.out.println("4. Change Credentials");
                System.out.println("5. Exit");
                System.out.print("Enter option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1: // Add new user
                        try {
                        System.out.println("Enter username, password, and role (ADMIN/OFFICE/LECTURER) for the new user:");
                        String US = scanner.next();
                        String PW = scanner.next();
                        String RO = scanner.next();
                        UserRole newRole = UserRole.valueOf(RO.toUpperCase());
                        ((Admin) currentUser).addUser(users, US, PW, newRole);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid role specified. Please enter one of the following roles: ADMIN, OFFICE, LECTURER.");
                    } catch (Exception e) {
                        System.out.println("An error occurred while adding the user: " + e.getMessage());
                    }
                    break;
                    case 2: // Modify existing user
                        try {
                        System.out.println("Enter old username, new username, new password, and new role (ADMIN/OFFICE/LECTURER):");
                        String OUS = scanner.next();
                        String NUS = scanner.next();
                        String NPW = scanner.next();
                        String NRO = scanner.next();
                        UserRole nrRole = UserRole.valueOf(NRO.toUpperCase());
                        ((Admin) currentUser).modifyUser(users, OUS, NUS, NPW, nrRole);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid role specified. Please enter one of the following roles: ADMIN, OFFICE, LECTURER.");
                    } catch (Exception e) {
                        System.out.println("An error occurred while modifying the user: " + e.getMessage());
                    }
                    break;
                    case 3: // Delete user
                        System.out.println("Enter the username of the user you wish to delete:");
                        String DUS = scanner.next();
                        ((Admin) currentUser).deleteUser(users, DUS);
                        break;
                    case 4:  // Change credentials
                        System.out.println("Enter new username and new password:");
                        String NUUsername = scanner.next();
                        String NUPassword = scanner.next();
                        currentUser.changeCredentials(NUUsername, NUPassword);
                        break;
                    case 5: // Exit program
                        System.out.println("Exiting program.");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } else {
                // General user menu for generating reports
                MainMenu();
                int reportOption = scanner.nextInt();
                switch (reportOption) {
                    case 1: // Generate course report
                        if (!(currentUser instanceof Lecturer)) { // Only non-lecturers can generate course and student reports
                            generateAndExportReport(conn, ReportType.COURSE);
                        } else {
                            System.out.println("Unauthorized to generate this report.");
                        }
                        break;
                    case 2: // Generate student report
                        if (!(currentUser instanceof Lecturer)) { // Only non-lecturers can generate course and student reports
                            generateAndExportReport(conn, ReportType.STUDENT);
                        } else {
                            System.out.println("Unauthorized to generate this report.");
                        }
                        break;
                    case 3: // Generate lecturer report
                        // All users including Lecturers can generate lecturer reports, but Lecturers can generate only their own
                        generateAndExportReport(conn, ReportType.LECTURER);
                        break;
                    case 4: // Exit program
                        System.out.println("Exiting program.");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
            System.out.println("Do you want to log out? (Y/N)");
            String logoutOption = scanner.next();
            if (logoutOption.equalsIgnoreCase("Y")) {
                System.out.println("Logging out...");
                currentUser = logout();
                if (currentUser == null) {
                    System.out.println("Logout successful. Exiting program.");
                    running = false;
                } else {
                    System.out.println("Login successful. Welcome, " + currentUser.getUsername() + "!");
                }
            }
        }
        scanner.close();
    }

    private static User logout() {
        System.out.println("Please log in with another user.");
        return UserAuthenticator.authenticateUser(users);
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
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"))) {
            writer.write(reportData.toString());
            System.out.println("Report exported to " + fileName + ".csv");
        } catch (IOException e) {
            System.out.println("Error exporting report to CSV file: " + e.getMessage());
        }
    }

    private static void exportToCSV(StringBuilder reportData, String fileName) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".csv"))) {
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