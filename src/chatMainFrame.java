import javax.swing.*;
import java.awt.*;

public class chatMainFrame extends JFrame {
    CardLayout cardLayout;
    JPanel mainPanel;

    public chatMainFrame() {

        setTitle("팀매칭 채팅 UI");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        //각 패널 
    


        // 화면 등록
        ChatListPanel chatListPanel = new ChatListPanel(this);
        ChatDetailPanel detail1 = new ChatDetailPanel(this, 1);
        ChatDetailPanel detail2 = new ChatDetailPanel(this, 2);
        ChatDetailPanel detail3 = new ChatDetailPanel(this, 3);

        JPanel notifyPanel = new JPanel();
        notifyPanel.add(new JLabel("알림 화면"));

        JPanel myPagPanel = new JPanel();
        myPagPanel.add(new JLabel("마이페이지"));
        
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
            e -> MainFrame.main(null),
            e -> showPanel("list"),
            e -> JOptionPane.showMessageDialog(this, "알림은 준비 중"),
            e -> MyPage.main(null)
        );
        add(bottomNavBar, BorderLayout.PAGE_END);

    

        setVisible(true);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    public static void main(String[] args) {
        new chatMainFrame();
    }
}
