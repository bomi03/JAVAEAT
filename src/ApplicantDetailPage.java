import javax.swing.*;
import java.awt.*;
import model.Application;

public class ApplicantDetailPage extends JFrame {
    private Application application;

    public ApplicantDetailPage(Application application) {
        super("지원자 상세 정보");
        this.application = application;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(393, 853);
        setLocationRelativeTo(null);

        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 상단 바
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        topBar.setBackground(Color.WHITE);

        JButton backButton = new JButton("←");
        backButton.setPreferredSize(new Dimension(50, 30));
        backButton.setFocusPainted(false);
        //backButton.addActionListener(e -> dispose());
        backButton.addActionListener(e -> {
            dispose();
            new PostDetailPage(null, null, null);
        });

        JLabel titleLabel = new JLabel("지원자 확인하기", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));

        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        contentPanel.add(topBar);

        // 프로필 패널
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("새송이버섯");
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 16f));
        JLabel idLabel = new JLabel("22학번");
        idLabel.setFont(idLabel.getFont().deriveFont(Font.PLAIN, 12f));

        textPanel.add(nameLabel);
        textPanel.add(idLabel);

        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(60, 60));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(Color.LIGHT_GRAY);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setText("IMG");

        profilePanel.add(textPanel, BorderLayout.WEST);
        profilePanel.add(imageLabel, BorderLayout.EAST);

        contentPanel.add(profilePanel);

        // 자기소개 영역
        JLabel introLabel = new JLabel("자기소개", SwingConstants.LEFT);
        introLabel.setFont(introLabel.getFont().deriveFont(Font.BOLD, 14f));
        

        JTextArea introArea = new JTextArea(application.getMessage());
        introArea.setLineWrap(true);
        introArea.setWrapStyleWord(true);
        introArea.setEditable(false);
        introArea.setBackground(new Color(245, 245, 245));
        introArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(introArea);
        scrollPane.setPreferredSize(new Dimension(360, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        contentPanel.add(introLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(scrollPane);
        contentPanel.add(Box.createVerticalStrut(20));

        // 하단 버튼
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        buttonPanel.setBackground(Color.WHITE);

        JButton rejectButton = new JButton("거절");
        rejectButton.setBackground(Color.white);
        rejectButton.setForeground(Color.gray);
        rejectButton.setFocusPainted(false);
        rejectButton.addActionListener(e -> {
            application.reject();
            JOptionPane.showMessageDialog(this, "지원자가 거절되었습니다.");
            dispose();
        });

        JButton acceptButton = new JButton("수락");
        acceptButton.setBackground(Color.white);
        acceptButton.setForeground(Color.gray);
        acceptButton.setFocusPainted(false);
        acceptButton.addActionListener(e -> {
            application.accept();
            JOptionPane.showMessageDialog(this, "지원자가 수락되었습니다.");
            dispose();
        });

        buttonPanel.add(rejectButton);
        buttonPanel.add(acceptButton);

        contentPanel.add(buttonPanel);

        add(contentPanel);
    }

    // 테스트용 main
    public static void main(String[] args) {
        Application dummy = new Application(1, 1, 1001, "안녕하세요. 팀원으로 함께 하고 싶어요!");
        new ApplicantDetailPage(dummy);
    }
}

