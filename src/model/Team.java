package model;

import java.util.List;

public class Team {
    private int teamID;
    private int postID;
    private List<Profile> memberProfiles;

    public void accept(Profile profile) {}
    public void reject(Profile profile) {}
    public void assignRole(Profile profile, String role) {}
}