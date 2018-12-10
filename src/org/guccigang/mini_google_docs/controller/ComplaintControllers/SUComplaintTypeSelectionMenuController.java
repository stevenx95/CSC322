package org.guccigang.mini_google_docs.controller.ComplaintControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.guccigang.mini_google_docs.UILocation;
import org.guccigang.mini_google_docs.controller.LoginAndApplicationControllers.UserAppManagerController;
import org.guccigang.mini_google_docs.model.GuiUtil;
import org.guccigang.mini_google_docs.model.UserObject;


public class SUComplaintTypeSelectionMenuController {
    private UserObject currentUser;

    public SUComplaintTypeSelectionMenuController(UserObject currentUser){
        this.currentUser = currentUser;
    }

    @FXML
    public void openDocumentComplaints(ActionEvent event){
        try
        {
            SUDocumentComplaintController controller = new SUDocumentComplaintController(currentUser);
            GuiUtil.changeScene(event, UILocation.SUPER_USER_DOCUMENT_COMPLAINT_VIEW,"Document Complaint", controller);
        }catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void openUserComplaints(ActionEvent event){

    }



}
