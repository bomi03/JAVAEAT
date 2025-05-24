package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatRoom {

    private static int nextID = 1;
    private static List<ChatRoom> allChatRooms = new ArrayList<>();

    private int chatRoomID;
    private int postID;
    private List<Profile> participants;
    private List<Message> messages;
    

    public ChatRoom(Profile p1,Profile p2,int postID){
        this.chatRoomID = nextID++;
        this.postID = postID;
        this.participants = new ArrayList<>(Arrays.asList(p1,p2));
        this.messages = new ArrayList<>();
    }
    //전체 채팅방에 등록
    public static void addChatRoom(ChatRoom chatRoom){

        allChatRooms.add(chatRoom);
    }
    // 참가자 추가
    public void addMember(Profile profile){
        if(!participants.contains(profile)){
            participants.add(profile);
        }
    }
    
    public List<Message> getMessages() {
        return messages;
       
    }
    public List<Message> getRecentMessages(int count){
        int size = messages.size();
        return messages.subList(Math.max(0,size - count), size);
        //가장 최근 메시지 count만큼 불러오는 메서드
    }
    public int getChatRoomID(){
        return chatRoomID;
    }

    public void addMessage(Message message){
        messages.add(message);
    }

    

    public List<Profile> getParticipants(){
        return participants;
    }

    public String getTargetName(Profile me){
        for(Profile p : participants){
            if(!p.equals(me)){
                return p.getNickname();
            }
        }
        return "알 수 없음";
    }

}  