package org.guccigang.mini_google_docs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.guccigang.mini_google_docs.GuiUtil;
import org.guccigang.mini_google_docs.model.TabooWord;
import org.guccigang.mini_google_docs.model.TabooWordDAO;

import java.io.IOException;
import java.sql.SQLException;

public class SUViewTabooListController {

    @FXML
    private Button homeButton;
    @FXML
    private Button addTabooButton;
    @FXML
    private TextField addTextBar;
    @FXML
    private Button deleteTabooButton;
    @FXML
    private Button editTabooButton;
    @FXML
    private TableView<TabooWord> tabooWordTable;
    @FXML
    private TableColumn<TabooWord, String> tabooWordColumn;

    public SUViewTabooListController() {

    }

    @FXML
    public void handleHome(ActionEvent event){
        try{
            GuiUtil.createWindowAndDestroy(event, "views/superUserUI.fxml","Visitor");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize(){
        fillTable();

        tabooWordColumn.setCellValueFactory(cellData -> cellData.getValue().tabooWordProperty());

    }

    public void fillTable(){
        try {
            tabooWordTable.setItems(TabooWordDAO.getAllTabooWords());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void insertTabooDB(ActionEvent actionEvent) throws SQLException, IOException {

        TabooWordDAO.insertTabooWord(addTextBar.getText());
    }

    @FXML
    private void removeTabooFromDB() throws  SQLException{
        TabooWord tabooWord = new TabooWord();
        tabooWord= tabooWordTable.getSelectionModel().getSelectedItem();

        if (tabooWord != null){
            TabooWordDAO.removeTabooWord(tabooWord.toString());
        }
        else {
            //Nothing selected.
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Please select a word from the table.",
                    "No Taboo Word Selected", "No Selection");
        }
    }





}
