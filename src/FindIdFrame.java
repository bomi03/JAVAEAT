// FindIdFrame.java
import java.awt.*;
import javax.swing.*;

public class FindIdFrame extends JFrame {
    public FindIdFrame() {
        setTitle("아이디 찾기");
        setSize(393, 852);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // 뒤로가기 버튼
        JButton backButton = new JButton("<");
        backButton.setBounds(10, 10, 50, 30);
        add(backButton);

        // 제목 라벨
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

        // 이름 라벨 및 입력창
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

        // 이메일 라벨 및 입력창
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

        // 다음 버튼
        JButton nextButton = new JButton("다음");
        nextButton.setBounds(30, 280, 320, 40);
        add(nextButton);

        nextButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            nameErrorLabel.setText("");
            emailErrorLabel.setText("");

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
                JOptionPane.showMessageDialog(this, "아이디 찾았어요!");
            }
        });

        setVisible(true);
    }
}
