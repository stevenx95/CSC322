package org.guccigang.mini_google_docs.model;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

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
    public static boolean isDocumentFlagged(String userName, int docID){
        String SQLStatment = "Select * from documents where owner = ? AND docID = ? AND tabooFlag = 1";
        ResultSet resultSet = DbUtil.processQuery(SQLStatment, userName, Integer.toString(docID));
        try {
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

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

    public static boolean tabooOnSave(TextArea areaText, DocumentFile selectedDocument, UserObject currentUser){
        if(TabooUtil.containTabooAndUNK(areaText.getText())){
            areaText.setText(TabooUtil.censorTabooWords(areaText.getText()));
            TabooUtil.flagDocument(selectedDocument.getOwner(), selectedDocument.getID());
            VersionUtil.save(selectedDocument.getID(),areaText.getText(),currentUser.getUserName());
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Document contains taboo words. Document has been flaged. Next time the owner logs in he/she must review all flagged documents." ,
                    "Document contains taboo words", "Taboo Warning");
            return true;
        }
        //if document was previously flagged and the user changes it. it will not be unflagged.
        else if(!TabooUtil.containTabooAndUNK(areaText.getText()) && TabooUtil.isDocumentFlagged(selectedDocument.getOwner(),selectedDocument.getID())){
            TabooUtil.unFlagDocument(selectedDocument.getOwner(), selectedDocument.getID());
            VersionUtil.save(selectedDocument.getID(),areaText.getText(),currentUser.getUserName());
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Document contains taboo words. Document has been flaged. Next time the owner logs in he/she must review all flagged documents." ,
                    "Document contains taboo words", "Taboo Warning");
            return true;
        }
        return false;
    }
    public static boolean tabooOnOpen(DocumentFile selectedDocument, UserObject currentUser){
        if(TabooUtil.containTabooAndUNK(selectedDocument.getContent())){
            selectedDocument.setContent(TabooUtil.censorTabooWords(selectedDocument.getContent()));
            TabooUtil.flagDocument(selectedDocument.getOwner(), selectedDocument.getID());
            VersionUtil.save(selectedDocument.getID(),selectedDocument.getContent(),currentUser.getUserName());
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Document contains taboo words. Document has been flaged. Next time the owner logs in he/she must review all flagged documents." ,
                    "Document contains taboo words", "Taboo Warning");
            return true;
        }
        return false;
    }
}
