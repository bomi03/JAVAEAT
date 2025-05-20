// MyPage.java
import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

import model.User;
import model.Profile;
import model.Management;
import model.Test;
import dialog.ProfilePopup;

public class MyPage extends JFrame {
    private User user;
    private Management manager;
    private Profile profile;
    private Test test;

    private JPanel mainPanel;
    private JScrollPane scrollPane;

    private JLabel nicknameLabel, yearLabel;
    private JButton editProfileBtn;
    private JLabel profilePicLabel;

    private JButton supportStatusBtn;
    private JButton myPostsBtn;
    private JButton testBtn;

    private JLabel idValueLabel;
    private JButton changePasswordBtn;

    private JButton privacyBtn, termsBtn, deleteAccountBtn, logoutBtn;

    private static final int H30 = 30;
    private static final int H40 = 40;

    public MyPage(User user, Management manager) {
        this.user = user;
        this.manager = manager;
        this.profile = user.getProfile();
        this.test = new Test();

        setTitle("마이페이지");
        setSize(393, 852);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
        registerListeners();
        setVisible(true);
    }

    private void buildUI() {
        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(393, 1200));

        int y = 0;

        // 상단바
        JLabel header = new JLabel("마이페이지", SwingConstants.CENTER);
        header.setForeground(Color.decode("#4A4A4A"));
        header.setBounds(0, 20, 393, H30);
        mainPanel.add(header);

        y += 60;
        JSeparator sep1 = new JSeparator();
        sep1.setBounds(0, y, 393, 1);
        mainPanel.add(sep1);

        // 프로필 섹션
        y += 10;
        JPanel profSec = new JPanel(null);
        profSec.setBackground(Color.decode("#F5F5F5"));
        profSec.setBounds(0, y, 393, 140);
        mainPanel.add(profSec);

        String nick = profile != null && profile.getNickname() != null
                ? profile.getNickname() : "닉네임 없음";
        String year = profile != null && profile.getAdmissionYear() != null
                ? profile.getAdmissionYear() : "–";

        nicknameLabel = new JLabel(nick);
        nicknameLabel.setFont(nicknameLabel.getFont().deriveFont(16f));
        nicknameLabel.setBounds(20, 15, 200, 25);
        profSec.add(nicknameLabel);

        yearLabel = new JLabel(year);
        yearLabel.setForeground(Color.GRAY);
        yearLabel.setBounds(120, 15, 100, 25);
        profSec.add(yearLabel);

        editProfileBtn = new JButton("프로필 편집");
        editProfileBtn.setBackground(Color.decode("#003087"));
        editProfileBtn.setForeground(Color.WHITE);
        editProfileBtn.setBorderPainted(false);
        editProfileBtn.setFocusPainted(false);
        editProfileBtn.setBounds(20, 55, 120, H30);
        profSec.add(editProfileBtn);

