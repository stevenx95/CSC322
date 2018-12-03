package org.guccigang.mini_google_docs.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.guccigang.mini_google_docs.DbUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersDAO {
    public static ObservableList<UserObject> getAllUsers()throws SQLException{
        ObservableList<UserObject> users = FXCollections.observableArrayList();
        String selectStatementUsers = "select * from users order by userName";
        String selectStatemnetInterests = "select * from interests order by userName ASC";
        //Execute select statment
        ResultSet resultSetUsers = DbUtil.processQuery(selectStatementUsers);
        users = getAllUsersList(resultSetUsers);
        return users;
    }
    private static ObservableList<UserObject> getAllUsersList(ResultSet resultSetUsers){
        ObservableList<UserObject> users = FXCollections.observableArrayList();
        String selectStatemnetInterests = "select * from interests where userName = ?";
        try{
            while(resultSetUsers.next()){
                String userName = resultSetUsers.getString("userName");
                String password = resultSetUsers.getString("password");
                String firstName = resultSetUsers.getString("firstName");
                String lastName = resultSetUsers.getString("lastName");
                int membershipLevel = resultSetUsers.getInt("membershipLevel");
                ResultSet resultSet = DbUtil.processQuery(selectStatemnetInterests,resultSetUsers.getString("userName"));
                ArrayList<String> interests = new ArrayList<String>();
                while (resultSet.next()){
                    interests.add(resultSet.getString("interest"));
                }
                String[] arrayInterests = new String[interests.size()];
                UserObject user = new UserObject(userName,password,firstName,lastName,membershipLevel);
                user.addInterests(interests.toArray(arrayInterests));
                users.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }
}
