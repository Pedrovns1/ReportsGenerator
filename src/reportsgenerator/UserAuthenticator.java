/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportsgenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author peuvi
 */
public class UserAuthenticator {
    public static User authenticateUser(Connection conn) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next(); // Em prática real, compare hashes de senhas.

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE Username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String dbPassword = rs.getString("Password");
                String role = rs.getString("Role");

                if (dbPassword.equals(password)) { // Aqui você deve comparar o hash da senha.
                    switch (UserRole.valueOf(role.toUpperCase())) {
                        case ADMIN:
                            return new Admin(username, dbPassword);
                        case OFFICE:
                            return new Office(username, dbPassword);
                        case LECTURER:
                            return new Lecturer(username, dbPassword);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Authentication failed due to database error: " + e.getMessage());
        }
        return null; // Autenticação falhou
    }
}