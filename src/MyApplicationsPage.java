import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;
import model.*;

public class MyApplicationsPage extends JFrame {
    private User user;
    private Management manager;
    private JPanel listPanel;
    private JScrollPane scrollPane;

    public MyApplicationsPage(User user, Management manager) {
        this.user = user;
        this.manager = manager;

        setTitle("지원 현황");
        setSize(393, 852);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        buildHeader();
        buildList();

        setVisible(true);
    }

    private void buildHeader() {
        JPanel header = new JPanel(null);
        header.setPreferredSize(new Dimension(393, 60));
        header.setBackground(Color.WHITE);

        JButton backBtn = new JButton("<html>&lt;-</html>");
        backBtn.setBounds(10, 15, 40, 30);
        styleGrayButton(backBtn);
        header.add(backBtn);

        backBtn.addActionListener(e -> {
            dispose();
            new MyPage(user, manager);
        });

        JLabel title = new JLabel("지원 현황", SwingConstants.CENTER);
        title.setForeground(Color.decode("#4A4A4A"));
        title.setBounds(0, 15, 393, 30);
        header.add(title);

        JSeparator sep = new JSeparator();
        sep.setBounds(0, 59, 393, 1);
        sep.setForeground(Color.decode("#8A8888"));
        header.add(sep);

        add(header, BorderLayout.NORTH);
    }

    private void buildList() {
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        List<Application> myApps = user.getMyApplications();

        if (myApps.isEmpty()) {
            JLabel empty = new JLabel("지원한 글이 없습니다.", SwingConstants.CENTER);
            empty.setFont(empty.getFont().deriveFont(Font.PLAIN, 14f));
            empty.setForeground(Color.GRAY);
            empty.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            listPanel.add(empty);
        } else {
            for (Application app : myApps) {
                Post p = manager.getPostByID(app.getPostID());
                if (p != null) {
                    listPanel.add(createRow(p, app.getStatus()));
                    listPanel.add(createSeparator());
                }
            }
        }

        scrollPane = new JScrollPane(listPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createRow(Post p, String appStatus) {
        JPanel row = new JPanel(null);
        Dimension rowSize = new Dimension(393, 70);
        row.setPreferredSize(rowSize);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, rowSize.height));
        row.setBackground(Color.WHITE);

        JLabel cat = new JLabel(p.getCategory());
        cat.setFont(cat.getFont().deriveFont(Font.PLAIN, 12f));
        cat.setForeground(Color.decode("#8A8A8A"));
        cat.setBounds(10, 5, 70, 20);

        JLabel title = new JLabel(p.getTitle());
        title.setFont(title.getFont().deriveFont(Font.PLAIN, 14f));
        title.setForeground(Color.BLACK);
        title.setBounds(90, 5, 180, 20);

        String period = (p.getRecruitDeadline() == null)
            ? "모집기간 없음"
            : String.format("모집기간 ~%1$tY/%1$tm/%1$td까지", p.getRecruitDeadline());
        JLabel dl = new JLabel(period);
        dl.setFont(dl.getFont().deriveFont(Font.PLAIN, 12f));
        dl.setForeground(Color.BLACK);
        dl.setBounds(10, 25, 250, 18);

        JLabel statusLabel = new JLabel(appStatus);
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.PLAIN, 12f));
        statusLabel.setForeground(switch (appStatus) {
            case "수락" -> new Color(0x007BFF);  // 파란색
            case "거절" -> Color.GRAY;
            default -> new Color(0xFF6200);     // 주황색 (대기중)
        });
        statusLabel.setBounds(10, 45, 100, 18);

        JLabel stat = new JLabel(String.format("%s %d/%d명", 
            "모집중".equals(p.getStatus()) ? "모집중" : "모집완료",
            p.getCurrentApplicants(), p.getMaxApplicants()));
        stat.setFont(stat.getFont().deriveFont(Font.PLAIN, 12f));
        stat.setForeground("모집중".equals(p.getStatus()) ? Color.decode("#FF6200") : Color.GRAY);
        stat.setBounds(200, 45, 90, 18);

        JLabel img = new JLabel();
        img.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        img.setBounds(275, 5, 90, 60);
        String path = p.getPostImagePath();
        if (path != null && !path.isEmpty()) {
            ImageIcon raw = new ImageIcon(path);
            Image scaled = raw.getImage().getScaledInstance(90, 60, Image.SCALE_SMOOTH);
            img.setIcon(new ImageIcon(scaled));
        }

        row.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new PostDetailPage(user, manager, p);
            }
        });

        row.add(cat);
        row.add(title);
        row.add(dl);
        row.add(statusLabel); // 지원 상태
        row.add(stat);        // 모집 상태
        row.add(img);
        return row;
    }

    private JSeparator createSeparator() {
        JSeparator sep = new JSeparator();
        sep.setPreferredSize(new Dimension(393, 1));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(Color.decode("#E0E0E0"));
        return sep;
    }

    private void styleGrayButton(AbstractButton b) {
        b.setBackground(Color.decode("#D9D9D9"));
        b.setForeground(Color.decode("#4A4A4A"));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Management mgr = new Management();
            User u = new User("테스트유저", "test", "pw", "test@example.com");
            Profile p = new Profile(1, "test");
            u.setProfile(p);
            mgr.addUser(u);

            Post post = new Post(1, p.getProfileID(), "", "스터디", "스터디 모집", "모집중", new java.util.Date(), 4, 2, "설명");
            Post.addPost(post);

            Application app = new Application(1, post.getPostID(), p.getProfileID(), "열심히 하겠습니다!");
            u.apply(app);

            new MyApplicationsPage(u, mgr);
        });
    }
}
