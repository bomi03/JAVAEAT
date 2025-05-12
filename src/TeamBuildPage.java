import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Post;
import model.Team;

public class TeamBuildPage extends JFrame {
    private static int nextPostId = 1;
    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy/MM/dd");

    private int profileID;

    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JLabel warningLabel;

    private JLabel imgLabel;
    private JButton uploadImgBtn;

    private JComboBox<String> categoryBox;
    private JTextField titleField;

    private JToggleButton recruitingBtn, closedBtn;
    private ButtonGroup statusGroup;

    private JTextField deadlineField;
    private JTextField maxField;
    private JTextField currentField;
    private JTextArea descArea;

    private JButton completeBtn;

    private static final String[] CATEGORIES = {
        "카테고리 선택", "공모전", "스터디", "수업 팀플", "교내 대회", "프로젝트", "기타"
    };
    private static final Color PLACEHOLDER_COLOR = Color.decode("#ADADAD");
    private static final Color TEXT_COLOR = Color.BLACK;

    public TeamBuildPage(int profileID) {
        this.profileID = profileID;

        setTitle("팀원 모집 글 작성");
        setSize(393, 852);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
        registerListeners();

        setVisible(true);
    }

    private void buildUI() {
        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(393, 1700));

        int y = 0;

        JButton backBtn = new JButton("<html>&lt;-</html>");
        styleGrayButton(backBtn);
        backBtn.setBounds(10, y + 20, 40, 30);
        mainPanel.add(backBtn);

        JLabel header = new JLabel("팀원 모집 글 작성", SwingConstants.CENTER);
        header.setForeground(Color.decode("#4A4A4A"));
        header.setBounds(0, y + 20, 393, 30);
        mainPanel.add(header);
        y += 60;

        JSeparator sep = new JSeparator();
        sep.setBounds(0, y, 393, 1);
        sep.setForeground(Color.decode("#8A8888"));
        mainPanel.add(sep);
        y += 22;

        imgLabel = new JLabel();
        imgLabel.setBorder(new LineBorder(Color.GRAY, 1, true));
        imgLabel.setBounds((393 - 100) / 2, y, 100, 100);
        mainPanel.add(imgLabel);
        y += 110;

        uploadImgBtn = new JButton("이미지 업로드");
        styleGrayButton(uploadImgBtn);
        uploadImgBtn.setBounds((393 - 120) / 2, y, 120, 30);
        mainPanel.add(uploadImgBtn);
        y += 50;

        warningLabel = new JLabel();
        warningLabel.setForeground(Color.RED);
        warningLabel.setBounds(20, y, 360, 20);
        mainPanel.add(warningLabel);
        y += 30;

