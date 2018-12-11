package org.guccigang.mini_google_docs.controller.DocumentControllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import org.guccigang.mini_google_docs.model.DocRestriction;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.GuiUtil;

import java.util.List;
import java.util.Arrays;

public class FirstTimeSaveController {

    private DocumentFile document;
    private List<String> sharingUsers;

    @FXML
    TextField userListInput;

    public FirstTimeSaveController() {
        this(null, null);
    }

    public FirstTimeSaveController(DocumentFile document, List<String> sharingUsers) {
        this.document = document;
        this.sharingUsers = sharingUsers;
    }

    @FXML
    public void handlePublicOption (ActionEvent event) {
        this.document.setRestricted(DocRestriction.PUBLIC);
        GuiUtil.closeWindow(event);
    }

    @FXML
    public void handleRestrictedOption(ActionEvent event) {
        this.document.setRestricted(DocRestriction.RESTRICTED);
        GuiUtil.closeWindow(event);
    }

    @FXML
    public void handlePrivateOption(ActionEvent event) {
        this.document.setRestricted(DocRestriction.PRIVATE);
        GuiUtil.closeWindow(event);
    }

    @FXML
    public void handleSharing(ActionEvent event) {
        this.document.setRestricted(DocRestriction.SHARED);
        List<String> userList = Arrays.asList(userListInput.getText().split(","));
        for (int i = 0; i < userList.size(); i++) {
            sharingUsers.set(i, userList.get(i));
        }
        GuiUtil.closeWindow(event);
    }
}
