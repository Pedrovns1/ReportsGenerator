/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportsgenerator;

/**
 *
 * @author peuvi
 */
public class Lecturer extends User {
    public Lecturer(String username, String password) {
        super(username, password, UserRole.LECTURER);
    }

    @Override
    public void generateReport() {
        System.out.println("Lecturer generating lecturer-specific report.");
    }
}
