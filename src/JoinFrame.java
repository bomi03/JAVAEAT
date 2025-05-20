import java.awt.*;
import javax.swing.*;
import model.Management;
import model.User;

public class JoinFrame extends JFrame {

    private Management manager;
    private JTextField authCodeField;

    public JoinFrame(Management manager) {
        this.manager = manager;

        setTitle("회원가입");
        setSize(393, 852);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // 뒤로가기
        JButton backButton = new JButton("<");
        backButton.setBounds(10, 10, 50, 30);
        styleGray(backButton);
        backButton.addActionListener(e -> dispose()); // ✅ 창만 닫기
        add(backButton);

        JLabel titleLabel = new JLabel("회원가입", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setBounds(120, 10, 150, 30);
        add(titleLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(20, 50, 350, 1);
        add(separator);

        // 이름
        JLabel nameLabel = new JLabel("이름");
        nameLabel.setBounds(30, 70, 100, 25);
        add(nameLabel);

        JLabel nameRequiredLabel = new JLabel("*필수입력항목입니다");
        nameRequiredLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        nameRequiredLabel.setForeground(Color.RED);
        nameRequiredLabel.setBounds(70, 70, 150, 25);
        add(nameRequiredLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(30, 100, 320, 30);
        add(nameField);

        JLabel nameErrorLabel = new JLabel("");
        nameErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        nameErrorLabel.setForeground(Color.RED);
        nameErrorLabel.setBounds(30, 130, 300, 15);
        add(nameErrorLabel);

        // 아이디
        JLabel idLabel = new JLabel("아이디");
        idLabel.setBounds(30, 140, 100, 25);
        add(idLabel);

        JLabel idRequiredLabel = new JLabel("*필수입력항목입니다");
        idRequiredLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        idRequiredLabel.setForeground(Color.RED);
        idRequiredLabel.setBounds(70, 140, 150, 25);
        add(idRequiredLabel);

        JTextField idField = new JTextField();
        idField.setBounds(30, 170, 320, 30);
        add(idField);

        JLabel idErrorLabel = new JLabel("");
        idErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        idErrorLabel.setForeground(Color.RED);
        idErrorLabel.setBounds(30, 200, 300, 15);
        add(idErrorLabel);

        // 비밀번호
        JLabel pwLabel = new JLabel("비밀번호");
        pwLabel.setBounds(30, 210, 100, 25);
        add(pwLabel);

        JLabel pwRequiredLabel = new JLabel("*필수입력항목입니다");
        pwRequiredLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        pwRequiredLabel.setForeground(Color.ORANGE);
        pwRequiredLabel.setBounds(90, 210, 150, 25);
        add(pwRequiredLabel);

        JPasswordField pwField = new JPasswordField();
        pwField.setBounds(30, 240, 320, 30);
        add(pwField);

        JLabel pwErrorLabel = new JLabel("");
        pwErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        pwErrorLabel.setForeground(Color.RED);
        pwErrorLabel.setBounds(30, 270, 300, 15);
        add(pwErrorLabel);

        // 비밀번호 확인
        JLabel pwCheckLabel = new JLabel("비밀번호 확인");
        pwCheckLabel.setBounds(30, 290, 100, 25);
        add(pwCheckLabel);

        JPasswordField pwCheckField = new JPasswordField();
        pwCheckField.setBounds(30, 320, 320, 30);
        add(pwCheckField);

        JLabel pwCheckErrorLabel = new JLabel("");
        pwCheckErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        pwCheckErrorLabel.setForeground(Color.RED);
        pwCheckErrorLabel.setBounds(30, 350, 300, 15);
        add(pwCheckErrorLabel);

        // 이메일
        JLabel emailLabel = new JLabel("이메일");
        emailLabel.setBounds(30, 360, 100, 25);
        add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(30, 390, 200, 30);
        add(emailField);

        JButton sendAuthButton = new JButton("인증번호 전송");
        sendAuthButton.setBounds(240, 390, 110, 30);
        styleGray(sendAuthButton);
        add(sendAuthButton);

        JLabel emailErrorLabel = new JLabel("");
        emailErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        emailErrorLabel.setForeground(Color.RED);
        emailErrorLabel.setBounds(30, 420, 300, 15);
        add(emailErrorLabel);

        // 인증번호
        JLabel authLabel = new JLabel("인증번호");
        authLabel.setBounds(30, 430, 100, 25);
        add(authLabel);

        authCodeField = new JTextField();
        authCodeField.setBounds(30, 460, 320, 30);
        add(authCodeField);

        JLabel authErrorLabel = new JLabel("");
        authErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        authErrorLabel.setForeground(Color.RED);
        authErrorLabel.setBounds(30, 490, 300, 15);
        add(authErrorLabel);

        // 인증번호 전송 버튼 클릭 시
        sendAuthButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "이메일로 인증번호를 전송했습니다.",
                "인증번호 발송", JOptionPane.INFORMATION_MESSAGE);
        });



        // 약관
        JCheckBox term1 = new JCheckBox("[필수] 만 14세 이상 서비스 이용 동의");
        JCheckBox term2 = new JCheckBox("[필수] 개인정보 수집/이용 동의");
        JCheckBox term3 = new JCheckBox("[필수] 서비스 이용 약관");
        

        term1.setBounds(30, 520, 300, 20);
        term2.setBounds(30, 550, 220, 20);
        term3.setBounds(30, 580, 220, 20);
        add(term1); add(term2); add(term3);

        JCheckBox term4 = new JCheckBox("[선택] 광고성 정보 수신 동의");
        term4.setBounds(30, 610, 300, 20);
        add(term4);

        JCheckBox term5 = new JCheckBox("[선택] 마케팅 정보/이용 동의");
        term5.setBounds(30, 640, 300, 20);
        add(term5);


        JButton viewPrivacyBtn = new JButton("보기");
        viewPrivacyBtn.setBounds(260, 550, 60, 20);
        styleGray(viewPrivacyBtn);
        add(viewPrivacyBtn);

        JButton viewServiceBtn = new JButton("보기");
        viewServiceBtn.setBounds(260, 580, 60, 20);
        styleGray(viewServiceBtn);
        add(viewServiceBtn);

        JLabel termsErrorLabel = new JLabel("");
        termsErrorLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        termsErrorLabel.setForeground(Color.RED);
        termsErrorLabel.setBounds(30, 670, 300, 15);
        add(termsErrorLabel);

        viewPrivacyBtn.addActionListener(e -> {
        JFrame termsFrame = new JFrame("개인정보 수집/이용 동의");
        termsFrame.setSize(400, 500);
        termsFrame.setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea(
        "※ 서비스 이용 약관 및 개인정보 수집/이용 동의\n\n" +
        "「개인정보 보호법」제 15조 법규에 의거하여, '눈뭉치'는 이용자의 개인정보 수집 및 활용에 대해\n" +
        "개인정보 수집 및 이용 동의서를 받고 있습니다.\n" +
        "개인정보 제공자가 동의한 내용 외의 다른 목적으로 활용하지 않으며,\n" +
        "제공된 개인정보의 이용을 거부하고자 할 때에는 개인정보 관리 책임자를 통해 열람, 정정 혹은 삭제를 요구할 수 있습니다.\n\n" +
        "제공된 개인정보는 '눈뭉치'의 아래 항목에 제한된 범위 내에서만 활용됩니다.\n\n" +
        "1. 개인정보의 제공 목적\n" +
        "- 계약의 체결 · 유지 · 이행 · 관리\n" +
        "- 개인식별 · 본인확인 · 부정사용방지 등 회원관리, 본 서비스 및 부가/제휴서비스 제공 등\n\n" +
        "2. 개인정보 제공 항목\n" +
        "- 개인의 성명, 이메일\n" +
        "- 기타 계약의 체결, 유지, 이행, 관리 등과 관련하여 본인이 제공한 정보\n\n" +
        "3. 개인정보를 제공받는 자\n" +
        "- '눈뭉치'\n\n" +
        "4. 제공받는 자의 개인정보 보유 및 이용기간\n" +
        "- 위 개인정보의 수집 및 이용 동의일로부터 목적을 달성할 때까지 보유/이용합니다.\n" +
        "  (단, 법률에 따라 일정 기간 보존될 수 있습니다.)\n\n" +
        "   가. 계약 또는 청약철회 등에 관한 기록: 5년\n" +
        "   나. 대금결제 및 재화 등의 공급에 관한 기록: 5년\n" +
        "   다. 소비자의 불만 또는 분쟁처리에 관한 기록: 3년\n\n" +
        "※ 위 사항에 대해 동의를 거부할 수 있으나, 본 동의는 필수 항목으로 거부 시 서비스 가입 또는 유지, \n" +
        "거래 인증 및 부정사용 예방 및 관리 등이 불가능합니다.\n\n" +
        "※ 본 내용이 변경되는 경우, 서비스 내 공지를 통해 안내드립니다.\n"
        );
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        textArea.setMargin(new Insets(10,10,10,10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        termsFrame.add(scrollPane);
        termsFrame.setVisible(true);
    });


    viewServiceBtn.addActionListener(e -> {
    JFrame termsFrame = new JFrame("서비스 이용 약관");
    termsFrame.setSize(500, 600);
    termsFrame.setLocationRelativeTo(null);

    JTextArea textArea = new JTextArea(
        "※ 서비스 이용 약관\n\n" +

        "제 1조 (목적)\n" +
        "본 약관은 눈뭉치에서 제공하는 서비스를 제공함에 있어 눈뭉치와 이용자의 권리, 의무 및 책임사항을 규정함을 목적으로 합니다.\n\n" +

        "제 2조 (이용약관의 효력 및 변경)\n" +
        "1. 눈뭉치는 본 약관의 내용을 회원이 쉽게 알 수 있도록 회원가입 화면 및 마이페이지 등에 게시합니다.\n" +
        "2. 눈뭉치는 관련법을 위배하지 않는 범위에서 본 약관을 개정할 수 있습니다.\n" +
        "3. 눈뭉치는 약관을 개정할 경우 적용일자 및 개정사유를 명시하여 사전 공지합니다.\n" +
        "4. 회원은 변경에 동의하지 않을 경우 탈퇴할 수 있으며, 계속 이용 시 동의한 것으로 간주합니다.\n" +
        "5. 변경된 약관에 대한 정보를 알지 못해 발생하는 피해는 눈뭉치가 책임지지 않습니다.\n\n" +

        "제 3조 (서비스의 제공)\n" +
        "1. 눈뭉치는 다음 서비스를 제공합니다:\n" +
        "   i. 대학생활 편의 서비스\n" +
        "   ii. 팀 매칭 서비스\n" +
        "   iii. 기타 눈뭉치가 정하는 서비스\n" +
        "2. 운영상 또는 기술상 변경이 있을 수 있습니다.\n" +
        "3. 개인정보 및 기록에 따라 이용 조건에 차이가 있을 수 있습니다.\n\n" +

        "제 4조 (이용계약의 성립)\n" +
        "1. 본 약관 동의와 이용 신청, 눈뭉치의 승낙으로 계약이 성립합니다.\n" +
        "2. 동의는 회원가입 시 체크로 간주됩니다.\n\n" +

        "제 5조 (서비스 이용 신청)\n" +
        "1. 이름과 이메일 등 정보 제공이 필요합니다.\n" +
        "2. 타인 정보 도용 시 삭제 및 법적 책임이 따릅니다.\n" +
        "3. 본인 인증 및 학교 인증이 요구될 수 있습니다.\n" +
        "4. 만 14세 미만은 이용이 불가합니다.\n\n" +

        "제 6조 (개인정보의 보호 및 사용)\n" +
        "1. 눈뭉치는 관련법에 따라 개인정보를 보호하며, 책임을 다합니다.\n" +
        "2. 제휴사 제공 시 사전 동의를 받습니다.\n" +
        "3. 회원은 언제든 수집 및 이용 동의를 철회할 수 있습니다.\n" +
        "4. 아이디, 비밀번호 등의 관리는 회원 책임입니다.\n\n" +

        "제 7조 (서비스 이용계약의 종료)\n" +
        "1. 회원은 직접 탈퇴 요청할 수 있으며 눈뭉치는 이를 처리합니다.\n" +
        "2. 탈퇴 후에도 게시물은 삭제되지 않습니다.\n\n" +

        "제 8조 (기타)\n" +
        "1. 이 약관은 2025년 OO월 OO일에 최신화 되었습니다.\n" +
        "2. 해석 및 기타 사항은 관련 법령과 관례에 따릅니다.\n" +
        "3. 다른 정책과 상충 시 별도 정책을 우선합니다.\n\n" +

        "※ 개인정보 수집 및 이용 동의\n\n" +
        "「개인정보 보호법」 제15조에 따라, 눈뭉치는 이용자의 개인정보 수집 및 이용에 대한 동의를 받고 있습니다.\n" +
        "1. 수집 목적: 계약 체결 및 이행, 본인 확인, 서비스 제공 등\n" +
        "2. 수집 항목: 성명, 이메일, 기타 회원이 제공한 정보\n" +
        "3. 제공 대상: 눈뭉치\n" +
        "4. 보유 기간: 수집일로부터 목적 달성 시까지\n\n" +
        "- 계약 관련 기록: 5년\n" +
        "- 결제/공급 관련 기록: 5년\n" +
        "- 소비자 불만/분쟁 처리 기록: 3년\n\n" +
        "※ 위 동의는 필수 항목이며, 거부 시 서비스 이용이 제한될 수 있습니다.\n" +
        "※ 내용 변경 시 공지를 통해 안내됩니다.\n"
    );
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setEditable(false);
    textArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
    textArea.setMargin(new Insets(10, 10, 10, 10));

    JScrollPane scrollPane = new JScrollPane(textArea);
    termsFrame.add(scrollPane);
    termsFrame.setVisible(true);
});


        // 다음 버튼
        JButton nextButton = new JButton("다음");
        nextButton.setBounds(30, 700, 320, 40);
        styleBlue(nextButton);
        add(nextButton);

        nextButton.addActionListener(e -> {
            String username = nameField.getText().trim();
            String userID = idField.getText().trim();
            String password = new String(pwField.getPassword()).trim();
            String passwordCheck = new String(pwCheckField.getPassword()).trim();
            String email = emailField.getText().trim();
            String inputAuthCode = authCodeField.getText().trim();
            String actualAuthCode = "1234";
            boolean isVerified = true;
            boolean isAgreed = term1.isSelected() && term2.isSelected() && term3.isSelected();

            nameErrorLabel.setText("");
            idErrorLabel.setText("");
            pwErrorLabel.setText("");
            pwCheckErrorLabel.setText("");
            emailErrorLabel.setText("");
            authErrorLabel.setText("");
            termsErrorLabel.setText("");

            User tempUser = new User("", "", "", "");
            String result = tempUser.validateSignup(
                    username, userID, password, passwordCheck, email,
                    inputAuthCode, actualAuthCode, isVerified, isAgreed,
                    manager.getAllUsers()  // ✅ 변경
            );

            if (!result.equals("회원가입이 완료되었습니다!")) {
                if (result.contains("이름")) nameErrorLabel.setText(result);
                else if (result.contains("아이디")) idErrorLabel.setText(result);
                else if (result.contains("비밀번호") && !result.contains("일치")) pwErrorLabel.setText(result);
                else if (result.contains("인증번호")) authErrorLabel.setText(result);
                else if (result.contains("일치") && result.contains("비밀번호")) pwCheckErrorLabel.setText(result);
                else if (result.contains("이메일")) emailErrorLabel.setText(result);
                else if (result.contains("약관")) termsErrorLabel.setText(result);
                else JOptionPane.showMessageDialog(null, result);
            } else {
                User newUser = new User(username, userID, password, email);
                manager.addUser(newUser);  // ✅ 등록
                //new ProfilePage(newUser, manager); // ✅ 전달
                ProfilePage pp = new ProfilePage(newUser, manager);
                pp.setLocationRelativeTo(null);   // JoinFrame(this) 기준으로 위치 맞춤
                //dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleBlue(AbstractButton b) {
        b.setBackground(Color.decode("#003087"));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }

    private void styleGray(AbstractButton b) {
        b.setBackground(Color.decode("#D9D9D9"));
        b.setForeground(Color.decode("#4A4A4A"));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }
}
