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
        post.autoClosePost(); // 모집 마감 체크 - 보미 수정
        if(post.isClosed()){

            throw new IllegalAccessException("모집이 마감 되어 팀원을 추가할 수 없습니다.");
        }
        if (!memberProfiles.contains(profile)){
            memberProfiles.add(profile);
            
        }

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

    
}