package org.guccigang.mini_google_docs.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class TabooUtil {
    /**
     * Checks if string contains taboo words by querying the database.
     * @param string
     * @return
     */
    public static boolean containsTaboo (String string){
        String[] documentContents = string.split("\n");
        String SQLStatement = "select * from tabooList where tabooWord = ?";

        for (String word : documentContents){
            try{
                ResultSet resultSet = DbUtil.processQuery(SQLStatement,word);
                if(resultSet.next()){
                    return true;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Returns a hashset containing all taboo words from the database.
     * @return Hastset
     */
    private static HashSet<String> getTabooList(){
        HashSet<String> tabooSet = new HashSet<>();
        String SQLStatement = "select * from tabooList";
        ResultSet resultSet = DbUtil.processQuery(SQLStatement);
        try{
            while (resultSet.next()){
                tabooSet.add(resultSet.getString("tabooWord"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tabooSet;
    }

    /**
     * Replaces taboo words within string with "UNK"
     * @param string
     * @return
     */
    public static String replaceTaboo(String string){
        HashSet<String> tabooSet = getTabooList();
        String[] documentContents = string.split("\n");
        for(String word : documentContents){
            if (tabooSet.contains(word)){
                string = string.replace(word,"UNK");
            }
        }
        return string;

    }

    /**
     * Flags user due to document containing taboo words.
     * 1 is OU, 2 is SU, 3 is Flag for taboo review
     * @param userName
     */
    public static void flagUser(String userName){
        String SQLStatement = "Update users SET membershipLevel = 3 WHERE userName = ?";
        DbUtil.executeUpdateDB(SQLStatement,userName);
    }


}
