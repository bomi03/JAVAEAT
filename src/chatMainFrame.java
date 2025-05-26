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
        setResizable(true);
        setLayout(new BorderLayout());

        /*상단바
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(393, 50));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        headerLabel = new JLabel("채팅", SwingConstants.CENTER); // 👈 클래스 필드 사용
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerLabel.setForeground(Color.BLACK);

        headerPanel.add(headerLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
        */
        

     

        //CardLayout 중앙 영역
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);

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

        add(mainPanel, BorderLayout.CENTER);

 

        
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
    public void showPreviousPage(){
        showPanel("list");
    }
    public Management getManager() {
        return manager;
    }
    

    public static void main(String[] args) {
       
    }
}