        profilePicLabel = new JLabel();
        profilePicLabel.setBounds(260, 10, 70, 70);
        profilePicLabel.setBorder(new LineBorder(Color.GRAY, 1, true));
        profSec.add(profilePicLabel);
        // 프로필 사진 클릭 시 팝업 띄우기
        profilePicLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profilePicLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ProfilePopup(MyPage.this, profile);
            }
        });


        // 서비스 섹션
        y += 140;
        JSeparator sep2 = new JSeparator();
        sep2.setBounds(0, y, 393, 1);
        mainPanel.add(sep2);

        y += 10;
        JLabel srvLabel = new JLabel("서비스");
        srvLabel.setFont(srvLabel.getFont().deriveFont(14f).deriveFont(Font.BOLD));
        srvLabel.setBounds(20, y, 200, 25);
        mainPanel.add(srvLabel);

        y += 30;
        supportStatusBtn = makeMenuButton("지원 현황", y);
        mainPanel.add(supportStatusBtn);

        y += H40;
        myPostsBtn = makeMenuButton("작성한 글", y);
        mainPanel.add(myPostsBtn);

        y += H40;
        testBtn = makeMenuButton("협업 유형 테스트", y);
        mainPanel.add(testBtn);

        // 계정 섹션
        y += H40;
        JSeparator sep3 = new JSeparator();
        sep3.setBounds(0, y, 393, 1);
        mainPanel.add(sep3);

        y += 10;
        JLabel acctLabel = new JLabel("계정");
        acctLabel.setFont(acctLabel.getFont().deriveFont(14f).deriveFont(Font.BOLD));
        acctLabel.setBounds(20, y, 200, 25);
        mainPanel.add(acctLabel);

        y += 30;
        JLabel idLabel = new JLabel("아이디");
        idLabel.setBounds(20, y, 100, 25);
        mainPanel.add(idLabel);

        idValueLabel = new JLabel(user.getUserID(), SwingConstants.RIGHT);
        idValueLabel.setForeground(Color.GRAY);
        idValueLabel.setBounds(120, y, 200, 25);
        mainPanel.add(idValueLabel);

        y += H40;
        changePasswordBtn = makeMenuButton("비밀번호 변경", y);
        mainPanel.add(changePasswordBtn);

        // 기타 섹션
        y += H40;
        JSeparator sep4 = new JSeparator();
        sep4.setBounds(0, y, 393, 1);
        mainPanel.add(sep4);

        y += 10;
        JLabel etcLabel = new JLabel("기타");
        etcLabel.setFont(etcLabel.getFont().deriveFont(14f).deriveFont(Font.BOLD));
        etcLabel.setBounds(20, y, 200, 25);
        mainPanel.add(etcLabel);

        y += 30;
        privacyBtn = makeMenuButton("개인정보 수집/이용 동의", y);
        mainPanel.add(privacyBtn);

        y += H40;
        termsBtn = makeMenuButton("서비스 이용 약관", y);
        mainPanel.add(termsBtn);

        y += H40;
        deleteAccountBtn = makeMenuButton("회원 탈퇴", y);
        mainPanel.add(deleteAccountBtn);

        y += H40;
        logoutBtn = makeMenuButton("로그아웃", y);
        mainPanel.add(logoutBtn);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBounds(0, 0, 393, 852);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scrollPane, BorderLayout.CENTER);

        BottomNavBar nav = new BottomNavBar(
            e -> { new TeamListPage(user, manager); dispose(); },
            e -> { /* 나중에 채팅 페이지 */ },
            e -> { /* 나중에 알림 페이지 */ },
            e -> { new MyPage(user, manager); dispose(); }
        );
        wrapper.add(nav, BorderLayout.SOUTH);
        setContentPane(wrapper);
        refreshProfileDisplay();
    }

    private void registerListeners() {
        editProfileBtn.addActionListener(e -> {
            dispose();
            new ProfilePage(user, manager, true, this);
        });
        supportStatusBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "지원 현황")
        );
        myPostsBtn.addActionListener(e ->
            {                                  // ← 중괄호로 묶고
               new MyPostsPage(user, manager, profile.getProfileID());
             dispose();                     // ← 창 닫기
            }
        );
        testBtn.addActionListener(e -> {
            test.takeTest(user, manager);
            profile.updateType(test.getUserResultType(), test.getResultImagePath());
            refreshProfileDisplay();
            JOptionPane.showMessageDialog(this,
                "테스트 완료: " + test.getUserResultType().name()
            );
        });
        changePasswordBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "비밀번호 변경")
        );
        privacyBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "개인정보 동의")
        );
        termsBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "약관 보기")
        );
        deleteAccountBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "회원 탈퇴")
        );
        logoutBtn.addActionListener(e -> {
            user.logout();
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame(manager));
        });
    }

    public void refreshProfileDisplay() {
        this.profile = user.getProfile();
        nicknameLabel.setText(profile.getNickname());
        yearLabel.setText(profile.getAdmissionYear());

        String pImg = profile.getProfileImagePath();
        if (pImg != null && !pImg.isEmpty()) {
            profilePicLabel.setIcon(new ImageIcon(
                new ImageIcon(pImg).getImage()
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH)
            ));
        } else {
            profilePicLabel.setIcon(null);
        }
    }

    private JButton makeMenuButton(String text, int y) {
        JButton b = new JButton(text + "  >");
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBackground(Color.WHITE);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setBounds(20, y, 350, H40);
        return b;
    }

    // 테스트용 main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Management mgr = new Management(new java.util.HashMap<>());
            User u = new User("홍길동", "hg123", "pw", "hg@sookmyung.ac.kr");
            Profile p = new Profile(1, "hg123");
            p.setNickname("새송이버섯");
            p.setAdmissionYear("22학번");
            u.setProfile(p);
            new MyPage(u, mgr);
        });
    }
}