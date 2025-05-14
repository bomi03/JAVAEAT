import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    CardLayout cardLayout;
    JPanel mainPanel;

    public MainFrame() {

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

        mainPanel.add(chatListPanel, "list");
        mainPanel.add(detail1, "detail1");
        mainPanel.add(detail2, "detail2");
        mainPanel.add(detail3, "detail3");

         //네비게이션 바 
        JPanel bottomNavBar = new JPanel(new GridLayout(1,3));

        JButton homeButton = new JButton("홈");
        JButton chatButton = new JButton("채팅");
        JButton notificationButton = new JButton("알림");
        JButton myPageButton = new JButton("마이페이지");

        bottomNavBar.add(homeButton);
        bottomNavBar.add(chatButton);
        bottomNavBar.add(notificationButton);
        bottomNavBar.add(myPageButton);

        //상단 박스
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(230,230,230));
        headerPanel.setPreferredSize(new Dimension(0,50));

        JLabel headerLabel =new JLabel("채팅", SwingConstants.CENTER);
        headerLabel.setFont(new Font("SanSerif", Font.BOLD,18));

        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        add(bottomNavBar,BorderLayout.PAGE_END);

        add(mainPanel);
        setVisible(true);

    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
