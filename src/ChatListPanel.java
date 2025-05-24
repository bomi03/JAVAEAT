import javax.swing.*;

import model.ChatRoom;
import model.Message;
import model.Profile;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class ChatListPanel extends JPanel {
    private chatMainFrame parentFrame;
    //private JPanel listContainer;

    public ChatListPanel(chatMainFrame frame) {
        this.parentFrame = frame;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(393,852));


        refresh();
    }
    /*    private JPanel buildTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(393, 50));
        topBar.setBackground(new Color(245, 245, 245));

        JButton backButton = new JButton("←");
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        backButton.setForeground(Color.BLACK);
        backButton.setPreferredSize(new Dimension(50, 50));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // 뒤로가기 로직 (parentFrame 내부에서 처리)
        backButton.addActionListener(e -> parentFrame.showPreviousPage());

        JLabel title = new JLabel("채팅 목록", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);

        return topBar;
    } */

    
  
  
     public void refresh() {
        removeAll();
    
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);
    
        List<ChatRoom> rooms = parentFrame.getChatRooms();
        if(rooms.isEmpty()){
            JLabel emptyLabel = new JLabel("참여 중인 채팅방이 없습니다.",SwingConstants.CENTER);
            emptyLabel.setFont(new Font("맑은 고딕",Font.PLAIN,14));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
            listPanel.add(emptyLabel);
        }else{
            Profile myProfile = parentFrame.getUser().getProfile();
            for (ChatRoom room : rooms) {
                JPanel item = createChatItemForRoom(room,myProfile);
                listPanel.add(item);
                listPanel.add(createSeparator());
            

        }
        }
    
        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setPreferredSize(new Dimension(393, 700));

        add(scroll, BorderLayout.CENTER);
    
        revalidate();
        repaint();
    }
    
    private JPanel createChatItemForRoom(ChatRoom room,Profile myProfile) {

        String targetName = room.getTargetName(myProfile);

        JPanel item = new JPanel(new BorderLayout());
        item.setPreferredSize(new Dimension(393, 80));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE,80));
        item.setBackground(Color.WHITE);
        item.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //프로필 이미지
        JLabel profileImg = new JLabel();
        profileImg.setPreferredSize(new Dimension(50,50));
        profileImg.setMaximumSize(new Dimension(50,50));

        try{
            ImageIcon icon = new ImageIcon("꾸꾸송이.png");
            Image scaled = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            profileImg.setIcon(new ImageIcon(scaled));
        } catch(Exception e){
            System.out.println("이미지 로드 실패: " + e.getMessage());
            profileImg.setText("?");

        }

        item.add(profileImg,BorderLayout.WEST);

        // 텍스트 정보
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(targetName);
        nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        List<Message> recent = room.getRecentMessages(1);
        String previewText = recent.isEmpty() ? "메시지 없음":recent.get(0).getContent();
        JLabel preview = new JLabel(previewText);
        preview.setFont(new Font("맑은 고딕",Font.PLAIN,12));
        preview.setBackground(Color.GRAY);
       // item.add(nameLabel, BorderLayout.CENTER);

       textPanel.add(nameLabel);
       textPanel.add(Box.createHorizontalStrut(4));
       textPanel.add(preview);

       item.add(textPanel,BorderLayout.CENTER);

        // 클릭 이벤트 → 실제 채팅방 열기
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        item.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                parentFrame.openChatRoom(room);
            }
        });

        return item;
    }
    private JSeparator createSeparator() {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(Color.decode("#E0E0E0"));
        return sep;
    } 

    
    


}
