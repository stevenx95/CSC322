package org.guccigang.mini_google_docs.model;

import java.util.ArrayList;

public enum DocRestriction {
    PRIVATE(0,"private"), SHARED(1,"shared"), PUBLIC(2,"public"), RESTRICTED(3,"restricted");

    public final int id;
    public final String string;

    DocRestriction(int id, String string) {
        this.id = id;
        this.string = string;
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

    public static DocRestriction getDocRestriction(String string) {
        DocRestriction restriction;
        switch (string) {
            case "private":
                restriction = PRIVATE;
                break;
            case "shared":
                restriction = SHARED;
                break;
            case "public":
                restriction = PUBLIC;
                break;
            case "restricted":
                restriction = RESTRICTED;
                break;
            default:
                restriction = null;
                break;
        }
        return restriction;
    }

    public static ArrayList<String> stringValues() {
        DocRestriction[] values = values();
        ArrayList<String> stringVals = new ArrayList<>(4);
        for (DocRestriction value : values) {
            stringVals.add(value.string);
        }
        return stringVals;
    }

}
