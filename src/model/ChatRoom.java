package model;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private int chatRoomID;
    private int postID;
    private List<Profile> participants;
    private List<Message> messages;

    public void createRoom(int chatRoomID,int postID,List<Profile> participants) {
        this.chatRoomID =chatRoomID;
        this.postID = postID;
        this.participants = new ArrayList<>(participants);
        this.messages= new ArrayList<>();

    }
    public List<Message> getMessages() {
        return messages;
       
    }
    public List<Message> getRecentMessages(int count){
        int size = messages.size();
        return messages.subList(Math.max(0,size - count), size);
        //가장 최근 메시지 count만큼 불러오는 메서드
    }
}  