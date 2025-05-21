import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.Application;
import model.ChatRoom;
import model.Profile;
//클래스 추가
import model.Team;
import model.User;
import model.Management;
import model.Post;
//Post 객체 생성을 위한 Date추가
import java.util.Date;
//더미 매니저를 위해서 추가
import java.util.HashMap;


public class ApplicantDetailPage extends JFrame {
    private Application application;
    private Profile profile;
    //User,Manager 필드 추가
    private User user;
    private Management manager;
    private Post post;

    public ApplicantDetailPage(Application application, Profile profile, User user,Management manager) {
        super("지원자 상세 정보");
        this.application = application;
        this.profile = profile;
        //채팅룸 이동을 위해 추가
        this.user = user;
        this.manager = manager;
        this.post = application.getPost();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(393, 853);
        setLocationRelativeTo(null);

        buildUI();
        setVisible(true);
    }

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
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));

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

        JLabel nameLabel = new JLabel("새송이버섯");
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 16f));
        JLabel idLabel = new JLabel("22학번");
        idLabel.setFont(idLabel.getFont().deriveFont(Font.PLAIN, 12f));

        textPanel.add(nameLabel);
        textPanel.add(idLabel);

        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(60, 60));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(Color.LIGHT_GRAY);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setText("IMG");

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

        JTextArea introArea = new JTextArea(application.getMessage());
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
        rejectButton.setBackground(Color.white);
        rejectButton.setForeground(Color.gray);
        rejectButton.setFocusPainted(false);
        rejectButton.addActionListener(e -> {
            application.reject();
            JOptionPane.showMessageDialog(this, "지원자가 거절되었습니다.");
            dispose();
        });

        JButton acceptButton = new JButton("수락");
        acceptButton.setBackground(Color.white);
        acceptButton.setForeground(Color.gray);
        acceptButton.setFocusPainted(false);
        
        // 팀원 수락 -> 채팅룸 생성 이로 이동동
        acceptButton.addActionListener(e -> {
            try {
                Team team = application.accept(); // 도메인 로직 처리
                ChatRoom chatRoom = team.getChatRoom();
        
                JOptionPane.showMessageDialog(this, "지원자가 수락되었습니다.\n채팅방으로 이동합니다.");
        
                new chatMainFrame(user, manager).showPanel("detail1");
                dispose();
        
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "수락 중 오류 발생: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        buttonPanel.add(rejectButton);
        buttonPanel.add(acceptButton);

        contentPanel.add(buttonPanel);

        add(contentPanel);
    }

    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        
        // 더미 Application
        //Application dummyApp = new Application(1, 1, 1001, "안녕하세요. 팀원으로 함께 하고 싶어요!");


        // 더미 Profile
        Profile dummyProfile = new Profile(1001, "tester");
        dummyProfile.setNickname("새송이버섯");
        dummyProfile.setAdmissionYear("22학번");
        dummyProfile.setIntroduction("자바 스윙으로 프로젝트 해보고 싶어요!");
        dummyProfile.setProfileImagePath("");  // 이미지 경로 없으면 기본 처리됨

        // 더미 Post객체 생성
        Post dummyPost = new Post(
            1,                          // postID
            dummyProfile.getProfileID(), // profileID
            "",                         // postImagePath
            "스터디",                    // category
            "자바 스터디 모집",            // title
            "모집중",                    // status
            new Date(),                // recruitDeadline (현재 날짜)
            4,                         // maxApplicants
            1,                         // currentApplicants
            "같이 스터디 하실 분 구합니다." // description
        );

        // 더미 application 생성성
        Application dummyApp = new Application(1, dummyPost, dummyProfile, "안녕하세요. 팀원으로 함께 하고 싶어요!");

        //더미 User생성
        User dummyUser = new User("홍길동", "testID", "pw", "test@sookmyung.ac.kr");
        Management dummyManager = new Management(new HashMap<>());

        // 팝업 테스트
        new ApplicantDetailPage(dummyApp, dummyProfile, dummyUser, dummyManager);
    });
}

}


    


