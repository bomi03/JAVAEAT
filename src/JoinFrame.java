import javax.swing.*;
import java.awt.*;

public class JoinFrame extends JFrame {
    public JoinFrame() {
        setTitle("회원가입");
        setSize(393, 852);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // 뒤로가기 버튼
        JButton backButton = new JButton("<");
        backButton.setBounds(10, 10, 50, 30);
        add(backButton);

        // 제목 라벨
        JLabel titleLabel = new JLabel("회원가입", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBounds(120, 10, 150, 30);
        add(titleLabel);

        // 구분선
        JSeparator separator = new JSeparator();
        separator.setBounds(20, 50, 350, 1);
        add(separator);


         // 이름
        JLabel nameLabel = new JLabel("이름");
        nameLabel.setBounds(30, 70, 100, 25);
        add(nameLabel);

        JLabel nameRequiredLabel = new JLabel("*필수입력항목입니다");
        nameRequiredLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        nameRequiredLabel.setForeground(Color.RED);
        nameRequiredLabel.setBounds(70, 70, 150, 25);
        add(nameRequiredLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(30, 100, 320, 30);
        add(nameField);

        JLabel nameErrorLabel = new JLabel("");
        nameErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        nameErrorLabel.setForeground(Color.RED);
        nameErrorLabel.setBounds(30, 130, 300, 15);
        add(nameErrorLabel);

        // 아이디
        JLabel idLabel = new JLabel("아이디");
        idLabel.setBounds(30, 140, 100, 25);
        add(idLabel);

        JLabel idRequiredLabel = new JLabel("*필수입력항목입니다");
        idRequiredLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        idRequiredLabel.setForeground(Color.RED);
        idRequiredLabel.setBounds(70, 140, 150, 25);
        add(idRequiredLabel);

        JTextField idField = new JTextField();
        idField.setBounds(30, 170, 320, 30);
        add(idField);

        JLabel idErrorLabel = new JLabel("");
        idErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        idErrorLabel.setForeground(Color.RED);
        idErrorLabel.setBounds(30, 200, 300, 15);
        add(idErrorLabel);

        // 비밀번호
        JLabel pwLabel = new JLabel("비밀번호");
        pwLabel.setBounds(30, 210, 100, 25);
        add(pwLabel);

        JLabel pwRequiredLabel = new JLabel("*필수입력항목입니다");
        pwRequiredLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        pwRequiredLabel.setForeground(Color.RED);
        pwRequiredLabel.setBounds(90, 210, 150, 25);
        add(pwRequiredLabel);

        JPasswordField pwField = new JPasswordField();
        pwField.setBounds(30, 240, 320, 30);
        add(pwField);

        JLabel pwErrorLabel = new JLabel("");
        pwErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        pwErrorLabel.setForeground(Color.RED);
        pwErrorLabel.setBounds(30, 270, 300, 15);
        add(pwErrorLabel);

        // 비밀번호 확인
        JLabel pwCheckLabel = new JLabel("비밀번호 확인");
        pwCheckLabel.setBounds(30, 280, 100, 25);
        add(pwCheckLabel);

        JPasswordField pwCheckField = new JPasswordField();
        pwCheckField.setBounds(30, 310, 320, 30);
        add(pwCheckField);

        JLabel pwCheckErrorLabel = new JLabel("");
        pwCheckErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        pwCheckErrorLabel.setForeground(Color.RED);
        pwCheckErrorLabel.setBounds(30, 340, 300, 15);
        add(pwCheckErrorLabel);

        // 이메일
        JLabel emailLabel = new JLabel("이메일 인증");
        emailLabel.setBounds(30, 350, 100, 25);
        add(emailLabel);

        JLabel emailRequiredLabel = new JLabel("*필수입력항목입니다");
        emailRequiredLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        emailRequiredLabel.setForeground(Color.RED);
        emailRequiredLabel.setBounds(110, 350, 150, 25);
        add(emailRequiredLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(30, 380, 220, 30);
        add(emailField);

        JButton sendCodeButton = new JButton("인증번호 전송");
        sendCodeButton.setBounds(260, 380, 90, 30);
        add(sendCodeButton);

        JLabel emailErrorLabel = new JLabel("");
        emailErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        emailErrorLabel.setForeground(Color.RED);
        emailErrorLabel.setBounds(30, 410, 300, 15);
        add(emailErrorLabel);

        // 인증번호
        JLabel codeLabel = new JLabel("인증번호 입력");
        codeLabel.setBounds(30, 420, 100, 25);
        add(codeLabel);

        JTextField codeField = new JTextField();
        codeField.setBounds(30, 450, 320, 30);
        add(codeField);

        JLabel codeErrorLabel = new JLabel("");
        codeErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        codeErrorLabel.setForeground(Color.RED);
        codeErrorLabel.setBounds(30, 480, 300, 15);
        add(codeErrorLabel);

        // 약관 동의 체크박스
        JLabel termsLabel = new JLabel("약관 동의");
        termsLabel.setBounds(30, 490, 100, 25);
        add(termsLabel);

        JCheckBox term1 = new JCheckBox("[필수] 이용약관 동의");
        term1.setBounds(30, 520, 250, 25);
        add(term1);

        JCheckBox term2 = new JCheckBox("[필수] 개인정보 수집 동의");
        term2.setBounds(30, 550, 250, 25);
        add(term2);

        JCheckBox term3 = new JCheckBox("[필수] 위치정보 이용 동의");
        term3.setBounds(30, 580, 250, 25);
        add(term3);

        JCheckBox term4 = new JCheckBox("[선택] 마케팅 정보 수신 동의");
        term4.setBounds(30, 610, 250, 25);
        add(term4);

        JCheckBox term5 = new JCheckBox("[선택] 이벤트 알림 동의");
        term5.setBounds(30, 640, 250, 25);
        add(term5);

        JLabel termsErrorLabel = new JLabel("");
        termsErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        termsErrorLabel.setForeground(Color.RED);
        termsErrorLabel.setBounds(30, 670, 300, 15);
        add(termsErrorLabel);

        // 다음 버튼
        JButton nextButton = new JButton("다음");
        nextButton.setBounds(30, 700, 320, 40);
        add(nextButton);


        nextButton.addActionListener(e -> {
            new ProfileFrame(); // 프로필 입력 화면 띄우기
        });

        //dispose(); // JoinFrame 닫기

        setVisible(true);
    }
}
