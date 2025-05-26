package model;

import java.util.*;

public class ChatRoomManager {
    private Map<Integer, ChatRoom> chatRooms;
    private int nextRoomId;

    public ChatRoomManager() {
        chatRooms = new HashMap<>();
        nextRoomId = 1;
    }

    // 채팅방 생성 (memberList에 두 명 넣어서)
    public ChatRoom createChatRoom(Profile p1, Profile p2, int postID) {
        ChatRoom room = new ChatRoom(p1,p2,postID);
        chatRooms.put(room.getChatRoomID(), room);
        return room;
    }

    // 채팅방 ID로 조회
    public ChatRoom getChatRoom(int roomId) {
        return chatRooms.get(roomId);
    }

    // 모든 채팅방 반환
    public List<ChatRoom> getAllChatRooms() {
        return new ArrayList<>(chatRooms.values());
    }

    // (선택) 특정 프로필이 포함된 채팅방 찾기
    public List<ChatRoom> getChatRoomsForProfile(Profile profile) {
        List<ChatRoom> result = new ArrayList<>();
        for (ChatRoom room : chatRooms.values()) {
            if (room.getParticipants().contains(profile)) {
                result.add(room);
            }
        }
        return result;
    }
}
