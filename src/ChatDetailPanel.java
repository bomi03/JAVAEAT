import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.ChatRoom;
import model.Message;

public class ChatDetailPanel extends JPanel {
    private JTextArea chatArea;
    private JTextField inputField;
    private ChatRoom chatRoom;

    public ChatDetailPanel(chatMainFrame frame, ChatRoom chatRoom) {
        this.chatRoom = chatRoom; // ✅ 저장
        setLayout(new BorderLayout());

        // 👉 상단 전체 묶는 패널
        JPanel topSection = new JPanel();
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));

        // ← 버튼 + 제목 바
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(230, 230, 230));
        topBar.setPreferredSize(new Dimension(0, 50));

        JButton backButton = new JButton("←");
        backButton.addActionListener(e -> frame.showPanel("list"));
        JLabel titleLabel = new JLabel("채팅", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);

        // 📦 모집글 요약 박스
        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        summaryPanel.setBackground(new Color(245, 245, 245));

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setOpaque(false);
        infoPanel.add(new JLabel("스터디 OOO 팀원 모집"));
        infoPanel.add(new JLabel("모집기간 : 2025/05/01 ~ 2025/05/14"));
        infoPanel.add(new JLabel("모집인원 5/5명"));

        JPanel thumbnail = new JPanel();
        thumbnail.setPreferredSize(new Dimension(60, 60));
        thumbnail.setBackground(Color.LIGHT_GRAY);

        summaryPanel.add(infoPanel, BorderLayout.CENTER);
        summaryPanel.add(thumbnail, BorderLayout.EAST);

        topSection.add(topBar);
        topSection.add(summaryPanel);
        add(topSection, BorderLayout.NORTH);

        // 📄 채팅 내용 표시 영역
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // ✅ ChatRoom 메시지 불러오기
        chatArea.setText("🧩 채팅방 ID: " + chatRoom.getChatRoomID() + "\n");
        chatArea.append("👥 참여자 수: " + chatRoom.getParticipants().size() + "명\n");
        for (Message msg : chatRoom.getMessages()) {
            chatArea.append("[" + msg.getSenderID() + "] " + msg.getContent() + "\n");
        }

        // 💬 채팅 입력 바
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
            Message msg = new Message(
                chatRoom.getMessages().size() + 1,  // 메시지 ID (단순 증가)
                chatRoom.getChatRoomID(),
                "나",  // 실제 senderID 필요 시 바꾸기
                text
            );
            chatRoom.addMessage(msg);

            chatArea.append("[나] " + text + "\n");
            inputField.setText("");
        }
    }
}
