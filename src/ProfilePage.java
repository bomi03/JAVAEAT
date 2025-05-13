// ProfilePage.java - 298, 308 라인 협업 테스트 구현되면 바꾸기!

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import model.Profile;
import model.Test;
import model.SongiType;
import model.Management;
import model.User;

public class ProfilePage extends JFrame {
    private User user;
    private Profile profile;
    private Test test;
    private Management manager;

    private JScrollPane scrollPane;
    private JPanel mainPanel;
    private JLabel warningLabel;
    private JLabel imgLabel;
    private JButton uploadImgBtn;

    private JTextField nicknameField;
    private JComboBox<String> yearBox, gradeBox;
    private JToggleButton enrolledBtn, leaveBtn;
    private ButtonGroup enrolledGroup;

    private JPanel majorsPanel;
    private JButton addMajorBtn;
    private java.util.List<JTextField> majorNameFields = new ArrayList<>();
    private java.util.List<JComboBox<String>> majorTypeBoxes = new ArrayList<>();

    private JPanel careersPanel;
    private JButton addCareerBtn;
    private java.util.List<JTextField> careerNameFields = new ArrayList<>();
    private java.util.List<JComboBox<String>> careerTypeBoxes = new ArrayList<>();

    private JPanel certsPanel;
    private JButton addCertBtn;
    private java.util.List<JTextField> certFields = new ArrayList<>();

    private JTextArea introArea;
    private JButton testBtn;
    private JButton completeBtn;

    // 결과 패널: 이제 설명 이미지만 보여줍니다
    private JPanel resultPanel;
    private JLabel resultImageLabel;

    private File selectedImageFile;
    private static int nextProfileId = 1;
    private static final String[] YEARS = makeYears(2009, 2025);
    private static final String[] GRADES = { "1학년","2학년","3학년","4학년","추가학기" };
    private static final String[] MAJOR_TYPES = { "주전공","복수전공","연계전공","부전공" };
    private static final String[] CAREER_TYPES = { "수상","동아리","교내 팀플","공모전","아르바이트" };

    private static String[] makeYears(int start, int end) {
        String[] yrs = new String[end - start + 2];
        yrs[0] = "연도 선택";
        for (int i = start; i <= end; i++) {
            yrs[i - start + 1] = i + "학번";
        }
        return yrs;
    }

    private static final Color PLACEHOLDER = Color.decode("#ADADAD");
    private static final Color TEXT_COLOR = Color.BLACK;

