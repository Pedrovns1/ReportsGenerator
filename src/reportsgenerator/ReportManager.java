/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportsgenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author peuvi
 */
public class ReportManager {
    public static Connection connect() {
        DBConnector dbConnector = new DBConnector(); 
        try {
            return DriverManager.getConnection(dbConnector.getDbUrl(), dbConnector.getUser(), dbConnector.getPassword());
        } catch (SQLException e) {
            System.out.println("");
            return null;
        }
    }
}
