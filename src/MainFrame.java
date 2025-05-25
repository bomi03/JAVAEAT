import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import model.Management;
import model.User;



public class MainFrame {
    public static void main(String[] args) {
        Management manager = new Management(new HashMap<>());

        User testUser = new User("서연", "test", "1234", "123@sookmyung.ac.kr");
        manager.addUser(testUser);  // ✅ 관리자에 유저 등록
        SwingUtilities.invokeLater(() -> new LoginFrame(manager));

        // JFrame frame = new JFrame("로그인");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(393, 852);

        // JPanel panel = new JPanel();
        // panel.setBackground(Color.decode("#FfFfFf"));
        // frame.add(panel);

        // placeComponents(panel, manager); // ✅ manager 전달
        // frame.setVisible(true);
    }

    public static void placeComponents(JPanel panel, Management manager) {
        panel.setLayout(null);

        final JLabel messageLabel = new JLabel("");
        messageLabel.setBounds(50, 250, 300, 25);
        messageLabel.setForeground(Color.RED);
        panel.add(messageLabel);

        JLabel emojiLabel = new JLabel("❄️");
        emojiLabel.setBounds(145, 40, 100, 100);
        emojiLabel.setFont(new Font("SansSerif", Font.PLAIN, 60));
        panel.add(emojiLabel);

        JLabel logoLabel = new JLabel("눈뭉치", SwingConstants.CENTER);
        logoLabel.setBounds(125, 130, 100, 30); // 위치: ❄️ 아래, 아이디 위
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        logoLabel.setForeground(Color.decode("#003087")); // 파란 글씨 추천
        panel.add(logoLabel);


        JLabel userLabel = new JLabel("아이디");
        userLabel.setBounds(50, 180, 80, 25); // 150 → 180
        panel.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(130, 180, 200, 25); // 150 → 180
        panel.add(userText);

        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setBounds(50, 220, 80, 25); // 190 → 220
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(130, 220, 200, 25); // 190 → 220
        panel.add(passwordText);

        JButton loginButton = new JButton("로그인");
        loginButton.setBounds(40, 275, 300, 35); // 245 → 275
        styleBlue(loginButton);
        panel.add(loginButton);

        // 회원가입
        JLabel signupLabel = new JLabel("회원가입");
        signupLabel.setBounds(70, 330, 80, 25); // 300 → 330
        panel.add(signupLabel);
        signupLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new JoinFrame(manager);
                SwingUtilities.getWindowAncestor(panel).dispose(); // ✅ 현재 로그인 창 닫기
            }
        });

        // 아이디 찾기
        JLabel findIdLabel = new JLabel("아이디 찾기");
        findIdLabel.setBounds(150, 330, 80, 25); // 300 → 330
        panel.add(findIdLabel);
        findIdLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new FindIdFrame(manager);
                SwingUtilities.getWindowAncestor(panel).dispose(); // ✅ 현재 로그인 창 닫기
            }
        });

        // 비밀번호 찾기
        JLabel findPwLabel = new JLabel("비밀번호 찾기");
        findPwLabel.setBounds(240, 330, 100, 25); // 300 → 330
        panel.add(findPwLabel);
        findPwLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new FindPwFrame(manager);
                SwingUtilities.getWindowAncestor(panel).dispose(); // ✅ 현재 로그인 창 닫기
            }
        });

        // 로그인 버튼 클릭 처리
        loginButton.addActionListener(e -> {
            String id = userText.getText().trim();
            String pw = new String(passwordText.getPassword()).trim();

            User foundUser = manager.getUserById(id); // ✅ manager에서 찾기

            if (foundUser == null) {
                messageLabel.setText("존재하지 않는 아이디입니다.");
                messageLabel.setForeground(Color.RED);
                return;
            }

            String resultMessage = foundUser.login(id, pw);
            messageLabel.setText(resultMessage);

            if (foundUser.isLoggedIn()) {
                messageLabel.setForeground(new Color(0, 128, 0));
                SwingUtilities.getWindowAncestor(panel).dispose();
                new TeamListPage(foundUser, manager); // ✅ 성공 시 메인 페이지로
            } else {
                messageLabel.setForeground(Color.RED);
            }
        });
    }

    private static void styleBlue(AbstractButton b) {
        b.setBackground(Color.decode("#003087"));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }
}