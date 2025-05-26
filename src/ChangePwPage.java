import java.awt.*;
import javax.swing.*;
import model.Management;
import model.User;

public class ChangePwPage extends JFrame {
    private final User user;
    private final Management manager; // ✅ 추가

    public ChangePwPage(User user, Management manager) {
        this.user = user;
        this.manager = manager;

        setTitle("비밀번호 변경");
        setSize(393, 852);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // ⬅️ 뒤로가기 버튼
        JButton backButton = new JButton("<");
        backButton.setBounds(10, 10, 50, 30);
        styleGray(backButton);
        backButton.addActionListener(e -> dispose());
        add(backButton);

        // 표제
        JLabel titleLabel = new JLabel("비밀번호 변경", SwingConstants.CENTER);
        titleLabel.setBounds(120, 10, 150, 30);
        add(titleLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(20, 50, 350, 1);
        add(separator);

        // 기존 비밀번호
        JLabel currentLabel = new JLabel("기존 비밀번호:");
        currentLabel.setBounds(30, 80, 150, 25);
        add(currentLabel);

        JPasswordField currentField = new JPasswordField();
        currentField.setBounds(30, 110, 320, 30);
        add(currentField);

        // 새 비밀번호
        JLabel newLabel = new JLabel("새 비밀번호:");
        newLabel.setBounds(30, 160, 150, 25);
        add(newLabel);

        JPasswordField newField = new JPasswordField();
        newField.setBounds(30, 190, 320, 30);
        add(newField);

        JLabel confirmLabel = new JLabel("비밀번호 확인:");
        confirmLabel.setBounds(30, 240, 150, 25);
        add(confirmLabel);

        JPasswordField confirmField = new JPasswordField();
        confirmField.setBounds(30, 270, 320, 30);
        add(confirmField);

        JLabel errorLabel = new JLabel("");
        errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(30, 310, 320, 20);
        add(errorLabel);

        JButton changeBtn = new JButton("변경하기");
        changeBtn.setBounds(30, 350, 320, 40);
        styleBlue(changeBtn);
        add(changeBtn);

        changeBtn.addActionListener(e -> {
            String current = new String(currentField.getPassword());
            String newPw = new String(newField.getPassword());
            String confirm = new String(confirmField.getPassword());
            errorLabel.setText("");

            if (!user.getPassword().equals(current)) {
                errorLabel.setText("기존 비밀번호가 일치하지 않습니다.");
                return;
            }

            if (!newPw.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=]).{8,12}$")) {
                errorLabel.setText("비밀번호는 영문/숫자/특수문자 포함 8~12자여야 합니다.");
                return;
            }

            if (!newPw.equals(confirm)) {
                errorLabel.setText("비밀번호가 일치하지 않습니다.");
                return;
            }

            user.changePassword(current, newPw);
            JOptionPane.showMessageDialog(this, "비밀번호가 성공적으로 변경되었습니다.");
            dispose();
        });

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