        mainPanel.add(labeled("카테고리 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        categoryBox = new JComboBox<>(CATEGORIES);
        categoryBox.setBounds(20, y, 350, 30);
        categoryBox.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        mainPanel.add(categoryBox);
        y += 50;

        mainPanel.add(labeled("제목 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        titleField = new JTextField();
        titleField.setBounds(20, y, 350, 30);
        titleField.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(titleField, "제목을 작성해 주세요.");
        mainPanel.add(titleField);
        y += 50;

        mainPanel.add(labeled("모집 상태 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        recruitingBtn = new JToggleButton("모집중");
        closedBtn    = new JToggleButton("모집완료");
        styleToggleButton(recruitingBtn);
        styleToggleButton(closedBtn);
        recruitingBtn.setBounds(20, y, 170, 40);
        closedBtn    .setBounds(200, y, 170, 40);
        statusGroup = new ButtonGroup();
        statusGroup.add(recruitingBtn);
        statusGroup.add(closedBtn);
        mainPanel.add(recruitingBtn);
        mainPanel.add(closedBtn);
        y += 60;

        mainPanel.add(labeled("모집 기간", "", 20, y));
        y += 20;
        deadlineField = new JTextField();
        deadlineField.setBounds(20, y, 350, 30);
        deadlineField.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(deadlineField, "YYYY/MM/DD");
        mainPanel.add(deadlineField);
        y += 50;

        mainPanel.add(labeled("모집 인원 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        maxField = new JTextField();
        maxField.setBounds(20, y, 350, 30);
        maxField.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(maxField, "숫자로만 입력해 주세요. ex.10");
        mainPanel.add(maxField);
        y += 50;

        mainPanel.add(labeled("모집된 인원", "", 20, y));
        y += 20;
        currentField = new JTextField();
        currentField.setBounds(20, y, 350, 30);
        currentField.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(currentField, "모집된 인원을 숫자로만 입력해 주세요. ex.5");
        mainPanel.add(currentField);
        y += 50;

        mainPanel.add(labeled("팀 설명 *", "필수 입력 항목입니다.", 20, y));
        y += 20;
        descArea = new JTextArea();
        descArea.setLineWrap(true);
        descArea.setBounds(20, y, 350, 380);
        descArea.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(descArea,
            "[모집 대상]\n(ex. UX/UI 디자인 공모전에 관심 있는 사람)\n\n" +
            "[역할]\n(ex. 디자인 2명, 개발 3명)\n\n" +
            "[현재 구성원]\n(ex. 기획 1명, 개발 1명 참여중)\n\n" +
            "[진행 기간]\n(ex. 2025.04.01~2025.06.30)\n\n" +
            "[진행 방법/일정]\n(ex. 주 1회 미팅)\n\n" +
            "[모임 방식(온라인/오프라인)]\n(ex. 온라인 회의 + 오프라인 발표)\n\n" +
            "[우대사항]\n(ex. 피그마 사용 가능자 우대)"
        );
        mainPanel.add(descArea);
        y += 380;

        completeBtn = new JButton("완료");
        styleBlueButton(completeBtn);
        completeBtn.setBounds(20, y, 350, 40);
        mainPanel.add(completeBtn);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBounds(0, 0, 393, 852);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);
    }

    private JLabel labeled(String text, String hint, int x, int y) {
        JLabel l = new JLabel(String.format(
            "<html><span style='color:#4A4A4A'><b>%s</b></span> " +
            "<span style='color:#BABABA'>%s</span></html>",
            text, hint));
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

    private void setPlaceholder(JTextComponent comp, String placeholder) {
        comp.setText(placeholder);
        comp.setForeground(PLACEHOLDER_COLOR);
        comp.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (comp.getText().equals(placeholder)) {
                    comp.setText("");
                    comp.setForeground(TEXT_COLOR);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (comp.getText().isEmpty()) {
                    comp.setText(placeholder);
                    comp.setForeground(PLACEHOLDER_COLOR);
                }
            }
        });
    }

    private void registerListeners() {
        uploadImgBtn.addActionListener(e -> {
            JFileChooser ch = new JFileChooser();
            if (ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = ch.getSelectedFile();
                imgLabel.setIcon(new ImageIcon(
                    new ImageIcon(f.getAbsolutePath())
                        .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)
                ));
            }
        });

        completeBtn.addActionListener(e -> {
            warningLabel.setText("");

            if (categoryBox.getSelectedIndex() == 0) {
                warningLabel.setText("카테고리를 선택해주세요.");
                return;
            }
            String title = titleField.getText().trim();
            if (title.isEmpty() || title.equals("제목을 작성해 주세요.")) {
                warningLabel.setText("제목을 입력해주세요.");
                return;
            }
            if (!recruitingBtn.isSelected() && !closedBtn.isSelected()) {
                warningLabel.setText("모집 상태를 선택해주세요.");
                return;
            }

            String ds = deadlineField.getText().trim();
            if (!ds.isEmpty() && !ds.equals("YYYY/MM/DD")) {
                String[] parts = ds.split("/");
                if (parts.length != 3) {
                    warningLabel.setText("날짜 형식은 YYYY/MM/DD로 입력해주세요.");
                    return;
                }
                int mm, dd;
                try {
                    mm = Integer.parseInt(parts[1]);
                    dd = Integer.parseInt(parts[2]);
                } catch (NumberFormatException ex) {
                    warningLabel.setText("날짜를 확인해주세요.");
                    return;
                }
                if (mm < 1 || mm > 12 || dd < 1 || dd > 31) {
                    warningLabel.setText("날짜를 확인해주세요.");
                    return;
                }
            }

            String maxTxt = maxField.getText().trim();
            if (maxTxt.isEmpty() || maxTxt.equals("숫자로만 입력해 주세요. ex.10")) {
                warningLabel.setText("모집 인원을 입력해주세요.");
                return;
            }
            int max, curr = 0;
            try {
                max = Integer.parseInt(maxTxt);
            } catch (NumberFormatException ex) {
                warningLabel.setText("모집 인원은 숫자만 입력해주세요.");
                return;
            }

            String currTxt = currentField.getText().trim();
            if (!currTxt.isEmpty() && !currTxt.equals("모집된 인원을 숫자로만 입력해 주세요. ex.5")) {
                try {
                    curr = Integer.parseInt(currTxt);
                } catch (NumberFormatException ex) {
                    warningLabel.setText("모집된 인원은 숫자만 입력해주세요.");
                    return;
                }
                if (curr > max) {
                    warningLabel.setText("모집된 인원은 모집 인원수 이하여야 합니다.");
                    return;
                }
            }

            String desc = descArea.getText().trim();
            if (desc.isEmpty() || desc.startsWith("[모집 대상]")) {
                warningLabel.setText("팀 설명을 입력해주세요.");
                return;
            }

            Date deadline = null;
            if (!ds.isEmpty() && !ds.equals("YYYY/MM/DD")) {
                try {
                    deadline = DATE_FMT.parse(ds);
                } catch (ParseException ex) {
                    warningLabel.setText("날짜 형식은 YYYY/MM/DD로 입력해주세요.");
                    return;
                }
            }

            Post p = new Post(
                nextPostId++,
                profileID,
                "",
                (String) categoryBox.getSelectedItem(),
                title,
                recruitingBtn.isSelected() ? "모집중" : "모집완료",
                deadline,
                max,
                curr,
                desc
            );

            // 저장소에 추가
            Post.addPost(p);

            // Team 생성 및 저장
            Team team = new Team(p.getPostID());
            Team.addTeam(team);

            JOptionPane.showMessageDialog(this, "팀 모집글이 생성되었습니다.");
            dispose();
        });
    }

    public static void main(String[] args) {
        // 예시로 1
        new TeamBuildPage(1);
    }
}