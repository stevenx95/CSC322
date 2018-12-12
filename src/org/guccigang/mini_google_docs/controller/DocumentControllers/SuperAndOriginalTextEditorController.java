package org.guccigang.mini_google_docs.controller.DocumentControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import org.guccigang.mini_google_docs.controller.ComplaintControllers.OUUserComplaintFormController;
import org.guccigang.mini_google_docs.controller.ComplaintControllers.VisitorComplaintFormController;
import org.guccigang.mini_google_docs.model.*;

import java.net.URL;
import java.util.ArrayList;
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
        try {
            String documentContent = VersionUtil.open(Integer.toString(selectedDocument.getID()));

            if(TabooUtil.containTabooAndUNK(documentContent)){
                selectedDocument.setContent(TabooUtil.censorTabooWords(documentContent));
                TabooUtil.flagDocument(selectedDocument.getOwner(), selectedDocument.getID());
                VersionUtil.save(selectedDocument.getID(),selectedDocument.getContent(),currentUser.getUserName());
                GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "Document contains taboo words. Document has been flaged. Next time the owner logs in he/she must review all flagged documents." ,
                        "Document contains taboo words", "Taboo Warning");
            }

            if (!DocumentDAO.canWrite(selectedDocument,currentUser)) {
                this.areaText.setEditable(false);
                GuiUtil.createAlertWindow(Alert.AlertType.WARNING, "While locked, this document is in View-Only mode" ,
                        "Document is locked", "Warning");
            }
            areaText.setText(documentContent);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //DocumentDAO.lockDocument(selectedDocument.getID());//locks document once it's opened
    }
    public void onSave(ActionEvent event)
    {
        //If text field contains taboo words
        if(!DocumentDAO.canWrite(selectedDocument,currentUser))
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
            GuiUtil.createAlertWindow(Alert.AlertType.INFORMATION,"Thank You!","Taboo Detection","Document contains no taboo words");
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

    public void handleChangeDocType() {
        ArrayList<String> options = DocRestriction.stringValues();
        options.removeIf(item -> item.equals("shared"));
        options.add(0,null);
        String selectedOption = GuiUtil.createCancellableOptionAlert(
                options,
                "Select the document type: ",
                "Change document type",
                "Change document type"
        );
        if(selectedOption != null) {
            DocRestriction restriction = DocRestriction.getDocRestriction(selectedOption);
            String query = "UPDATE documents SET restricted=? WHERE docID=?";
            DbUtil.executeUpdateDB(query, statement -> {
                statement.setInt(1, restriction.id);
                statement.setInt(2, selectedDocument.getID());
            });
            GuiUtil.createAlertWindow(Alert.AlertType.CONFIRMATION,
                    "",
                    String.format("Document type has been changed to %s: ", restriction.string),
                    "Confirmation");
        }
    }

    public void handleShareOfDoc() {
        ArrayList<String> userList = SharingUtil.getAllUsers(currentUser.getUserName());
        userList.add(0, null);
        String selectedUser = GuiUtil.createCancellableOptionAlert(
                userList,
                "Share this document with: ",
                "Share Document",
                "Share document"
        );
        if (selectedUser != null) {
            SharingUtil.processInvitation(selectedDocument.getID(), currentUser.getUserName());
            UserObject selectedUserObject = UsersDAO.getSearchedResult(selectedUser, currentUser.getUserName()).get(0);
            GuiUtil.createAlertWindow(Alert.AlertType.CONFIRMATION,
                    "This user can make updates to this document once he accepts invitation",
                    String.format("Invitation sent to %s %s", selectedUserObject.getFirstName(), selectedUserObject.getLastName()),
                    "Confirmation");
        }
    }
}
