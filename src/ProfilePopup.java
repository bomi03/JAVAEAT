// ProfilePopup.java - 프로필 팝업 카드


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;
import model.Profile;
import model.SongiType;
import java.net.URL;
import java.net.URISyntaxException;
import java.io.File;

public class ProfilePopup extends JDialog {
    private static final String CARD_SUMMARY = "SUMMARY";
    private static final String CARD_DETAILS = "DETAILS";

    public ProfilePopup(Window owner, Profile profile) {
        super(owner, "프로필", ModalityType.APPLICATION_MODAL);
        setUndecorated(true);
        setSize(306, 648);
        setLocationRelativeTo(owner);

        // 배경색 설정
        getContentPane().setBackground(Color.decode("#F3F3F3"));

        CardLayout cards = new CardLayout();
        JPanel cardPanel = new JPanel(cards);
        cardPanel.setBackground(Color.decode("#F3F3F3"));
        cardPanel.add(buildSummaryCard(profile, cards, cardPanel), CARD_SUMMARY);
        cardPanel.add(buildDetailsCard(profile, cards, cardPanel), CARD_DETAILS);

        add(cardPanel);
        setVisible(true);
    }

    // ────────────────────────────────────────
    // 요약 화면: 사진 / 유형명+이미지 / 닉네임·학번·소개
    private JPanel buildSummaryCard(Profile p, CardLayout cards, JPanel parent) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#F3F3F3"));

        // 상단 닫기
        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        top.setOpaque(false);
        JButton close = makeCloseButton();
        close.addActionListener(e -> dispose());
        top.add(close);
        panel.add(top, BorderLayout.NORTH);

        // 중앙 콘텐츠
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(new EmptyBorder(10, 10, 10, 10));

        center.add(Box.createVerticalStrut(10));
        // 프로필 사진
        String imgPath = p.getProfileImagePath();
        ImageIcon pic = (imgPath != null && !imgPath.isEmpty())
            ? new ImageIcon(new ImageIcon(imgPath).getImage()
                    .getScaledInstance(100, 100, Image.SCALE_SMOOTH))
            : new ImageIcon(new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB));
        JLabel picLabel = new JLabel(pic);
        picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(picLabel);

        // 협업 유형
        center.add(Box.createVerticalStrut(10));
        if (p.getResultType() != null) {
            JLabel typeName = new JLabel(p.getResultType().name(), SwingConstants.CENTER);
            typeName.setFont(typeName.getFont().deriveFont(Font.BOLD, 16f));
            typeName.setAlignmentX(Component.CENTER_ALIGNMENT);
            center.add(typeName);

            // (수정 후)
            String tImg = p.getResultImagePath();
            if (tImg != null && !tImg.isEmpty()) {
                center.add(Box.createVerticalStrut(10));
                // 1) 클래스패스에서 URL 얻기
                URL url = getClass().getClassLoader().getResource(tImg.replaceAll("^/+", ""));
                if (url != null) {
                    // 2) ImageIcon 생성 & 스케일링
                    ImageIcon rawIcon = new ImageIcon(url);
                    Image scaled = rawIcon.getImage()
                            .getScaledInstance(200, 300, Image.SCALE_SMOOTH);
                    ImageIcon tIcon = new ImageIcon(scaled);
                    JLabel tLabel = new JLabel(tIcon);
                    tLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    center.add(tLabel);
                } else {
                    System.err.println("리소스를 찾을 수 없습니다: " + tImg);
                }
            }
        }

        // 닉네임·학번
        center.add(Box.createVerticalStrut(10));
        String nick = p.getNickname() != null ? p.getNickname() : "";
        String year = p.getAdmissionYear() != null ? p.getAdmissionYear() : "";
        JLabel idLine = new JLabel(nick + " · " + year, SwingConstants.CENTER);
        idLine.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(idLine);

        // 한 줄 소개
        if (p.getIntroduction() != null && !p.getIntroduction().isEmpty()) {
            center.add(Box.createVerticalStrut(10));
            JTextArea intro = new JTextArea(p.getIntroduction());
            intro.setLineWrap(true);
            intro.setWrapStyleWord(true);
            intro.setEditable(false);
            intro.setOpaque(false);
            intro.setMaximumSize(new Dimension(320, 60));
            intro.setBorder(null);
            intro.setAlignmentX(Component.CENTER_ALIGNMENT);
            center.add(intro);
        }

        // 클릭 시 세부 카드로
        center.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cards.show(parent, CARD_DETAILS);
            }
        });

        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // ────────────────────────────────────────
    // 세부 화면: 학년·재학여부 / 전공 / 경력 / 자격증
    private JPanel buildDetailsCard(Profile p, CardLayout cards, JPanel parent) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#F3F3F3"));

        // 상단 닫기
        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        top.setOpaque(false);
        JButton close = makeCloseButton();
        close.addActionListener(e -> dispose());
        top.add(close);
        panel.add(top, BorderLayout.NORTH);

        // 내용 영역
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(10, 20, 20, 20));

        // 학년·재학 여부
        if (p.getGrade() != null && !p.getGrade().isEmpty()) {
            String status = p.getisEnrolled() ? "재학" : "휴학";
            content.add(new JLabel(p.getGrade() + " · " + status));
            content.add(Box.createVerticalStrut(15));
        }

        // 전공
        List<String> majors = p.getMajors();
        if (!majors.isEmpty()) {
            content.add(new JLabel("전공"));
            for (String m : majors) {
                content.add(new JLabel("  • " + m));
            }
            content.add(Box.createVerticalStrut(15));
        }

        // 경력사항
        List<String> careers = p.getCareers();
        if (!careers.isEmpty()) {
            content.add(new JLabel("경력사항"));
            for (String c : careers) {
                content.add(new JLabel("  • " + c));
            }
            content.add(Box.createVerticalStrut(15));
        }

        // 자격증
        List<String> certs = p.getCertificates();
        if (!certs.isEmpty()) {
            content.add(new JLabel("자격증"));
            for (String c : certs) {
                content.add(new JLabel("  • " + c));
            }
        }

        // 클릭 시 요약 카드로
        content.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                cards.show(parent, CARD_SUMMARY);
            }
        });

        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    // 닫기 버튼 스타일 메서드
    private JButton makeCloseButton() {
        JButton b = new JButton("✕");
        b.setBorder(null);
        b.setContentAreaFilled(false);
        b.setFont(b.getFont().deriveFont(16f));
        return b;
    }

    // ────────────────────────────────────────
    // standalone 테스트용 main (IDE에서 이 파일만 실행할 때)
    public static void main(String[] args) {
        // 더미 프로필 세팅
        Profile p = new Profile(1, "tester");
        p.setProfileImagePath("");  // 실제 이미지 경로 넣으세요
        p.updateType(SongiType.평화송이, "");
        p.setNickname("테스트닉네임");
        p.setAdmissionYear("22학번");
        p.setIntroduction("팝업 테스트용 자기소개입니다.");
        p.addMajor("경영학부", "주전공");
        p.addCareer("최우수상", "수상");
        p.addCertification("정보처리기사");

        SwingUtilities.invokeLater(() -> {
            new ProfilePopup(null, p);
        });
    }
}
