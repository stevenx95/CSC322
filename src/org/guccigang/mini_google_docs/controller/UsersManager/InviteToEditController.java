package org.guccigang.mini_google_docs.controller.UsersManager;

import org.guccigang.mini_google_docs.model.UserObject;

public class InviteToEditController {

    private UserObject inviterUser;
    private UserObject inviteeUser;


    public InviteToEditController() {
        this(null,null);
    }

    public InviteToEditController(UserObject inviterUser, UserObject inviteeUser) {
        this.inviterUser = inviterUser;
        this.inviteeUser = inviteeUser;
    }

}
