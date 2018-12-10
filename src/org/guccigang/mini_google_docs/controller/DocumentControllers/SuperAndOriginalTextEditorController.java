package org.guccigang.mini_google_docs.controller.DocumentControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import org.guccigang.mini_google_docs.model.UILocation;
import org.guccigang.mini_google_docs.model.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SuperAndOriginalTextEditorController implements Initializable {

    @FXML
    private TextArea areaText;

    private UserObject currentUser;
    private DocumentFile selectedDocument;
    private List<String> usersToShare;

    public SuperAndOriginalTextEditorController() {
        this(null,null);
    }

    public SuperAndOriginalTextEditorController(UserObject currentUser, DocumentFile selectedDocument) {
        this.currentUser = currentUser;
        this.selectedDocument = selectedDocument;
        this.usersToShare = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.areaText.setText(selectedDocument.getContent());
        if (currentUser.getMembershipLevel() != 2 && DocumentDAO.documentIsLocked(selectedDocument.getID())) {
            this.areaText.setEditable(false);
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "While locked, this document is in View-Only mode" ,
                    "Document is locked", "Warning");
        }
    }
    public void onSave(ActionEvent event)
    {
        if(selectedDocument.getID() == 0) {
            handleFirstTimeSave();
        }else{
                VersionUtil.save(Integer.toString(selectedDocument.getID()), areaText.getText(), currentUser.getUserName());
            }
    }

    private void handleFirstTimeSave()  {
        FirstTimeSaveController controller = new FirstTimeSaveController(selectedDocument, usersToShare);
        try {
            GuiUtil.createWindow(UILocation.FIRST_TIME_SAVING, "Document Type", controller);

        } catch (Exception e) {
            e.printStackTrace();
            GuiUtil.createAlertWindow(Alert.AlertType.ERROR, "An error occured creating the document. Try Again Later",
                    "Cannot Save Document", "Error");
        }
    }

    public void onLoad(ActionEvent event)
    {
        System.out.println("Stub! OnLoad");
    }

    public void onClose(ActionEvent event)
    {
        System.out.println("Stub! OnClose");
    }

    public void onAbout(ActionEvent event)
    {
        System.out.println("Stub! OnAbout");
    }

    public void handleComplaint(ActionEvent event)
    {
        System.out.println("Stub! OnAbout");
    }

    @FXML
    private void initialize()
    {
        try {
            areaText.setText(VersionUtil.open(Integer.toString(selectedDocument.getID())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
