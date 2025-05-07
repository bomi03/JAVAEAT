package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Team {

    private List<Profile> memberProfiles;
    private int teamID;
    private int postID;
    Map<Profile, String> memberRoles;

    public Team (int teamID, int postID){
        this.teamID = teamID;
        this.postID = postID;
        this.memberProfiles= new ArrayList<>();
    }

    public void accept(Profile profile,Post post) throws IllegalAccessException {
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

    
}