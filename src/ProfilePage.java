import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import model.User;
import model.Profile;
import model.Test;
import model.SongiType;
import model.Management;

public class ProfilePage extends JFrame {
    private User user;
    private Profile profile;
    private Test test;
    private Management manager;

    private JScrollPane scrollPane;
    private JPanel mainPanel;
    private JLabel warningLabel;

    private JTextField nicknameField;
    private JComboBox<String> yearBox, gradeBox;
    private JToggleButton enrolledBtn, leaveBtn;
    private ArrayList<JTextField> majorFields = new ArrayList<>();
    private ArrayList<JComboBox<String>> majorTypeBoxes = new ArrayList<>();
    private JPanel majorContainer;
    private ArrayList<JTextField> expFields = new ArrayList<>();
    private ArrayList<JComboBox<String>> expTypeBoxes = new ArrayList<>();
    private JPanel expContainer;
    private ArrayList<JTextField> certFields = new ArrayList<>();
    private JPanel certContainer;
    private JTextArea introArea;
    private JButton testStyleBtn, completeBtn;
    private JLabel styleResultLabel, styleResultImage;

    private static final String[] YEARS = {
        "2009학번","2010학번","2011학번","2012학번","2013학번",
        "2014학번","2015학번","2016학번","2017학번","2018학번",
        "2019학번","2020학번","2021학번","2022학번","2023학번",
        "2024학번","2025학번"
    };
    private static final String[] GRADES = {"1학년","2학년","3학년","4학년","추가학기"};
    private static final String[] MAJOR_TYPES = {"주전공","복수전공","연계전공","부전공"};
    private static final String[] EXP_TYPES = {"수상","동아리","교내 팀플","공모전","아르바이트"};

    public ProfilePage(User user, Management manager) {
        this.user = user;
        this.profile = user.getProfile();
        this.test = new Test();
        this.manager = manager;

        setTitle("회원가입 - 프로필 작성");
        setSize(393, 852);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        buildUI();
        loadExisting();

        setVisible(true);
    }

    private void buildUI() {
        mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(393, 1400));
        int y = 0;

        // 제목 영역
        JButton backBtn = new JButton("←");
        backBtn.setBounds(10, y + 20, 40, 30);
        mainPanel.add(backBtn);
        JLabel title = new JLabel("회원가입", SwingConstants.CENTER);
        title.setBounds(0, y + 20, 393, 30);
        mainPanel.add(title);
        y += 60;

        // 구분선
        JSeparator sep = new JSeparator();
        sep.setBounds(0, y, 393, 1);
        sep.setForeground(new Color(0x8A8888));
        mainPanel.add(sep);
        y += 22;

        // 안내 문구
        JLabel guide = new JLabel(
            "<html>눈송님께 딱 맞는 팀 매칭을 위한<br>프로필을 완성해 주세요 :)</html>",
            SwingConstants.CENTER);
        guide.setBounds(0, y, 393, 40);
        mainPanel.add(guide);
        y += 60;

