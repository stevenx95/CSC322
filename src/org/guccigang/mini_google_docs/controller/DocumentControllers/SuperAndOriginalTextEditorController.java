package org.guccigang.mini_google_docs.controller.DocumentControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import org.guccigang.mini_google_docs.model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SuperAndOriginalTextEditorController implements Initializable {

    @FXML
    private TextArea areaText;

    private UserObject currentUser;
    private DocumentFile selectedDocument;

    public SuperAndOriginalTextEditorController() {
        this(null,null);
    }

    public SuperAndOriginalTextEditorController(UserObject currentUser, DocumentFile selectedDocument) {
        this.currentUser = currentUser;
        this.selectedDocument = selectedDocument;
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
        VersionUtil.save(selectedDocument.getID(),areaText.getText(),currentUser.getUserName());
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
