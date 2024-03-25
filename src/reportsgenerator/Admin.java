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
public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, UserRole.ADMIN);
    }

    @Override
    public void generateReport() {
        System.out.println("Admins cannot generate reports.");
    }

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

}
