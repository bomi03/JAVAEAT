// ProfilePage.java - 프로필 작성 및 프로필 편집 페이지

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
import dialog.CollaborationTypeTestDialog;

public class ProfilePage extends JFrame {
    private User user;
    private Profile profile;
    private Test test;
    private Management manager;

    private boolean isEditMode;
    private MyPage parentPage;

    private JScrollPane scrollPane;
    private JPanel mainPanel;
    private JLabel warningLabel, imgLabel;
    private JButton uploadImgBtn;

    private JTextField nicknameField;
    private JComboBox<String> yearBox, gradeBox;
    private JToggleButton enrolledBtn, leaveBtn;
    private ButtonGroup enrolledGroup;

    private JPanel majorsPanel, careersPanel, certsPanel;
    private JButton addMajorBtn, addCareerBtn, addCertBtn;
    private java.util.List<JTextField> majorNameFields = new ArrayList<>();
    private java.util.List<JComboBox<String>> majorTypeBoxes = new ArrayList<>();
    private java.util.List<JTextField> careerNameFields = new ArrayList<>();
    private java.util.List<JComboBox<String>> careerTypeBoxes = new ArrayList<>();
    private java.util.List<JTextField> certFields = new ArrayList<>();

    private JTextArea introArea;
    private JButton testBtn, completeBtn;

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
    private static final Color TEXT_COLOR   = Color.BLACK;

    // 높이 상수
    private static final int H20 = 20, H30 = 30, H40 = 40, H60 = 60, H110 = 110, H150 = 150;

    // 생성 모드
    public ProfilePage(User user, Management manager) {
        this(user, manager, false, null);
    }

