import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatDetailPanel extends JPanel {
    private JTextArea chatArea;
    private JTextField inputField;

    public ChatDetailPanel(MainFrame frame, int chatType) {
        setLayout(new BorderLayout());

    // 👉 상단 전체 묶는 패널
    JPanel topSection = new JPanel();
    topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));

    // ← 버튼 + 제목 바
    JPanel topBar = new JPanel(new BorderLayout());
    topBar.setBackground(new Color(230, 230, 230));
    topBar.setPreferredSize(new Dimension(0, 50));

    JButton backButton = new JButton("←");
    backButton.addActionListener(e-> frame.showPanel("list"));
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

    // ✅ 상단 전체에 두 패널 추가
    topSection.add(topBar);
    topSection.add(summaryPanel);

    // ✅ 전체 상단 영역을 NORTH에 한 번에 추가
    add(topSection, BorderLayout.NORTH);


       

        // 📄 채팅 내용 표시 영역
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // 🧾 채팅 내용 초기 세팅
        switch (chatType) {
            case 1 -> chatArea.setText("송송이님과 팀이 되었어요!\n");
            case 2 -> chatArea.setText("""
                논송님과 팀이 되었어요!
                [상대] 안녕하세요!
                [나] 안녕하세요! 팀원 한 분 모집될까요?
                [상대] 네! 전화번호 주시면 단톡 만들고 연락 드릴게요!
                [나] 010-XXXX-XXXX입니다 잘 부탁드립니다~\n""");
            case 3 -> chatArea.setText("""
                눈꽃송님과 팀이 되었어요!
                [나] 안녕하세요!! 수락해주셔서 감사합니다!
                https://XXX.XXX.XXX 초대링크입니다!
                [상대] 여기로 들어가면 될까요?
                [나] 네! 여기서 업무 협업을 조정하면 됩니다!
                [상대] 알겠습니다!\n""");
        }

        // 💬 채팅 입력 바
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        JButton sendButton = new JButton("전송");

        // 전송 버튼 누를 때 입력값을 아래 채팅창에 추가
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage()); // Enter 키도 가능

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

    


    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            chatArea.append("[나] " + text + "\n");
            inputField.setText("");
        }
    }
}
