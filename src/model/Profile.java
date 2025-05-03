package model;

import java.util.List;

public class Profile {
    private int profileID;
    private String userID;
    private String profileImagePath;
    private String nickname;
    private String admissionYear;
    private String grade;
    private boolean isEnrolled;
    private List<String> majors;
    private List<String> careers;
    private List<String> certificates;
    private String introduction;
    private SongiType resultType;
    private String resultImagePath;

    public void editProfile() {}
    public void addMajor(String major) {}
    public void addCareer(String career) {}
    public void addCertification(String cert) {}
    public void updateType(SongiType type) {
        this.resultType = type;
    }
}