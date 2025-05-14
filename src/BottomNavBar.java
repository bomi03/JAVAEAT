// BottomNavBar.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BottomNavBar extends JPanel {
    public BottomNavBar(ActionListener onHome,
                        ActionListener onChat,
                        ActionListener onNotify,
                        ActionListener onMy) {
        setLayout(new GridLayout(1, 4));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.decode("#D9D9D9")));

        add(makeButton("홈",    onHome));
        add(makeButton("채팅",  onChat));
        add(makeButton("알림",  onNotify));
        add(makeButton("MY",   onMy));
    }

    private JButton makeButton(String text, ActionListener listener) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setBackground(Color.WHITE);
        b.addActionListener(listener);
        return b;
    }
}
