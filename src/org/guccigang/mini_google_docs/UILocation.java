package org.guccigang.mini_google_docs;

public enum UILocation {

    SUPER_AND_ORIGINAL_DOC_MANAGER("views/DocumentUI/SuperAndOriginalDocManager.fxml"),
    SUPER_AND_ORIGINAL_TEXT_EDITOR("views/DocumentUI/SuperAndOriginalTextEditor.fxml"),
    VISITOR_TEXT_EDITOR("views/DocumentUI/VisitorTextEditor.fxml"),
    VISITOR_VIEW_DOCUMENTS("views/DocumentUI/VisitorViewDocuments.fxml"),
    LOGIN("views/LoginAndApplicationUI/login.fxml"),
    SIGNUP("views/LoginAndApplicationUI/signup.fxml"),
    TABOO_SU_LIST_MANAGER("views/LoginAndApplicationUI/login.fxml"),
    TABOO_SU_LIST_VIEW("views/TabooUI/tabooSUListView.fxml"),
    USER_APP_MANAGER("views/LoginAndApplicationUI/UserAppManager.fxml"),
    ORIGINAL_AND_VISITOR_REPORT_TABOO_WORD("views/TabooUI/OriginalAndVisitorReportTabooWord.fxml"),
    OU_VIEW_OF_USERS_WINDOW("views/UserManagerUI/OUViewOfUsersWindow.fxml"),
    SEARCH_USERS_WINDOW("views/UserManagerUI/SearchUsersWindow.fxml"),
    SU_VIEW_OF_USERS_WINDOW("views/UserManagerUI/SUViewOfUsersWindow.fxml"),
    ORIGINAL_USER_UI("views/UserUI/originalUserUI.fxml"),
    SUPER_USER_UI("views/UserUI/superUserUI.fxml"),
    VISITOR_UI("views/UserUI/visitorUI.fxml"),
    VISITOR_COMPLAINT_FORM_VIEW("views/VisitorComplaintFormView.fxml"),
    TABOO_DOCUMENT_REVIEW_UI("views/TabooUI/TabooDocumentReview.fxml");


    public final String directory;
    UILocation(String location) {
        this.directory = location;
    }
}
