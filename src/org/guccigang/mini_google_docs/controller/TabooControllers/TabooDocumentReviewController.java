package org.guccigang.mini_google_docs.controller.TabooControllers;

import org.guccigang.mini_google_docs.model.UserObject;

public class TabooDocumentReviewController {
    private UserObject currentUser;

    TabooDocumentReviewController(){
        this.currentUser = null;
    }

    public TabooDocumentReviewController(UserObject currentUser){
        this.currentUser = currentUser;
    }
}
