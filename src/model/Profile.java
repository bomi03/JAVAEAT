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

    // Profile 클래스의 메소드 - 프로필 정보 수정 메소드
    public void editProfile(String profileImagePath, String nickname, String admissionYear, String grade, 
                            boolean isEnrolled, String introduction, SongiType resultType, String resultImagePath) {
        this.profileImagePath = profileImagePath;
        this.nickname = nickname;
        this.admissionYear = admissionYear;
        this.grade = grade;
        this.isEnrolled = isEnrolled;
        this.introduction = introduction;
        this.resultType = resultType;
        this.resultImagePath = resultImagePath;
    }

    // Profile 클래스의 메소드 - 전공 추가 메소드 (전공명 + 유형)
    public void addMajor(String name, String type) {
        String formattedMajor = name + " (" + type + ")";
        this.majors.add(formattedMajor);
    }

    // Profile 클래스의 메소드 - 경력 추가 메소드 (경력명 + 유형형)
    public void addCareer(String name, String type) {
        String formattedCareer = name + " (" + type + ")";
        this.careers.add(formattedCareer); 
    }

    // Profile 클래스의 메소드 - 자격증 추가 메소드
    public void addCertification(String certificatename) {
        this.certificates.add(certificatename);
    }

    // Profile 클래스의 메소드 - 테스트 결과 유형 및 이미지 경로 저장 메소드 
    public void updateType(SongiType type, String imagePath) {
        this.resultType = type;
        this.resultImagePath = imagePath;
    }

    // Getter 메소드 (임의로 추가)
    // Getter 메소드 - 프로필 이미지 경로
    public String getProfileImagePath() {
        return profileImagePath;
    }

    // Getter 메소드 - 닉네임
    public String getNickname() {
        return nickname;
    }

    // Getter 메소드 - 입학년도
    public String getAdmissionYear() {
        return admissionYear;
    }

    // Getter 메소드 - 학년
    public String getGrade() {
        return grade;
    }

    // Getter 메소드 - 재학 여부
    public boolean getisEnrolled() {
        return isEnrolled;
    }

    // Getter 메소드 - 전공들
    public List<String> getMajors() {
        return majors;
    }

    // Getter 메소드 - 경력사항들
    public List<String> getCareers() {
        return careers;
    }

    // Getter 메소드 - 자격증들
    public List<String> getCertificates() {
        return certificates;
    }

    // Getter 메소드 - 한 줄 자기소개
    public String getIntroduction() {
        return introduction;
    } 

    // Getter 메소드 - 테스트 결과 유형
    public SongiType getResultType() {
        return resultType;
    }

    // Getter 메소드 - 테스트 결과 이미지 경로
    public String getResultImagePath() {
        return resultImagePath;
    }
    // 추가한 메서드 (하원)
    public int getProfileID() {

        return profileID;
    }
}