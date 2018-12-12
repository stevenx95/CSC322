package org.guccigang.mini_google_docs.controller.TabooControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.guccigang.mini_google_docs.model.GuiUtil;
import org.guccigang.mini_google_docs.model.UILocation;
import org.guccigang.mini_google_docs.model.UserObject;


import java.io.IOException;

public class SuperUserTabooMenuSelectorController {

    private UserObject currentUser;
    public SuperUserTabooMenuSelectorController() {
        this(null);
    }

    public SuperUserTabooMenuSelectorController(UserObject currentUser){
        this.currentUser = currentUser;
    }



    @FXML
    public void openApprovedList(ActionEvent event){

        try{

            System.out.println(currentUser.getUserName());
            SUViewTabooListController controller = new SUViewTabooListController(currentUser);
            GuiUtil.changeScene(event,UILocation.APPROVED_TABOO_SU_LIST_MANAGER, "Document Manager",controller);

        }catch (IOException e) {
            e.printStackTrace();
        }

    }










    @FXML
    public void openApprovalManager(ActionEvent event){
        try{
            SUApprovalTabooWordController controller = new SUApprovalTabooWordController(currentUser);
            GuiUtil.changeScene(event,UILocation.TABOO_SU_LIST_MANAGER, "Document Manager",controller);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }




}
