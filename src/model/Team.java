package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Team {

    private static List<Team> allTeams = new ArrayList<>(); // 보미 수정
    private static int nextTeamId = 1;  // 보미 수정

    private List<Profile> memberProfiles;
    private int teamID;
    private int postID;
    Map<Profile, String> memberRoles;

    //추가: 채팅방 리스트 필드
    private List<ChatRoom> chatRooms = new ArrayList<>();

    public Team (int postID){
        this.teamID = nextTeamId++;  // 보미 수정 - 팀 생성 시 인자를 하나만 받기 위해서 인자 하나 없애고 ++로 수정
        this.postID = postID;
        this.memberProfiles= new ArrayList<>();
        this.memberRoles    = new HashMap<>();  // 보미 수정
    }

    // 팀 생성 시 호출 - 보미 수정
    public static void addTeam(Team t) {
        allTeams.add(t);
    }
    public static List<Team> getAllTeams() {
        return Collections.unmodifiableList(allTeams);
    }

    public void accept(Profile profile,Post post) throws IllegalAccessException {
        System.out.println("DEBUG - 현재 인원: " + post.getCurrentApplicants());
        System.out.println("DEBUG - 최대 인원: " + post.getMaxApplicants());
        System.out.println("DEBUG - 마감 여부: " + post.isClosed());

        if(post.isClosed()){

            throw new IllegalAccessException("모집이 마감 되어 팀원을 추가할 수 없습니다.");
        }
        if (!memberProfiles.contains(profile)){
            memberProfiles.add(profile);
            
            post.increaseCurrentApplicants();

             
            
        }
        post.autoClosePost();

    }

    public void reject(Profile profile, Post post) {

       
         int targetProfiledId = profile.getProfileID();
         post.removeApplicationByProfileId(targetProfiledId);
        
        System.out.println("지원이 거절되었습니다.");


    }
    public void assignRole(Profile profile,Post post) {


        int authorId = post.getProfileID();

        if(!memberProfiles.contains(profile)) return;

        if(profile.getProfileID() == authorId){
            memberRoles.put(profile, "리더");
        } else{
            memberRoles.put(profile, "팀원");
        }

    
    }

    // 추가 : 채팅방 리스트 반환
    public List<ChatRoom> getChatRooms(){
        return Collections.unmodifiableList(chatRooms);
    }

    //추가: 채팅방 추가 메서드
    public void addChatRoom(ChatRoom chatRoom){
        chatRooms.add(chatRoom);
    }

    //추가: 수락 + 채팅방 생성 통합 메서드
    public void acceptAndCreateChat(Profile profile, Post post, ChatRoomManager chatRoomManager, Management manager) throws IllegalAccessException {
        accept(profile, post); // 기존 수락 로직 활용
        Profile leader = manager.getProfileByID(post.getProfileID());

        if(leader == null){
            System.out.println("리더프로필을 찾을 수 없습니다.");
            return;
        }

        ChatRoom chatRoom = chatRoomManager.createChatRoom(leader, profile, post.getPostID());

        addChatRoom(chatRoom);
        System.out.println("채팅방이 생성되었습니다. ID: " + chatRoom.getChatRoomID());
        
        // ✅ 리더가 보낸 첫 환영 메시지 추가
        Message welcome = new Message(
            1, // 고정된 ID가 아닌 경우 ChatRoom에 generateMessageID() 메서드 추가 권장
            chatRoom.getChatRoomID(),
            leader.getUserID(),  // 반드시 리더 ID
            "지원 감사합니다! 함께 하게 되어 반가워요."
        );
        chatRoom.addMessage(welcome);



    }

    // Get 함수 - 보미 수정
    public int getTeamID() {
        return teamID;
    }

    public int getPostID() {
        return postID;
    }

    public List<Profile> getMemberProfiles() {
        return memberProfiles;
    }

    public Map<Profile, String> getMemberRoles() {
        return memberRoles;
    }

    
}