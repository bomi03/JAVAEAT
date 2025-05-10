package model;

public enum SongiType {
    샘송이("샘송이.png"),
    저요송이("저요송이.png"),
    똑송이("똑송이.png"),
    논리송이("논리송이.png"),
    꾸꾸송이("꾸꾸송이.png"),
    침착송이("침착송이.png"),
    평화송이("평화송이.png"),
    피티송이("피티송이.png");

    private final String fileName;

    SongiType(String fileName) {
        this.fileName = fileName;
    }

    // 전체 이미지 경로 반환
    public String getImagePath() {
        return "/model/assets/management/" + fileName;
    }
}
