/*
    This class will be handling basic database utilities like connecting to the database.
 */
package sample;
import java.sql.*;
import javax.swing.JOptionPane; // this class comes from the swing library it produces a pop up windows
//I use it hear instead of JavaFX becasue it is quicker to code and use.

public class DbUtil {
     public static Connection connectDB() {
         try {
            Class.forName("com.mysql.jdbc.Driver");
             Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/testdb", "root", "5342");
             return conn;
         } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                return null;
         }
     }
}
