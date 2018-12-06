package org.guccigang.mini_google_docs.controller.DocumentControllers;

import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.UserObject;

public class SuperAndOriginalTextEditorController {

    private UserObject currentUser;
    private DocumentFile selectedDocument;

    public SuperAndOriginalTextEditorController() {
        this(null, null);
    }

    public SuperAndOriginalTextEditorController(UserObject currentUser, DocumentFile selectedDocument) {
        this.currentUser = currentUser;
        this.selectedDocument = selectedDocument;
    }
}
