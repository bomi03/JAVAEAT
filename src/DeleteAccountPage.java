import java.awt.*;
import javax.swing.*;
import model.Management;
import model.User;

public class DeleteAccountPage extends JFrame {
    private final User user;
    private final Management manager;

    public DeleteAccountPage(User user, Management manager) {
        this.user = user;
        this.manager = manager;

        setTitle("회원 탈퇴");
        setSize(393, 852);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // 뒤로가기
        JButton backButton = new JButton("←");
        backButton.setBounds(10, 10, 50, 30);
        styleGray(backButton);
        backButton.addActionListener(e -> dispose());
        add(backButton);

        // 제목
        JLabel titleLabel = new JLabel("회원 탈퇴", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBounds(0, 10, 393, 30);
        add(titleLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(20, 50, 350, 1);
        add(separator);

        JLabel warningLabel = new JLabel("회원 탈퇴 시 모든 정보가 삭제됩니다.");
        warningLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        warningLabel.setBounds(30, 80, 330, 20);
        add(warningLabel);

        JLabel confirmLabel = new JLabel("정말로 탈퇴하시겠습니까?");
        confirmLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        confirmLabel.setBounds(30, 120, 330, 30);
        add(confirmLabel);

        JButton confirmBtn = new JButton("탈퇴하기");
        confirmBtn.setBounds(30, 200, 320, 40);
        styleBlue(confirmBtn);
        add(confirmBtn);

        confirmBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                this,
                "정말로 탈퇴하시겠습니까? 이 작업은 되돌릴 수 없습니다.",
                "회원 탈퇴 확인",
                JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                manager.getAllUsers().remove(user);
                user.withdraw();
                JOptionPane.showMessageDialog(this, "정상적으로 탈퇴 처리되었습니다.");
                dispose();
                new LoginFrame(manager);
            }
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
