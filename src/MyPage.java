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
    // private JButton testBtn;

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
        y += 0;
        JPanel profSec = new JPanel(null);
        profSec.setBackground(Color.decode("#F5F5F5"));
        profSec.setBounds(0, y, 393, 115);
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
        y += 115;
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

        // y += H40;
        // testBtn = makeMenuButton("협업 유형 테스트", y);
        // mainPanel.add(testBtn);

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
        idLabel.setBounds(40, y, 100, 25);
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
            e -> { new chatMainFrame(user, manager); dispose(); },
            e -> { NotificationPage page = new NotificationPage(user, manager);
                    page.setVisible(true);
                    dispose();
                 },
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
        supportStatusBtn.addActionListener(e -> {
            new MyApplicationsPage(user, manager);
            dispose();
        });

        myPostsBtn.addActionListener(e ->
            {                                  // ← 중괄호로 묶고
               new MyPostsPage(user, manager, profile.getProfileID());
             dispose();                     // ← 창 닫기
            }
        );
        // testBtn.addActionListener(e -> {
        //     test.takeTest(user, manager);
        //     profile.updateType(test.getUserResultType(), test.getResultImagePath());
        //     refreshProfileDisplay();
        //     JOptionPane.showMessageDialog(this,
        //         "테스트 완료: " + test.getUserResultType().name()
        //     );
        // });
        changePasswordBtn.addActionListener(e -> {
            new ChangePwPage(user, manager); 
        });

        // 개인정보 수집/이용 동의 보기
        privacyBtn.addActionListener(e -> {
            JFrame termsFrame = new JFrame("개인정보 수집/이용 동의");
            termsFrame.setSize(400, 500);
            termsFrame.setLocationRelativeTo(null);

            JTextArea textArea = new JTextArea(
        "※ 서비스 이용 약관 및 개인정보 수집/이용 동의\n\n" +
        "「개인정보 보호법」제 15조 법규에 의거하여, '눈뭉치'는 이용자의 개인정보 수집 및 활용에 대해\n" +
        "개인정보 수집 및 이용 동의서를 받고 있습니다.\n" +
        "개인정보 제공자가 동의한 내용 외의 다른 목적으로 활용하지 않으며,\n" +
        "제공된 개인정보의 이용을 거부하고자 할 때에는 개인정보 관리 책임자를 통해 열람, 정정 혹은 삭제를 요구할 수 있습니다.\n\n" +
        "제공된 개인정보는 '눈뭉치'의 아래 항목에 제한된 범위 내에서만 활용됩니다.\n\n" +
        "1. 개인정보의 제공 목적\n" +
        "- 계약의 체결 · 유지 · 이행 · 관리\n" +
        "- 개인식별 · 본인확인 · 부정사용방지 등 회원관리, 본 서비스 및 부가/제휴서비스 제공 등\n\n" +
        "2. 개인정보 제공 항목\n" +
        "- 개인의 성명, 이메일\n" +
        "- 기타 계약의 체결, 유지, 이행, 관리 등과 관련하여 본인이 제공한 정보\n\n" +
        "3. 개인정보를 제공받는 자\n" +
        "- '눈뭉치'\n\n" +
        "4. 제공받는 자의 개인정보 보유 및 이용기간\n" +
        "- 위 개인정보의 수집 및 이용 동의일로부터 목적을 달성할 때까지 보유/이용합니다.\n" +
        "  (단, 법률에 따라 일정 기간 보존될 수 있습니다.)\n\n" +
        "   가. 계약 또는 청약철회 등에 관한 기록: 5년\n" +
        "   나. 대금결제 및 재화 등의 공급에 관한 기록: 5년\n" +
        "   다. 소비자의 불만 또는 분쟁처리에 관한 기록: 3년\n\n" +
        "※ 위 사항에 대해 동의를 거부할 수 있으나, 본 동의는 필수 항목으로 거부 시 서비스 가입 또는 유지, \n" +
        "거래 인증 및 부정사용 예방 및 관리 등이 불가능합니다.\n\n" +
        "※ 본 내용이 변경되는 경우, 서비스 내 공지를 통해 안내드립니다.\n"
            );
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
            textArea.setMargin(new Insets(10, 10, 10, 10));

            JScrollPane scrollPane = new JScrollPane(textArea);
            termsFrame.add(scrollPane);
            termsFrame.setVisible(true);
        });

        // 서비스 이용 약관 보기
        termsBtn.addActionListener(e -> {
            JFrame termsFrame = new JFrame("서비스 이용 약관");
            termsFrame.setSize(500, 600);
            termsFrame.setLocationRelativeTo(null);

            JTextArea textArea = new JTextArea(
                   "※ 서비스 이용 약관\n\n" +

                "제 1조 (목적)\n" +
                "본 약관은 눈뭉치에서 제공하는 서비스를 제공함에 있어 눈뭉치와 이용자의 권리, 의무 및 책임사항을 규정함을 목적으로 합니다.\n\n" +

                "제 2조 (이용약관의 효력 및 변경)\n" +
                "1. 눈뭉치는 본 약관의 내용을 회원이 쉽게 알 수 있도록 회원가입 화면 및 마이페이지 등에 게시합니다.\n" +
                "2. 눈뭉치는 관련법을 위배하지 않는 범위에서 본 약관을 개정할 수 있습니다.\n" +
                "3. 눈뭉치는 약관을 개정할 경우 적용일자 및 개정사유를 명시하여 사전 공지합니다.\n" +
                "4. 회원은 변경에 동의하지 않을 경우 탈퇴할 수 있으며, 계속 이용 시 동의한 것으로 간주합니다.\n" +
                "5. 변경된 약관에 대한 정보를 알지 못해 발생하는 피해는 눈뭉치가 책임지지 않습니다.\n\n" +

                "제 3조 (서비스의 제공)\n" +
                "1. 눈뭉치는 다음 서비스를 제공합니다:\n" +
                "   i. 대학생활 편의 서비스\n" +
                "   ii. 팀 매칭 서비스\n" +
                "   iii. 기타 눈뭉치가 정하는 서비스\n" +
                "2. 운영상 또는 기술상 변경이 있을 수 있습니다.\n" +
                "3. 개인정보 및 기록에 따라 이용 조건에 차이가 있을 수 있습니다.\n\n" +

                "제 4조 (이용계약의 성립)\n" +
                "1. 본 약관 동의와 이용 신청, 눈뭉치의 승낙으로 계약이 성립합니다.\n" +
                "2. 동의는 회원가입 시 체크로 간주됩니다.\n\n" +

                "제 5조 (서비스 이용 신청)\n" +
                "1. 이름과 이메일 등 정보 제공이 필요합니다.\n" +
                "2. 타인 정보 도용 시 삭제 및 법적 책임이 따릅니다.\n" +
                "3. 본인 인증 및 학교 인증이 요구될 수 있습니다.\n" +
                "4. 만 14세 미만은 이용이 불가합니다.\n\n" +

                "제 6조 (개인정보의 보호 및 사용)\n" +
                "1. 눈뭉치는 관련법에 따라 개인정보를 보호하며, 책임을 다합니다.\n" +
                "2. 제휴사 제공 시 사전 동의를 받습니다.\n" +
                "3. 회원은 언제든 수집 및 이용 동의를 철회할 수 있습니다.\n" +
                "4. 아이디, 비밀번호 등의 관리는 회원 책임입니다.\n\n" +

                "제 7조 (서비스 이용계약의 종료)\n" +
                "1. 회원은 직접 탈퇴 요청할 수 있으며 눈뭉치는 이를 처리합니다.\n" +
                "2. 탈퇴 후에도 게시물은 삭제되지 않습니다.\n\n" +

                "제 8조 (기타)\n" +
                "1. 이 약관은 2025년 OO월 OO일에 최신화 되었습니다.\n" +
                "2. 해석 및 기타 사항은 관련 법령과 관례에 따릅니다.\n" +
                "3. 다른 정책과 상충 시 별도 정책을 우선합니다.\n\n" +

                "※ 개인정보 수집 및 이용 동의\n\n" +
                "「개인정보 보호법」 제15조에 따라, 눈뭉치는 이용자의 개인정보 수집 및 이용에 대한 동의를 받고 있습니다.\n" +
                "1. 수집 목적: 계약 체결 및 이행, 본인 확인, 서비스 제공 등\n" +
                "2. 수집 항목: 성명, 이메일, 기타 회원이 제공한 정보\n" +
                "3. 제공 대상: 눈뭉치\n" +
                "4. 보유 기간: 수집일로부터 목적 달성 시까지\n\n" +
                "- 계약 관련 기록: 5년\n" +
                "- 결제/공급 관련 기록: 5년\n" +
                "- 소비자 불만/분쟁 처리 기록: 3년\n\n" +
                "※ 위 동의는 필수 항목이며, 거부 시 서비스 이용이 제한될 수 있습니다.\n" +
                "※ 내용 변경 시 공지를 통해 안내됩니다.\n"
            );
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
            textArea.setMargin(new Insets(10, 10, 10, 10));

            JScrollPane scrollPane = new JScrollPane(textArea);
            termsFrame.add(scrollPane);
            termsFrame.setVisible(true);
        });



        deleteAccountBtn.addActionListener(e -> {
            new DeleteAccountPage(user, manager);
        });

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