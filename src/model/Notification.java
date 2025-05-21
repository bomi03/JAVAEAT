package model;

// 알림 정보 클래스
public class Notification {
    private final int notificationId;
    private final String receiverId;
    private final String message;
    private final NotificationType type;
    private boolean isRead;
    private final String redirectUrl;

    // 전체 필드 초기화
    public Notification(int notificationId,
                        String receiverId,
                        String message,
                        NotificationType type,
                        boolean isRead,
                        String redirectUrl) {
        this.notificationId = notificationId;
        this.receiverId    = receiverId;
        this.message       = message;
        this.type          = type;
        this.isRead        = isRead;
        this.redirectUrl   = redirectUrl;
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
}

//         알림 예시 
//         Notification n1 = new Notification(1, u.getUserID(), "📝 [피티송이] 님이 팀에 지원했습니다.", NotificationType.APPLY, false, "지원서 #001");
//         Notification n2 = new Notification(2, u.getUserID(), "💬 새 채팅 메시지가 도착했습니다.", NotificationType.MESSAGE, false, "채팅방 #123");
//         Notification n3 = new Notification(3, u.getUserID(), "📢 모집 마감 1일 전입니다!", NotificationType.MESSAGE, true, "공모전 #ABC");