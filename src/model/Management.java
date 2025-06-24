package model;

import java.util.*;

public class Management {
    // 답변 ID(String)와 송이 타입(SongiType) 간의 매핑
    private final Map<String, SongiType> answerToQuestion;

    //하원 추가(0525)
    private User currentUser;

    //사용자 리스트 필드 선언
    private final List<User> allUsers; 

    // 생성자: 답변-타입 매핑만 받음
    public Management(Map<String, SongiType> answerToQuestion) {
        this.answerToQuestion = answerToQuestion;
        this.allUsers = new ArrayList<>();

    }

    // 사용자 답변 리스트를 기반으로 가장 많이 선택된 송이 타입을 평가
    // 동점일 경우 랜덤으로 하나 선택
    public SongiType evaluate(List<String> userAnswers) {
        Map<SongiType, Integer> counts = new HashMap<>();

        for (String key : userAnswers) {
            SongiType type = answerToQuestion.get(key);
            if (type != null) {
                counts.put(type, counts.getOrDefault(type, 0) + 1);
            }
        }

        int maxCount = counts.values().stream().max(Integer::compareTo).orElse(0);

        List<SongiType> topTypes = new ArrayList<>();
        for (Map.Entry<SongiType, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == maxCount) {
                topTypes.add(entry.getKey());
            }
        }

        if (!topTypes.isEmpty()) {
            Random random = new Random();
            return topTypes.get(random.nextInt(topTypes.size()));
        }

        return null;
    }
    // 25.05.13 추가 - test.java와의 호환
    // Management.java 안에 추가
    public SongiType evaluate(Map<Integer, String> answerMap) {
        List<String> answers = new ArrayList<>(answerMap.values());
        return evaluate(answers);
    }

    // 특정 송이 타입에 해당하는 이미지 경로를 반환 (enum 내부에서 경로 조합)
    public String getImagePath(SongiType type) {
        return type.getImagePath();
    }

    // 05.17 서연 추가 - 새 생성자 (일반 로그인/회원가입용)
    public Management() {
        this.answerToQuestion = new HashMap<>();
        this.allUsers = new ArrayList<>();
    }

     // ✅ 사용자 등록
    public void addUser(User user) {
        allUsers.add(user);
    }

    // ✅ 전체 사용자 리스트 반환
    public List<User> getAllUsers() {
        return allUsers;
    }

    // ✅ ID로 사용자 찾기
    public User getUserById(String userID) {
        for (User user : allUsers) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }

    // ✅ ProfileID로 프로필 찾기 05.21 채빈 추가
    public Profile getProfileByID(int profileID) {
    for (User user : allUsers) {
        Profile profile = user.getProfile();
        if (profile != null && profile.getProfileID() == profileID) {
            return profile;
            }
        }
        return null;
    }
    //하원 추가 ApplicantDetailPage 에서 호출
    public User getUserByProfile(Profile profile) {
        for (User u : allUsers) {
            if (u.getProfile() != null && u.getProfile().getProfileID() == profile.getProfileID()) {
                return u;
            }
        }
        return null;
    }
    //하원 추가 
    public Post getPostByID(int id){
        List<Post> allPosts = Post.getAllPosts();
        for(Post post:allPosts){
            if(post.getPostID() == id){
                return post;
            }
        }
        return null;
    }
    //하원 추가
    public User getCurrentUser() {
        return currentUser;
    }
    
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    


    //05.20 서연 추가

    // 필드 추가
    private final List<Notification> notifications = new ArrayList<>();

    // 메서드 추가
    public void addNotification(Notification n) { notifications.add(n); }
        
    public List<Notification> getNotificationsFor(String userId) {
        return notifications.stream()
        .filter(n -> n.getReceiverId().equals(userId))
        .sorted(Comparator.comparingInt(Notification::getNotificationId).reversed())
        .toList();
    }

    // ✅ 오버로드된 알림 생성 메서드 추가
    public void addNotification(String receiverId, String message, NotificationType type, String redirectUrl, int postId) {
        int newId = notifications.size() + 1;
        Notification noti = new Notification(newId, receiverId, message, type, false, redirectUrl, postId);
        notifications.add(noti);
    }
}


