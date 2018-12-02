package org.guccigang.mini_google_docs.model;

public class UserObject {

    final private String userName;
    final private String password;
    final private String firstName;
    final private String lastName;
    private String[] interests;

    public UserObject(String userName, String password, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String[] getInterests() {
        return interests;
    }
    public void addInterests(String... interests) {
        this.interests = interests;
    }
}
