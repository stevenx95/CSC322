/*
    This class will be handling basic database utilities like connecting to the database.
 */
package org.guccigang.mini_google_docs;
import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;
import javax.swing.JOptionPane; // this class comes from the swing library it produces a pop up windows
//I use it here instead of JavaFX becasue it is quicker to code and use.

public class DbUtil {
    //Declare JDBC Driver
    private static final String JDBC_Driver = "com.mysql.jdbc.Driver";

    //Connection
    private static Connection connection = null;

    //Connection String  YOU GUYS WILL HAVE TO KEEP CHANGING THIS CRAP DEPENDING ON THE NAME OF YOUR DATABASE
    private static final String connString = "jdbc:mysql://localhost/document_system";

    public static void connectDB() throws SQLException, ClassNotFoundException{
        try{
            Class.forName(JDBC_Driver);
        }catch (ClassNotFoundException e) {
            System.out.println("Please get the JDBC drive installed...");
            e.printStackTrace();
            throw e;
        }

        System.out.println("JDBC driver detected...");
        //Establishing a connection to database using connection string
        try{
            connection = DriverManager.getConnection(connString,"root","password");
        }catch (SQLException e){
            System.out.println("Connection has failed...");
            e.printStackTrace();
            throw e;
        }
    }
    //Close Connection
    public static void disconnectDB() throws SQLException{
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch (Exception e){
            throw e;
        }
    }
    //Execute Query Operation
    public static ResultSet executQueryDB(String queryStatment) throws SQLException, ClassNotFoundException{
        Statement statement = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;

        try{
            //Connect to the database
            connectDB();
            System.out.println("Select statment: " + queryStatment + "\n");
            //Create statment
            statement = connection.createStatement();
            //Execute select query statment.
            resultSet = statement.executeQuery(queryStatment);

            /**
             * CachedRowSet , in order to prevent "java.sql.SQLRECOVERABLEEXCEPTION: CLOSED CONNECTION: NEXT" error
             * We use cachedrowset.
             */
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);

        }catch (SQLException e){
            System.out.println("A problemhas occurred please check query statment." + e);
            throw e;
        }finally {
            if(resultSet != null){
                //Close result set
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            disconnectDB();
        }
        return crs;

    }
    //Execute update for (insert,update,delete) operations
    public static void executeUpdateDB(String queryStatement) throws SQLException, ClassNotFoundException{
        //Declare statment as null
        Statement statement = null;
        try{
            connectDB();
            statement = connection.createStatement();
            statement.executeUpdate(queryStatement);
        }catch (SQLException e){
            System.out.println("Problem occurred at in execute update. Please check statment" + e);
            throw e;
        }finally {
            if(statement != null){
                statement.close();
            }
            disconnectDB();
        }
    }
}
