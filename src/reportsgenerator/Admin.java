/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportsgenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author peuvi
 */
public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, UserRole.ADMIN);
    }

    @Override
    public void generateReport() {
        System.out.println("Admins cannot generate reports.");
    }

   

    public void addUser(Connection conn, String username, String password, UserRole role) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users (Username, Password, Role) VALUES (?, ?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, password); // Use senha hash na prática.
            stmt.setString(3, role.name());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User added successfully: " + username);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while adding the user: " + e.getMessage());
        }
    }

    public void modifyUser(Connection conn, String oldUsername, String newUsername, String newPassword, UserRole newRole) {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE Users SET Username = ?, Password = ?, Role = ? WHERE Username = ?")) {
            stmt.setString(1, newUsername);
            stmt.setString(2, newPassword); // Use senha hash na prática.
            stmt.setString(3, newRole.name());
            stmt.setString(4, oldUsername);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User's details updated successfully: " + newUsername);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while updating the user: " + e.getMessage());
        }
    }

    public void deleteUser(Connection conn, String username) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE Username = ?")) {
            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully: " + username);
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while deleting the user: " + e.getMessage());
        }
    }
}
