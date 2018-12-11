package org.guccigang.mini_google_docs.model;

public enum DocRestriction {
    PRIVATE(0), SHARED(1), PUBLIC(2), RESTRICTED(3);

    public final int id;

    DocRestriction(int id) {
        this.id = id;
    }

    public static DocRestriction getDocRestriction(int id) {
        DocRestriction restriction;
        switch (id) {
            case 0:
                restriction = PRIVATE;
                break;
            case 1:
                restriction = SHARED;
                break;
            case 2:
                restriction = PUBLIC;
                break;
            case 3:
                restriction = RESTRICTED;
                break;
            default:
                restriction = null;
                break;
        }
        return restriction;
    }

}
