import javax.swing.*;

import model.Management;
import model.Profile;
import model.User;

import java.awt.*;
import java.util.HashMap;

public class chatMainFrame extends JFrame {

    private User user;
    private Management manager;
    private JPanel mainPanel;

    CardLayout cardLayout;

    public chatMainFrame(User user, Management manager) {

        this.user =user;
        this.manager = manager;

        setTitle("팀매칭 채팅 UI");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        
        // 화면 등록
        ChatListPanel chatListPanel = new ChatListPanel(this);
        ChatDetailPanel detail1 = new ChatDetailPanel(this, 1);
        ChatDetailPanel detail2 = new ChatDetailPanel(this, 2);
        ChatDetailPanel detail3 = new ChatDetailPanel(this, 3);

        

        JPanel notifyPanel = new JPanel();
        notifyPanel.add(new JLabel("알림 화면"));

        JPanel myPagPanel = new JPanel();
        myPagPanel.add(new JLabel("마이페이지"));


        // 패널 등록
        mainPanel.add(chatListPanel, "list");
        mainPanel.add(detail1, "detail1");
        mainPanel.add(detail2, "detail2");
        mainPanel.add(detail3, "detail3");
        mainPanel.add(notifyPanel, "notify");
        mainPanel.add(myPagPanel, "mypage");


        // 상단 박스
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(230, 230, 230));
        headerPanel.setPreferredSize(new Dimension(0, 50));

        JLabel headerLabel = new JLabel("채팅", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SanSerif", Font.BOLD, 18));

        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel,BorderLayout.CENTER);

        // 하단  네비게이션 바
        BottomNavBar bottomNavBar = new BottomNavBar(
            e -> {
                new TeamListPage(user, manager);
                dispose();
            },
            e -> showPanel("list"),
            e -> showPanel("notify"),
            e -> {
                //[테스트용] MyPage가 실행되도록 임시 프로필 설정
                Profile profile = new Profile(1,user.getUserID());
                profile.setNickname("새송이버섯");
                profile.setAdmissionYear("22학번");
                user.setProfile(profile);
                System.out.println("MyPage로 이동 시도도");
                new MyPage(user, manager);
                dispose();
            }
        );
        add(bottomNavBar, BorderLayout.PAGE_END);

    

        setVisible(true);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    public static void main(String[] args) {
        Management manager = new Management(new HashMap<>());
        User user = new User("서연","test","pw","s@sm.ac.kr");
        manager.addUser(user);

        SwingUtilities.invokeLater((()-> new chatMainFrame(user, manager)));
        
        
    }
}
