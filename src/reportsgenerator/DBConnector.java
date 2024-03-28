/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportsgenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author peuvi
 */

/*
 * This class is responsible for establishing a connection to the database by loading the necessary configurations from a properties file
 * It demonstrates usage of Java's Properties class to read configurations which include database URL, username, and password
 */
public class DBConnector {

    // Private fields to hold database connection details
    private String dbUrl; // Database URL
    private String user; // Database username
    private String password; // Database password

    // Constructor of the DBConnector class
    public DBConnector() {
        // Load properties
        Properties props = new Properties(); // Creates an instance of Properties
        try ( FileInputStream input = new FileInputStream("config.properties")) { // Tries to open the config.properties file
            props.load(input); // Loads properties from the file
            this.dbUrl = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.password = props.getProperty("db.password");

        } catch (IOException e) { // Catches IOException if reading from the file fails
            System.out.println(e); // Prints the exception to the console
        }
    }

    // Getter method for the database URL
    public String getDbUrl() {
        return dbUrl;
    }

    // Getter method for the database user
    public String getUser() {
        return user;
    }

    // Getter method for the database password
    public String getPassword() {
        return password;
    }

}
