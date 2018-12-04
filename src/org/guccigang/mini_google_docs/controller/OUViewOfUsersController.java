package org.guccigang.mini_google_docs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.guccigang.mini_google_docs.model.UserObject;

/**
 * This controller is for when we open another user's profile.
 */
public class OUViewOfUsersController {
    private UserObject currentUser;
    private UserObject otherUser;

    @FXML
    private Label userNameLabel;

    OUViewOfUsersController(UserObject currentUser, UserObject otherUser){
        this.currentUser = currentUser;
        this.otherUser = otherUser;
    }

}
