package model;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    // Profile 클래스의 속성
    private int profileID;  // 프로필 고유 ID
    private String userID;  // 프로필을 가진 사용자
    private String profileImagePath;  // 프로필 이미지 경로
    private String nickname;  // 닉네임
    private String admissionYear;  // 입학년도(드롭다운)
    private String grade;  // 학년(드롭다운)
    private boolean isEnrolled;  // 재학 여부(재학/휴학)
    private List<String> majors;  // 전공들
    private List<String> careers;  // 경력사항들
    private List<String> certificates;  // 자격증들
    private String introduction;  // 한 줄 자기소개
    private SongiType resultType;  // 테스트 결과 송이 유형
    private String resultImagePath;  // 테스트 결과 이미지

    // Profile 클래스의 생성자
    public Profile(int profileID, String userID) {
        this.profileID = profileID;
        this.userID = userID;
        this.majors = new ArrayList<>();  // 내부에서 new ArrayList()로 초기화하므로 생성자의 매개변수로 받지 않음(빈 상태로 시작하여 addMajor() 메서드로 하나씩 추가하는 구조)
        this.careers = new ArrayList<>();  // 내부에서 new ArrayList()로 초기화하므로 생성자의 매개변수로 받지 않음(빈 상태로 시작하여 addCareer() 메서드로 하나씩 추가하는 구조)
        this.certificates = new ArrayList<>();  // 내부에서 new ArrayList()로 초기화하므로 생성자의 매개변수로 받지 않음(빈 상태로 시작하여 addCertification() 메서드로 하나씩 추가하는 구조)
    }

    public void editProfile() {}
    public void addMajor(String major) {}
    public void addCareer(String career) {}
    public void addCertification(String cert) {}
    public void updateType(SongiType type) {
        this.resultType = type;
    }
}