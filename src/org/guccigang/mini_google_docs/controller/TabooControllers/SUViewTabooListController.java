package org.guccigang.mini_google_docs.controller.TabooControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.guccigang.mini_google_docs.controller.UserUI.OriginalUserUIController;
import org.guccigang.mini_google_docs.controller.UserUI.SuperUserUIController;
import org.guccigang.mini_google_docs.model.*;

import java.io.IOException;
import java.sql.SQLException;

public class SUViewTabooListController {

    UserObject currentUser;

    public SUViewTabooListController( UserObject currentUser){
        this.currentUser = currentUser;
    }

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


    @FXML
    public void handleHome(ActionEvent event){
        System.out.println(currentUser.getUserName());
        try{
                SuperUserUIController controller = new SuperUserUIController(currentUser);
                GuiUtil.changeScene(event,UILocation.SUPER_USER_UI,"Home",controller);

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
            tabooWordTable.setItems(TabooWordDAO.getAllApprovedTabooWords());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void insertTabooDB(ActionEvent actionEvent) throws SQLException, IOException {

        TabooWordDAO.insertTabooWord(addTextBar.getText());
        fillTable();
    }

    @FXML
    private void removeTabooFromDB() throws  SQLException{
        TabooWord tabooWord = new TabooWord();
        int selectedIndex = tabooWordTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >=0){
         tabooWord = tabooWordTable.getItems().get(selectedIndex);
        }

        if (tabooWord != null){
            TabooWordDAO.removeTabooWord(tabooWord.getTabooText());
        }
        else {
            //Nothing selected.
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Please select a word from the table.",
                    "No Taboo Word Selected", "No Selection");
        }
        fillTable();
    }





}
