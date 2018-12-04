package org.guccigang.mini_google_docs.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.guccigang.mini_google_docs.model.DbUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersDAO {
    public static ObservableList<UserObject> getAllUsers()throws SQLException{
        ObservableList<UserObject> users;
        String selectStatementUsers = "select * from users order by userName";
        //Execute select statement
        ResultSet resultSetUsers = DbUtil.processQuery(selectStatementUsers);
        users = getAllUsersList(resultSetUsers);
        return users;
    }
    private static ObservableList<UserObject> getAllUsersList(ResultSet resultSetUsers){
        ObservableList<UserObject> users = FXCollections.observableArrayList();
        String selectStatemnetInterests = "select * from interests where userName = ?";
        try{
            while(resultSetUsers.next()){
                ArrayList<String> interests = new ArrayList<String>();
                String userName = resultSetUsers.getString("userName");
                String password = resultSetUsers.getString("password");
                String firstName = resultSetUsers.getString("firstName");
                String lastName = resultSetUsers.getString("lastName");
                int membershipLevel = resultSetUsers.getInt("membershipLevel");
                ResultSet interestsResultSet = DbUtil.processQuery(selectStatemnetInterests,resultSetUsers.getString("userName"));
                UserObject user = new UserObject(userName,password,firstName,lastName,membershipLevel);
                while (interestsResultSet.next()){
                    user.addInterest(interestsResultSet.getString("interest"));
                }
                users.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }
    public static ObservableList<UserObject> getSearchedResult(String userInput){
        ObservableList<UserObject> users;
        String modifiedUserInput = "%"+userInput+"%";
        String selectStatmentUserInput = "select * from users natural join interests where userName like ? OR interest like ?";
        ResultSet searchedResultSet = DbUtil.processQuery(selectStatmentUserInput,modifiedUserInput,modifiedUserInput);
        users = getAllSearchedUsersList(searchedResultSet);
        return users;
    }
    private static ObservableList<UserObject> getAllSearchedUsersList(ResultSet searchedResultSet){
        ObservableList<UserObject> users = FXCollections.observableArrayList();
        String selectStatemnetInterests = "select * from interests where userName = ?";
        int i = 0;
        try{
            while(searchedResultSet.next()){
                ArrayList<String> interests = new ArrayList<String>();
                String userName = searchedResultSet.getString("userName");
                String password = searchedResultSet.getString("password");
                String firstName = searchedResultSet.getString("firstName");
                String lastName = searchedResultSet.getString("lastName");
                int membershipLevel = searchedResultSet.getInt("membershipLevel");
                ResultSet interestsResultSet = DbUtil.processQuery(selectStatemnetInterests,searchedResultSet.getString("userName"));
                UserObject user = new UserObject(userName,password,firstName,lastName,membershipLevel);

                while (interestsResultSet.next()){
                    user.addInterest(interestsResultSet.getString("interest"));
                }
                if(i == 0){
                    users.add(user);
                    i+=1;
                }else if(i != 0 && !users.get(i-1).getUserName().equals(user.getUserName())){
                    users.add(user);
                    i+=1;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }
}
