import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
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

    private JLabel profileImageLabel;
    private JButton uploadImageBtn;
    private JTextField nicknameField;
    private JButton duplicateCheckBtn;
    private JComboBox<String> yearBox, gradeBox;
    private JToggleButton enrolledBtn, leaveBtn;
    private java.util.List<JTextField> majorFields = new ArrayList<>();
    private java.util.List<JComboBox<String>> majorTypeBoxes = new ArrayList<>();
    private JPanel majorContainer;
    private JButton addMajorBtn;
    private java.util.List<JTextField> expFields = new ArrayList<>();
    private java.util.List<JComboBox<String>> expTypeBoxes = new ArrayList<>();
    private JPanel expContainer;
    private JButton addExpBtn;
    private java.util.List<JTextField> certFields = new ArrayList<>();
    private JPanel certContainer;
    private JButton addCertBtn;
    private JTextArea introArea;
    private JButton testStyleBtn, completeBtn;
    private JLabel styleResultLabel, styleResultImage;

    private static final String[] YEARS = {
        "연도 선택(학번)",
        "2009학번","2010학번","2011학번","2012학번","2013학번",
        "2014학번","2015학번","2016학번","2017학번","2018학번",
        "2019학번","2020학번","2021학번","2022학번","2023학번",
        "2024학번","2025학번"
    };
    private static final String[] GRADES = {"학년 선택", "1학년","2학년","3학년","4학년","추가학기"};
    private static final String[] MAJOR_TYPES = {"주전공","복수전공","연계전공","부전공"};
    private static final String[] EXP_TYPES = {"수상","동아리","교내 팀플","공모전","아르바이트"};

    private Set<String> existingNicknames = new HashSet<>(Arrays.asList("홍길동","testUser"));

    public ProfilePage(User user, Management manager) {
        this.user = user;
        this.profile = user.getProfile();
        this.test = new Test();
        this.manager = manager;

        setTitle("회원가입 - 프로필 작성");
        setSize(393, 852);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        buildUI();
        loadExisting();
        registerListeners();

        setVisible(true);
    }

    private void buildUI() {
        getContentPane().setBackground(Color.WHITE);

        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(393, 1600));
        int y = 0;

        // 뒤로가기 + 타이틀
        JButton backBtn = new JButton("<html>&lt;-</html>");
        styleGrayButton(backBtn);
        backBtn.setBounds(10, y+20, 40, 30);
        mainPanel.add(backBtn);

        JLabel title = new JLabel("회원가입", SwingConstants.CENTER);
        title.setForeground(Color.decode("#4A4A4A"));
        title.setBounds(0, y+20, 393, 30);
        mainPanel.add(title);
        y += 60;

        // 구분선
        JSeparator sep = new JSeparator();
        sep.setBounds(0, y, 393, 1);
        sep.setForeground(Color.decode("#8A8888"));
        mainPanel.add(sep);
        y += 22;

        // 안내문구
        JLabel guide = new JLabel(
            "<html><span style='color:#4A4A4A'>눈송님께 딱 맞는 팀 매칭을 위한<br>프로필을 완성해 주세요 :)</span></html>",
            SwingConstants.CENTER);
        guide.setBounds(0, y, 393, 40);
        mainPanel.add(guide);
        y += 60;

        // 프로필 이미지
        profileImageLabel = new JLabel();
        profileImageLabel.setBorder(new LineBorder(Color.GRAY, 1, true));
        profileImageLabel.setBounds((393-100)/2, y, 100, 100);
        mainPanel.add(profileImageLabel);
        y += 110;

        // 이미지 업로드 버튼
        uploadImageBtn = new JButton("이미지 업로드");
        styleGrayButton(uploadImageBtn);
        uploadImageBtn.setBounds((393-120)/2, y, 120, 30);
        mainPanel.add(uploadImageBtn);
        y += 50;

        // 경고 라벨
        warningLabel = new JLabel();
        warningLabel.setForeground(Color.RED);
        warningLabel.setBounds(20, y, 360, 20);
        mainPanel.add(warningLabel);
        y += 30;

        // 닉네임
        mainPanel.add(labeled("닉네임 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        nicknameField = new JTextField();
        nicknameField.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        nicknameField.setBounds(20, y, 240, 30);
        mainPanel.add(nicknameField);
        duplicateCheckBtn = new JButton("중복 확인");
        styleGrayButton(duplicateCheckBtn);
        duplicateCheckBtn.setBounds(270, y, 100, 30);
        mainPanel.add(duplicateCheckBtn);
        y += 40;

        // 입학년도
        mainPanel.add(labeled("입학년도 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        yearBox = new JComboBox<>(YEARS);
        yearBox.setBounds(20, y, 350, 30);
        yearBox.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        mainPanel.add(yearBox);
        y += 50;

        // 학년
        mainPanel.add(labeled("학년 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        gradeBox = new JComboBox<>(GRADES);
        gradeBox.setBounds(20, y, 350, 30);
        gradeBox.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        mainPanel.add(gradeBox);
        y += 50;

        // 재학 여부
        mainPanel.add(labeled("재학 여부 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        enrolledBtn = new JToggleButton("재학");
        leaveBtn    = new JToggleButton("휴학");
        styleToggleButton(enrolledBtn);
        styleToggleButton(leaveBtn);
        enrolledBtn.setBounds(20, y, 170, 40);
        leaveBtn   .setBounds(200, y, 170, 40);
        ButtonGroup bg = new ButtonGroup(); bg.add(enrolledBtn); bg.add(leaveBtn);
        mainPanel.add(enrolledBtn);
        mainPanel.add(leaveBtn);
        y += 60;

        // 전공
        mainPanel.add(labeled("전공 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        majorContainer = new JPanel();
        majorContainer.setLayout(new BoxLayout(majorContainer, BoxLayout.Y_AXIS));
        majorContainer.setBackground(Color.WHITE);
        majorContainer.setBounds(20, y, 350, 50);
        mainPanel.add(majorContainer);
        addMajorRow();
        addMajorBtn = new JButton("<html>&#43; 항목 추가</html>");
        styleBlueButton(addMajorBtn);
        addMajorBtn.setBounds(20, y+60, 350, 30);
        mainPanel.add(addMajorBtn);
        y += 100;

        // 경력사항
        mainPanel.add(labeled("경력사항", "", 20, y));
        y += 20;
        expContainer = new JPanel();
        expContainer.setLayout(new BoxLayout(expContainer, BoxLayout.Y_AXIS));
        expContainer.setBackground(Color.WHITE);
        expContainer.setBounds(20, y, 350, 50);
        mainPanel.add(expContainer);
        addExpRow();
        addExpBtn = new JButton("<html>&#43; 항목 추가</html>");
        styleBlueButton(addExpBtn);
        addExpBtn.setBounds(20, y+60, 350, 30);
        mainPanel.add(addExpBtn);
        y += 100;

        // 자격증
        mainPanel.add(labeled("자격증", "", 20, y));
        y += 20;
        certContainer = new JPanel();
        certContainer.setLayout(new BoxLayout(certContainer, BoxLayout.Y_AXIS));
        certContainer.setBackground(Color.WHITE);
        certContainer.setBounds(20, y, 350, 50);
        mainPanel.add(certContainer);
        addCertRow();
        addCertBtn = new JButton("<html>&#43; 항목 추가</html>");
        styleBlueButton(addCertBtn);
        addCertBtn.setBounds(20, y+60, 350, 30);
        mainPanel.add(addCertBtn);
        y += 100;

        // 한 줄 자기소개
        mainPanel.add(labeled("한 줄 자기소개 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        introArea = new JTextArea();
        introArea.setLineWrap(true);
        introArea.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        introArea.setBounds(20, y, 350, 100);
        mainPanel.add(introArea);
        y += 120;

        // 협업 스타일 테스트 버튼
        mainPanel.add(labeled("협업 스타일 유형 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        testStyleBtn = new JButton("협업 유형 테스트 하러 가기");
        styleBlueButton(testStyleBtn);
        testStyleBtn.setBounds(20, y, 350, 40);
        mainPanel.add(testStyleBtn);
        y += 60;

        styleResultLabel = new JLabel();
        styleResultLabel.setBounds(20, y, 350, 20);
        mainPanel.add(styleResultLabel);
        styleResultImage = new JLabel();
        styleResultImage.setBounds(20, y+30, 100, 100);
        mainPanel.add(styleResultImage);
        y += 140;

        // 완료 버튼
        completeBtn = new JButton("완료");
        styleBlueButton(completeBtn);
        completeBtn.setBounds(20, y, 350, 40);
        mainPanel.add(completeBtn);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBounds(0, 0, 393, 852);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane);
    }

    private JLabel labeled(String title, String hint, int x, int y) {
        JLabel l = new JLabel(
            String.format(
                "<html><span style='color:#4A4A4A'><b>%s</b></span> " +
                "<span style='color:#BABABA'>%s</span></html>",
                title, hint));
        l.setBounds(x, y, 360, 20);
        return l;
    }

    private void styleGrayButton(AbstractButton b) {
        b.setBackground(Color.decode("#D9D9D9"));
        b.setForeground(Color.decode("#4A4A4A"));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }

    private void styleBlueButton(AbstractButton b) {
        b.setBackground(Color.decode("#003087"));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }

    private void styleToggleButton(JToggleButton t) {
        styleGrayButton(t);
        t.addItemListener(e -> {
            if (t.isSelected()) {
                t.setBackground(Color.decode("#003087"));
                t.setForeground(Color.WHITE);
            } else {
                t.setBackground(Color.decode("#D9D9D9"));
                t.setForeground(Color.decode("#4A4A4A"));
            }
        });
    }

    private void addMajorRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(350, 30));

        // 전공명
        JTextField f = new JTextField();
        f.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        f.setPreferredSize(new Dimension(240, 25));

        // 전공유형
        JComboBox<String> b = new JComboBox<>(MAJOR_TYPES);
        b.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        b.setPreferredSize(new Dimension(100, 25));

        row.add(f);
        row.add(b);
        majorContainer.add(row);
        majorFields.add(f);
        majorTypeBoxes.add(b);
        majorContainer.revalidate();
    }

    private void addExpRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(350, 35));

        // 경력사항명
        JTextField f = new JTextField();
        f.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        f.setPreferredSize(new Dimension(240, 25));

        // 경력유형
        JComboBox<String> b = new JComboBox<>(EXP_TYPES);
        b.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        b.setPreferredSize(new Dimension(100, 25));

        row.add(f);
        row.add(b);
        expContainer.add(row);
        expFields.add(f);
        expTypeBoxes.add(b);
        expContainer.revalidate();
    }

    private void addCertRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(350, 30));

        // 자격증명
        JTextField f = new JTextField();
        f.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        f.setPreferredSize(new Dimension(350, 25));

        row.add(f);
        certContainer.add(row);
        certFields.add(f);
        certContainer.revalidate();
    }

    private void registerListeners() {
        uploadImageBtn.addActionListener(e -> {
            JFileChooser ch = new JFileChooser();
            if (ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = ch.getSelectedFile();
                profile.setProfileImagePath(file.getAbsolutePath());
                ImageIcon icon = new ImageIcon(
                    new ImageIcon(file.getAbsolutePath())
                        .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)
                );
                profileImageLabel.setIcon(icon);
            }
        });

        duplicateCheckBtn.addActionListener(e -> {
            warningLabel.setForeground(Color.RED);
            String nick = nicknameField.getText().trim();
            if (nick.isEmpty()) {
                warningLabel.setText("닉네임을 입력해 주세요.");
            } else if (existingNicknames.contains(nick)) {
                warningLabel.setText("사용할 수 없는 닉네임입니다.");
            } else {
                warningLabel.setForeground(Color.decode("#008000"));
                warningLabel.setText("사용 가능한 닉네임입니다.");
            }
        });

        addMajorBtn.addActionListener(e -> addMajorRow());
        addExpBtn .addActionListener(e -> addExpRow());
        addCertBtn.addActionListener(e -> addCertRow());

        testStyleBtn .addActionListener(e -> runTest());
        completeBtn .addActionListener(e -> saveProfileData());
    }

    private void runTest() {
        test.takeTest(user, manager);
        SongiType t = test.getUserResultType();
        styleResultLabel.setText("결과: " + t);
        styleResultImage.setIcon(new ImageIcon(test.getResultImagePath()));
        profile.updateType(t, test.getResultImagePath());
    }

    private void loadExisting() {
        if (profile.getNickname() != null) nicknameField.setText(profile.getNickname());
        if (profile.getAdmissionYear() != null) yearBox.setSelectedItem(profile.getAdmissionYear());
        if (profile.getGrade() != null) gradeBox.setSelectedItem(profile.getGrade());
        if (profile.getisEnrolled()) enrolledBtn.setSelected(true);
        else leaveBtn.setSelected(true);

        // 기존 전공
        majorFields.clear();
        majorTypeBoxes.clear();
        majorContainer.removeAll();
        for (String m : profile.getMajors()) {
            addMajorRow();
            int i = majorFields.size() - 1;
            String name = m.substring(0, m.indexOf(" ("));
            String tp = m.substring(m.indexOf("(") + 1, m.length() - 1);
            majorFields.get(i).setText(name);
            majorTypeBoxes.get(i).setSelectedItem(tp);
        }

        // 기존 경력
        expFields.clear();
        expTypeBoxes.clear();
        expContainer.removeAll();
        for (String c : profile.getCareers()) {
            addExpRow();
            int i = expFields.size() - 1;
            String name = c.substring(0, c.indexOf(" ("));
            String tp = c.substring(c.indexOf("(") + 1, c.length() - 1);
            expFields.get(i).setText(name);
            expTypeBoxes.get(i).setSelectedItem(tp);
        }

        // 기존 자격증
        certFields.clear();
        certContainer.removeAll();
        for (String c : profile.getCertificates()) {
            addCertRow();
            int i = certFields.size() - 1;
            certFields.get(i).setText(c);
        }

        if (profile.getIntroduction() != null) introArea.setText(profile.getIntroduction());
        if (profile.getResultType() != null) {
            styleResultLabel.setText("결과: " + profile.getResultType());
            styleResultImage.setIcon(new ImageIcon(profile.getResultImagePath()));
        }
    }

    private boolean validateAll() {
        warningLabel.setForeground(Color.RED);
        warningLabel.setText("");
        if (nicknameField.getText().trim().isEmpty()) {
            scrollTo(nicknameField);
            warningLabel.setText("닉네임을 입력해 주세요."); return false;
        }
        if (nicknameField.getText().length() < 2 || nicknameField.getText().length() > 16) {
            scrollTo(nicknameField);
            warningLabel.setText("닉네임은 2~16자 이내여야 합니다."); return false;
        }
        if (existingNicknames.contains(nicknameField.getText().trim())) {
            scrollTo(nicknameField);
            warningLabel.setText("사용할 수 없는 닉네임입니다."); return false;
        }
        if (yearBox.getSelectedIndex() < 0) {
            scrollTo(yearBox);
            warningLabel.setText("입학년도를 선택해 주세요."); return false;
        }
        if (yearBox.getSelectedIndex() == 0) {
            scrollTo(yearBox);
            warningLabel.setText("입학년도를 선택해 주세요.");
            return false;
        }   
        if (gradeBox.getSelectedIndex() < 0) {
            scrollTo(gradeBox);
            warningLabel.setText("학년을 선택해 주세요."); return false;
        }
        if (gradeBox.getSelectedIndex() == 0) {
            scrollTo(gradeBox);
            warningLabel.setText("학년을 선택해 주세요.");
            return false;
        }   
        if (!enrolledBtn.isSelected() && !leaveBtn.isSelected()) {
            scrollTo(enrolledBtn);
            warningLabel.setText("재학 여부를 선택해 주세요."); return false;
        }
        if (majorFields.isEmpty() || majorFields.get(0).getText().trim().isEmpty()) {
            scrollTo(majorFields.get(0));
            warningLabel.setText("전공을 한 개 이상 작성해 주세요."); return false;
        }
        if (introArea.getText().trim().isEmpty()) {
            scrollTo(introArea);
            warningLabel.setText("자기소개를 작성해 주세요."); return false;
        }
        if (introArea.getText().length() > 70) {
            scrollTo(introArea);
            warningLabel.setText("자기소개는 70자 이내여야 합니다."); return false;
        }
        if (test.getUserResultType() == null) {
            scrollTo(testStyleBtn);
            warningLabel.setText("협업 유형 테스트를 완료해 주세요."); return false;
        }
        return true;
    }

    private void scrollTo(Component c) {
        Point p = c.getLocation();
        scrollPane.getViewport().setViewPosition(new Point(p.x, p.y - 20));
    }

    private void saveProfileData() {
        if (!validateAll()) return;

        SongiType t = test.getUserResultType();
        String img = test.getResultImagePath();
        profile.editProfile(
            profile.getProfileImagePath(),
            nicknameField.getText().trim(),
            (String) yearBox.getSelectedItem(),
            (String) gradeBox.getSelectedItem(),
            enrolledBtn.isSelected(),
            introArea.getText().trim(),
            t, img
        );

        profile.getMajors().clear();
        for (int i = 0; i < majorFields.size(); i++) {
            profile.addMajor(
                majorFields.get(i).getText().trim(),
                (String) majorTypeBoxes.get(i).getSelectedItem()
            );
        }

        profile.getCareers().clear();
        for (int i = 0; i < expFields.size(); i++) {
            profile.addCareer(
                expFields.get(i).getText().trim(),
                (String) expTypeBoxes.get(i).getSelectedItem()
            );
        }

        profile.getCertificates().clear();
        for (JTextField f : certFields) {
            profile.addCertification(f.getText().trim());
        }

        user.setProfile(profile);
        JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.");
        // TODO: 팀 목록 화면으로 이동
    }

    public static void main(String[] args) {
        Map<String,SongiType> map = new HashMap<>();
        Management manager = new Management(map);
        User u = new User("홍길동","hg123","pw","hg@sookmyung.ac.kr");
        u.setProfile(new Profile(1,"hg123"));
        new ProfilePage(u, manager);
    }
}