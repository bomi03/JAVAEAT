import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// 보미 수정
import model.User;
import model.Application;
import model.Management;
import model.NotificationType;
import model.Post;
// 보미 수정
import model.Profile;

public class TeamApplicationForm extends JFrame {
    //보미 수정
    private final User user;
    private final Management manager;
    private final Post post;
    // 보미 수정

    private JTextArea introArea;
    private JLabel warningLabel;
    private final String placeholderText = "자기소개를 입력해 주세요.";
    private boolean showingPlaceholder = true;

    public TeamApplicationForm(User user, Management manager, Post post) {    
        this.user = user;
        this.manager = manager;
        this.post = post;

        setTitle("지원하기");
        setSize(393, 852);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel titleLabel = new JLabel("지원하기", SwingConstants.CENTER);
        titleLabel.setBounds(0, 10, 393, 40);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        add(titleLabel);

        JLabel introLabel = new JLabel("자기소개 * 필수 입력 항목입니다.");
        introLabel.setBounds(30, 70, 300, 20);
        introLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        add(introLabel);

        introArea = new JTextArea(placeholderText);
        introArea.setLineWrap(true);
        introArea.setWrapStyleWord(true);
        introArea.setForeground(Color.GRAY);
        introArea.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        introArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        introArea.setFocusable(false); // 처음엔 포커스 막기

        JScrollPane scrollPane = new JScrollPane(introArea);
        scrollPane.setBounds(30, 95, 330, 200);
        add(scrollPane);

        // Focus events to simulate placeholder
        introArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder) {
                    introArea.setText("");
                    introArea.setForeground(Color.BLACK);
                    introArea.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
                    showingPlaceholder = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (introArea.getText().trim().isEmpty()) {
                    introArea.setText(placeholderText);
                    introArea.setForeground(Color.GRAY);
                    introArea.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
                    showingPlaceholder = true;
                }
            }
        });

        warningLabel = new JLabel("※ 자기소개를 입력해 주세요.");
        warningLabel.setForeground(Color.RED);
        warningLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        warningLabel.setBounds(30, 300, 300, 20);
        warningLabel.setVisible(false);
        add(warningLabel);

        JButton submitBtn = new JButton("지원하기");
        submitBtn.setBounds(30, 340, 330, 45);
        submitBtn.setBackground(new Color(0, 47, 135));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(submitBtn);

        submitBtn.addActionListener(e -> handleSubmit());

        setVisible(true);

        // 포커스 강제 넘기기 (placeholder 안 사라지게)
        SwingUtilities.invokeLater(() -> requestFocusInWindow());

        // 이후에 포커스 허용
        introArea.setFocusable(true);
    }

    private void handleSubmit() {
        String input = introArea.getText().trim();
        if (input.isEmpty() || showingPlaceholder) {
            warningLabel.setVisible(true);
        } else {
            warningLabel.setVisible(false);

            // 보미 수정
            int newAppId = generateNewApplicationID();
            Application app = new Application(newAppId, post.getPostID(), user.getProfile().getProfileID(), input);
            post.addApplication(app);

            // ✅ 팀장에게 알림 보내기 (채빈 수정)
            if (manager != null) {
                Profile writerProfile = manager.getProfileByID(post.getProfileID()); // 팀장 프로필
                if (writerProfile != null) {
                    String writerUserId = writerProfile.getUserID(); // 팀장 userID
                    String msg = user.getProfile().getNickname() + " 님이 [" + post.getTitle() + "]에 지원했어요!";
                    manager.addNotification(writerUserId, msg, NotificationType.APPLY, "/post/" + post.getPostID());
                }
            }

            // 보미 수정

            showSuccessDialog();

            // 보미 수정
            dispose();
            new PostDetailPage(user, manager, post);
            // 보미 수정
        }
    }

    private void showSuccessDialog() {
        JDialog dialog = new JDialog(this, "지원 완료", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new BorderLayout());

        // 상단 메시지
        JLabel msg = new JLabel("지원이 완료되었습니다.", SwingConstants.CENTER);
        msg.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        dialog.add(msg, BorderLayout.CENTER);

        // 하단 버튼
        JButton okBtn = new JButton("확인");
        okBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        okBtn.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okBtn);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // 보미 수정
    private int generateNewApplicationID() {
        return (int) (Math.random() * 100000); // 간단한 ID 생성
    }
    // 보미 수정


    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(TeamApplicationForm::new);
    // }
}
