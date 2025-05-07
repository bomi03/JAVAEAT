package model;

public class Application {
    private int applicationID;
    private int postID;      
    private int profileID;    
    private String message;    
    private String status;      

    public Application(int applicationID, int postID, int profileID, String message) {
        this.applicationID = applicationID;
        this.postID = postID;
        this.profileID = profileID;
        this.message = message;
        this.status = "대기중"; // 기본상태
    }

    // getter
    public int getApplicationID() {
        return applicationID;
    }

    public int getPostID() {
        return postID;
    }

    public int getProfileID() {
        return profileID;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    // setter
    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // 상태 변화 
    public void accept() {
        this.status = "수락";
    }

    public void reject() {
        this.status = "거절";
    }

    public void reset() {
        this.status = "대기중";
    }
}