    // 편집 모드
    public ProfilePage(User user, Management manager, boolean isEditMode, MyPage parentPage) {
        this.user = user;
        this.manager = manager;
        this.isEditMode = isEditMode;
        this.parentPage = parentPage;

        // 프로필 초기화 또는 생성
        Profile p = user.getProfile();
        if (p == null) {
            p = new Profile(nextProfileId++, user.getUserID());
            user.setProfile(p);
        }
        this.profile = p;
        this.test = new Test();

        setTitle(isEditMode ? "프로필 편집" : "회원가입 - 프로필 작성");
        setSize(393, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
        if (isEditMode) prefillFields();
        registerListeners();

        setVisible(true);
    }

    private void buildUI() {
        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(393, 2300));
        int y = 0;

        // 뒤로가기 + 헤더
        JButton back = new JButton(isEditMode ? "←" : "<html>&lt;-</html>");
        styleGray(back);
        back.setBounds(10, 20, 60, H30);
        mainPanel.add(back);

        JLabel header = new JLabel(isEditMode ? "프로필 편집" : "회원가입", SwingConstants.CENTER);
        header.setForeground(Color.decode("#4A4A4A"));
        header.setBounds(0, 20, 393, H30);
        mainPanel.add(header);

        y += 60;
        newSeparator(y); y += 20;

        if (!isEditMode) {
            JLabel instr = new JLabel("눈송님께 딱 맞는 팀 매칭을 위한 프로필을 완성해 주세요 :)");
            instr.setBounds(20, y, 350, H30);
            mainPanel.add(instr);
            y += 40;
        }

        // 이미지 업로드
        imgLabel = new JLabel();
        imgLabel.setBorder(new LineBorder(Color.GRAY,1,true));
        imgLabel.setBounds((393-100)/2, y, 100, 100);
        mainPanel.add(imgLabel);
        y += 110;

        uploadImgBtn = new JButton("이미지 업로드");
        styleGray(uploadImgBtn);
        uploadImgBtn.setBounds((393-120)/2, y, 120, H30);
        mainPanel.add(uploadImgBtn);
        y += 50;

        warningLabel = new JLabel("", SwingConstants.LEFT);
        warningLabel.setForeground(Color.RED);
        warningLabel.setBounds(20, y, 360, H20);
        mainPanel.add(warningLabel);
        y += 30;

        // 닉네임
        mainPanel.add(labeled("닉네임 *","필수 입력 항목입니다.",20,y));
        y += H20;
        nicknameField = new JTextField();
        nicknameField.setBounds(20, y, 350, H30);
        nicknameField.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(nicknameField,"2~16자 이내로 입력해 주세요.");
        mainPanel.add(nicknameField);
        y += 50;

        // 입학년도 / 학년
        mainPanel.add(labeled("입학년도 *","필수 입력 항목입니다.",20,y));
        y += H20;
        yearBox = new JComboBox<>(YEARS);
        yearBox.setBounds(20, y, 350, H30);
        yearBox.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        mainPanel.add(yearBox);
        y += 50;

        mainPanel.add(labeled("학년 *","필수 입력 항목입니다.",20,y));
        y += H20;
        gradeBox = new JComboBox<>(concat(new String[]{"학년 선택"}, GRADES));
        gradeBox.setBounds(20, y, 350, H30);
        gradeBox.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        mainPanel.add(gradeBox);
        y += 50;

        // 재학 여부
        mainPanel.add(labeled("재학 여부 *","필수 입력 항목입니다.",20,y));
        y += H20;
        enrolledBtn = new JToggleButton("재학");
        leaveBtn    = new JToggleButton("휴학");
        styleToggle(enrolledBtn);
        styleToggle(leaveBtn);
        enrolledBtn.setBounds(20, y, 170, H40);
        leaveBtn   .setBounds(200, y, 170, H40);
        enrolledGroup = new ButtonGroup();
        enrolledGroup.add(enrolledBtn);
        enrolledGroup.add(leaveBtn);
        mainPanel.add(enrolledBtn);
        mainPanel.add(leaveBtn);
        y += 60;

        // 전공
        mainPanel.add(labeled("전공 *","필수 입력 항목입니다.",20,y));
        y += H20;
        majorsPanel = new JPanel();
        majorsPanel.setLayout(new BoxLayout(majorsPanel,BoxLayout.Y_AXIS));
        majorsPanel.setBackground(Color.WHITE);
        JScrollPane majorsScroll = new JScrollPane(majorsPanel);
        majorsScroll.setBounds(20, y, 350, H110);
        majorsScroll.setBorder(null);
        majorsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(majorsScroll);
        addMajorBtn = new JButton("+ 항목 추가");
        styleBlue(addMajorBtn);
        addMajorBtn.setBounds(20, y+H110, 350, H30);
        mainPanel.add(addMajorBtn);
        y += H150;
        addMajorRow();

        // 경력사항
        mainPanel.add(labeled("경력사항","",20,y));
        y += H20;
        careersPanel = new JPanel();
        careersPanel.setLayout(new BoxLayout(careersPanel,BoxLayout.Y_AXIS));
        careersPanel.setBackground(Color.WHITE);
        JScrollPane careersScroll = new JScrollPane(careersPanel);
        careersScroll.setBounds(20, y, 350, H110);
        careersScroll.setBorder(null);
        careersScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(careersScroll);
        addCareerBtn = new JButton("+ 항목 추가");
        styleBlue(addCareerBtn);
        addCareerBtn.setBounds(20, y+H110, 350, H30);
        mainPanel.add(addCareerBtn);
        y += H150;
        addCareerRow();

        // 자격증
        mainPanel.add(labeled("자격증","",20,y));
        y += H20;
        certsPanel = new JPanel();
        certsPanel.setLayout(new BoxLayout(certsPanel,BoxLayout.Y_AXIS));
        certsPanel.setBackground(Color.WHITE);
        JScrollPane certsScroll = new JScrollPane(certsPanel);
        certsScroll.setBounds(20, y, 350, 80);
        certsScroll.setBorder(null);
        certsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(certsScroll);
        addCertBtn = new JButton("+ 항목 추가");
        styleBlue(addCertBtn);
        addCertBtn.setBounds(20, y+90, 350, H30);
        mainPanel.add(addCertBtn);
        y += 120;
        addCertRow();

        // 자기소개
        mainPanel.add(labeled("한 줄 자기소개 *","필수 입력 항목입니다.",20,y));
        y += H20;
        introArea = new JTextArea();
        introArea.setLineWrap(true);
        introArea.setBounds(20, y, 350, 80);
        introArea.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(introArea,"자기소개를 70자 이내로 작성해 주세요.");
        mainPanel.add(introArea);
        y += 100;

        // 테스트 버튼
        testBtn = new JButton("협업 유형 테스트 하러 가기");
        styleBlue(testBtn);
        testBtn.setBounds(20, y, 350, H40);
        mainPanel.add(testBtn);
        y += 60;

        // 결과 패널
        resultPanel = new JPanel(null);
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setBounds(20, y, 350, 200);
        resultPanel.setVisible(false);
        mainPanel.add(resultPanel);

        resultImageLabel = new JLabel();
        resultImageLabel.setBounds(0, 0, 350, 200);
        resultPanel.add(resultImageLabel);

        // 완료/저장 버튼
        completeBtn = new JButton("완료");
        styleBlue(completeBtn);
        completeBtn.setBounds(20, y+220, 350, H40);
        mainPanel.add(completeBtn);

        // 스크롤
        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBounds(0, 0, 393, 900);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);
    }

    private void prefillFields() {
        // 이미지
        String imgPath = profile.getProfileImagePath();
        if (imgPath != null && !imgPath.isEmpty()) {
            selectedImageFile = new File(imgPath);
            imgLabel.setIcon(new ImageIcon(
                new ImageIcon(imgPath).getImage()
                    .getScaledInstance(100,100,Image.SCALE_SMOOTH)
            ));
        }
        // 기본 필드
        nicknameField.setText(profile.getNickname());
        yearBox.setSelectedItem(profile.getAdmissionYear());
        gradeBox.setSelectedItem(profile.getGrade());
        if (profile.getisEnrolled()) enrolledBtn.setSelected(true);
        else leaveBtn.setSelected(true);

        // 전공들
        majorsPanel.removeAll();
        majorNameFields.clear();
        majorTypeBoxes.clear();
        for (String m: profile.getMajors()) {
            String[] sp = m.split(" \\(");
            addMajorRow();
            int idx = majorNameFields.size()-1;
            majorNameFields.get(idx).setText(sp[0]);
            majorTypeBoxes.get(idx).setSelectedItem(sp[1].replace(")",""));
        }
        // 경력들
        careersPanel.removeAll();
        careerNameFields.clear();
        careerTypeBoxes.clear();
        for (String c: profile.getCareers()) {
            String[] sp = c.split(" \\(");
            addCareerRow();
            int idx = careerNameFields.size()-1;
            careerNameFields.get(idx).setText(sp[0]);
            careerTypeBoxes.get(idx).setSelectedItem(sp[1].replace(")",""));
        }
        // 자격증들
        certsPanel.removeAll();
        certFields.clear();
        for (String c: profile.getCertificates()) {
            addCertRow();
            certFields.get(certFields.size()-1).setText(c);
        }
        // 소개
        introArea.setText(profile.getIntroduction());

        // 이미 테스트 결과가 있으면
        if (profile.getResultType()!=null) {
            showResultPanel();
        }
    }

    private void registerListeners() {
        // 뒤로가기/취소
        ((JButton)mainPanel.getComponent(0)).addActionListener(e -> {
            dispose();
            if (isEditMode) parentPage.setVisible(true);
            else new JoinFrame(manager);
        });

        // 이미지 업로드
        uploadImgBtn.addActionListener(e -> {
            JFileChooser ch = new JFileChooser();
            if (ch.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
                selectedImageFile = ch.getSelectedFile();
                imgLabel.setIcon(new ImageIcon(
                    new ImageIcon(selectedImageFile.getAbsolutePath())
                        .getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH)
                ));
            }
        });

        addMajorBtn.addActionListener(e -> addMajorRow());
        addCareerBtn.addActionListener(e -> addCareerRow());
        addCertBtn.addActionListener(e -> addCertRow());

        // 협업유형 테스트 버튼 → Test 모델과 실제 테스트 페이지 연결
        // testBtn.addActionListener(e -> {
        //     // TODO: 실제 테스트 화면(TestPage 등)을 띄워서 사용자가 테스트를 완료하게 한 뒤 돌아오도록 구현
        //     // 예시: new TestPage(this, test).setVisible(true);

        //     // 테스트 모델에서 결과 유형과 이미지 경로를 꺼내 옵니다.
        //     SongiType result = test.getUserResultType();
        //     String    imgPath = test.getResultImagePath();

        //     if (result != null && imgPath != null) {
        //         // 프로필에 유형명과 이미지 경로를 함께 저장
        //         profile.updateType(result, imgPath);
        //         showResultPanel();
        //     } else {
        //         JOptionPane.showMessageDialog(this, "테스트를 완료해 주세요.");
        //     }
        // });
        // 유림 수정

        // 협업유형 테스트 버튼 → Test 모델과 실제 테스트 페이지 연결
        testBtn.addActionListener(e -> {
            CollaborationTypeTestDialog dialog = new CollaborationTypeTestDialog(this);
            dialog.setVisible(true);
        });
        // 유림 수정

        // 완료/저장 버튼 → onSubmit() 결과에 따라만 창 닫기 및 이동
        completeBtn.addActionListener(e -> {
            if (!onSubmit()) {
                // 검증 실패 시 경고만 띄우고 리턴
                return;
            }
            dispose();
            if (isEditMode) {
                parentPage.setVisible(true);
                parentPage.refreshProfileDisplay();
            } else {
                new TeamListPage(user, manager);
            }
        });
    }

    /** 유효성 검사 및 Profile 업데이트, 성공 시 true 반환 */
    private boolean onSubmit() {
        warningLabel.setText("");

        // 닉네임 검증
        String nick = nicknameField.getText().trim();
        if (nick.isEmpty() || nick.equals("2~16자 이내로 입력해 주세요.")) {
            warningLabel.setText("닉네임을 입력해 주세요."); return false;
        }
        if (nick.length() < 2 || nick.length() > 16) {
            warningLabel.setText("닉네임은 2~16자 이내여야 합니다."); return false;
        }
        // 연도/학년
        if (yearBox.getSelectedIndex() == 0) {
            warningLabel.setText("입학년도를 선택해 주세요."); return false;
        }
        if (gradeBox.getSelectedIndex() == 0) {
            warningLabel.setText("학년을 선택해 주세요."); return false;
        }
        // 재학 여부
        if (!enrolledBtn.isSelected() && !leaveBtn.isSelected()) {
            warningLabel.setText("재학 여부를 선택해 주세요."); return false;
        }
        // 전공
        boolean hasMajor = majorNameFields.stream().anyMatch(f -> {
            String t = f.getText().trim();
            return !t.isEmpty() && !t.equals("전공명을 작성해 주세요.");
        });
        if (!hasMajor) {
            warningLabel.setText("전공을 한 개 이상 작성해 주세요."); return false;
        }
        // 자기소개
        String intro = introArea.getText().trim();
        if (intro.isEmpty() || intro.equals("자기소개를 70자 이내로 작성해 주세요.")) {
            warningLabel.setText("자기소개를 작성해 주세요."); return false;
        }
        if (intro.length() > 70) {
            warningLabel.setText("자기소개는 70자 이내여야 합니다."); return false;
        }
        // 테스트 완료 여부
        if (profile.getResultType() == null) {
            warningLabel.setText("협업 유형 테스트를 완료해 주세요."); return false;
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

        // 리스트 초기화 후 추가
        profile.getMajors().clear();
        for (int i = 0; i < majorNameFields.size(); i++) {
            String m = majorNameFields.get(i).getText().trim();
            String t = (String)majorTypeBoxes.get(i).getSelectedItem();
            if (!m.isEmpty() && !m.equals("전공명을 작성해 주세요.")) {
                profile.addMajor(m, t);
            }
        }
        profile.getCareers().clear();
        for (int i = 0; i < careerNameFields.size(); i++) {
            String c = careerNameFields.get(i).getText().trim();
            String t = (String)careerTypeBoxes.get(i).getSelectedItem();
            if (!c.isEmpty() && !c.equals("활동명을 작성해 주세요.")) {
                profile.addCareer(c, t);
            }
        }
        profile.getCertificates().clear();
        for (JTextField f : certFields) {
            String c = f.getText().trim();
            if (!c.isEmpty() && !c.equals("취득한 자격증명을 작성해 주세요.")) {
                profile.addCertification(c);
            }
        }

        user.setProfile(profile);
        JOptionPane.showMessageDialog(this,
            isEditMode ? "프로필이 저장되었습니다." : "회원가입이 완료되었습니다!");
        return true;
    }

    public void showResultPanel() {
        SongiType type = profile.getResultType();
        if (type == null) return;
        resultImageLabel.setIcon(new ImageIcon(
            new ImageIcon(profile.getResultImagePath()).getImage()
                .getScaledInstance(350, 200, Image.SCALE_SMOOTH)
        ));
        resultPanel.setVisible(true);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // 유림 추가가
        public void onTestCompleted(String typeName, String imagePath) {
        SongiType type = SongiType.valueOf(typeName);
        profile.updateType(type, imagePath);
        showResultPanel();
    }
    // 유림 추가


    // — UI 헬퍼 메서드들 (생략 없이 완전 구현) —

    private JLabel labeled(String text, String hint, int x, int y) {
        JLabel l = new JLabel(String.format(
            "<html><span style='color:#4A4A4A'><b>%s</b></span> " +
            "<span style='color:#BABABA'>%s</span></html>", text, hint));
        l.setBounds(x, y, 360, H20);
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

    private void addMajorRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(350,35));
        JTextField name = new JTextField();
        name.setPreferredSize(new Dimension(230,30));
        setPlaceholder(name,"전공명을 작성해 주세요.");
        JComboBox<String> type = new JComboBox<>(MAJOR_TYPES);
        type.setPreferredSize(new Dimension(110,30));
        row.add(name); row.add(type);
        majorsPanel.add(row);
        majorsPanel.revalidate(); majorsPanel.repaint();
        majorNameFields.add(name);
        majorTypeBoxes.add(type);
    }

    private void addCareerRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(350,35));
        JTextField name = new JTextField();
        name.setPreferredSize(new Dimension(230,30));
        setPlaceholder(name,"활동명을 작성해 주세요.");
        JComboBox<String> type = new JComboBox<>(CAREER_TYPES);
        type.setPreferredSize(new Dimension(110,30));
        row.add(name); row.add(type);
        careersPanel.add(row);
        careersPanel.revalidate(); careersPanel.repaint();
        careerNameFields.add(name);
        careerTypeBoxes.add(type);
    }

    private void addCertRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(350,35));
        JTextField cert = new JTextField();
        cert.setPreferredSize(new Dimension(350,30));
        setPlaceholder(cert,"취득한 자격증명을 작성해 주세요.");
        row.add(cert);
        certsPanel.add(row);
        certsPanel.revalidate(); certsPanel.repaint();
        certFields.add(cert);
    }

    // 메인 (테스트용)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Management mgr = new Management(new HashMap<>());
            User u = new User("홍길동","hg123","pw","hg@sookmyung.ac.kr");
            new ProfilePage(u, mgr);
        });
    }
}