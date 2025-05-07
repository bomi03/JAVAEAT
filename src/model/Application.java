package model;

public class Application {
    private int applicationID;  // 지원서 고유 ID (PK)
    private int postID;         // 지원한 모집글 ID (FK)
    private int profileID;      // 지원자 프로필 ID (FK)
    private String message;     // 자기소개 메시지
    private String status;      // 지원 상태: 대기중, 수락, 거절

    public Application(int applicationID, int postID, int profileID, String message) {
        this.applicationID = applicationID;
        this.postID = postID;
        this.profileID = profileID;
        this.message = message;
        this.status = "대기중"; // 기본 상태
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

    // 상태 변화 메서드들
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