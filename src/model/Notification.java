package model;

// 알림 정보 클래스
public class Notification {
    private final int notificationId;
    private final String receiverId;
    private final String message;
    private final NotificationType type;
    private boolean isRead;
    private final String redirectUrl;
    private final int postId; 

    // 전체 필드 초기화
    public Notification(int notificationId,
                        String receiverId,
                        String message,
                        NotificationType type,
                        boolean isRead,
                        String redirectUrl,
                        int postId) {
        this.notificationId = notificationId;
        this.receiverId    = receiverId;
        this.message       = message;
        this.type          = type;
        this.isRead        = isRead;
        this.redirectUrl   = redirectUrl;
        this.postId = postId; 
    }

    // 읽음 처리리
    public void markAsRead() {
        this.isRead = true;
    }

    // 읽지 않은 알림인지 확인
    public boolean isUnread() {
        return !this.isRead;
    }

    // 알림 ID 반환 
    public int getNotificationId() {
        return notificationId;
    }

    // 수신자 ID 반환
    public String getReceiverId() {
        return receiverId;
    }

    // 알림 메시지 반환
    public String getMessage() {
        return message;
    }

    // 알림 유형 반환
    public NotificationType getType() {
        return type;
    }

    // 읽음 여부 반환
    public boolean isRead() {
        return isRead;
    }

    // 클릭 시 이동할 URL 반환
    public String getRedirectUrl() {
        return redirectUrl;
    }

    public int getPostId() {
    return postId;
    }
    
}

