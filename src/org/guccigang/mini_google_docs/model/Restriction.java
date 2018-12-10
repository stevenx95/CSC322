package org.guccigang.mini_google_docs.model;

public enum Restriction {
    PRIVATE(0), SHARED(1), PUBLIC(2), RESTRICTED(3);

    public final int id;

    Restriction(int id) {
        this.id = id;
    }
}
