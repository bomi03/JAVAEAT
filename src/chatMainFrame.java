import javax.swing.*;

import model.ChatRoom;
import model.Management;
import model.Profile;
import model.Team;
import model.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class chatMainFrame extends JFrame {

    private User user;
    private Management manager;
    private JPanel mainPanel;
    private JLabel headerLabel;

    CardLayout cardLayout;

    // ✅ 기본 생성자 (채팅방 바로 열지 않음)
    public chatMainFrame(User user, Management manager) {
        this.user = user;
        this.manager = manager;
        initUI();
    }

    // ✅ 공통 UI 구성
    private void initUI() {
        setTitle("팀매칭 채팅 UI");
        setSize(393, 852);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.decode("#FfFfFf"));

        // 화면 등록
        ChatListPanel chatListPanel = new ChatListPanel(this);
        mainPanel.add(chatListPanel, "list");

        JPanel notifyPanel = new JPanel();
        notifyPanel.add(new JLabel("알림 화면"));

        JPanel myPagPanel = new JPanel();
        myPagPanel.add(new JLabel("마이페이지"));

        // 패널 등록
        mainPanel.add(notifyPanel, "notify");
        mainPanel.add(myPagPanel, "mypage");

        // 상단 박스
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(230, 230, 230));
        headerPanel.setPreferredSize(new Dimension(0, 50));

        headerLabel = new JLabel("채팅", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SanSerif", Font.BOLD, 18));

        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // 하단 네비게이션 바
        /*BottomNavBar bottomNavBar = new BottomNavBar(
            e -> {
                new TeamListPage(user, manager);
                dispose();
            },
            e -> showPanel("list"),
            e -> showPanel("notify"),
            e -> {
                Profile profile = new Profile(1, user.getUserID());
                profile.setNickname("새송이버섯");
                profile.setAdmissionYear("22학번");
                user.setProfile(profile);
                new MyPage(user, manager);
                dispose();
            }
        ); */
        
        BottomNavBar bottomNavBar = new BottomNavBar(
            e -> { new TeamListPage(user, manager); dispose(); },
            e -> { showPanel("list"); },
            e -> { NotificationPage page = new NotificationPage(user, manager);
                    page.setVisible(true);
                    dispose();
                },
            e -> { new MyPage(user, manager); dispose(); }
        );
        add(bottomNavBar, BorderLayout.PAGE_END);

        setVisible(true);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
        if (name.equals("list")) headerLabel.setText("채팅");
    }

    public List<ChatRoom> getChatRooms(){
        List<ChatRoom> result = new ArrayList<>();
        for(Team t: Team.getAllTeams()){
            result.addAll(t.getChatRooms());
        }
        return result;
    }

    public void openChatRoom(ChatRoom chatRoom) {
        String key = "chatRoom_" + chatRoom.getChatRoomID();
    
        // 이미 등록된 패널인지 확인
        for (Component comp : mainPanel.getComponents()) {
            if (key.equals(comp.getName())) {
                showPanel(key);
                return;
            }
        }
    
        // 패널이 없으면 새로 추가
        ChatDetailPanel detailPanel = new ChatDetailPanel(this, chatRoom);
        detailPanel.setName(key); // 이름 설정 중요!!
        mainPanel.add(detailPanel, key);
        showPanel(key);
        headerLabel.setText("채팅방");
    }
    

    public void refreshChatList(){
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof ChatListPanel chatListPanel) {
                chatListPanel.refresh();
            }
        }
    }

    public User getUser() {
        return user;
    }

    public static void main(String[] args) {
        /*
        Management manager = new Management(new HashMap<>());
        User user = new User("서연", "test", "pw", "s@sm.ac.kr");
        manager.addUser(user);
        SwingUtilities.invokeLater(() -> {
            chatMainFrame frame = new chatMainFrame(user, manager);
            // 채팅방이 있다면 열기
            for (ChatRoom room : frame.getChatRooms()) {
                frame.openChatRoom(room);
            }
        });
        */
    }
}
