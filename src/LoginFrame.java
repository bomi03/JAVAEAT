// LoginFrame.java

import javax.swing.*;
import java.awt.*;
import model.Management;
import model.User;

public class LoginFrame extends JFrame {
    private Management manager;

    public LoginFrame(Management mgr) {
        super("로그인");
        this.manager = mgr;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(393, 852);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#FfFfFf"));
        setContentPane(panel);

        // 기존 MainFrame.placeComponents(panel, manager) 그대로 호출
        MainFrame.placeComponents(panel, manager);

        setVisible(true);
    }

    // standalone 테스트용
    public static void main(String[] args) {
        Management manager = new Management(new java.util.HashMap<>());
        // 테스트용 기본 유저 등록
        manager.addUser(new User("서연", "test", "1234", "123@sookmyung.ac.kr"));
        SwingUtilities.invokeLater(() -> new LoginFrame(manager));
    }
}