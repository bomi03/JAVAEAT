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
        

        listPanel.add(createChatItem(frame, "송송이", "송송이님과 팀이 되었어요!", "detail1"));
        listPanel.add(createChatItem(frame, "논송", "010-XXXX-XXXX입니다", "detail2"));
        listPanel.add(createChatItem(frame, "눈꽃송이", "잘 부탁합니다!", "detail3"));

        JScrollPane scroll = new JScrollPane(listPanel);
        add(scroll, BorderLayout.CENTER);

        refresh();
    }
    public void refresh() {
        removeAll(); // 기존 UI 모두 제거

        // 최신 채팅방 정보 다시 가져오기
        List<ChatRoom> rooms = parentFrame.getChatRooms();

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        for (ChatRoom room : rooms) {
            // 참여자 이름 추출 (자기 제외)
            String name = room.getParticipants().stream()
                            .filter(p -> p.getProfileID() != parentFrame.getUser().getProfile().getProfileID())
                            .map(Profile::getNickname)
                            .findFirst()
                            .orElse("알 수 없음");

            // 최근 메시지 추출
            String preview = room.getMessages().isEmpty() ?
                            "대화를 시작해보세요!" :
                            room.getMessages().get(room.getMessages().size() - 1).getContent();

            // 카드형 패널로 추가
            JPanel item = createChatItem(parentFrame, name, preview, "chatRoom_" + room.getChatRoomID());
            listPanel.add(item);
            listPanel.add(Box.createVerticalStrut(5));
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    /* 
    public void refresh() {
        removeAll();
        // 예시: 모든 채팅방 목록을 다시 그리는 코드
        List<ChatRoom> rooms = parentFrame.getChatRooms();
        for (ChatRoom room : rooms) {
            JButton btn = new JButton("채팅방 #" + room.getChatRoomID());
            btn.addActionListener(e -> parentFrame.openChatRoom(room));
            add(btn);
        }
        revalidate();
        repaint();
    }
    */
    
    

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
