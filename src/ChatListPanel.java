import javax.swing.*;

import model.ChatRoom;
import model.Profile;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ChatListPanel extends JPanel {
    private chatMainFrame parentFrame;
    private JPanel listContainer;

    public ChatListPanel(chatMainFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout());

       
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        

        
        JScrollPane scroll = new JScrollPane(listPanel);
        add(scroll, BorderLayout.CENTER);

        refresh();
    }
    /*
     * public void refresh() {
        removeAll();
        revalidate();
        repaint();
    
        List<ChatRoom> rooms = parentFrame.getChatRooms();
        for (ChatRoom room : rooms) {
            JButton btn = new JButton("채팅방 #" + room.getChatRoomID());
            btn.addActionListener(e -> parentFrame.openChatRoom(room));
            add(btn);
        }
    }
    
     */
  
     public void refresh() {
        removeAll();
        setLayout(new BorderLayout()); // 다시 설정
    
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
    
        List<ChatRoom> rooms = parentFrame.getChatRooms();
        for (ChatRoom room : rooms) {
            JPanel item = createChatItemForRoom(room);
            listPanel.add(item);
        }
    
        JScrollPane scroll = new JScrollPane(listPanel);
        add(scroll, BorderLayout.CENTER);
    
        revalidate();
        repaint();
    }
    
    private JPanel createChatItemForRoom(ChatRoom room) {
        JPanel item = new JPanel(new BorderLayout());
        item.setPreferredSize(new Dimension(350, 60));
        item.setMaximumSize(new Dimension(1000, 60));
        item.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        // 텍스트 정보
        JLabel nameLabel = new JLabel("채팅방 #" + room.getChatRoomID());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        item.add(nameLabel, BorderLayout.CENTER);

        // 클릭 이벤트 → 실제 채팅방 열기
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        item.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                parentFrame.openChatRoom(room);
            }
        });

        return item;
    }

    
    


}
