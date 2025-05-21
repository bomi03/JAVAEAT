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
    private Map<Profile, String> memberRoles;

    // 추가 
    private ChatRoom chatRoom;



    public Team (int postID){
        this.teamID = nextTeamId++;  // 보미 수정 - 팀 생성 시 인자를 하나만 받기 위해서 인자 하나 없애고 ++로 수정
        this.postID = postID;
        this.memberProfiles= new ArrayList<>();
        this.memberRoles    = new HashMap<>();  // 보미 수정
    }

    // 팀 생성 시 호출 - 보미 수정
    private static void addTeam(Team t) {
        allTeams.add(t);
    }
    public static List<Team> getAllTeams() {
        return Collections.unmodifiableList(allTeams);
    }

    // 수락 -> 채팅룸 생성 까지 수정 (하원)
    public void accept(Profile profile,Post post) throws IllegalAccessException {
        post.autoClosePost(); // 모집 마감 체크 - 보미 수정
        if(post.isClosed()){

            throw new IllegalAccessException("모집이 마감 되어 팀원을 추가할 수 없습니다.");
        }
        if (!memberProfiles.contains(profile)){
            memberProfiles.add(profile);
            // 수락 후 채팅방 생성
            if(this.chatRoom == null){
                this.chatRoom = new ChatRoom(this);
                ChatRoom.addChatRoom(chatRoom);
            }

            chatRoom.addMember(profile);
            
        }

    }
    //팀 반환 및 새로 만들어 리스트(allTeams)에 추가(하원)
    public static Team findOrCreateTeam(int postID){
        for(Team t: allTeams){
            if(t.getPostID() == postID){
                return t;
            }
        }
        Team newTeam = new Team(postID);
        addTeam(newTeam);
        return newTeam;
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

    public ChatRoom getChatRoom(){
        return chatRoom;
    }
    // 객체 반환 함수 추가 (하원)

    
}