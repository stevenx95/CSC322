package org.guccigang.mini_google_docs.controller.DocumentControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import javafx.event.ActionEvent;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.UserObject;
import org.guccigang.mini_google_docs.model.VersionUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class SuperAndOriginalTextEditorController implements Initializable {

    @FXML
    TextArea editingArea;

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
        this.editingArea.setText(selectedDocument.getContent());
    }
    public void onSave(ActionEvent event)
    {
        try{
            VersionUtil.save(Integer.toString(selectedDocument.getID()),areaText.getText(),currentUser.getUserName());
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
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
}
