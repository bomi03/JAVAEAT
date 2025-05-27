import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;

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

        // 게시물 정보
        int postID = chatRoom.getPostID();
        Post post = frame.getManager().getPostByID(postID);

        // 상단: 뒤로가기 + 타이틀
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(0, 50));
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JButton backButton = new JButton("←");
        backButton.setPreferredSize(new Dimension(50, 30));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> frame.showPanel("list"));

        JLabel titleLabel = new JLabel("채팅방", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);

        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        add(topBar, BorderLayout.NORTH);

        // ✅ 채팅 내용 패널 (텍스트만)
        chatContentPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                int lineHeight = 20;
                return new Dimension(0, getComponentCount() * lineHeight);
            }
        };
        chatContentPanel.setLayout(new BoxLayout(chatContentPanel, BoxLayout.Y_AXIS));
        chatContentPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(chatContentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // ✅ 초기 메시지
        for (Message msg : chatRoom.getMessages()) {
            addMessageLine(msg);
        }

        // ✅ 입력창
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
            String sender = myProfile.getUserID();
            Message msg = new Message(
                chatRoom.getMessages().size() + 1,
                chatRoom.getChatRoomID(),
                sender,
                text
            );
            chatRoom.addMessage(msg);
            addMessageLine(msg);
            inputField.setText("");
        }
    }

private void addMessageLine(Message msg) {
    boolean isMe = msg.getSenderID().equals(myProfile.getUserID());

    // ✅ HTML로 max-width 제한 (텍스트에 맞게 줄바꿈)
    String text = "<html><div style='max-width: 250px;'>" + msg.getSenderID() + ": " + msg.getContent() + "</div></html>";

    JLabel label = new JLabel(text);
    label.setFont(new Font("SansSerif", Font.PLAIN, 13));
    label.setOpaque(true);
    label.setBackground(isMe ? new Color(200, 230, 255) : new Color(240, 240, 240));
    label.setForeground(Color.BLACK);
    label.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

    // ✅ label 크기를 텍스트에 맞게 조절 (고정 폭 방지)
    label.setMaximumSize(label.getPreferredSize());

    // ✅ 정렬용 줄 패널
    JPanel linePanel = new JPanel();
    linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.X_AXIS));
    linePanel.setOpaque(false);
    linePanel.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));

    if (isMe) {
        linePanel.add(Box.createHorizontalGlue());
        linePanel.add(label);
    } else {
        linePanel.add(label);
        linePanel.add(Box.createHorizontalGlue());
    }

    chatContentPanel.add(linePanel);
    chatContentPanel.revalidate();
    chatContentPanel.repaint();

    SwingUtilities.invokeLater(() -> {
        JScrollBar bar = scrollPane.getVerticalScrollBar();
        bar.setValue(bar.getMaximum());
    });
}

    
    


    
}
