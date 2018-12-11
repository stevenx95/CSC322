package org.guccigang.mini_google_docs.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;

public class SharingUtil {

    public static ArrayList<String> getAllUsers(String currentUser) {
        ArrayList<String> userNames = new ArrayList<>();
        try {
            ObservableList<UserObject> userObjects = UsersDAO.getAllUsers(currentUser);
            System.out.println(userObjects.toString());
            userObjects.forEach(user -> userNames.add(user.getUserName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userNames;
    }

    public static void shareDoc(int docId, String userName) {
        String query1 = "INSERT INTO sharedDocs VALUE (?,?)",
                query2 = "UPDATE documents SET restricted= ? WHERE docId=?";
        DbUtil.executeUpdateDB(query1, pstmt -> {
            pstmt.setString(1,userName);
            pstmt.setInt(2,docId);
        });
        DbUtil.executeUpdateDB(query2, pstmt-> {
            pstmt.setInt(1,1);
            pstmt.setInt(2,docId);
        });
    }
}
