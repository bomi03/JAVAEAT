import java.awt.*;
import javax.swing.*;
import model.Management;
import model.User;

public class FindIdFrame extends JFrame {

    private Management manager;

    public FindIdFrame(Management manager) {
        this.manager = manager;

        setTitle("아이디 찾기");
        setSize(393, 852);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // 뒤로가기 버튼
        JButton backButton = new JButton("<");
        backButton.setBounds(10, 10, 50, 30);
        styleGray(backButton);
        add(backButton);

        backButton.addActionListener(e -> dispose());

        // 제목
        JLabel titleLabel = new JLabel("아이디 찾기", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBounds(120, 10, 150, 30);
        add(titleLabel);

        // 구분선
        JSeparator separator = new JSeparator();
        separator.setBounds(20, 50, 350, 1);
        add(separator);

        // 안내 문구
        JLabel guideLabel = new JLabel("회원가입시 등록한 이름과 이메일을 입력해주세요");
        guideLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        guideLabel.setBounds(30, 60, 330, 30);
        add(guideLabel);

        // 이름 입력
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

        // 이메일 입력
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

        // 결과 화면 구성
        JLabel foundLabel = new JLabel("아이디를 찾았어요!");
        foundLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        foundLabel.setBounds(30, 70, 300, 30);
        foundLabel.setVisible(false);
        add(foundLabel);

        JLabel subGuideLabel = new JLabel("아래 계정으로 로그인 해주세요.");
        subGuideLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subGuideLabel.setForeground(Color.DARK_GRAY);
        subGuideLabel.setBounds(30, 110, 300, 20);
        subGuideLabel.setVisible(false);
        add(subGuideLabel);

        JTextField resultField = new JTextField();
        resultField.setBounds(30, 150, 320, 40);
        resultField.setHorizontalAlignment(JTextField.CENTER);
        resultField.setFont(new Font("SansSerif", Font.BOLD, 18));
        resultField.setEditable(false);
        resultField.setVisible(false);
        add(resultField);

        JButton goToLoginButton = new JButton("로그인하러가기");
        goToLoginButton.setBounds(30, 200, 320, 40);
        goToLoginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        goToLoginButton.setVisible(false);
        styleBlue(goToLoginButton);
        add(goToLoginButton);

        goToLoginButton.addActionListener(e2 -> dispose());

        JLabel[] toHide = {
            guideLabel, nameLabel, emailLabel, nameErrorLabel, emailErrorLabel
        };
        JTextField[] toHideFields = { nameField, emailField };

        // 다음 버튼
        JButton nextButton = new JButton("다음");
        nextButton.setBounds(30, 280, 320, 40);
        styleBlue(nextButton);
        add(nextButton);

        nextButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            nameErrorLabel.setText("");
            emailErrorLabel.setText("");
            resultField.setText("");

            boolean valid = true;
            if (name.isEmpty()) {
                nameErrorLabel.setText("이름을 입력해주세요.");
                valid = false;
            }
            if (email.isEmpty()) {
                emailErrorLabel.setText("이메일을 입력해주세요.");
                valid = false;
            }

            if (valid) {
                String foundID = findUserID(name, email);
                if (foundID != null) {
                    for (JLabel label : toHide) label.setVisible(false);
                    for (JTextField field : toHideFields) field.setVisible(false);
                    nextButton.setVisible(false);

                    foundLabel.setVisible(true);
                    subGuideLabel.setVisible(true);
                    resultField.setText(foundID);
                    resultField.setVisible(true);
                    goToLoginButton.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "일치하는 회원 정보를 찾을 수 없습니다.");
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 아이디 찾기
    private String findUserID(String name, String email) {
        for (User user : manager.getAllUsers()) {
            if (user.getUsername().equals(name) && user.getEmail().equals(email)) {
                return user.getUserID();
            }
        }
        return null;
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
