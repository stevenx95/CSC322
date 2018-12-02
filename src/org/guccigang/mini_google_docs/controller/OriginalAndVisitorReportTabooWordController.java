package org.guccigang.mini_google_docs.controller;

import javafx.fxml.FXML;
import org.guccigang.mini_google_docs.model.TabooWordDAO;

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class OriginalAndVisitorReportTabooWordController {


    @FXML
    TextField userNameBar;
    @FXML
    TextField tabooWordBar;
    @FXML
    Button tabooSuggestionToDB;

    public void tabooSuggestionToDB() throws IOException, SQLException {
        TabooWordDAO.sendTabooSuggestion(userNameBar.getText(),tabooWordBar.getText());
    }
}
