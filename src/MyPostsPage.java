// MyPostsPage.java - 마이페이지-작성한 글 페이지

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;
import model.Post;
import model.User;
import model.Management;
import model.Profile;


public class MyPostsPage extends JFrame {
    private User user;
    private Management manager;
    private int profileID;
    private JPanel listPanel;
    private JScrollPane scrollPane;

    public MyPostsPage(User user, Management manager, int profileID) {
        this.user = user;
        this.manager = manager;
        this.profileID = profileID;

        setTitle("작성한 글");
        setSize(393, 852);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        buildHeader();
        buildList();

        setVisible(true);
    }

    /** 상단 바(뒤로가기 + 제목) 구성 */
    private void buildHeader() {
        JPanel header = new JPanel(null);
        header.setPreferredSize(new Dimension(393, 60));
        header.setBackground(Color.WHITE);

        JButton backBtn = new JButton("<html>&lt;-</html>");
        backBtn.setBounds(10, 15, 40, 30);
        styleGrayButton(backBtn);
        header.add(backBtn);

        // 뒤로가기 눌렀을 때 MyPage로 돌아가기
        backBtn.addActionListener(e -> {
            dispose();
            new MyPage(user, manager);
        });

        JLabel title = new JLabel("작성한 글", SwingConstants.CENTER);
        title.setForeground(Color.decode("#4A4A4A"));
        title.setBounds(0, 15, 393, 30);
        header.add(title);

        JSeparator sep = new JSeparator();
        sep.setBounds(0, 59, 393, 1);
        sep.setForeground(Color.decode("#8A8888"));
        header.add(sep);

        add(header, BorderLayout.NORTH);
    }

    /** 본문 리스트 구성 */
    private void buildList() {
        // 모집 기간 지난 포스트 상태 자동 갱신
        Post.getAllPosts().forEach(Post::autoClosePost);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        List<Post> myPosts = Post.getAllPosts().stream()
            .filter(p -> p.getProfileID() == profileID)
            .collect(Collectors.toList());

        if (myPosts.isEmpty()) {
            JLabel empty = new JLabel("작성한 글이 없습니다.", SwingConstants.CENTER);
            empty.setFont(empty.getFont().deriveFont(Font.PLAIN, 14f));
            empty.setForeground(Color.GRAY);
            empty.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            listPanel.add(empty);
        } else {
            for (Post p : myPosts) {
                listPanel.add(createRow(p));
                listPanel.add(createSeparator());
            }
        }

        scrollPane = new JScrollPane(listPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    /** 한 줄 아이템 */
    private JPanel createRow(Post p) {
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

        boolean open = "모집중".equals(p.getStatus());
        JLabel stat = new JLabel(
            String.format("%s %d/%d명",
                open ? "모집중" : "모집완료",
                p.getCurrentApplicants(),
                p.getMaxApplicants()
            )
        );
        stat.setFont(stat.getFont().deriveFont(Font.PLAIN, 12f));
        stat.setForeground(open ? Color.decode("#FF6200") : Color.GRAY);
        stat.setBounds(275 - 90 - 10, 26, 90, 18);

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
                // 현재 MyPostsPage 닫고
                dispose();
                // PostDetailPage 열기 (PostDetailPage 내부에서 isWriter 판별)
                new PostDetailPage(user, manager, p);
            }
        });

        row.add(cat);
        row.add(title);
        row.add(dl);
        row.add(stat);
        row.add(img);
        return row;
    }

    /** 항목 사이의 구분선 생성 */
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
            // 테스트용 더미 객체
            Management mgr = new Management(new java.util.HashMap<>());
            User u = new User("테스트유저", "test", "pw", "test@example.com");
            u.setProfile(new Profile(1, "test"));
            new MyPostsPage(u, mgr, /*profileID=*/1);
        });
    }
}
