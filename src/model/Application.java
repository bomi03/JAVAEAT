package model;

public class Application {

    private Profile profile;
    private Post post;

    private int applicationID;
    private int postID;      
    private int profileID;    
    private String message;    
    private String status;      
    
    //생성자 수정 (하원)
    public Application(int applicationID, Post post, Profile profile, String message) {
        this.applicationID = applicationID;
        this.post = post;
        this.profile = profile;
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
    //더미포스트 생성을 위해 추가함(하원)
    public Post getPost(){
        return this.post;
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
    public Team accept() {
        this.status = "수락";

        //새로 추가한 코드
        int postID = post.getPostID();
        Team foundTeam = Team.findOrCreateTeam(postID);
        
        try{
            foundTeam.accept(profile, post);
        }catch(Exception e){
            System.out.println("팀원 수락 실패:" + e.getMessage());
        }

        return foundTeam;

    }

    public void reject() {
        this.status = "거절";
    }

    public void reset() {
        this.status = "대기중";
    }
}