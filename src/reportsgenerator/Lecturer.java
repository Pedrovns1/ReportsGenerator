/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportsgenerator;

/**
 *
 * @author peuvi
 */

/*
 * This class represents a Lecturer user in the ReportsGenerator application 
 * It extends the User class, providing specific functionalities for Lecturers, 
 * such as the ability to generate lecturer-specific reports. This showcases the use of inheritance 
 * and polymorphism, where Lecturer inherits from User and overrides the generateReport method 
 * to implement functionality specific to Lecturers
 */
// Lecturer class declaration which extends the abstract User class
public class Lecturer extends User {

    // Constructor that creates a Lecturer object with the provided username, password, and sets the user role to LECTURER
    public Lecturer(String username, String password) {
        super(username, password, UserRole.LECTURER); // Calls the superclass (User) constructor
    }

    // Override the generateReport method from the User class to provide a specific implementation for Lecturers
    @Override
    public void generateReport() { // Implementation of report generation for Lecturers
        System.out.println("Lecturer generating lecturer-specific report.");
    }
}
