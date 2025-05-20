import javax.swing.*;
import java.awt.*;
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

        super.setTitle(isWriter ? "모집글 상세 (팀장)" : "모집글 상세");
        // super("모집글 상세 (팀장)");
        this.user = user;
        this.manager = manager;
        this.post = post;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(393, 800);
        setLocationRelativeTo(null);

        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        // contentPanel 세팅
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(Color.WHITE);

        // 상단 바
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        topBar.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

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
        contentPanel.add(topBar);

        // 작성자 여부 판단
        boolean isWriter = post != null && user.getProfile() != null && post.getProfileID() == user.getProfile().getProfileID();

        // 작성자일 경우 수정 버튼 추가
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

        // 구분선
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(Color.LIGHT_GRAY);
        contentPanel.add(sep);

        // 모집글 정보
        JPanel postInfo = new JPanel();
        postInfo.setLayout(new BoxLayout(postInfo, BoxLayout.Y_AXIS));
        postInfo.setBackground(Color.WHITE);
        postInfo.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        JLabel postTitle = new JLabel("[공모전] 0000공모전 팀원 모집");
        postTitle.setFont(postTitle.getFont().deriveFont(Font.BOLD, 14f));
        postTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        postInfo.add(postTitle);

        JLabel postPeriod = new JLabel("모집기간 ~ 2025.06.15");
        postPeriod.setFont(postPeriod.getFont().deriveFont(Font.PLAIN, 12f));
        postPeriod.setForeground(Color.DARK_GRAY);
        postPeriod.setAlignmentX(Component.LEFT_ALIGNMENT);
        postInfo.add(postPeriod);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        statusPanel.setBackground(Color.WHITE);
        statusPanel.add(new JLabel("모집중"));
        statusPanel.add(new JLabel("0/5명"));
        postInfo.add(statusPanel);

        contentPanel.add(postInfo);

        // 모집 설명
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBackground(Color.WHITE);
        descPanel.setBorder(BorderFactory.createTitledBorder("모집 내용"));
        JTextArea descArea = new JTextArea("<실전 웹 서비스 개발 프로젝트 팀원 모집>\n\n"
            + "안녕하세요. 실사용자를 위한 웹 서비스 개발 프로젝트를 함께할 팀원을 모집합니다. 본 프로젝트는 서비스 기획부터 개발까지 전 과정을 함께하며 포트폴리오를 구축하고 실무 중심의 협업 경험을 쌓는 것을 목표로 하고 있습니다.\n\n"
            + "[모집 대상]\n"
            + "웹 또는 앱 서비스 개발에 관심 있고, 실전 프로젝트 경험을 쌓고 싶은 분\n"
            + "개발, 디자인, 기획 등 다양한 역할에 열정 있는 분이면 환영합니다!\n\n"
            + "[역할]\n"
            + "- 프론트엔드 개발자: 2명 (React, Vue 등 가능하신 분)\n"
            + "- 백엔드 개발자: 1명 (Node.js, Spring, Firebase 등 가능자)\n"
            + "- 디자이너: 1명 (UI/UX 가능자)\n\n"
            + "[현재 구성원]\n"
            + "- 기획자 1명 (전체 일정 및 기능 설계 담당)\n"
            + "- 백엔드 개발자 1명\n\n"
            + "[진행 기간]\n"
            + "2025.04.05 ~ 2025.06.15 (약 10주간 진행 예정)\n"
            + "5월 중순까지 1차 개발을 완료한 후, 사용자 피드백을 반영하여 기능을 개선하고 배포를 진행할 예정입니다.\n\n"
            + "[진행 방법/일정]\n"
            + "- 주 1회 온라인 회의 (일요일 저녁 9시 예정)\n"
            + "- Notion으로 업무 정리, GitHub으로 협업\n"
            + "- 기능별 마감 시점마다 간단한 데모 공유\n"
            + "- 간헐적인 오프라인 회의 가능 (서울/경기권)\n\n"
            + "[모임 방식 (온라인/오프라인)]\n"
            + "- 기본은 온라인 중심 (Zoom, Google Meet 사용)\n"
            + "- 상황에 따라 오프라인 모임 진행 가능\n\n"
            + "[우대사항]\n"
            + "- Git 협업 경험 있으신 분\n"
            + "- 간단한 배포 경험 있는 분 (Netlify, Vercel, Firebase 등)\n"
            + "- UI/UX에 관심 있거나 디자인툴(Figma 등) 사용 가능하신 분\n"
            + "- 책임감 있게 일정 조율할 수 있는 분\n\n"
            + "----------\n\n"
            + "질문은 언제든지 주세요!\n"
            + "참여를 원하시면 앱 내 ‘팀 참여하기’ 기능을 통해 지원해주시면 됩니다 :)\n"
            + "서로 배워가며 즐겁게 프로젝트 해봐요!");
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setBackground(Color.WHITE);
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBorder(null);
        descPanel.add(descScroll, BorderLayout.CENTER);
        contentPanel.add(descPanel);

        // 작성자일 경우 지원자 목록
        if (isWriter) {
            JPanel applPanel = new JPanel();
            applPanel.setLayout(new BoxLayout(applPanel, BoxLayout.Y_AXIS));
            applPanel.setBackground(Color.WHITE);
            applPanel.setBorder(BorderFactory.createTitledBorder("지원자 목록"));
            String[] names = {"새송이버섯","눈송이","김숙명"};
            for (String nm : names) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

                JLabel lbl = new JLabel(nm);
                lbl.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                card.add(lbl, BorderLayout.WEST);

                JButton detail = new JButton(">");
                detail.setFocusPainted(false);
                detail.addActionListener(e -> {
                    // TODO: ApplicantDetailPage 로 전환
                });
                JPanel btnP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                btnP.setBackground(Color.WHITE);
                btnP.add(detail);
                card.add(btnP, BorderLayout.EAST);

                contentPanel.add(card);
                contentPanel.add(Box.createVerticalStrut(10));
            }
        } else {
            // 작성자가 아닐 경우 지원하기 버튼
            JButton applyButton = new JButton("지원하기");
            applyButton.setPreferredSize(new Dimension(100, 40));
            applyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            applyButton.addActionListener(e -> {
                // TODO: 지원 처리
                 // 현재 창 닫기
                dispose();
                // 새로운 지원 폼 창 열기
                new TeamApplicationForm();
            });

            JPanel applyPanel = new JPanel();
            applyPanel.setBackground(Color.WHITE);
            applyPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            applyPanel.add(applyButton);

            contentPanel.add(Box.createVerticalStrut(20));
            contentPanel.add(applyPanel);
        }

        // 전체를 스크롤에
        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    // 테스트용 main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Management mgr = new Management(new java.util.HashMap<>());
            User u = new User("홍길동", "hg123", "pw", "hg@sookmyung.ac.kr");

    

            new PostDetailPage(u, mgr, null);
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