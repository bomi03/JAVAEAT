import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import model.Application;
import model.ChatRoom;
import model.ChatRoomManager;
import model.Management;
import model.NotificationType;
import model.Post;
import model.Profile;
import model.Team;
public class ApplicantDetailPage extends JFrame {
    private Application application;
    private Profile profile;

    //의존성 주입용 필드(생성자에서 안 받음)
    private Post post;
    private Team team;
    private ChatRoomManager chatRoomManager;
    private Management manager;

    //콜백 인터페이스 추가
    private java.util.function.Consumer<ChatRoom> onAccept; // ✅ 추가



    public ApplicantDetailPage(Application application, Profile profile) {
        super("지원자 상세 정보");
        this.application = application;
        this.profile = profile;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(393, 853);
        setLocationRelativeTo(null);

        buildUI();
        setVisible(true);
    }
    //필요한 객체 주입용 메서드
    public void setDependencies(Post post, Team team, ChatRoomManager chatRoomManager, Management manager) {
        this.post = post;
        this.team = team;
        this.chatRoomManager = chatRoomManager;
        this.manager = manager;
    }
    //설정 메서드 추가

    private void buildUI() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 상단 바
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        topBar.setBackground(Color.WHITE);

        JButton backButton = new JButton("←");
        backButton.setPreferredSize(new Dimension(50, 30));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> dispose());

        JLabel titleLabel = new JLabel("지원자 확인하기", SwingConstants.CENTER);
        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);

        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        contentPanel.add(topBar);

        // 프로필 패널
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(profile.getNickname() != null ? profile.getNickname() : "이름 없음");
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 16f));

        JLabel idLabel = new JLabel(profile.getAdmissionYear() != null ? profile.getAdmissionYear() : "학번 없음");
        idLabel.setFont(idLabel.getFont().deriveFont(Font.PLAIN, 12f));

        textPanel.add(nameLabel);
        textPanel.add(idLabel);

        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(60, 60));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(Color.LIGHT_GRAY);
        imageLabel.setBorder(new LineBorder(Color.GRAY, 1, true));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //채빈 0523 프로필 이미지 연결 추가
        String imgPath = profile.getProfileImagePath();
        if (imgPath != null && !imgPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(new ImageIcon(imgPath)
                .getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            imageLabel.setIcon(icon);
            imageLabel.setBackground(Color.WHITE);
            imageLabel.setText("");
        } else {
            imageLabel.setBackground(Color.LIGHT_GRAY);
        }

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ProfilePopup(ApplicantDetailPage.this, profile);
            }
        });

        profilePanel.add(textPanel, BorderLayout.WEST);
        profilePanel.add(imageLabel, BorderLayout.EAST);
        contentPanel.add(profilePanel);

        // 자기소개 영역
        JLabel introLabel = new JLabel("자기소개");
        introLabel.setFont(introLabel.getFont().deriveFont(Font.BOLD, 14f));
        introLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        introLabel.setHorizontalAlignment(SwingConstants.LEFT);
        introLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, introLabel.getPreferredSize().height));

        JTextArea introArea = new JTextArea(application.getMessage() != null ? application.getMessage() : "자기소개 없음");
        introArea.setLineWrap(true);
        introArea.setWrapStyleWord(true);
        introArea.setEditable(false);
        introArea.setBackground(new Color(245, 245, 245));
        introArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(introArea);
        scrollPane.setPreferredSize(new Dimension(360, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        contentPanel.add(introLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(scrollPane);
        contentPanel.add(Box.createVerticalStrut(20));

        // 하단 버튼
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        buttonPanel.setBackground(Color.WHITE);

        JButton rejectButton = new JButton("거절");
        rejectButton.setBackground(Color.WHITE);
        rejectButton.setForeground(Color.GRAY);
        rejectButton.setFocusPainted(false);
        rejectButton.addActionListener(e -> {
            application.reject();
            if (post != null) post.removeApplicationByProfileId(profile.getProfileID()); // ✅ 지원자 제거 추가
            //0523 채빈 알림기능 추가
            //0526 서연 수정 post.getPostID() 추가가
            if (manager != null) {
                manager.addNotification(
                    profile.getUserID(),
                    post.getTitle() + ": 팀 매칭이 거절되었어요.",
                    NotificationType.REJECT,
                    "/post/" + post.getPostID(), post.getPostID()
                );
            }
            JOptionPane.showMessageDialog(this, "지원자가 거절되었습니다.");
            dispose();
        });


        JButton acceptButton = new JButton("수락");
        acceptButton.setBackground(Color.WHITE);
        acceptButton.setForeground(Color.GRAY);
        acceptButton.setFocusPainted(false);
        acceptButton.addActionListener(e -> {
            try {
                application.accept();//1단계 지원 상태 변경
                team.acceptAndCreateChat(profile, post, chatRoomManager, manager);

                ChatRoom chatRoom = team.getChatRooms().get(team.getChatRooms().size()-1);

                //0523 채빈 알림 기능 추가
                //0526 서연 수정 post.getPostID() 추가
                if (manager != null) {
                    manager.addNotification(
                        profile.getUserID(),
                        post.getTitle() + ": 팀원이 되었어요!",
                        NotificationType.ACCEPT,
                        "/post/" + post.getPostID(), post.getPostID()
                    );
                }

                dispose();

                JOptionPane.showMessageDialog(this, "지원자가 수락되었고 채팅방이 생성되었습니다.");
                SwingUtilities.invokeLater(()->{
                    chatMainFrame frame = new chatMainFrame(manager.getCurrentUser(),manager);
                    frame.setLocationRelativeTo(null);
                    frame.openChatRoom(chatRoom);
                });

           

            } catch (IllegalAccessException ex) {
                JOptionPane.showMessageDialog(this, "팀 수락 중 오류: " + ex.getMessage());
            }
        });

        buttonPanel.add(rejectButton);
        buttonPanel.add(acceptButton);
        contentPanel.add(buttonPanel);

        add(contentPanel);
    }
    //설정 메서드 추가
    public void setOnAccept(java.util.function.Consumer<ChatRoom> onAccept) { // ✅ 추가
        this.onAccept = onAccept;
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 더미 Application
            Application dummyApp = new Application(1, 1, 1001, "안녕하세요. 팀원으로 함께 하고 싶어요!");

            // 더미 Profile
            Profile dummyProfile = new Profile(1001, "tester");
            dummyProfile.setNickname("새송이버섯");
            dummyProfile.setAdmissionYear("22학번");
            dummyProfile.setIntroduction("자바 스윙으로 프로젝트 해보고 싶어요!");
            dummyProfile.setProfileImagePath("");  // 이미지 경로 없으면 기본 처리됨

            // 팝업 테스트
            new ApplicantDetailPage(dummyApp, dummyProfile);
        });
    }
}