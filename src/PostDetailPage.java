import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import model.User;
import model.Management;
import model.Post;
import model.Profile;

public class PostDetailPage extends JFrame {
    private User user;
    private Management manager;
    private Post post;

    public PostDetailPage(User user, Management manager, Post post) {
        boolean isWriter = post != null
            && user.getProfile() != null
            && post.getProfileID() == user.getProfile().getProfileID();

        setTitle(isWriter ? "모집글 상세 (팀장)" : "모집글 상세");
        this.user = user;
        this.manager = manager;
        this.post = post;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(393, 800);
        setLocationRelativeTo(null);

        buildUI(isWriter);
        setVisible(true);
    }

    private void buildUI(boolean isWriter) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(Color.WHITE);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        topBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton backButton = new JButton("←");
        backButton.setPreferredSize(new Dimension(50, 30));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            dispose();
            new TeamListPage(user, manager);
        });

        JLabel titleLabel = new JLabel("팀원 모집", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));
        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);

        if (isWriter) {
            JButton editButton = new JButton("수정");
            editButton.setPreferredSize(new Dimension(60, 30));
            editButton.setFocusPainted(false);
            editButton.setFont(editButton.getFont().deriveFont(Font.PLAIN, 12f));
            editButton.addActionListener(e -> {
                dispose();
                new TeamBuildPage(user, manager, post);
            });
            topBar.add(editButton, BorderLayout.EAST);
        }

        contentPanel.add(topBar);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(Color.LIGHT_GRAY);
        contentPanel.add(sep);

        JPanel postInfo = new JPanel();
        postInfo.setLayout(new BoxLayout(postInfo, BoxLayout.Y_AXIS));
        postInfo.setBackground(Color.WHITE);
        postInfo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // 카테고리 + 제목 한 줄 표현
        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titleRow.setBackground(Color.WHITE);
        titleRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel categoryLabel = new JLabel("[" + post.getCategory() + "] ");
        categoryLabel.setFont(categoryLabel.getFont().deriveFont(Font.BOLD, 16f));
        categoryLabel.setForeground(Color.GRAY);

        JLabel titleLabelText = new JLabel(post.getTitle());
        titleLabelText.setFont(titleLabelText.getFont().deriveFont(Font.PLAIN, 14f));

        titleRow.add(categoryLabel);
        titleRow.add(titleLabelText);
        postInfo.add(titleRow);

        String deadline = post.getRecruitDeadline() != null
            ? new SimpleDateFormat("yyyy.MM.dd").format(post.getRecruitDeadline())
            : "미정";

        JLabel postPeriod = new JLabel("모집기간 ~ " + deadline);
        postPeriod.setFont(postPeriod.getFont().deriveFont(Font.PLAIN, 12f));
        postPeriod.setForeground(Color.DARK_GRAY);
        postPeriod.setAlignmentX(Component.LEFT_ALIGNMENT);
        postInfo.add(postPeriod);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        statusPanel.setBackground(Color.WHITE);

        JLabel statusLabel = new JLabel(post.getStatus());
        statusLabel.setForeground(new Color(0xFF, 0xA5, 0x00)); // 주황색
        statusPanel.add(statusLabel);

        statusPanel.add(new JLabel(post.getCurrentApplicants() + "/" + post.getMaxApplicants() + "명"));
        postInfo.add(statusPanel);

        contentPanel.add(postInfo);

        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBackground(Color.WHITE);
        descPanel.setBorder(BorderFactory.createTitledBorder("모집 내용"));

        JTextArea descArea = new JTextArea(post.getDescription());
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setBackground(Color.WHITE);

        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBorder(null);
        descPanel.add(descScroll, BorderLayout.CENTER);
        contentPanel.add(descPanel);

        if (isWriter) {
            JPanel applPanel = new JPanel();
            applPanel.setLayout(new BoxLayout(applPanel, BoxLayout.Y_AXIS));
            applPanel.setBackground(Color.WHITE);
            applPanel.setBorder(BorderFactory.createTitledBorder("지원자 목록"));

            String[] names = {"새송이버섯", "눈송이", "김숙명"};
            for (String nm : names) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                JLabel lbl = new JLabel(nm);
                lbl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                card.add(lbl, BorderLayout.WEST);

                JButton detail = new JButton(">");
                detail.setFocusPainted(false);
                detail.addActionListener(e -> {
                    // TODO: ApplicantDetailPage로 이동
                });

                JPanel btnP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                btnP.setBackground(Color.WHITE);
                btnP.add(detail);
                card.add(btnP, BorderLayout.EAST);

                contentPanel.add(card);
                contentPanel.add(Box.createVerticalStrut(10));
            }
        } else {
            JButton applyButton = new JButton("지원하기");
            applyButton.setPreferredSize(new Dimension(100, 40));
            applyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            applyButton.addActionListener(e -> {
                dispose();
                new TeamApplicationForm(); // TODO: user, manager, post 전달 필요
            });

            JPanel applyPanel = new JPanel();
            applyPanel.setBackground(Color.WHITE);
            applyPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            applyPanel.add(applyButton);

            contentPanel.add(Box.createVerticalStrut(20));
            contentPanel.add(applyPanel);
        }

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Management mgr = new Management(new java.util.HashMap<>());
            User u = new User("홍길동", "hg123", "pw", "hg@sookmyung.ac.kr");
            Profile p = new Profile(1, u.getUserID());
            u.setProfile(p);

            Post testPost = new Post(
                1, p.getProfileID(), "", "공모전", "AI 해커톤 팀원 모집", "모집중",
                new java.util.Date(), 5, 2, "프로젝트 설명 예시입니다."
            );

            new PostDetailPage(u, mgr, testPost);
        });
    }





    // // 수정 버튼 테스트 main (보미 추가)
    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         // 1) Management 인스턴스 생성
    //         Management mgr = new Management(new java.util.HashMap<>());

    //         // 2) User 생성 (두 번째 인자가 userID)
    //         User u = new User("홍길동", "hg123", "pw", "hg@sookmyung.ac.kr");

    //         // 3) Profile 생성 (profileID=1, userID는 위의 u.getUserID())
    //         Profile prof = new Profile(1, u.getUserID());
    //         u.setProfile(prof);

    //     // 4) Post 생성 (profileID도 1로 맞춰서 isWriter 조건 만족)
    //     Post testPost = new Post(
    //         1,                     // postID
    //         prof.getProfileID(),   // profileID (1)
    //         "",                    // imgPath
    //         "공모전",               // category
    //         "0000공모전 팀원 모집",  // title
    //         "모집중",               // status
    //         null,                  // deadline
    //         5,                     // maxApplicants
    //         0,                     // currentApplicants
    //         "테스트용 설명"          // description
    //     );

    //     // 5) 상세 페이지 열기 → 상단에 “수정” 버튼이 표시됩니다.
    //     new PostDetailPage(u, mgr, testPost);
    //     });
    // }
}