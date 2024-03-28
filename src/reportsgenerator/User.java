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
 * The User class serves as an abstract base for different user types within the ReportsGenerator application
 * It encapsulates common properties and behaviors shared across various user roles, such as username, password, and role 
 * This class also defines the structure for user-specific actions, including report generation and credentials updating, 
 * leveraging polymorphism to enforce the implementation of role-specific reporting behavior in subclasses
 */
public abstract class User {

    // User attributes protected to allow access in subclasses
    protected String username; // Stores the username of the user
    protected String password; // Stores the password for user authentication
    protected UserRole role; // Enum value representing the user's role within the application

    // Constructor to initialize a User object with username, password, and role
    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username allows changing the username of the user
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password allows changing the password for the user
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for the user's role
    public UserRole getRole() {
        return role;
    }

    // Abstract method declaration for generating a report. This method will be implemented by all subclasses
    public abstract void generateReport();

    // Method to change the user's credentials, updating both username and password
    public void changeCredentials(String newUsername, String newPassword) {
        setUsername(newUsername); // Updates the username
        setPassword(newPassword); // Updates the password
        System.out.println("Credentials changed for user: " + newUsername);
    }
}
