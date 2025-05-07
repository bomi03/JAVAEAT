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
       
    }
}