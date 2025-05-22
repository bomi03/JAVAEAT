package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Post {
    // Post 클래스의 속성
    private static List<Post> allPosts = new ArrayList<>();  // 모집글 리스트 (마이페이지-작성한 글에 사용하기 위해서)
    private int postID;  // 모집글 고유 ID
    private int profileID;  // 작성자의 프로필 ID
    private String postImagePath;  // 모집글 이미지 경로 
    private String category;  // 카테고리(드롭다운)
    private String title;  // 모집글 제목
    private String status;  // 모집 상태
    private Date recruitDeadline;  // 모집 기간(마감일)
    private int maxApplicants;  // 최대 모집 인원
    private int currentApplicants;  // 모집된 인원
    private String description;  // 팀 설명
    
    //새로 만든 필드
    private List <Application> applications = new ArrayList<>();

    public List<Application> getApplications(){
        return applications;
    }

    public void addApplication(Application application){
        applications.add(application);
    }
    public void removeApplicationByProfileId(int profileID){
        applications.removeIf(app -> app.getProfileID() == profileID);
    }

    // Post 클래스의 생성자
    public Post(int postID, int profileID, String postImagePath, String category, String title, String status, 
                Date recruitDeadline, int maxApplicants, int currentApplicants, String description) {
        this.postID = postID;
        this.profileID = profileID;
        this.postImagePath = postImagePath;
        this.category = category;
        this.title = title;
        this.status = status;
        this.recruitDeadline = recruitDeadline;
        this.maxApplicants = maxApplicants;
        this.currentApplicants = currentApplicants;
        this.description = description;
    }

    // Post 클래스의 메소드 - 모집글 수정 메소드
    public void editPost(String postImagePath, String category, String title, String status, 
                           Date recruitDeadline, int maxApplicants, int currentApplicants, String description) {
        if(!this.status.equals("삭제됨")) {
            this.postImagePath = postImagePath;
            this.category = category;
            this.title = title;
            this.status = status;
            this.recruitDeadline = recruitDeadline;
            this.maxApplicants = maxApplicants;
            this.currentApplicants = currentApplicants;
            this.description = description;
        }
    }

    // Post 클래스의 메소드 - 모집글 삭제 메소드
    public void deletePost() {
        this.status = "삭제됨";
    }

    // Post 클래스의 메소드 - 모집 마감 메소드
    public void closePost() {
        if(!this.status.equals("삭제됨")) {
            this.status = "모집완료";
        }
    }

    //Post 클래스의 메소드 - 마감일이 지났는지 확인하는 메소드 (임의로 추가)
    public boolean isRecruitDeadlinePassed() {
        // 마감일이 설정되지 않은 경우(=null)이면 false 반환
        if (recruitDeadline == null) {
            return false;
        }
        Date today = new Date();
        return today.after(recruitDeadline);
    }

    //post 클래스의 메소드 - 모집이 마감됬는지 확인하는 메소드(임의로 추가(하원))
    public boolean isClosed(){

        return "모집완료".equals(this.status);
    }

    // Post 클래스의 메소드 - 마감일이 지났으면 자동으로 상태 변경 (임의로 추가)
    public void autoClosePost() {
        if(this.isRecruitDeadlinePassed() && this.status.equals("모집중")) {
            this.status = "모집완료";
        }
    }

    // Post 클래스의 메소드 - 모집글 리스트에 모집글 추가
    public static void addPost(Post p) {
        allPosts.add(p);
    }

    // Getter 메소드 - 모집글 리스트
    public static List<Post> getAllPosts() {
        return Collections.unmodifiableList(allPosts);
    }

    // Getter 메소드 - 모집글 이미지 경로
    public String getPostImagePath() {
        return postImagePath;
    }

    // Getter 메소드 - 카테고리
    public String getCategory() {
        return category;
    }

    // Getter 메소드 - 모집글 제목
    public String getTitle() {
        return title;
    }

    // Getter 메소드 - 모집 상태
    public String getStatus() {
        return status;
    }

    // Getter 메소드 - 모집 기간(마감일)
    public Date getRecruitDeadline() {
        return recruitDeadline;
    }

    // Getter 메소드 - 최대 모집 인원
    public int getMaxApplicants() {
        return maxApplicants;
    }

    // Getter 메소드 - 모집된 인원
    public int getCurrentApplicants() {
        return currentApplicants;
    }

    // Getter 메소드 - 팀 설명
    public String getDescription() {
        return description;
    }
    public int getProfileID() {
        
        return profileID;
    }
    
    public int getPostID() {
        
        return postID;
    }
    public void increaseCurrentApplicants() {
        this.currentApplicants++;
    }
    

}