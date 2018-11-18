

/*
    This class is a utility class that facilitates manipulation of database. We can:
        connect to a database
        send a query to the database
        add parameters to such query if needed
        get results of query
 */
package org.guccigang.mini_google_docs;
//import com.sun.rowset.CachedRowSetImpl;

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

    public static void connectDB() {
        try {
            Class.forName(JDBC_Driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Please get the JDBC drive installed...");
            e.printStackTrace();
        }

        System.out.println("JDBC driver detected...");
        //Establishing a connection to database using connection string
        try {
            connection = DriverManager.getConnection(connString, "root", "password");
        } catch (SQLException e) {
            System.out.println("Connection has failed...");
            e.printStackTrace();
        }
    }

    //Close Connection
    public static void disconnectDB() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    //Execute Query Operation
//    public static ResultSet executQueryDB(String queryStatment) throws SQLException, ClassNotFoundException {
//        Statement statement = null;
//        ResultSet resultSet = null;
//        //CachedRowSetImpl crs = null;
//
//        try {
//            //Connect to the database
//            connectDB();
//            System.out.println("Select statment: " + queryStatment + "\n");
//            //Create statment
//            statement = connection.createStatement();
//            //Execute select query statment.
//            resultSet = statement.executeQuery(queryStatment);
//
//            /**
//             * CachedRowSet , in order to prevent "java.sql.SQLRECOVERABLEEXCEPTION: CLOSED CONNECTION: NEXT" error
//             * We use cachedrowset.
//             */
//            crs = new CachedRowSetImpl();
//            crs.populate(resultSet);
//
//        } catch (SQLException e) {
//            System.out.println("A problemhas occurred please check query statment." + e);
//            throw e;
//        } finally {
//            if (resultSet != null) {
//                //Close result set
//                resultSet.close();
//            }
//            if (statement != null) {
//                statement.close();
//            }
//            disconnectDB();
//        }
//        return crs;
//
//    }

    //Execute update for (insert,update,delete) operations
    public static int executeUpdateDB(String sqlStatement, String... sqlParams) throws SQLException  {
        connectDB();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            for (int i = 0; i < sqlParams.length; i++) {
                preparedStatement.setString(i + 1, sqlParams[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnectDB();
        }
        return (preparedStatement == null) ? 0: preparedStatement.executeUpdate();
    }

    public static ResultSet processQuery(String sqlStatement) throws SQLException {
        //precondition: sql is a valid SQL statement  with ? for each in sqlParams.
        //post-condition: a result set object is returned containing the results from the query as a set or it is null.
        connectDB();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnectDB();
        }
        return (preparedStatement == null) ? null: preparedStatement.executeQuery();
    }

    public static ResultSet processQuery(String sqlStatement, String... sqlParams) throws SQLException {
        //precondition: sql is a valid SQL statement  with ? for each in sqlParams.
        //post-condition: a result set object is returned containing the results from the query as a set or it is null.
        connectDB();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            for (int i = 0; i < sqlParams.length; i++) {
                preparedStatement.setString(i + 1, sqlParams[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnectDB();
        }
        return (preparedStatement == null) ? null: preparedStatement.executeQuery();
    }
}
