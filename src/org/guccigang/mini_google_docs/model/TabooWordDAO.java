package org.guccigang.mini_google_docs.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TabooWordDAO {

    public static ObservableList<TabooWord> getAllTabooWords() throws SQLException {
        String selectStatement = "SELECT * FROM tabooSuggestions";

        try{
            ResultSet rs = DbUtil.processQuery(selectStatement);
            ObservableList<TabooWord> tabooWordsData = getTabooWordList(rs);
            return tabooWordsData;
        }catch (SQLException e){
            System.out.println("SQL query has failed " + e);
            throw e;
        }
    }

    public static ObservableList<TabooWord> getAllApprovedTabooWords() throws SQLException {
        String selectStatement = "SELECT * FROM approvedTabooWords";

        try{
            ResultSet rs = DbUtil.processQuery(selectStatement);
            ObservableList<TabooWord> tabooWordsData = getTabooWordList(rs);
            return tabooWordsData;
        }catch (SQLException e){
            System.out.println("SQL query has failed " + e);
            throw e;
        }
    }

    private static ObservableList<TabooWord> getTabooWordList(ResultSet rs) throws SQLException {
        ObservableList<TabooWord> tabooList = FXCollections.observableArrayList();

        while(rs.next()){
            TabooWord tabooWord = new TabooWord();

            tabooWord.setTabooText(rs.getString("tabooWord"));
            tabooList.add(tabooWord);
        }
        return tabooList;
    }

    public static void insertTabooWord(String word) throws SQLException, IOException {
        String selectStatement = "SELECT * FROM approvedTabooWords";
        ArrayList<String> list = new ArrayList<>();

        try {
            ResultSet rs = DbUtil.processQuery(selectStatement);
            while(rs.next()){
                list.add(rs.getString(1));
            }
            if(!list.contains(word)){
                String selectStatement2 = "INSERT INTO approvedTabooWords(tabooWord) VALUES ('"+word+"')";
                DbUtil.executeUpdateDB(selectStatement2);
            }

        }catch (SQLException e){
            throw e;
        }

    }

    public static void removeTabooWord(String word) throws SQLException{
        String selectStatement = "DELETE FROM approvedTabooWords WHERE tabooWord = ?";
        DbUtil.executeUpdateDB(selectStatement,word);
    }

    public static void sendTabooSuggestion(String userName, String tabooWord) throws SQLException, IOException {
        String selectStatement = "SELECT tabooWord FROM tabooSuggestions";
        ArrayList<String> list = new ArrayList<>();

        try{
            ResultSet rs = DbUtil.processQuery(selectStatement);
            while(rs.next()){
                list.add(rs.getString(1));
            }
            if(!list.contains(tabooWord)){
                String selectStatment2 = "INSERT INTO tabooSuggestions VALUES (?,?)";
                DbUtil.executeUpdateDB(selectStatment2,userName,tabooWord);
            }
            else {
                return;
            }
        }catch (SQLException e){
            throw e;
        }

    }







    public static void insertIntoApprovedTable(String word) throws SQLException, IOException {
        String selectStatement = "SELECT * FROM approvedTabooWords";
        ArrayList<String> list = new ArrayList<>();

        try {
            ResultSet rs = DbUtil.processQuery(selectStatement);
            while(rs.next()){
                list.add(rs.getString(1));
            }
            if(!list.contains(word)){
                String selectStatement2 = "INSERT INTO approvedTabooWords(tabooWord) VALUES ('"+word+"')";
                DbUtil.executeUpdateDB(selectStatement2);
            }

        }catch (SQLException e){
            throw e;
        }

    }




    public static void removeTabooWordApproval(String word) throws SQLException{
        String selectStatement = "DELETE FROM tabooSuggestions WHERE tabooWord = ?";
        DbUtil.executeUpdateDB(selectStatement,word);
    }





}
