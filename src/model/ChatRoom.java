package model;

import java.util.List;

public class ChatRoom {
    private int chatRoomID;
    private int postID;
    private List<Profile> participants;
    private List<Message> messages;

    public void createRoom() {}
    public List<Message> getMessages() {
        return messages;
    }
}