/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportsgenerator;

/**
 *
 * @author peuvi
 */
public class Office extends User {
    public Office(String username, String password) {
        super(username, password, UserRole.OFFICE);
    }

    @Override
    public void generateReport() {
        System.out.println("Office user generating all types of reports.");
    }
}
