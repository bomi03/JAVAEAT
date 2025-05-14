package model;

import java.util.List;
import java.util.ArrayList;
import java.util.List;
import model.Profile;
import model.Post;
import model.Application;



public class User {
    // [1] 필드 선언
    private String username;
    private String userID;
    private String password;
    private String email;
    private Profile profile;
    private List<Post> bookmark;
    private List<Post> myPosts;
    private List<Application> myApplications;
    private boolean isLoggedIn = false;

    // [2] 생성자 
    public User(String username, String userID, String password, String email) {
        this.username = username;
        this.userID = userID;
        this.password = password;
        this.email = email;
        this.bookmark = new ArrayList<>();
        this.myPosts = new ArrayList<>();
        this.myApplications = new ArrayList<>();
    }

    //[3] 메서드
    public String login(String inputID, String inputPassword) {
        if (inputID == null || inputID.trim().isEmpty()) {
            isLoggedIn = false;
            return "아이디를 입력해주세요.";
        }
    
        if (inputPassword == null || inputPassword.trim().isEmpty()) {
            isLoggedIn = false;
            return "비밀번호를 입력해주세요.";
        }
    
        if (this.userID.equals(inputID) && this.password.equals(inputPassword)) {
            isLoggedIn = true;
            return "로그인 성공!";
        } else {
            isLoggedIn = false;
            return "아이디 또는 비밀번호가 일치하지 않습니다.";
        }
    }
    
    

        // 저장된 정보를 불러와야 할 것 같음 그 정보들이 마이페이지에 보이게끔? 
        // 로그인 성공했을 때 메인 화면으로 이동하는 코드 추후 추가
        // +) 이거 둘 다 따로 하는게 낫다고 그럼 지피티가 
    
    
    public boolean isLoggedIn() { //로그인 성공여부 다른곳에서 확인할 수 있도록
        return isLoggedIn;
    }
        

    public void logout() { // user.logout();   이렇게 사용 
        // 로그아웃
        if (isLoggedIn) {
            isLoggedIn = false;
            System.out.println("로그아웃 되었습니다.");
        } else {
            System.out.println("현재 로그인 상태가 아닙니다.");
        }

    }
        

    // 입력 받아서 회원가입하고 정보 저장
    // 회원가입시 받아와야하는 정보 
    // 이름, 아이디(중복확인 기능), 비밀번호, 비밀번호 확인, 이메일주소(도메인 @sookmyung.ac.kr 이어야함), 이메일 인증, 약관 동의 여부,  
    public String validateSignup(String username, String userID, String password, String passwordCheck,
    String email, String inputAuthCode, String actualAuthCode,
    boolean isEmailVerified, boolean isAgreed,
    List<User> allUsers) {
if (username == null || username.trim().isEmpty()) {
return "이름을 입력해주세요.";
}
if (userID == null || userID.trim().isEmpty()) {
return "아이디를 입력해주세요.";
}
if (!userID.matches("^[a-z0-9]{4,12}$")) {
return "아이디는 영문소문자/숫자, 4~12자여야 합니다.";
}
for (User u : allUsers) {
if (u.getUserID().equals(userID)) {
return "사용할 수 없는 아이디입니다.";
}
}
if (password == null || password.trim().isEmpty()) {
return "비밀번호를 입력해주세요.";
}
if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=]).{8,12}$")) {
return "비밀번호는 영문/숫자/특수문자를 포함한 8~12자리여야 합니다.";
}
if (!password.equals(passwordCheck)) {
return "비밀번호가 일치하지 않습니다.";
}
if (email == null || email.trim().isEmpty()) {
return "이메일을 입력해주세요.";
}
if (!email.endsWith("@sookmyung.ac.kr")) {
return "올바른 이메일 형식이 아닙니다.";
}
if (inputAuthCode == null || inputAuthCode.trim().isEmpty() || !inputAuthCode.equals(actualAuthCode)) {
return "인증번호가 일치하지 않습니다.";
}
if (!isAgreed) {
return "필수 약관을 모두 체크해주세요.";
}

this.username = username;
this.userID = userID;
this.password = password;
this.email = email;

return "회원가입이 완료되었습니다!";
}

    public Profile getProfile() { return profile; }
    //User 객체 안에 있는 profile 객체를 외부에서 접근할 수 있게 

    /* 사용 예시 
        User user = ...; // 로그인한 사용자 객체
        Profile p = user.getProfile();

        System.out.println("한 줄 소개: " + p.getBio());
        System.out.println("전공: " + p.getMajor());
     */


     //모집글 스크랩 하기 
     public void bookmark(Post post) {
        if (post == null) {
            System.out.println("유효하지 않은 게시글입니다.");
            return;
        }
    
        if (bookmark.contains(post)) {
            System.out.println("이미 스크랩한 게시글입니다.");
        } else {
            bookmark.add(post);
        }
    }

    /* 
        사용 예시
        Post post1 = new Post("글 제목", "내용", ...);
        user.bookmark(post1);
        user.unbookmark(post1);

    */
    // 모집글 스크랩 해제
    public void unbookmark(Post post) {
        if (bookmark.contains(post)) {
            bookmark.remove(post);
            System.out.println("스크랩이 해제되었습니다.");
        } else {
            System.out.println("스크랩한 게시글이 아닙니다.");
        }
    }
    
    //알림 수신
    public void receiveNotification(String message) {
        if (message == null || message.trim().isEmpty()) {
            System.out.println("알림 내용이 없습니다.");
            return;
        }
    
        System.out.println(message);

        //메서드만 이용하는 지금 상태에서는 울리고 사라지는 알림만 가능하고
        //알림 화면에서 확인하고, 클릭해서 이동하려면 Notification 클래스 + 리스트 저장이 꼭 필요
        //원래 팀장한테 오는 알림은 누르면 상세페이지로 이동해야하는데 
        //그러려면 알림 클래스를 새로 만들어서 저장하고 여기서 리스트로 불러와야함 
        //어떻게하는게 좋을까요? 
    }

    /*
     사용 예시
     user.receiveNotification("새로운 댓글이 있습니다.")
     user.receiveNotification("새로운 지원자가 있습니다.")
     */
    

    //회원 탈퇴
    public void withdraw() {
        this.username = null;
        this.userID = null;
        this.password = null;
        this.email = null;
        this.profile = null;
        this.bookmark.clear();
        this.myPosts.clear();
        this.myApplications.clear();

        System.out.println("탈퇴가 완료되었습니다.");
    }

    //사용자가 스크랩한 글 불러오기
    public List<Post> getBookmarkedPosts() { 
        return bookmark; }

    //사용자가 작성한 모집글 불러오기 
    public List<Post> getMyPosts() { 
        return myPosts; }

    //사용자가 지원한 글 불러오기
    public List<Application> getMyApplications() { 
        return myApplications; }

        public String getUsername() {
            return username;
        }
        
        public String getUserID() {
            return userID;
        }
        
        public String getEmail() {
            return email;
        }
        

    //모집글에 지원하기 
    //Application 클래스의 값 받아와서 리스트에 추가 후 사용
    public void apply(Application application) {
        myApplications.add(application);
    }
}
