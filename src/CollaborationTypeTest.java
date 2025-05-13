import javax.swing.*;
import java.awt.*;
import model.*;

public class CollaborationTypeTest extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private int currentPage = 0;
    private final int TOTAL_PAGES = 10;

    private JButton[] optionButtons;
    private int selectedOptionIndex = -1;
    private Test test = new Test();
    private Management manager = new Management(AnswerMapFactory.createAnswerMap());

    private final String[][] questions = new String[][] {
        {"Q1. 조별과제가 시작되면 나는 가장 먼저…",
            "전체 구조와 일정부터 구상해본다.",
            "어떤 주제로 하면 좋을지 기발한 아이디어를 막 떠올려본다.",
            "팀원들끼리 먼저 분위기 파악하고 친해지려 한다.",
            "다들 뭐할지 모르고 있는 것 같으니 내가 먼저 말을 꺼내본다."},
        {"Q2. 역할 분담 시간! 나는…",
            "적절한 분배가 이뤄지도록 리드한다.",
            "내가 잘할 수 있는 실무 위주로 맡는다.",
            "역할보다는 전체 흐름 조정이나 피드백을 중심으로 참여한다.",
            "분위기 흐르는데 맞춰 유연하게 수용한다."},
        {"Q3. 예기치 않은 문제가 발생하면 나는…",
            "즉각적으로 문제의 핵심을 파악하고 논리적으로 해결 방안을 제시한다.",
            "창의적인 아이디어로 기존 틀을 깨고 새로운 해결책을 모색한다.",
            "팀원들과 신속하게 협의하여 모두의 의견을 반영한 대안을 마련한다.",
            "침착하게 상황을 정리하고 필요한 경우 외부 도움을 받아 문제를 극복한다."},
        {"Q4. 프로젝트의 진행 상황이 더딜 때 나는…",
            "분위기를 환기하며 동기부여를 해준다.",
            "직접 나서 꼼꼼히 체크하고, 실행력을 불어넣는다.",
            "실무적으로 필요한 일을 먼저 빠르게 처리한다.",
            "진행이 느린 이유를 분석하고, 방향을 제시한다."},
        {"Q5. 내가 잘 아는 분야에서 다른 사람과 의견이 엇갈렸을 때 나는…",
            "상대 의견을 듣고, 공통점을 찾아 조율하려 한다.",
            "확신이 있으면 책임감을 갖고 팀을 이끈다.",
            "내 의견을 논리적으로 정리해 말로 설득해본다.",
            "자료와 근거를 통해 체계적으로 의견을 정리해 조율한다."},
        {"Q6. 나는 아이디어 회의 중 보통…",
            "말의 흐름을 잡아가며 설득력 있게 의견을 이끈다.",
            "즉흥적으로 떠오르는 아이디어를 자유롭게 던진다.",
            "다들 말할 수 있도록 분위기를 부드럽게 정리하고 중심을 잡는다.",
            "회의 내용을 시각적으로 정리하거나 도식으로 표현한다."},
        {"Q7. 팀에서 내가 맡는다면 가장 자신 있는 건?",
            "탁월한 화술과 설득력으로 발표와 토론을 이끈다.",
            "팀의 의견을 보기쉽게 정돈해 표현한다.",
            "팀 분위기를 잘 살피며 모두의 의견을 조율한다.",
            "창의적인 아이디어 도출과 문제 해결로 팀에 방향을 제시한다."},
        {"Q8. 모르는 사람이 많은 장소에서 나는?",
            "자신감 있게 말을 꺼내 분위기를 주도한다.",
            "즉흥적으로 떠오르는 이야기를 꺼내며 자연스럽게 어울린다.",
            "말하기 전에 상황을 정리하고 필요한 순간에 조심스럽게 의견을 낸다.",
            "유쾌하게 리액션하며 분위기를 살린다."},
        {"Q9. 마감일이 2주 정도 남았을 때 나는…",
            "일정표를 작성하고 계획에 따라 차근차근 진행한다.",
            "머릿속 내용을 말처럼 정리하며 흐름을 잡는다.",
            "급박한 상황에도 침착하게 기간을 활용한다.",
            "당장 해야 할 것부터 정리하며 흐름을 잡는다."},
        {"Q10. 조원이 실수했을 때 나는?",
            "일정을 재정비하고 계획을 다시 짠다.",
            "실수한 과정을 분석해 원인을 찾는다.",
            "침착하게 재발 방지 대책을 마련한다.",
            "자료나 내용을 다시 정리해 도와준다."}
    };

    public CollaborationTypeTest() {
        setTitle("협업유형테스트");
        setSize(393, 852);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        for (int i = 0; i < TOTAL_PAGES; i++) {
            cardPanel.add(createQuestionPage(i), "question" + i);
        }

        add(cardPanel);
        setVisible(true);
    }

    private JPanel createQuestionPage(int index) {
        JPanel panel = new JPanel(null);

        JLabel title = new JLabel("협업 유형 테스트", SwingConstants.CENTER);
        title.setBounds(0, 20, 393, 30);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        panel.add(title);

        int progress = (int)(((index + 1) / (double) TOTAL_PAGES) * 100);
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(progress);
        progressBar.setBounds(30, 60, 330, 8);
        panel.add(progressBar);

        JLabel questionLabel = new JLabel("<html><div style='text-align:center;'>" + questions[index][0] + "</div></html>", SwingConstants.CENTER);
        questionLabel.setBounds(0, 100, 393, 60);
        questionLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        panel.add(questionLabel);

        optionButtons = new JButton[4];
        int y = 180;
        for (int i = 0; i < 4; i++) {
            char optionLetter = (char) ('A' + i);
            JButton btn = new JButton("<html><div style='text-align:left; width:260px; padding:8px; white-space:normal;'>" + optionLetter + ". " + questions[index][i + 1] + "</div></html>");
            btn.setBounds(50, y, 290, 60);
            btn.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setVerticalAlignment(SwingConstants.CENTER);
            btn.setFocusPainted(false);
            btn.setContentAreaFilled(true);
            btn.setOpaque(true);
            int choice = i;
            btn.addActionListener(e -> {
                selectedOptionIndex = choice;
                for (int j = 0; j < optionButtons.length; j++) {
                    optionButtons[j].setBackground(UIManager.getColor("Button.background"));
                    optionButtons[j].setForeground(Color.BLACK);
                }
                btn.setBackground(new Color(0, 45, 114));
                btn.setForeground(Color.WHITE);
                btn.setOpaque(true);
                btn.repaint();
            });
            optionButtons[i] = btn;
            panel.add(btn);
            y += 70;
        }

        JButton nextBtn = new JButton(index == TOTAL_PAGES - 1 ? "결과 보기" : "다음");
        nextBtn.setBounds(50, 650, 290, 50);
        nextBtn.setBackground(new Color(0, 45, 114));
        nextBtn.setForeground(Color.WHITE);
        nextBtn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        nextBtn.addActionListener(e -> {
            if (selectedOptionIndex != -1) {
                String answerKey = "Q" + (index + 1) + (char) ('A' + selectedOptionIndex);
                test.addAnswer(index + 1, answerKey);
                selectedOptionIndex = -1;
                goToNextPage();
            } else {
                JOptionPane.showMessageDialog(this, "답변을 선택해주세요!");
            }
        });
        panel.add(nextBtn);

        return panel;
    }

    private JPanel createResultPage() {
        test.takeTest(null, manager);
        SongiType result = test.getUserResultType();

        if (result == null) {
            JOptionPane.showMessageDialog(this, "결과를 계산할 수 없습니다. 기본값을 보여줍니다.");
            result = SongiType.샘송이;
        }

        JPanel panel = new JPanel(null);
        JLabel title = new JLabel("결과", SwingConstants.CENTER);
        title.setBounds(0, 20, 393, 30);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        panel.add(title);

        JLabel subtitle = new JLabel("당신의 협업 유형은...", SwingConstants.CENTER);
        subtitle.setBounds(0, 50, 393, 20);
        subtitle.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        subtitle.setForeground(Color.GRAY);
        panel.add(subtitle);

        ImageIcon icon = new ImageIcon(getClass().getResource(result.getImagePath()));
        JLabel imgLabel = new JLabel(icon);
        imgLabel.setBounds((393 - icon.getIconWidth()) / 2, 90, icon.getIconWidth(), icon.getIconHeight());
        panel.add(imgLabel);
// ✅ 공유하기 버튼 result 참조 오류 해결
// SongiType result -> finalResult로 람다 안에서 안전하게 참조되도록 수정됨

// (중략) 기존 코드 유지
        // typeName 라벨 제거 (이미 이미지에 포함됨)
        // JLabel typeName = new JLabel(result.name(), SwingConstants.CENTER);
        // typeName.setBounds(0, 90 + icon.getIconHeight() + 20, 393, 30);
        // typeName.setFont(new Font("맑은 고딕", Font.BOLD, 17));
        // panel.add(typeName);

        // 결과 화면용 버튼들 추가
        JButton retryBtn = new JButton("다시하기");
        retryBtn.setBounds(60, 700, 120, 40);
        retryBtn.setBackground(new Color(200, 200, 200));
        retryBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        retryBtn.addActionListener(e -> {
            test = new Test();
            currentPage = 0;
            cardLayout.show(cardPanel, "question0");
        });
        panel.add(retryBtn);

        SongiType finalResult = result; // 람다 내 참조 위해 별도 변수 선언
        JButton shareBtn = new JButton("공유하기");
        shareBtn.setBounds(210, 700, 120, 40);
        shareBtn.setBackground(new Color(0, 120, 215));
        shareBtn.setForeground(Color.WHITE);
        shareBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        shareBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "[공유하기 기능] 당신의 유형은: " + (finalResult != null ? finalResult.name() : "(없음)"));
        });
        panel.add(shareBtn);

        return panel;
    }

    private void goToNextPage() {
        currentPage++;
        if (currentPage >= TOTAL_PAGES) {
            cardPanel.add(createResultPage(), "result");
            cardLayout.show(cardPanel, "result");
        } else {
            cardLayout.show(cardPanel, "question" + currentPage);
        }
    }

    public static void main(String[] args) {
        new CollaborationTypeTest();
    }
}
