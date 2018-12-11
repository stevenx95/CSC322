package org.guccigang.mini_google_docs.controller.TabooControllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import org.guccigang.mini_google_docs.model.*;

import java.sql.SQLException;

public class TabooDocumentReviewEditorController {
    private UserObject currentUser;
    private DocumentFile currentFile;

    @FXML
    private TextArea textArea;

    TabooDocumentReviewEditorController(){
        this.currentUser = null;
        this.currentFile = null;
    }
    TabooDocumentReviewEditorController(UserObject currentUser, DocumentFile currentFile){
        this.currentUser = currentUser;
        this.currentFile = currentFile;
    }

    public void handleSubmitChanges(){
        if (TabooUtil.containTabooAndUNK(textArea.getText())){
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING,"Remove all Taboo Words AND 'UNK'","Please Change","Warning");
        }else {
            TabooUtil.unFlagDocument(currentFile.getOwner(),currentFile.getID());
            VersionUtil.save(currentFile.getID(),textArea.getText(),currentFile.getOwner());
            GuiUtil.createAlertWindow(Alert.AlertType.INFORMATION,"Thank You!","Taboo Document Review","Document Review");

        }
    }
    @FXML
    private void initialize(){
        try {
            textArea.setText(VersionUtil.open(Integer.toString(currentFile.getID())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
