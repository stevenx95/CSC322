package org.guccigang.mini_google_docs.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class TabooWord {

    private StringProperty tabooText;


    public TabooWord() {
        tabooText = new SimpleStringProperty("");
    }




    public TabooWord(StringProperty tabooText) {
        this.tabooText = tabooText;
    }

    public String getTabooText() {
        return tabooText.get();
    }

    public StringProperty tabooTextProperty() {
        return tabooText;
    }

    public void setTabooText(String tabooText) {
        this.tabooText.set(tabooText);
    }

    public ObservableValue<String> tabooWordProperty() {
        return tabooText;
    }
}
