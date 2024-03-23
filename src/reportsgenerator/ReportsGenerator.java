/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reportsgenerator;

import java.sql.Connection;
import java.util.Scanner;

/**
 *
 * @author peuvi
 */
public class ReportsGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection conn = ReportManager.connect(); // Establish the connection using the ReportManager
        Scanner scanner = new Scanner(System.in);
    }
    
}
