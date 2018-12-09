package org.guccigang.mini_google_docs.controller.DocumentControllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.UserObject;
import org.guccigang.mini_google_docs.model.VersionUtil;

public class SuperAndOriginalTextEditorController {

    private UserObject currentUser;
    private DocumentFile selectedDocument;
    private TextArea areaText;

    public SuperAndOriginalTextEditorController() {
        this(null, null);
    }

    public SuperAndOriginalTextEditorController(UserObject currentUser, DocumentFile selectedDocument) {
        this.currentUser = currentUser;
        this.selectedDocument = selectedDocument;

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
