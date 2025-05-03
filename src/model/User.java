package model;

import java.util.List;

public class User {
    private String username;
    private String userID;
    private String password;
    private String email;
    private Profile profile;
    private List<Post> bookmark;
    private List<Post> myPosts;
    private List<Application> myApplications;

    public void login() {}
    public void logout() {}
    public void signup() {}
    public Profile getProfile() { return profile; }
    public void bookmark() {}
    public void receiveNotification() {}
    public void withdraw() {}
    public List<Post> getBookmarkedPosts() { return bookmark; }
    public List<Post> getMyPosts() { return myPosts; }
    public List<Application> getMyApplications() { return myApplications; }
    public void apply() {}
}
