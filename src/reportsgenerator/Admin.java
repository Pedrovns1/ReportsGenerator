/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportsgenerator;

import java.util.Map;

/**
 *
 * @author peuvi
 */
/**
 * Defines the Admin class, which extends the User class Admin users have
 * specific privileges such as the ability to add, modify, and delete users in
 * the system
 */
public class Admin extends User {

    /*
     * Constructor for the Admin class 
     * @param username the admin's username
     * @param password the admin's password
     */
    public Admin(String username, String password) {
        super(username, password, UserRole.ADMIN);
    }

    /*
     * Overrides the generateReport method from User class 
     * Admins are not allowed to generate reports, so this method informs them of that restriction.
     */
    @Override
    public void generateReport() {
        System.out.println("Admins cannot generate reports.");
    }

    /*
     * Adds a new user to the system 
     * @param users    the map of current users
     * @param username the new user's username
     * @param password the new user's password
     * @param role     the new user's role
     */
    public void addUser(Map<String, User> users, String username, String password, UserRole role) {
        if (users.containsKey(username)) {
            System.out.println("User already exists.");
            return;
        }
        switch (role) {
            case ADMIN:
                users.put(username, new Admin(username, password));
                break;
            case OFFICE:
                users.put(username, new Office(username, password));
                break;
            case LECTURER:
                users.put(username, new Lecturer(username, password));
                break;
        }
        System.out.println("User added successfully: " + username);
    }

    /*
     * Modifies an existing user's details in the system
     * @param users       the map of current users
     * @param oldUsername the user's old username
     * @param newUsername the user's new username
     * @param newPassword the user's new password
     * @param newRole     the user's new role
     */

    public void modifyUser(Map<String, User> users, String oldUsername, String newUsername, String newPassword, UserRole newRole) {
        if (!users.containsKey(oldUsername)) {
            System.out.println("User does not exist on the system.");
            return;
        }
        // Check if new username is already taken by another user
        if (!oldUsername.equals(newUsername) && users.containsKey(newUsername)) {
            System.out.println("New username already exists on the system. Please choose a unique username.");
            return;
        }
        // Remove the old user entry
        User oldUser = users.remove(oldUsername);

        // Update the user details
        oldUser.setUsername(newUsername);
        oldUser.setPassword(newPassword);

        if (oldUser.getRole() != newRole) {
            switch (newRole) {
                case ADMIN:
                    oldUser = new Admin(newUsername, newPassword);
                    break;
                case OFFICE:
                    oldUser = new Office(newUsername, newPassword);
                    break;
                case LECTURER:
                    oldUser = new Lecturer(newUsername, newPassword);
                    break;
            }
        }
        // Add the updated user back into the map
        users.put(newUsername, oldUser);
        System.out.println("User's details updated successfully: " + newUsername);
    }

    /*
     * Deletes a user from the system
     * @param users the map of current users
     * @param username the username of the user to delete
     */
    public void deleteUser(Map<String, User> users, String username) {
        if (!users.containsKey(username)) {
            System.out.println("User does not exist.");
            return;
        }
        users.remove(username);
        System.out.println("User deleted successfully: " + username);
    }
}
