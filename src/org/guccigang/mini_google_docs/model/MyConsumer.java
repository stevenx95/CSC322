package org.guccigang.mini_google_docs.model;

import java.sql.SQLException;

@FunctionalInterface
public interface MyConsumer<T> {
    void accept(T t) throws SQLException;
}
