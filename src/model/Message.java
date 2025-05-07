package model;

public class Message {
    private int messageID;     
    private int chatRoomID;    
    private String senderID;   
    private String content;    
    private boolean isRead;    

    public Message(int messageID, int chatRoomID, String senderID, String content) {
        this.messageID = messageID;
        this.chatRoomID = chatRoomID;
        this.senderID = senderID;
        this.content = content;
        this.isRead = false;  // 기본: 읽지 않음
    }

    // Get
    public int getMessageID() {
        return messageID;
    }

    public int getChatRoomID() {
        return chatRoomID;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getContent() {
        return content;
    }

    public boolean isRead() {
        return isRead;
    }

    // 메시지 전송 (생성 시 자동 호출하는 형태로 구현 가능)
    public static Message sendMessage(int messageID, int chatRoomID, String senderID, String content) {
        return new Message(messageID, chatRoomID, senderID, content);
    }

    // 읽음 처리
    public void markAsRead() {
        this.isRead = true;
    }
}
