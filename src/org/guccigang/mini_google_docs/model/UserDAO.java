package org.guccigang.mini_google_docs.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * This method access the database and queries all the entities in the application table
     * @return An observableList all user applications
     */
    public static ObservableList<UserObject> getAllApplications() {
        String sqlStatement = "SELECT * FROM application";
        ObservableList<UserObject> applications = null;
        try {
            ResultSet resultSet = DbUtil.processQuery(sqlStatement);
            applications = createUserList(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return applications;
        }
    }

    /**
     *
     * @param resultSet an object containing resulting table from querying the application or user table
     * @return an ObservableList containing user Objects
     * @throws SQLException
     */

    private static ObservableList<UserObject> createUserList(ResultSet resultSet) throws SQLException {
        ObservableList<UserObject> userList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            UserObject userObject = new UserObject(resultSet.getString("username"), resultSet.getString("password"),
                    resultSet.getString("firstname"), resultSet.getString("lastname"),
                    resultSet.getInt("membershiplevel"));
            userList.add(userObject);
        }
        return userList;
    }
}
