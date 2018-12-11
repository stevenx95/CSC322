package org.guccigang.mini_google_docs.controller.UserUI;

import javafx.event.ActionEvent;
import org.guccigang.mini_google_docs.UILocation;
import org.guccigang.mini_google_docs.controller.ComplaintControllers.SUComplaintTypeSelectionMenuController;
import org.guccigang.mini_google_docs.controller.ComplaintControllers.SUDocumentComplaintController;
import org.guccigang.mini_google_docs.controller.UsersManager.SUOUUsersManagerController;
import org.guccigang.mini_google_docs.controller.DocumentControllers.SuperAndOriginalDocManagerController;
import org.guccigang.mini_google_docs.controller.LoginAndApplicationControllers.UserAppManagerController;
import org.guccigang.mini_google_docs.model.GuiUtil;
import org.guccigang.mini_google_docs.model.UserObject;

import java.io.IOException;

public class SuperUserUIController {

    private UserObject currentUser;

    public SuperUserUIController() {
        this(null);
    }

    public SuperUserUIController(UserObject currentUser) {
        this.currentUser = currentUser;
    }

    public void signOutButton(ActionEvent event){
        try {

            GuiUtil.createWindowAndDestroy(event, UILocation.LOGIN, "Gucci Gang");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleTabooList(ActionEvent event){
        try{
            GuiUtil.createWindow(event, UILocation.TABOO_SU_LIST_MANAGER,"Manage Taboo List");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void handleDocumentManager(ActionEvent event) {
        try {
            SuperAndOriginalDocManagerController controller = new SuperAndOriginalDocManagerController(currentUser);
            GuiUtil.changeScene(event, UILocation.SUPER_AND_ORIGINAL_DOC_MANAGER, "Document Manager",controller);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleFindUsers(ActionEvent event){
        try{
            SUOUUsersManagerController controller = new SUOUUsersManagerController(currentUser);
            GuiUtil.changeScene(event, UILocation.SEARCH_USERS_WINDOW,"Search Users",controller);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleApplications(ActionEvent event) {
        try {
            UserAppManagerController controller = new UserAppManagerController(currentUser);
            GuiUtil.changeScene(event, UILocation.USER_APP_MANAGER, "Applications", controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openComplaintManager(ActionEvent event) {
        try {
            SUDocumentComplaintController controller = new SUDocumentComplaintController(currentUser);
            GuiUtil.changeScene(event, UILocation.SUPER_USER_DOCUMENT_COMPLAINT_VIEW, "Complaints", controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
