import javax.swing.*;
import model.Management;
import model.User;
import model.ProfilePage;
import java.awt.*;
import java.util.HashMap;
<<<<<<< HEAD

import model.Management;
import model.User;
=======
import java.util.List;
import java.util.ArrayList;
>>>>>>> User

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
        pwCheckLabel.setBounds(30, 290, 100, 25);
        add(pwCheckLabel);

        JPasswordField pwCheckField = new JPasswordField();
        pwCheckField.setBounds(30, 320, 320, 30);
        add(pwCheckField);

        JLabel pwCheckErrorLabel = new JLabel("");
        pwCheckErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        pwCheckErrorLabel.setForeground(Color.RED);
        pwCheckErrorLabel.setBounds(30, 350, 300, 15);
        add(pwCheckErrorLabel);

        // 이메일
        JLabel emailLabel = new JLabel("이메일");
        emailLabel.setBounds(30, 360, 100, 25);
        add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(30, 390, 320, 30);
        add(emailField);

        JLabel emailErrorLabel = new JLabel("");
        emailErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        emailErrorLabel.setForeground(Color.RED);
        emailErrorLabel.setBounds(30, 420, 300, 15);
        add(emailErrorLabel);

        // 약관 동의
        JCheckBox term1 = new JCheckBox("이용약관 동의");
        term1.setBounds(30, 450, 200, 20);
        add(term1);

        JCheckBox term2 = new JCheckBox("개인정보 처리방침 동의");
        term2.setBounds(30, 480, 250, 20);
        add(term2);

        JCheckBox term3 = new JCheckBox("광고성 정보 수신 동의");
        term3.setBounds(30, 510, 250, 20);
        add(term3);

        // 다음 버튼
        JButton nextButton = new JButton("다음");
        nextButton.setBounds(30, 700, 320, 40);
        add(nextButton);

<<<<<<< HEAD

        String username = nameField.getText().trim();
        String userID = idField.getText().trim();
        String password = new String(pwField.getPassword()).trim();
        String email = emailField.getText().trim();

        User newUser = new User(username, userID, password, email);
        Management manager = new Management(new HashMap<>());


        new ProfilePage(newUser, manager);
=======
        nextButton.addActionListener(e -> {
            String username = nameField.getText().trim();
            String userID = idField.getText().trim();
            String password = new String(pwField.getPassword()).trim();
            String passwordCheck = new String(pwCheckField.getPassword()).trim();
            String email = emailField.getText().trim();
            String inputAuthCode = "1234"; // 예시
            String actualAuthCode = "1234";
            boolean isVerified = true;
            boolean isAgreed = term1.isSelected() && term2.isSelected() && term3.isSelected();
>>>>>>> User

            List<User> userList = new ArrayList<>();
            User newUser = new User("", "", "", "");
            boolean success = newUser.signup(username, userID, password, passwordCheck, email,
                    inputAuthCode, actualAuthCode, isVerified, isAgreed, userList);

            if (!success) {
                // 라벨에 에러 메시지 설정 예시
                nameErrorLabel.setText("이름을 확인해주세요.");
                idErrorLabel.setText("아이디를 확인해주세요.");
                pwErrorLabel.setText("비밀번호를 확인해주세요.");
                pwCheckErrorLabel.setText("비밀번호가 일치하지 않습니다.");
                emailErrorLabel.setText("이메일을 확인해주세요.");
            } else {
                Management manager = new Management(new HashMap<>());
                new ProfilePage(newUser, manager);
                dispose();
            }
        });

        setVisible(true);
    }
}
