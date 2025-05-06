package model;

import java.util.Date;

public class Post {
    // Post 클래스의 속성
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

    public void createPost() {}
    public void editPost() {}
    public void deletePost() {}
    public void closePost() {}
}