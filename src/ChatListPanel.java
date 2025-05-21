import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatListPanel extends JPanel {
    
    public ChatListPanel(chatMainFrame frame) {
        
        setLayout(new BorderLayout());
   

       
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        listPanel.add(createChatItem(frame, "송송이", "송송이님과 팀이 되었어요!", "detail1"));
        listPanel.add(createChatItem(frame, "논송", "010-XXXX-XXXX입니다", "detail2"));
        listPanel.add(createChatItem(frame, "눈꽃송이", "잘 부탁합니다!", "detail3"));

        JScrollPane scroll = new JScrollPane(listPanel);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel createChatItem(chatMainFrame frame, String name, String message, String panelName) {
        JPanel item = new JPanel(new BorderLayout());
        item.setPreferredSize(new Dimension(350, 60));
        item.setMaximumSize(new Dimension(1000, 60));
        item.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        // 🖼️ 프로필 박스
        JPanel profilePic = new JPanel();
        profilePic.setPreferredSize(new Dimension(40, 40));
        profilePic.setMaximumSize(new Dimension(40, 40));
        profilePic.setBackground(Color.GRAY);
        profilePic.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        profilePic.setOpaque(true);
        profilePic.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JLabel profileRound = new JLabel(); // 이미지도 가능
        profileRound.setPreferredSize(new Dimension(40, 40));
        profilePic.add(profileRound);

        // 📝 텍스트 영역
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel msgLabel = new JLabel(message);
        msgLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        msgLabel.setForeground(Color.GRAY);

        textPanel.add(nameLabel);
        textPanel.add(msgLabel);

        JPanel leftWrap = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftWrap.setOpaque(false);
        leftWrap.add(profilePic);
        leftWrap.add(textPanel);

        item.add(leftWrap, BorderLayout.CENTER);

        // 클릭 이벤트
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        item.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.showPanel(panelName);
            }
        });

        return item;
    }
}
