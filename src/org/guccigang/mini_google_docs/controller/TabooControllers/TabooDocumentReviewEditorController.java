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
            GuiUtil.createAlertWindow(Alert.AlertType.CONFIRMATION,"Remove all Taboo Words AND 'UNK'","Please Change","Warning");
        }else {
            TabooUtil.unFlagDocument(currentUser.getUserName(),currentFile.getID());
            VersionUtil.save(currentFile.getID(),textArea.getText(),currentFile.getOwner());
        }
    }
    @FXML
    private void initialize(){
        textArea.setText(currentFile.getContent());
    }
}