    public ProfilePage(User user, Management manager) {
        this.user = user;
        this.manager = manager;
        Profile p = user.getProfile();
        if (p == null) {
            p = new Profile(nextProfileId++, user.getUserID());
            user.setProfile(p);
        }
        this.profile = p;
        this.test = new Test();

        setTitle("회원가입 - 프로필 작성");
        setSize(393, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
        registerListeners();

        setVisible(true);
    }

    private void buildUI() {
        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(393, 2300));
        int y = 0;

        // 뒤로가기 + 헤더
        JButton back = new JButton("<html>&lt;-</html>");
        styleGray(back);
        back.setBounds(10, 20, 40, 30);
        mainPanel.add(back);
        JLabel header = new JLabel("회원가입", SwingConstants.CENTER);
        header.setForeground(Color.decode("#4A4A4A"));
        header.setBounds(0, 20, 393, 30);
        mainPanel.add(header);
        y += 60;
        newSeparator(y); y += 20;

        // 안내문
        JLabel instr = new JLabel("눈송님께 딱 맞는 팀 매칭을 위한 프로필을 완성해 주세요 :)", SwingConstants.LEFT);
        instr.setBounds(20, y, 350, 30);
        mainPanel.add(instr);
        y += 40;

        // 이미지 업로드
        imgLabel = new JLabel();
        imgLabel.setBorder(new LineBorder(Color.GRAY, 1, true));
        imgLabel.setBounds((393 - 100) / 2, y, 100, 100);
        mainPanel.add(imgLabel);
        y += 110;
        uploadImgBtn = new JButton("이미지 업로드");
        styleGray(uploadImgBtn);
        uploadImgBtn.setBounds((393 - 120) / 2, y, 120, 30);
        mainPanel.add(uploadImgBtn);
        y += 50;

        // 경고 라벨
        warningLabel = new JLabel("", SwingConstants.LEFT);
        warningLabel.setForeground(Color.RED);
        warningLabel.setBounds(20, y, 360, 20);
        mainPanel.add(warningLabel);
        y += 30;

        // 닉네임
        mainPanel.add(labeled("닉네임 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        nicknameField = new JTextField();
        nicknameField.setBounds(20, y, 350, 30);
        nicknameField.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(nicknameField, "2~16자 이내로 입력해 주세요.");
        mainPanel.add(nicknameField);
        y += 50;

        // 입학년도 / 학년
        mainPanel.add(labeled("입학년도 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        yearBox = new JComboBox<>(YEARS);
        yearBox.setBounds(20, y, 350, 30);
        yearBox.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        mainPanel.add(yearBox);
        y += 50;
        mainPanel.add(labeled("학년 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        gradeBox = new JComboBox<>(concat(new String[]{"학년 선택"}, GRADES));
        gradeBox.setBounds(20, y, 350, 30);
        gradeBox.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        mainPanel.add(gradeBox);
        y += 50;

        // 재학 여부
        mainPanel.add(labeled("재학 여부 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        enrolledBtn = new JToggleButton("재학");
        leaveBtn = new JToggleButton("휴학");
        styleToggle(enrolledBtn);
        styleToggle(leaveBtn);
        enrolledBtn.setBounds(20, y, 170, 40);
        leaveBtn.setBounds(200, y, 170, 40);
        enrolledGroup = new ButtonGroup();
        enrolledGroup.add(enrolledBtn);
        enrolledGroup.add(leaveBtn);
        mainPanel.add(enrolledBtn);
        mainPanel.add(leaveBtn);
        y += 60;

        // 전공 (BoxLayout + no bg)
        mainPanel.add(labeled("전공 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        majorsPanel = new JPanel();
        majorsPanel.setLayout(new BoxLayout(majorsPanel, BoxLayout.Y_AXIS));
        majorsPanel.setBackground(Color.WHITE);
        JScrollPane majorsScroll = new JScrollPane(majorsPanel);
        majorsScroll.setBounds(20, y, 350, 100);
        majorsScroll.setBorder(null);
        majorsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(majorsScroll);
        addMajorBtn = new JButton("+ 항목 추가");
        styleBlue(addMajorBtn);
        addMajorBtn.setBounds(20, y + 110, 350, 30);
        mainPanel.add(addMajorBtn);
        y += 150;
        addMajorRow();

        // 경력사항
        mainPanel.add(labeled("경력사항", "", 20, y));
        y += 20;
        careersPanel = new JPanel();
        careersPanel.setLayout(new BoxLayout(careersPanel, BoxLayout.Y_AXIS));
        careersPanel.setBackground(Color.WHITE);
        JScrollPane careersScroll = new JScrollPane(careersPanel);
        careersScroll.setBounds(20, y, 350, 100);
        careersScroll.setBorder(null);
        careersScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(careersScroll);
        addCareerBtn = new JButton("+ 항목 추가");
        styleBlue(addCareerBtn);
        addCareerBtn.setBounds(20, y + 110, 350, 30);
        mainPanel.add(addCareerBtn);
        y += 150;
        addCareerRow();

        // 자격증
        mainPanel.add(labeled("자격증", "", 20, y));
        y += 20;
        certsPanel = new JPanel();
        certsPanel.setLayout(new BoxLayout(certsPanel, BoxLayout.Y_AXIS));
        certsPanel.setBackground(Color.WHITE);
        JScrollPane certsScroll = new JScrollPane(certsPanel);
        certsScroll.setBounds(20, y, 350, 80);
        certsScroll.setBorder(null);
        certsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(certsScroll);
        addCertBtn = new JButton("+ 항목 추가");
        styleBlue(addCertBtn);
        addCertBtn.setBounds(20, y + 90, 350, 30);
        mainPanel.add(addCertBtn);
        y += 120;
        addCertRow();

        // 자기소개
        mainPanel.add(labeled("한 줄 자기소개 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        introArea = new JTextArea();
        introArea.setLineWrap(true);
        introArea.setBounds(20, y, 350, 80);
        introArea.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(introArea, "자기소개를 70자 이내로 작성해 주세요.");
        mainPanel.add(introArea);
        y += 100;

        // 테스트 버튼
        testBtn = new JButton("협업 유형 테스트 하러 가기");
        styleBlue(testBtn);
        testBtn.setBounds(20, y, 350, 40);
        mainPanel.add(testBtn);
        y += 60;

        // 결과 패널 (설명 이미지만)
        resultPanel = new JPanel(null);
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setBounds(20, y, 350, 200);
        resultPanel.setVisible(false);
        mainPanel.add(resultPanel);

        resultImageLabel = new JLabel();
        resultImageLabel.setBounds(0, 0, 350, 200);
        resultPanel.add(resultImageLabel);

        // 완료 버튼
        completeBtn = new JButton("완료");
        styleBlue(completeBtn);
        completeBtn.setBounds(20, y + 220, 350, 40);
        mainPanel.add(completeBtn);

        // 스크롤
        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBounds(0, 0, 393, 900);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
    }

    private void registerListeners() {
        ((JButton)mainPanel.getComponent(0)).addActionListener(e -> {
            dispose();
            new TeamListPage();
        });
        uploadImgBtn.addActionListener(e -> {
            JFileChooser ch = new JFileChooser();
            if (ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = ch.getSelectedFile();
                ImageIcon icon = new ImageIcon(
                    new ImageIcon(selectedImageFile.getAbsolutePath())
                        .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)
                );
                imgLabel.setIcon(icon);
            }
        });
        addMajorBtn.addActionListener(e -> addMajorRow());
        addCareerBtn.addActionListener(e -> addCareerRow());
        addCertBtn.addActionListener(e -> addCertRow());
        testBtn.addActionListener(e -> {
            // TODO: 나중에 TestPage 연결  testBtn.addActionListener(e ->new TestPage(user, manager, test, this));
            // 지금은 더미로 '평화송이' 타입 결과를 강제로 세팅
            profile.updateType(
                SongiType.평화송이,
                SongiType.평화송이.getImagePath()
            );
            showResultPanel(); 
            JOptionPane.showMessageDialog(this, "테스트 결과를 미리 보기입니다!");
        });
        completeBtn.addActionListener(e -> {
            // 테스트가 안 되어 있을 때도 결과 패널을 보려면 똑같이 스텁 처리 - 테스트 안하고 다음 페이지 넘어가려고 추가한 것 나중에 바꾸기!
            if (profile.getResultType() == null) {
                profile.updateType(
                    SongiType.평화송이,
                    SongiType.평화송이.getImagePath()
                );
            }
            showResultPanel();
            JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다!");
            dispose();
            new TeamListPage();
        });
    }

    private void onSubmit() {
        warningLabel.setText("");

        String nick = nicknameField.getText().trim();
        if (nick.isEmpty() || nick.equals("2~16자 이내로 입력해 주세요.")) {
            warningLabel.setText("닉네임을 입력해 주세요."); return;
        }
        if (nick.length() < 2 || nick.length() > 16) {
            warningLabel.setText("닉네임은 2~16자 이내여야 합니다."); return;
        }
        if (yearBox.getSelectedIndex() == 0) {
            warningLabel.setText("입학년도를 선택해 주세요."); return;
        }
        if (gradeBox.getSelectedIndex() == 0) {
            warningLabel.setText("학년을 선택해 주세요."); return;
        }
        if (!enrolledBtn.isSelected() && !leaveBtn.isSelected()) {
            warningLabel.setText("재학 여부를 선택해 주세요."); return;
        }

        boolean hasMajor = majorNameFields.stream()
            .anyMatch(f -> {
                String t = f.getText().trim();
                return !t.isEmpty() && !t.equals("전공명을 작성해 주세요.");
            });
        if (!hasMajor) {
            warningLabel.setText("전공을 한 개 이상 작성해 주세요."); return;
        }

        String intro = introArea.getText().trim();
        if (intro.isEmpty() || intro.equals("자기소개를 70자 이내로 작성해 주세요.")) {
            warningLabel.setText("자기소개를 작성해 주세요."); return;
        }
        if (intro.length() > 70) {
            warningLabel.setText("자기소개는 70자 이내여야 합니다."); return;
        }

        if (profile.getResultType() == null) {
            warningLabel.setText("협업 유형 테스트를 완료해 주세요."); return;
        }

        // Profile 업데이트
        profile.setProfileImagePath(
            selectedImageFile != null ? selectedImageFile.getAbsolutePath() : ""
        );
        profile.setNickname(nick);
        profile.setAdmissionYear((String)yearBox.getSelectedItem());
        profile.setGrade((String)gradeBox.getSelectedItem());
        profile.setEnrolled(enrolledBtn.isSelected());
        profile.setIntroduction(intro);

        for (int i = 0; i < majorNameFields.size(); i++) {
            JTextField f = majorNameFields.get(i);
            String m = f.getText().trim();
            String t = (String) majorTypeBoxes.get(i).getSelectedItem();
            if (!m.isEmpty() && !"전공명을 작성해 주세요.".equals(m)) {
                profile.addMajor(m, t);
            }
        }
        for (int i = 0; i < careerNameFields.size(); i++) {
            JTextField f = careerNameFields.get(i);
            String c = f.getText().trim();
            String t = (String) careerTypeBoxes.get(i).getSelectedItem();
            if (!c.isEmpty() && !"활동명을 작성해 주세요.".equals(c)) {
                    profile.addCareer(c, t);
            }
    }
       for (JTextField f : certFields) {
            String c = f.getText().trim();
            if (!c.isEmpty() && !"취득한 자격증명을 작성해 주세요.".equals(c)) {
                    profile.addCertification(c);
        }
    }

        user.setProfile(profile);
        JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다!");
        dispose();
        new TeamListPage();
    }

    /**
     * 결과 패널에 이미지만 보여줍니다.
     */
    public void showResultPanel() {
        SongiType type = profile.getResultType();
        if (type == null) return;

        // Profile.getResultImagePath() 에서 설명 이미지 경로를 반환한다고 가정
        String imgPath = profile.getResultImagePath();
        ImageIcon icon = new ImageIcon(
            new ImageIcon(imgPath)
                .getImage().getScaledInstance(350, 200, Image.SCALE_SMOOTH)
        );
        resultImageLabel.setIcon(icon);

        resultPanel.setVisible(true);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void addMajorRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(350, 35));
        JTextField name = new JTextField();
        name.setPreferredSize(new Dimension(230, 30));
        setPlaceholder(name, "전공명을 작성해 주세요.");
        JComboBox<String> type = new JComboBox<>(MAJOR_TYPES);
        type.setPreferredSize(new Dimension(110, 30));
        row.add(name);
        row.add(type);
        majorsPanel.add(row);
        majorsPanel.revalidate();
        majorsPanel.repaint();
        majorNameFields.add(name);
        majorTypeBoxes.add(type);
    }

    private void addCareerRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(350, 35));
        JTextField name = new JTextField();
        name.setPreferredSize(new Dimension(230, 30));
        setPlaceholder(name, "활동명을 작성해 주세요.");
        JComboBox<String> type = new JComboBox<>(CAREER_TYPES);
        type.setPreferredSize(new Dimension(110, 30));
        row.add(name);
        row.add(type);
        careersPanel.add(row);
        careersPanel.revalidate();
        careersPanel.repaint();
        careerNameFields.add(name);
        careerTypeBoxes.add(type);
    }

    private void addCertRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(350, 35));
        JTextField cert = new JTextField();
        cert.setPreferredSize(new Dimension(350, 30));
        setPlaceholder(cert, "취득한 자격증명을 작성해 주세요.");
        row.add(cert);
        certsPanel.add(row);
        certsPanel.revalidate();
        certsPanel.repaint();
        certFields.add(cert);
    }

    private JLabel labeled(String text, String hint, int x, int y) {
        JLabel l = new JLabel(String.format(
            "<html><span style='color:#4A4A4A'><b>%s</b></span> <span style='color:#BABABA'>%s</span></html>",
            text, hint));
        l.setBounds(x, y, 360, 20);
        return l;
    }

    private void styleGray(AbstractButton b) {
        b.setBackground(Color.decode("#D9D9D9"));
        b.setForeground(Color.decode("#4A4A4A"));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }

    private void styleBlue(AbstractButton b) {
        b.setBackground(Color.decode("#003087"));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }

    private void styleToggle(JToggleButton t) {
        styleGray(t);
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

    private void setPlaceholder(JTextComponent comp, String placeholder) {
        comp.setText(placeholder);
        comp.setForeground(PLACEHOLDER);
        comp.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (comp.getText().equals(placeholder)) {
                    comp.setText("");
                    comp.setForeground(TEXT_COLOR);
                }
            }
            public void focusLost(FocusEvent e) {
                if (comp.getText().isEmpty()) {
                    comp.setText(placeholder);
                    comp.setForeground(PLACEHOLDER);
                }
            }
        });
    }

    private void newSeparator(int y) {
        JSeparator s = new JSeparator();
        s.setBounds(0, y, 393, 1);
        s.setForeground(Color.decode("#D9D9D9"));
        mainPanel.add(s);
    }

    private String[] concat(String[] a, String[] b) {
        String[] c = new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Map<String, SongiType> map = new HashMap<>();
            Management manager = new Management(map);
            User u = new User("홍길동", "hg123", "pw", "hg@sookmyung.ac.kr");
            Profile p = new Profile(1, "hg123");
            u.setProfile(p);
            new ProfilePage(u, manager);
        });
    }
}