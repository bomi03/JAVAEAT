import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;
import model.Post;

public class MyPostsPage extends JFrame {
    private int profileID;
    private JPanel listPanel;
    private JScrollPane scrollPane;

    public MyPostsPage(int profileID) {
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

        JLabel title = new JLabel("작성한 글", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
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

        Post.getAllPosts().forEach(Post::autoClosePost);  // 모집 기간 지나면 호출 시점에 자동으로 상태 변경되도록

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
            empty.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
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
        // 전체 폭을 다 쓰도록 최대 너비를 무제한으로 설정
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, rowSize.height));
        row.setBackground(Color.WHITE);

        // 카테고리
        JLabel cat = new JLabel(p.getCategory());
        cat.setFont(cat.getFont().deriveFont(Font.PLAIN, 12f));
        cat.setForeground(Color.decode("#8A8A8A"));
        cat.setBounds(10, 5, 70, 20);

        // 제목
        JLabel title = new JLabel(p.getTitle());
        title.setFont(title.getFont().deriveFont(Font.PLAIN, 14f));
        title.setForeground(Color.BLACK);
        title.setBounds(90, 5, 180, 20);

        // 모집기간
        String period = (p.getRecruitDeadline() == null)
            ? "모집기간 없음"
            : String.format("모집기간 ~%1$tY/%1$tm/%1$td까지", p.getRecruitDeadline());
        JLabel dl = new JLabel(period);
        dl.setFont(dl.getFont().deriveFont(Font.PLAIN, 12f));
        dl.setForeground(Color.BLACK);
        dl.setBounds(10, 25, 250, 18);

        // 상태/인원 (오른쪽으로 배치)
        boolean open = "모집중".equals(p.getStatus());
        JLabel stat = new JLabel(
            String.format("%s %d/%d명",
                open ? "모집중" : "모집완료",
                p.getCurrentApplicants(),
                p.getMaxApplicants()
            )
        );
        stat.setFont(stat.getFont().deriveFont(Font.PLAIN, 12f));
        stat.setForeground(open
            ? Color.decode("#FF6200")
            : Color.GRAY
        );
        stat.setBounds(275 - 90 - 10, 26, 90, 18);

        // 우측 이미지 자리
        JLabel img = new JLabel();
        img.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        img.setBounds(275, 5, 90, 60);

        row.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO: 상세 페이지 연결
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
        Dimension sepSize = new Dimension(393, 1);
        sep.setPreferredSize(sepSize);
        // 구분선도 전체 폭을 쓰도록
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, sepSize.height));
        sep.setForeground(Color.decode("#E0E0E0"));
        return sep;
    }

    private void styleGrayButton(AbstractButton b) {
        b.setBackground(Color.decode("#D9D9D9"));
        b.setForeground(Color.decode("#4A4A4A"));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }

    // 테스트용 main
    public static void main(String[] args) {
        Post.addPost(new Post(1, 1, "", "공모전", "OOOOOO 공모전 팀원 모집", "모집중", null, 5, 0, ""));
        Post.addPost(new Post(2, 1, "", "스터디", "OOOO 스터디 팀원 모집", "모집완료", null, 5, 5, ""));
        Post.addPost(new Post(3, 1, "", "수업 팀플", "OOO 수업 팀플 조원 모집", "모집중", null, 4, 2, ""));

        SwingUtilities.invokeLater(() -> new MyPostsPage(1));
    }
}