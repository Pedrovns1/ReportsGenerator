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
 * This class defines the Office user type within the ReportsGenerator application
 * As a subclass of User, Office users have specific functionalities and permissions tailored to their role, 
 * in this case, the ability to generate all types of reports. This implementation demonstrates the concept of 
 * inheritance, where the Office class inherits from the User class and provides its own implementation 
 * of the generateReport method to reflect the capabilities and responsibilities of office users
 */
// Office class definition, extending the abstract User class to inherit its properties and methods
public class Office extends User {

    // Constructor for creating an Office user with specified username and password, setting the user role to OFFICE
    public Office(String username, String password) {
        super(username, password, UserRole.OFFICE); // Calls the superclass (User) constructor with the role set to OFFICE
    }

    // Overridden generateReport method from the User class, to provide functionality specific to Office users
    @Override
    public void generateReport() { // Implementation for generating reports by Office users, showcasing their ability to generate any type of report
        System.out.println("Office user generating all types of reports.");
    }
}
