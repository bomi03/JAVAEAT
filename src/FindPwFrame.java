import java.awt.*;
import javax.swing.*;
import model.Management;
import model.User;

public class FindPwFrame extends JFrame {

    private Management manager;

    public FindPwFrame(Management manager) {
        this.manager = manager;

        setTitle("비밀번호 찾기");
        setSize(393, 852);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // 뒤로가기
        JButton backButton = new JButton("<");
        backButton.setBounds(10, 10, 50, 30);
        styleGray(backButton);
        add(backButton);

        backButton.addActionListener(e -> new LoginFrame(manager));

        JLabel titleLabel = new JLabel("비밀번호 찾기", SwingConstants.CENTER);
        titleLabel.setBounds(120, 10, 150, 30);
        add(titleLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(20, 50, 350, 1);
        separator.setForeground(Color.decode("#E0E0E0"));
        add(separator);

        JLabel guideLabel = new JLabel("회원가입시 등록한 아이디와 이메일을 입력해주세요");
        guideLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        guideLabel.setBounds(30, 60, 330, 30);
        add(guideLabel);

        JLabel nameLabel = new JLabel("이름:");
        nameLabel.setBounds(30, 100, 100, 25);
        add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(30, 130, 320, 30);
        add(nameField);

        JLabel nameErrorLabel = new JLabel("");
        nameErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        nameErrorLabel.setForeground(Color.RED);
        nameErrorLabel.setBounds(30, 160, 320, 15);
        add(nameErrorLabel);

        JLabel emailLabel = new JLabel("이메일:");
        emailLabel.setBounds(30, 180, 100, 25);
        add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(30, 210, 320, 30);
        add(emailField);

        JLabel emailErrorLabel = new JLabel("");
        emailErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        emailErrorLabel.setForeground(Color.RED);
        emailErrorLabel.setBounds(30, 240, 320, 15);
        add(emailErrorLabel);

        JButton nextButton1 = new JButton("다음");
        nextButton1.setBounds(30, 280, 320, 40);
        styleBlue(nextButton1);
        add(nextButton1);

        // 2단계
        JLabel resetGuide = new JLabel("이메일로 임시 비밀번호를 보냈습니다.");
        resetGuide.setFont(new Font("SansSerif", Font.PLAIN, 12));
        resetGuide.setBounds(30, 60, 330, 40);
        resetGuide.setVisible(false);
        add(resetGuide);

        JLabel tempPwLabel = new JLabel("임시 비밀번호:");
        tempPwLabel.setBounds(30, 100, 150, 25);
        tempPwLabel.setVisible(false);
        add(tempPwLabel);

        JPasswordField tempPwField = new JPasswordField();
        tempPwField.setBounds(30, 130, 320, 30);
        tempPwField.setVisible(false);
        add(tempPwField);

        JLabel newPwLabel = new JLabel("새 비밀번호:");
        newPwLabel.setBounds(30, 180, 150, 25);
        newPwLabel.setVisible(false);
        add(newPwLabel);

        JPasswordField newPwField = new JPasswordField();
        newPwField.setBounds(30, 210, 320, 30);
        newPwField.setVisible(false);
        add(newPwField);

        JLabel confirmPwLabel = new JLabel("비밀번호 확인:");
        confirmPwLabel.setBounds(30, 260, 150, 25);
        confirmPwLabel.setVisible(false);
        add(confirmPwLabel);

        JPasswordField confirmPwField = new JPasswordField();
        confirmPwField.setBounds(30, 290, 320, 30);
        confirmPwField.setVisible(false);
        add(confirmPwField);

        JLabel pwErrorLabel = new JLabel("");
        pwErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        pwErrorLabel.setForeground(Color.RED);
        pwErrorLabel.setBounds(30, 320, 320, 15);
        pwErrorLabel.setVisible(false);
        add(pwErrorLabel);

        JButton nextButton2 = new JButton("다음");
        nextButton2.setBounds(30, 360, 320, 40);
        styleBlue(nextButton2);
        nextButton2.setVisible(false);
        add(nextButton2);

        // 1단계 → 2단계
        nextButton1.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            nameErrorLabel.setText("");
            emailErrorLabel.setText("");
            pwErrorLabel.setText("");

            boolean valid = true;
            if (name.isEmpty()) {
                nameErrorLabel.setText("이름을 입력해주세요.");
                valid = false;
            }
            if (email.isEmpty()) {
                emailErrorLabel.setText("이메일을 입력해주세요.");
                valid = false;
            }

            boolean userExists = false;
            for (User u : manager.getAllUsers()) {
                if (u.getUsername().equals(name) && u.getEmail().equals(email)) {
                    userExists = true;
                    break;
                }
            }

            if (valid && !userExists) {
                emailErrorLabel.setText("일치하는 회원 정보가 없습니다.");
                return;
            }

            if (valid && userExists) {
                guideLabel.setVisible(false);
                nameLabel.setVisible(false);
                nameField.setVisible(false);
                nameErrorLabel.setVisible(false);
                emailLabel.setVisible(false);
                emailField.setVisible(false);
                emailErrorLabel.setVisible(false);
                nextButton1.setVisible(false);

                resetGuide.setText("<html>이메일로 임시 비밀번호를 보냈습니다.<br>임시 비밀번호와 새 비밀번호를 입력해주세요.</html>");
                resetGuide.setVisible(true);

                tempPwLabel.setVisible(true);
                tempPwField.setVisible(true);
                newPwLabel.setVisible(true);
                newPwField.setVisible(true);
                confirmPwLabel.setVisible(true);
                confirmPwField.setVisible(true);
                pwErrorLabel.setVisible(true);
                nextButton2.setVisible(true);
            }
        });

        // 비밀번호 재설정
        nextButton2.addActionListener(e -> {
            String temp = new String(tempPwField.getPassword());
            String newPw = new String(newPwField.getPassword());
            String confirmPw = new String(confirmPwField.getPassword());

            pwErrorLabel.setText("");

            if (temp.isEmpty() || newPw.isEmpty() || confirmPw.isEmpty()) {
                pwErrorLabel.setText("모든 항목을 입력해주세요.");
                return;
            }

            if (!newPw.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=]).{8,12}$")) {
                pwErrorLabel.setText("비밀번호는 영문/숫자/특수문자 포함 8~12자여야 합니다.");
                return;
            }

            if (!newPw.equals(confirmPw)) {
                pwErrorLabel.setText("비밀번호가 일치하지 않습니다.");
                return;
            }

            JOptionPane.showMessageDialog(this, "비밀번호가 성공적으로 변경되었습니다.");
            new LoginFrame(manager);
        });

        setLocationRelativeTo(null);
        setVisible(true);
        
    }

    private void styleBlue(AbstractButton b) {
        b.setBackground(Color.decode("#003087"));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }

    private void styleGray(AbstractButton b) {
        b.setBackground(Color.decode("#D9D9D9"));
        b.setForeground(Color.decode("#4A4A4A"));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }
}
