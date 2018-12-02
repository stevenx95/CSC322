package org.guccigang.mini_google_docs.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.guccigang.mini_google_docs.DbUtil;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TabooWordDAO {

    public static ObservableList<TabooWord> getAllTabooWords() throws SQLException {
        String selectStatement = "SELECT * FROM taboolist";

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
}
