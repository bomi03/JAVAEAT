import java.awt.Font;
import javax.swing.*;


import model.User;
import java.awt.Color;
import java.awt.event.*;


public class MainFrame {
    // main()에서 user 생성 + panel 전달
public static void main(String[] args) {
    User testUser = new User("서연", "sysy", "q1234!", "seo@sookmyung.ac.kr");

    JFrame frame = new JFrame("눈뭉치 로그인");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(393, 852);

    JPanel panel = new JPanel();
    frame.add(panel);

    placeComponents(panel, testUser); // 유저 전달
    frame.setVisible(true);
}

private static void placeComponents(JPanel panel, User user) {
    panel.setLayout(null);

    final JLabel messageLabel = new JLabel("");
    messageLabel.setBounds(50,  220, 300, 25);
    messageLabel.setForeground(Color.RED);
    panel.add(messageLabel);

    JLabel emojiLabel = new JLabel("❄️");
    emojiLabel.setBounds(145, 40, 100, 100);
    emojiLabel.setFont(new Font("SansSerif", Font.PLAIN, 60));
    panel.add(emojiLabel);

    JLabel userLabel = new JLabel("아이디");
    userLabel.setBounds(50, 150, 80, 25);
    panel.add(userLabel);

    JTextField userText = new JTextField(); 
    userText.setBounds(130, 150, 200, 25);
    panel.add(userText);

    JLabel passwordLabel = new JLabel("비밀번호");
    passwordLabel.setBounds(50, 190, 80, 25);
    panel.add(passwordLabel);

    JPasswordField passwordText = new JPasswordField();
    passwordText.setBounds(130, 190, 200, 25);
    panel.add(passwordText);

    JButton loginButton = new JButton("로그인");
    loginButton.setBounds(50, 260, 300, 30);
    panel.add(loginButton);

    // 회원가입
    JLabel signupLabel = new JLabel("회원가입");
    signupLabel.setBounds(80, 300, 80, 25);
    panel.add(signupLabel);

    // 클릭 시 새 창 띄우기
    signupLabel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            new JoinFrame(); // 회원가입 창 열기
        }
    });
    


    // 아이디 찾기
    JLabel findIdLabel = new JLabel("아이디 찾기");
    findIdLabel.setBounds(160, 300, 80, 25);
    panel.add(findIdLabel);

    // 비밀번호 찾기
    JLabel findPwLabel = new JLabel("비밀번호 찾기");
    findPwLabel.setBounds(250, 300, 100, 25);
    panel.add(findPwLabel);


    // 🌟 로그인 버튼 클릭 시 처리
    loginButton.addActionListener(e -> {
        String id = userText.getText();
        String pw = new String(passwordText.getPassword());
        String resultMessage = user.login(id, pw);  // 로그인 시도 + 메시지 받기
    
        // 결과 메시지를 라벨에 그대로 출력
        messageLabel.setText(resultMessage);
    
        // 성공일 때 초록색, 실패일 때 빨간색
        if (user.isLoggedIn()) {
            messageLabel.setForeground(new Color(0, 128, 0)); // 초록색
        } else {
            messageLabel.setForeground(Color.RED); // 빨간색
        }
    });
    
    
};

}
