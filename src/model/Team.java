package model;

import java.util.List;
import java.util.Map;

public class Team {

    private List<Profile> memberProfiles;
    Map<Profile, String> memberRoles;

    public void accept(Profile profile,Post post) {
        if(post.closePost()){

            throw new IllegalAccessException("모집이 마감 되어 팀원을 추가할 수 없습니다.");
        }
        if (!memberProfiles.contains(profile)){
            memberProfiles.add(profile);
            
        }

    }

    public void reject(Profile profile, Post post) {

       
         String targetProfiledId = profile.getProfileId();
         //getProfileId() -> 현재는 없는 메서드 profile 클래스에 추가해야함//
         post.getApplications().removeIf(application -> 
         application.getProfileId().equlas(targetProfiledId));
         //getApplications() -> 추가해야하는 메서드 //

        System.out.println("지원이 거절되었습니다.");


    }
    public void assignRole(Profile profile,Post post) {

        int authorId = post.author.getProfileId();

        if(!memberProfiles.contains(profile)) return;

        if(profile.getProfileId().equals(authorId)){
            memberRoles.put(profile, "리더");
        } else{
            memberRoles.put(profile, "팀원");
        }

    
    }

    
}