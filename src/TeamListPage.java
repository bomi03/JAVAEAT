// TeamListPage.java - 팀 목록 페이지

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import model.Post;

public class TeamListPage extends JFrame {
    private JTextField searchField;
    private JButton searchBtn;
    private JPanel categoryPanel;
    private JScrollPane categoryScroll;
    private JPanel listPanel;
    private JScrollPane listScroll;
    private JButton createBtn;

    private static final String[] CATEGORIES = {
        "전체", "공모전", "스터디", "수업 팀플", "교내 대회", "프로젝트", "기타"
    };

    public TeamListPage() {
        setTitle("팀 목록");
        setSize(393, 852);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        buildSearchBar();
        buildCategoryBar();
        buildListPanel();
        buildCreateButton();

        setVisible(true);
    }

    private void buildSearchBar() {
        searchField = new JTextField();
        searchField.setBounds(10, 10, 300, 36);
        searchField.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        add(searchField);

        searchBtn = new JButton("검색");
        searchBtn.setBounds(318, 10, 60, 36);
        styleGrayButton(searchBtn);
        add(searchBtn);

        ActionListener doSearch = e -> refreshList();
        searchBtn.addActionListener(doSearch);
        searchField.addActionListener(doSearch);
    }

    private void buildCategoryBar() {
        categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.X_AXIS));
        categoryPanel.setBackground(Color.WHITE);
        // 왼쪽 여백
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        ButtonGroup group = new ButtonGroup();
        for (String cat : CATEGORIES) {
            JToggleButton btn = new JToggleButton(cat);
            styleToggleCategory(btn);
            btn.addActionListener(e -> refreshList());
            group.add(btn);
            categoryPanel.add(btn);
            categoryPanel.add(Box.createRigidArea(new Dimension(8, 0)));
            if (cat.equals("전체")) {
                btn.setSelected(true);
                // 스타일 즉시 갱신
                btn.getModel().setPressed(true);
                btn.getModel().setPressed(false);
            }
        }

        categoryScroll = new JScrollPane(
            categoryPanel,
            JScrollPane.VERTICAL_SCROLLBAR_NEVER,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER // 가로 스크롤 제거
        );
        categoryScroll.setBounds(0, 56, 393, 40);
        categoryScroll.setBorder(null);
        add(categoryScroll);
    }

    private void buildListPanel() {
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        listScroll = new JScrollPane(listPanel);
        listScroll.setBounds(0, 100, 393, 660);
        listScroll.getVerticalScrollBar().setUnitIncrement(16);
        listScroll.setBorder(null);
        add(listScroll);

        refreshList();
    }

    private void buildCreateButton() {
        createBtn = new JButton("<html>&#43; 팀 생성</html>");
        styleBlueButton(createBtn);
        createBtn.setBounds(250, 770, 120, 40);
        add(createBtn);
        createBtn.addActionListener(e -> {
            new TeamBuildPage();
            dispose();
            });
    }

    private void refreshList() {
        listPanel.removeAll();

        List<Post> all = Post.getAllPosts();

        boolean isSearching = !searchField.getText().trim().isEmpty();
        String keyword = searchField.getText().trim().toLowerCase();
        String selectedCat = Arrays.stream(categoryPanel.getComponents())
            .filter(c -> c instanceof JToggleButton && ((JToggleButton)c).isSelected())
            .map(c -> ((JToggleButton)c).getText())
            .findFirst().orElse("전체");

        List<Post> filtered = all.stream()
            .filter(p -> p.getTitle().toLowerCase().contains(keyword))
            .filter(p -> selectedCat.equals("전체") || p.getCategory().equals(selectedCat))
            .collect(Collectors.toList());

        if (!isSearching && all.isEmpty()) {
            addEmptyLabel("모집글이 없습니다.");
        } else if (isSearching && filtered.isEmpty()) {
            addEmptyLabel("검색 결과가 없습니다.");
        } else if (!isSearching && filtered.isEmpty()) {
            addEmptyLabel("해당 카테고리에 모집글이 없습니다.");
        } else {
            for (Post p : filtered) {
                listPanel.add(createRow(p));
                listPanel.add(createSeparator());
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private void addEmptyLabel(String text) {
        JLabel empty = new JLabel(text, SwingConstants.CENTER);
        empty.setFont(empty.getFont().deriveFont(14f));
        empty.setForeground(Color.GRAY);
        empty.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        listPanel.add(empty);
    }

    private JPanel createRow(Post p) {
        JPanel row = new JPanel(null);
        row.setPreferredSize(new Dimension(393, 80));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        row.setBackground(Color.WHITE);

        JLabel cat = new JLabel(p.getCategory());
        cat.setFont(cat.getFont().deriveFont(12f));
        cat.setForeground(Color.decode("#8A8A8A"));
        cat.setBounds(10, 5, 70, 20);

        JLabel title = new JLabel(p.getTitle());
        title.setFont(title.getFont().deriveFont(14f));
        title.setBounds(90, 5, 200, 20);

        String period = p.getRecruitDeadline() == null
            ? "모집기간 없음"
            : String.format("모집기간 ~%1$tY/%1$tm/%1$td까지", p.getRecruitDeadline());
        JLabel dl = new JLabel(period);
        dl.setFont(dl.getFont().deriveFont(12f));
        dl.setBounds(10, 25, 250, 20);

        boolean open = "모집중".equals(p.getStatus());
        JLabel stat = new JLabel(
            String.format("%s %d/%d명",
                open ? "모집중" : "모집완료",
                p.getCurrentApplicants(),
                p.getMaxApplicants()
            )
        );
        stat.setFont(stat.getFont().deriveFont(12f));
        stat.setForeground(open ? Color.decode("#FF6200") : Color.GRAY);
        stat.setBounds(275 - 90 - 10, 45, 90, 20);

        JLabel thumb = new JLabel();
        thumb.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        thumb.setBounds(275, 5, 90, 70);

        String path = p.getPostImagePath();
        if (path != null && !path.isEmpty()) {
            ImageIcon raw = new ImageIcon(path);
            Image scaled = raw.getImage().getScaledInstance(90, 70, Image.SCALE_SMOOTH);
            thumb.setIcon(new ImageIcon(scaled));
        }

        row.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                // TODO: 상세 페이지 연결
            }
        });

        row.add(cat);
        row.add(title);
        row.add(dl);
        row.add(stat);
        row.add(thumb);
        return row;
    }

    private JSeparator createSeparator() {
        JSeparator sep = new JSeparator();
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

    private void styleBlueButton(AbstractButton b) {
        b.setBackground(Color.decode("#003087"));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }

    private void styleToggleCategory(JToggleButton t) {
        t.setBackground(Color.WHITE);
        t.setForeground(Color.decode("#4A4A4A"));
        t.setFocusPainted(false);
        t.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        t.setPreferredSize(new Dimension(100, 40));
        t.addItemListener(e -> {
            if (t.isSelected()) {
                t.setBackground(Color.decode("#003087"));
                t.setForeground(Color.WHITE);
            } else {
                t.setBackground(Color.WHITE);
                t.setForeground(Color.decode("#4A4A4A"));
            }
        });
    }

    // 테스트용 main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TeamListPage::new);
    }
}