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
public class DBConnector {

    private String dbUrl;
    private String user;
    private String password;

    public DBConnector() {
        // Load properties
        Properties props = new Properties();
        try ( FileInputStream input = new FileInputStream("config.properties")) {
            props.load(input);
            this.dbUrl = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.password = props.getProperty("db.password");

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public String getDbUrl() {
    return dbUrl;
}

public String getUser() {
    return user;
}

public String getPassword() {
    return password;
}

}
