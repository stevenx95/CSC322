package org.guccigang.mini_google_docs.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnkUtil {
    //dont touch
    public static boolean containsTaboo (String string)throws SQLException{
        String[] documentContents = string.split("\n");
        String SQLStatement = "select * from tabooList where tabooWord = ?";
        for (String word : documentContents){
            ResultSet resultSet = DbUtil.processQuery(SQLStatement,word);
            if(resultSet.next()){

            }
        }
        return false;
    }


}
