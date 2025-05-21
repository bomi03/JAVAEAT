import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import model.*;

public class NotificationPage extends JFrame {
    private final User user;
    private final Management manager;

    public NotificationPage(User user, Management manager) {
        this.user = user;
        this.manager = manager;

        setTitle("알림");
        setSize(393, 852);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout()); // ✅ 안정적인 레이아웃 사용
        getContentPane().setBackground(Color.WHITE);

        // 🔝 상단바 (뒤로가기 + 제목)
        JPanel headerPanel = new JPanel(null);
        headerPanel.setPreferredSize(new Dimension(393, 50));
        headerPanel.setBackground(Color.WHITE);

        JButton backButton = new JButton("←");
        backButton.setBounds(10, 10, 50, 30);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(e -> dispose());
        headerPanel.add(backButton);

        JLabel titleLabel = new JLabel("알림", SwingConstants.CENTER);
        titleLabel.setBounds(0, 10, 393, 30);
        headerPanel.add(titleLabel);

        // 📄 알림 리스트 패널
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        List<Notification> sorted = user.getNotifications().stream()
            .sorted((a, b) -> Integer.compare(b.getNotificationId(), a.getNotificationId()))
            .collect(Collectors.toList());

        for (Notification noti : sorted) {
            JPanel card = new JPanel(null);
            card.setPreferredSize(new Dimension(360, 60));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            card.setBackground(Color.WHITE);

            JLabel msgLabel = new JLabel(noti.getMessage());
            msgLabel.setBounds(10, 5, 300, 20);
            msgLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
            card.add(msgLabel);

            JLabel postTitle = new JLabel("공모전 OOOOOO " + noti.getRedirectUrl());
            postTitle.setBounds(10, 25, 300, 20);
            postTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
            card.add(postTitle);

            if (!noti.isRead()) {
                JLabel badge = new JLabel("NEW");
                badge.setOpaque(true);
                badge.setBackground(Color.BLUE);
                badge.setForeground(Color.WHITE);
                badge.setFont(new Font("SansSerif", Font.BOLD, 10));
                badge.setHorizontalAlignment(SwingConstants.CENTER);
                badge.setBounds(310, 20, 40, 20);
                card.add(badge);
            }

            card.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
            card.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    noti.markAsRead();
                    JOptionPane.showMessageDialog(null, "📌 이동: " + noti.getRedirectUrl());
                    dispose();
                    new NotificationPage(user, manager); // 새로고침
                }
            });

            listPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);

        // 🔻 하단바
        BottomNavBar nav = new BottomNavBar(
            e -> { new TeamListPage(user, manager); dispose(); },
            e -> { /* 채팅 페이지 연결 예정 */ },
            e -> { new NotificationPage(user, manager); },
            e -> { new MyPage(user, manager); dispose(); }
        );

        // ✅ 패널 정리
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(nav, BorderLayout.SOUTH);

        setVisible(true);
    }


    
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        Management mgr = new Management();
        User u = new User("김서연", "sy123", "pw123!", "sy@sookmyung.ac.kr");

        // 🔔 테스트 알림 추가
        Notification n1 = new Notification(1, u.getUserID(), "📝 [피티송이] 님이 팀에 지원했습니다.", NotificationType.APPLY, false, "지원서 #001");
        Notification n2 = new Notification(2, u.getUserID(), "💬 새 채팅 메시지가 도착했습니다.", NotificationType.MESSAGE, false, "채팅방 #123");
        Notification n3 = new Notification(3, u.getUserID(), "📢 모집 마감 1일 전입니다!", NotificationType.MESSAGE, true, "공모전 #ABC");

        u.addNotification(n1);
        u.addNotification(n2);
        u.addNotification(n3);

        mgr.addUser(u); // 안 해도 되지만 혹시 모를 참조 대비

        // 🔍 알림 페이지 띄우기
        new NotificationPage(u, mgr);
    });
}
}


