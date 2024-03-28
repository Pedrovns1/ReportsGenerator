/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportsgenerator;

import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author peuvi
 */

/*
 * This class is responsible for authenticating users within the ReportsGenerator application. It provides a static method that
 * takes a map of users and prompts for username and password via the console, validating these credentials against the provided user map.
 * If authentication succeeds, the corresponding User object is returned, otherwise null is returned to signify authentication failure.
 */
// Static method to authenticate a user against the provided users map
public class UserAuthenticator {

    public static User authenticateUser(Map<String, User> users) {
        Scanner scanner = new Scanner(System.in); // Scanner to read user input
        System.out.print("Enter username: ");
        String username = scanner.next(); // Reading the username input
        System.out.print("Enter password: ");
        String password = scanner.next(); // Reading the password input

        User user = users.get(username); // Attempting to retrieve the user by username
        if (user != null && user.getPassword().equals(password)) { // If the user exists and the password matches, the user is authenticated
            return user; // Authentication successful, return the user object
        }
        return null; // Authentication failed
    }
}