        // 닉네임
        mainPanel.add(labeled("닉네임 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        nicknameField = new JTextField();
        nicknameField.setBounds(20, y, 240, 30);
        mainPanel.add(nicknameField);
        JButton dupBtn = new JButton("중복 확인");
        dupBtn.setBounds(270, y, 100, 30);
        mainPanel.add(dupBtn);
        y += 40;
        warningLabel = new JLabel();
        warningLabel.setForeground(Color.RED);
        warningLabel.setBounds(20, y, 360, 20);
        mainPanel.add(warningLabel);
        y += 30;

        // 입학년도
        mainPanel.add(labeled("입학년도 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        yearBox = new JComboBox<>(YEARS);
        yearBox.setBounds(20, y, 350, 30);
        mainPanel.add(yearBox);
        y += 50;

        // 학년
        mainPanel.add(labeled("학년 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        gradeBox = new JComboBox<>(GRADES);
        gradeBox.setBounds(20, y, 350, 30);
        mainPanel.add(gradeBox);
        y += 50;

        // 재학 여부
        mainPanel.add(labeled("재학 여부 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        enrolledBtn = new JToggleButton("재학");
        enrolledBtn.setBounds(20, y, 170, 40);
        leaveBtn = new JToggleButton("휴학");
        leaveBtn.setBounds(200, y, 170, 40);
        ButtonGroup bg = new ButtonGroup();
        bg.add(enrolledBtn);
        bg.add(leaveBtn);
        mainPanel.add(enrolledBtn);
        mainPanel.add(leaveBtn);
        y += 60;

        // 전공
        mainPanel.add(labeled("전공 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        majorContainer = new JPanel(null);
        majorContainer.setBounds(20, y, 350, 50);
        addMajorRow(0);
        mainPanel.add(majorContainer);
        JButton addMajorBtn = new JButton("+ 항목 추가");
        addMajorBtn.setBounds(20, y + 60, 350, 30);
        addMajorBtn.addActionListener(e -> addMajorRow(majorFields.size()));
        mainPanel.add(addMajorBtn);
        y += 100;

        // 경력사항
        mainPanel.add(labeled("경력사항", "", 20, y));
        y += 20;
        expContainer = new JPanel(null);
        expContainer.setBounds(20, y, 350, 50);
        addExpRow(0);
        mainPanel.add(expContainer);
        JButton addExpBtn = new JButton("+ 항목 추가");
        addExpBtn.setBounds(20, y + 60, 350, 30);
        addExpBtn.addActionListener(e -> addExpRow(expFields.size()));
        mainPanel.add(addExpBtn);
        y += 100;

        // 자격증
        mainPanel.add(labeled("자격증", "", 20, y));
        y += 20;
        certContainer = new JPanel(null);
        certContainer.setBounds(20, y, 350, 50);
        addCertRow(0);
        mainPanel.add(certContainer);
        JButton addCertBtn = new JButton("+ 항목 추가");
        addCertBtn.setBounds(20, y + 60, 350, 30);
        addCertBtn.addActionListener(e -> addCertRow(certFields.size()));
        mainPanel.add(addCertBtn);
        y += 100;

        // 한 줄 자기소개
        mainPanel.add(labeled("한 줄 자기소개 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        introArea = new JTextArea();
        introArea.setLineWrap(true);
        introArea.setBounds(20, y, 350, 100);
        mainPanel.add(introArea);
        y += 120;

        // 협업 스타일 테스트
        mainPanel.add(labeled("협업 스타일 유형 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        testStyleBtn = new JButton("협업 유형 테스트 하러 가기");
        testStyleBtn.setBounds(20, y, 350, 40);
        testStyleBtn.addActionListener(e -> runTest());
        mainPanel.add(testStyleBtn);
        y += 60;
        styleResultLabel = new JLabel();
        styleResultLabel.setBounds(20, y, 350, 20);
        mainPanel.add(styleResultLabel);
        styleResultImage = new JLabel();
        styleResultImage.setBounds(20, y + 30, 100, 100);
        mainPanel.add(styleResultImage);
        y += 140;

        // 완료 버튼
        completeBtn = new JButton("완료");
        completeBtn.setBounds(20, y, 350, 40);
        completeBtn.addActionListener(e -> saveProfileData());
        mainPanel.add(completeBtn);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBounds(0, 0, 393, 852);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);
    }

    private JLabel labeled(String title, String hint, int x, int y) {
        JLabel lbl = new JLabel("<html><b>" + title + "</b> " + hint + "</html>");
        lbl.setBounds(x, y, 360, 20);
        return lbl;
    }

    private void addMajorRow(int idx) {
        JPanel row = new JPanel(null);
        row.setBounds(0, idx * 60, 350, 30);
        JTextField f = new JTextField(); f.setBounds(0, 0, 200, 30);
        JComboBox<String> b = new JComboBox<>(MAJOR_TYPES); b.setBounds(210, 0, 140, 30);
        row.add(f); row.add(b);
        majorContainer.add(row);
        majorFields.add(f); majorTypeBoxes.add(b);
    }

    private void addExpRow(int idx) {
        JPanel row = new JPanel(null);
        row.setBounds(0, idx * 60, 350, 30);
        JTextField f = new JTextField(); f.setBounds(0, 0, 200, 30);
        JComboBox<String> b = new JComboBox<>(EXP_TYPES); b.setBounds(210, 0, 140, 30);
        row.add(f); row.add(b);
        expContainer.add(row);
        expFields.add(f); expTypeBoxes.add(b);
    }

    private void addCertRow(int idx) {
        JPanel row = new JPanel(null);
        row.setBounds(0, idx * 60, 350, 30);
        JTextField f = new JTextField(); f.setBounds(0, 0, 260, 30);
        row.add(f); certContainer.add(row); certFields.add(f);
    }

    private void runTest() {
        test.takeTest(user, manager);
        SongiType t = test.getUserResultType();
        styleResultLabel.setText("결과: " + t);
        styleResultImage.setIcon(new ImageIcon(test.getResultImagePath()));
        profile.updateType(t, test.getResultImagePath());
    }

    private void loadExisting() {
        if (profile.getNickname() != null)
            nicknameField.setText(profile.getNickname());
        if (profile.getAdmissionYear() != null)
            yearBox.setSelectedItem(profile.getAdmissionYear());
        if (profile.getGrade() != null)
            gradeBox.setSelectedItem(profile.getGrade());
        if (profile.getisEnrolled())
            enrolledBtn.setSelected(true);
        else
            leaveBtn.setSelected(true);

        for (String m : profile.getMajors()) {
            int idx = majorFields.size();
            addMajorRow(idx);
            String name = m.substring(0, m.indexOf(" ("));
            String type = m.substring(m.indexOf("(") + 1, m.length() - 1);
            majorFields.get(idx).setText(name);
            majorTypeBoxes.get(idx).setSelectedItem(type);
        }

        for (String c : profile.getCareers()) {
            int idx = expFields.size();
            addExpRow(idx);
            String name = c.substring(0, c.indexOf(" ("));
            String type = c.substring(c.indexOf("(") + 1, c.length() - 1);
            expFields.get(idx).setText(name);
            expTypeBoxes.get(idx).setSelectedItem(type);
        }

        for (String c : profile.getCertificates()) {
            int idx = certFields.size();
            addCertRow(idx);
            certFields.get(idx).setText(c);
        }

        if (profile.getIntroduction() != null)
            introArea.setText(profile.getIntroduction());

        if (profile.getResultType() != null) {
            styleResultLabel.setText("결과: " + profile.getResultType());
            styleResultImage.setIcon(new ImageIcon(profile.getResultImagePath()));
        }
    }

    private boolean validateAll() {
        warningLabel.setText("");
        if (nicknameField.getText().trim().isEmpty()) {
            scrollTo(nicknameField);
            warningLabel.setText("닉네임을 입력해 주세요.");
            return false;
        }
        if (nicknameField.getText().length() < 2 || nicknameField.getText().length() > 16) {
            scrollTo(nicknameField);
            warningLabel.setText("닉네임은 2~16자 이내여야 합니다.");
            return false;
        }
        if (yearBox.getSelectedIndex() < 0) {
            scrollTo(yearBox);
            warningLabel.setText("입학년도를 선택해 주세요.");
            return false;
        }
        if (gradeBox.getSelectedIndex() < 0) {
            scrollTo(gradeBox);
            warningLabel.setText("학년을 선택해 주세요.");
            return false;
        }
        if (!enrolledBtn.isSelected() && !leaveBtn.isSelected()) {
            scrollTo(enrolledBtn);
            warningLabel.setText("재학 여부를 선택해 주세요.");
            return false;
        }
        if (majorFields.isEmpty() || majorFields.get(0).getText().trim().isEmpty()) {
            scrollTo(majorFields.get(0));
            warningLabel.setText("전공을 한 개 이상 작성해 주세요.");
            return false;
        }
        if (introArea.getText().trim().isEmpty()) {
            scrollTo(introArea);
            warningLabel.setText("자기소개를 작성해 주세요.");
            return false;
        }
        if (introArea.getText().length() > 70) {
            scrollTo(introArea);
            warningLabel.setText("자기소개는 70자 이내여야 합니다.");
            return false;
        }
        if (profile.getResultType() == null) {
            scrollTo(testStyleBtn);
            warningLabel.setText("협업 유형 테스트를 완료해 주세요.");
            return false;
        }
        return true;
    }

    private void scrollTo(Component c) {
        Point p = c.getLocation();
        scrollPane.getViewport().setViewPosition(new Point(p.x, p.y - 20));
    }

    private void saveProfileData() {
        if (!validateAll()) return;

        // Test 결과를 반영
        SongiType t = test.getUserResultType();
        String img = test.getResultImagePath();

        profile.editProfile(
            null,
            nicknameField.getText().trim(),
            (String) yearBox.getSelectedItem(),
            (String) gradeBox.getSelectedItem(),
            enrolledBtn.isSelected(),
            introArea.getText().trim(),
            t,
            img
        );

        // 전공/경력/자격증 추가
        for (int i = 0; i < majorFields.size(); i++) {
            profile.addMajor(
                majorFields.get(i).getText().trim(),
                (String) majorTypeBoxes.get(i).getSelectedItem()
            );
        }
        for (int i = 0; i < expFields.size(); i++) {
            profile.addCareer(
                expFields.get(i).getText().trim(),
                (String) expTypeBoxes.get(i).getSelectedItem()
            );
        }
        for (JTextField c : certFields) {
            profile.addCertification(c.getText().trim());
        }

        user.setProfile(profile);
        JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.");
        // TODO: 팀 목록 화면으로 이동
    }

    public static void main(String[] args) {
        Map<String, SongiType> map = new HashMap<>();
        // map.put(...) 으로 평가 기준 설정
        Management manager = new Management(map);

        User u = new User("홍길동", "hg123", "pw", "hg@sookmyung.ac.kr");
        u.setProfile(new Profile(1, "hg123"));
        new ProfilePage(u, manager);
    }
}