import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import model.ChatRoom;
import model.Message;
import model.Post;
import model.Profile;

public class ChatDetailPanel extends JPanel {
    private JTextField inputField;
    private ChatRoom chatRoom;
    private Profile myProfile;

    private JPanel chatContentPanel;
    private JScrollPane scrollPane;

    public ChatDetailPanel(chatMainFrame frame, ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        this.myProfile = frame.getUser().getProfile();
        setLayout(new BorderLayout());

        //게시믈 정보 불러오기
        int postID = chatRoom.getPostID();
        Post post = frame.getManager().getPostByID(postID);


        // ✅ 상단 영역: 뒤로가기 + 타이틀 + 모집글 요약
        JPanel topSection = new JPanel();
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(0, 50));
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JButton backButton = new JButton("←");
        backButton.setPreferredSize(new Dimension(50, 50));
        backButton.setFocusPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> frame.showPanel("list"));

        JLabel titleLabel = new JLabel("채팅방", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.decode("#003087"));

        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);

        // 모집글 요약 패널
        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        summaryPanel.setBackground(new Color(245, 245, 245));

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setOpaque(false);

        if(post != null){
            String title = post.getTitle();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String deadline = sdf.format(post.getRecruitDeadline());
            String members = post.getCurrentApplicants() + "/" + post.getMaxApplicants() + "명";

            infoPanel.add(new JLabel(title));
            infoPanel.add(new JLabel("모집마감: "+ deadline));
            infoPanel.add(new JLabel("모집인원: "+ members));
        }else{
            infoPanel.add(new JLabel("게시물 정보를 찾을 수 없습니다."));
        }
        

        JPanel thumbnail = new JPanel();
        thumbnail.setPreferredSize(new Dimension(60, 60));
        thumbnail.setBackground(Color.LIGHT_GRAY);

        summaryPanel.add(infoPanel, BorderLayout.CENTER);
        summaryPanel.add(thumbnail, BorderLayout.EAST);

        topSection.add(topBar);
        topSection.add(summaryPanel);
        add(topSection, BorderLayout.NORTH);

        // ✅ 채팅 내용 영역 (말풍선 정렬)
        chatContentPanel = new JPanel();
        chatContentPanel.setLayout(new BoxLayout(chatContentPanel, BoxLayout.Y_AXIS));
        chatContentPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(chatContentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // 초기 메시지 렌더링
        for (Message msg : chatRoom.getMessages()) {
            addMessageBubble(msg);
        }

        // ✅ 입력창 영역
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        JButton sendButton = new JButton("전송");

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            String senderName = myProfile.getUserID();
            Message msg = new Message(
                chatRoom.getMessages().size() + 1,
                chatRoom.getChatRoomID(),
                senderName,
                text
            );
            chatRoom.addMessage(msg);
            addMessageBubble(msg);
            inputField.setText("");
        }
    }

    private void addMessageBubble(Message msg) {
        boolean isMe = msg.getSenderID().equals(myProfile.getUserID());

        JPanel bubblePanel = new JPanel(new FlowLayout(isMe ? FlowLayout.RIGHT : FlowLayout.LEFT));
        bubblePanel.setOpaque(false);
        bubblePanel.setBorder(BorderFactory.createEmptyBorder(4 , 5, 4, 5)); // 간격 줄이기

        JTextArea bubble = new JTextArea(msg.getContent());
        bubble.setEditable(false);
        bubble.setLineWrap(true);
        bubble.setWrapStyleWord(true);
        bubble.setFont(new Font("SansSerif", Font.PLAIN, 13));
        bubble.setBackground(isMe ? new Color(220, 240, 255) : new Color(240, 240, 240));
        bubble.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        bubble.setMargin(new Insets(0, 0, 0, 0));
        bubble.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // ✅ 내부 패딩
        bubble.setMaximumSize(new Dimension(250, Short.MAX_VALUE));

        bubblePanel.add(bubble);
        chatContentPanel.add(bubblePanel);
        chatContentPanel.revalidate();
        chatContentPanel.repaint();

        SwingUtilities.invokeLater(() ->
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum())
        );
    }
}
