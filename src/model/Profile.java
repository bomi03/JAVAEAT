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
    public boolean editProfile(String profileImagePath, String nickname, String admissionYear, 
                            String grade, boolean isEnrolled, String introduction, 
                            SongiType resultType, String resultImagePath) {
        if (nickname == null || nickname.isEmpty() || admissionYear == null || admissionYear.isEmpty() 
            || grade == null || grade.isEmpty() || majors.isEmpty() || introduction == null || introduction.isEmpty()) {
            return false; // 필수 항목이 비어있으면 false
        }
        this.profileImagePath = profileImagePath;
        this.nickname = nickname;
        this.admissionYear = admissionYear;
        this.grade = grade;
        this.isEnrolled = isEnrolled;
        this.introduction = introduction;
        this.resultType = resultType;
        this.resultImagePath = resultImagePath;
        return true; // 성공적으로 수정된 경우 true
    }

    // Profile 클래스의 메소드 - 전공 추가 메소드 (전공명 + 유형)
    public boolean addMajor(String name, String type) {
        String formattedMajor = name + " (" + type + ")";
        if (majors.contains(formattedMajor)) {
            return false; // 이미 존재하는 전공이면 false
        }
        this.majors.add(formattedMajor);
        return true;
    }

    // Profile 클래스의 메소드 - 경력 추가 메소드 (경력명 + 유형형)
    public boolean addCareer(String name, String type) {
        String formattedCareer = name + " (" + type + ")";
        if (careers.contains(formattedCareer)) {
            return false; // 이미 존재하는 경력사항이면 false
        }
        this.careers.add(formattedCareer); 
        return true;
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

    // Set 메소드
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAdmissionYear(String admissionYear) {
        this.admissionYear = admissionYear;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setEnrolled(boolean isEnrolled) {
        this.isEnrolled = isEnrolled;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

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

    // Getter 메소드 - 테스트 결과 이미지 경로
    public String getUserID() {
        return userID;
    }

    // 추가한 메서드 (하원)
    public int getProfileID() {
        return profileID;
    }

    // 프로필 유효성 검증 메소드
    public boolean isValidProfile() {
        if (nickname == null || nickname.isEmpty()) {
            return false;  // 닉네임이 비어 있으면 false
        }
        if (admissionYear == null || admissionYear.isEmpty()) {
            return false;  // 입학년도 비어 있으면 false
        }
        if (grade == null || grade.isEmpty()) {
            return false;  // 학년 비어 있으면 false
        }
        if (majors.isEmpty()) {
            return false;  // 전공이 비어 있으면 false
        }
        if (introduction == null || introduction.isEmpty()) {
            return false;  // 자기소개 비어 있으면 false
        }
        return true;
    }
}