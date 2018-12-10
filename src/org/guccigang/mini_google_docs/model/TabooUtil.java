package org.guccigang.mini_google_docs.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class TabooUtil {
    /**
     * Queries the database and checks for flagged documents. If an empty relation is returned.
     * Then the user is cleared.
     * @param userName
     * @return
     */
    public static boolean isUserFlaged(String userName){
        String SQLStatement = "select * from documents where owner = ? AND tabooFlag = 1";
        try{
            ResultSet resultSet = DbUtil.processQuery(SQLStatement, userName);
            if(resultSet.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
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

    public static boolean containTabooAndUNK (String string){
        String[] documentContents = string.split("\n");
        String SQLStatement = "select * from tabooList where tabooWord = ?";

        for (String word : documentContents){
            if (word.equals("UNK") || containsTaboo(string)){
                return true;
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
    public static String censorTabooWords(String string){
        HashSet<String> tabooSet = getTabooList();
        String[] documentContents = string.split("\n");
        for(String word : documentContents){
            if (tabooSet.contains(word)){
                string = string.replace(word,"UNK");
            }
        }
        return string;

    }
    public static void flagDocument(String userName, int docID){
        String SQLStatement = "UPDATE documents set tabooFlag = 1 where owner = ? AND docID = ?";
        DbUtil.executeUpdateDB(SQLStatement, userName, Integer.toString(docID));
    }

    public static void unFlagDocument(String userName, int docID){
        String SQLStatement = "UPDATE documents set tabooFlag = 0 where owner = ? AND docID = ?";
        DbUtil.executeUpdateDB(SQLStatement, userName, Integer.toString(docID));
    }

    public static void foundUpdateAndFlagDocument(String userName, int docID, String newContent){
        String SQLStatement = "UPDATE documents set conent = ? where owner = ? AND docID = ?";
        DbUtil.executeUpdateDB(SQLStatement,newContent, userName, Integer.toString(docID));
        flagDocument(userName,docID);
    }
    public static void foundUpdateAndUnflagDocument(String userName, int docID, String newContent){
        String SQLStatement = "UPDATE documents set conent = ? where owner = ? AND docID = ?";
        DbUtil.executeUpdateDB(SQLStatement,newContent, userName, Integer.toString(docID));
        unFlagDocument(userName,docID);
    }
}
