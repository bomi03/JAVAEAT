package model;

public class Application {
    private int applicationID;
    private int postID;
    private int profileID;
    private String message;
    private String status;

    public String getStatus() {
        return status;
    }
    //추가한 메서드드(하원)
    public int getProfileID() {
        
        return profileID;
    }
}