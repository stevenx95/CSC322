package org.guccigang.mini_google_docs.controller.DocumentControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import org.guccigang.mini_google_docs.UILocation;
import org.guccigang.mini_google_docs.controller.ComplaintControllers.OUUserComplaintFormController;
import org.guccigang.mini_google_docs.controller.ComplaintControllers.VisitorComplaintFormController;
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
        if(TabooUtil.containTabooAndUNK(selectedDocument.getContent())){
            selectedDocument.setContent(TabooUtil.censorTabooWords(selectedDocument.getContent()));
            TabooUtil.flagDocument(selectedDocument.getOwner(), selectedDocument.getID());
            VersionUtil.save(selectedDocument.getID(),selectedDocument.getContent(),currentUser.getUserName());
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Document contains taboo words. Document has been flaged. Next time the owner logs in he/she must review all flagged documents." ,
                    "Document contains taboo words", "Taboo Warning");
        }
        this.areaText.setText(selectedDocument.getContent());
        if (!DocumentDAO.canWrite(selectedDocument,currentUser.getUserName())) {
            this.areaText.setEditable(false);
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "While locked, this document is in View-Only mode" ,
                    "Document is locked", "Warning");
        }
        //DocumentDAO.lockDocument(selectedDocument.getID());//locks document once it's opened
    }
    public void onSave(ActionEvent event)
    {
        //If text field contains taboo words
        if(!DocumentDAO.canWrite(selectedDocument,currentUser.getUserName()))
        {
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "You do not have write permission for this document!",
                    "No Permission", "No Permission");
        }
        if(TabooUtil.containTabooAndUNK(areaText.getText())){
            areaText.setText(TabooUtil.censorTabooWords(areaText.getText()));
            TabooUtil.flagDocument(selectedDocument.getOwner(), selectedDocument.getID());
            VersionUtil.save(selectedDocument.getID(),areaText.getText(),currentUser.getUserName());
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Document contains taboo words. Document has been flaged. Next time the owner logs in he/she must review all flagged documents." ,
                    "Document contains taboo words", "Taboo Warning");
        }
        //if document was previously flagged and the user changes it. it will not be unflagged.
        else if(!TabooUtil.containTabooAndUNK(areaText.getText()) && TabooUtil.isDocumentFlagged(selectedDocument.getOwner(),selectedDocument.getID())){
            TabooUtil.unFlagDocument(selectedDocument.getOwner(), selectedDocument.getID());
            VersionUtil.save(selectedDocument.getID(),areaText.getText(),currentUser.getUserName());
            GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Document contains taboo words. Document has been flaged. Next time the owner logs in he/she must review all flagged documents." ,
                    "Document contains taboo words", "Taboo Warning");
        }else {
            VersionUtil.save(selectedDocument.getID(),areaText.getText(),currentUser.getUserName());
        }
    }

    public void onLoad(ActionEvent event)
    {
        System.out.println("Stub! OnLoad");
    }

    public void onClose(ActionEvent event)
    {
        //DocumentDAO.unlockDocument(selectedDocument.getID());
        System.out.println("Stub! OnClose");
    }

    public void onAbout(ActionEvent event)
    {
        System.out.println("Stub! OnAbout");
    }

    public void handleComplaint(ActionEvent event)
    {
        try {
            VisitorComplaintFormController controller = new VisitorComplaintFormController(selectedDocument);
            GuiUtil.createWindow(UILocation.VISITOR_COMPLAINT_FORM_VIEW, "Complaint Form", controller);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleUserComplaint(ActionEvent event){

        try {
            OUUserComplaintFormController controller = new OUUserComplaintFormController(selectedDocument);
            GuiUtil.createWindow(UILocation.ORIGINAL_USER_DOCUMENT_COMPLAINT_VIEW, "Complaint Form", controller);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleVersion(ActionEvent event)
    {
        String version = GuiUtil.createOptionAlert(VersionUtil.getVersionList(selectedDocument.getID()),
                "Please select a version to load",
                "Version",
                "Version Selection");
        try {
            areaText.setText(VersionUtil.openVersion(selectedDocument.getID(),Integer.parseInt(version)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
