// TeamBuildPage.java - 팀 생성/수정 페이지

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import model.Post;
import model.Team;
import model.User;
import model.Management;

public class TeamBuildPage extends JFrame {
    private User user;
    private Management manager;
    private Post editingPost;      // 수정 모드 대상, null 이면 생성 모드
    private boolean isEditMode;

    private static int nextPostId = 1;
    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy/MM/dd");

    // UI 컴포넌트
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JLabel warningLabel;
    private JLabel imgLabel;
    private JButton uploadImgBtn;
    private JTextField titleField;
    private JComboBox<String> categoryBox;
    private JToggleButton recruitingBtn, closedBtn;
    private ButtonGroup statusGroup;
    private JTextField deadlineField, maxField, currentField;
    private JTextArea descArea;
    private JButton completeBtn;

    private File selectedImageFile;

    private static final String[] CATEGORIES = {
        "카테고리 선택","공모전","스터디","수업 팀플","교내 대회","프로젝트","기타"
    };
    private static final Color PLACEHOLDER_COLOR = Color.decode("#ADADAD");
    private static final Color TEXT_COLOR      = Color.BLACK;

    // 생성 모드 생성자
    public TeamBuildPage(User user, Management manager) {
        this(user, manager, null);
    }

    // 수정 모드 생성자
    public TeamBuildPage(User user, Management manager, Post postToEdit) {
        this.user = user;
        this.manager = manager;
        this.editingPost = postToEdit;
        this.isEditMode = postToEdit != null;

        setTitle(isEditMode ? "팀원 모집글 수정" : "팀원 모집 글 작성");
        setSize(393, 852);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buildUI();
        registerListeners();

        if (isEditMode) {
            prefillFields();
            completeBtn.setText("수정");
        }

        setVisible(true);
    }

    private void buildUI() {
        mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(393, 1700));
        int y = 0;

        // 뒤로가기
        JButton backBtn = new JButton("<html>&lt;-</html>");
        styleGrayButton(backBtn);
        backBtn.setBounds(10, y+20, 40,30);
        mainPanel.add(backBtn);

        JLabel header = new JLabel(getTitle(), SwingConstants.CENTER);
        header.setBounds(0, y+20, 393,30);
        mainPanel.add(header);
        y += 60;

        JSeparator sep = new JSeparator();
        sep.setBounds(0, y, 393,1);
        sep.setForeground(Color.decode("#8A8888"));
        mainPanel.add(sep);
        y += 22;

        // 이미지
        imgLabel = new JLabel();
        imgLabel.setBorder(new LineBorder(Color.GRAY,1,true));
        imgLabel.setBounds((393-100)/2, y,100,100);
        mainPanel.add(imgLabel);
        y += 110;

        uploadImgBtn = new JButton("이미지 업로드");
        styleGrayButton(uploadImgBtn);
        uploadImgBtn.setBounds((393-120)/2, y,120,30);
        mainPanel.add(uploadImgBtn);
        y += 50;

        // 경고
        warningLabel = new JLabel();
        warningLabel.setForeground(Color.RED);
        warningLabel.setBounds(20,y,360,20);
        mainPanel.add(warningLabel);
        y += 30;

        // 카테고리
        mainPanel.add(labeled("카테고리 *","필수 입력 항목입니다.",20,y));
        categoryBox = new JComboBox<>(CATEGORIES);
        categoryBox.setBounds(20,y+20,350,30);
        categoryBox.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        mainPanel.add(categoryBox);
        y += 70;

        // 제목
        mainPanel.add(labeled("제목 *","필수 입력 항목입니다.",20,y));
        titleField = new JTextField();
        titleField.setBounds(20,y+20,350,30);
        titleField.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(titleField,"제목을 작성해 주세요.");
        mainPanel.add(titleField);
        y += 70;

        // 모집 상태
        mainPanel.add(labeled("모집 상태 *","필수 입력 항목입니다.",20,y));
        recruitingBtn = new JToggleButton("모집중");
        closedBtn    = new JToggleButton("모집완료");
        styleToggleButton(recruitingBtn);
        styleToggleButton(closedBtn);
        recruitingBtn.setBounds(20,y+20,170,40);
        closedBtn   .setBounds(200,y+20,170,40);
        statusGroup = new ButtonGroup();
        statusGroup.add(recruitingBtn);
        statusGroup.add(closedBtn);
        mainPanel.add(recruitingBtn);
        mainPanel.add(closedBtn);
        y += 80;

        // 모집 기간
        mainPanel.add(labeled("모집 기간","",20,y));
        deadlineField = new JTextField();
        deadlineField.setBounds(20,y+20,350,30);
        deadlineField.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(deadlineField,"YYYY/MM/DD");
        mainPanel.add(deadlineField);
        y += 70;

        // 모집 인원
        mainPanel.add(labeled("모집 인원 *","필수 입력 항목입니다.",20,y));
        maxField = new JTextField();
        maxField.setBounds(20,y+20,350,30);
        maxField.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(maxField,"숫자로만 입력해 주세요. ex.10");
        mainPanel.add(maxField);
        y += 70;

        // 모집된 인원
        mainPanel.add(labeled("모집된 인원","",20,y));
        currentField = new JTextField();
        currentField.setBounds(20,y+20,350,30);
        currentField.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(currentField,"모집된 인원을 숫자로만 입력해 주세요. ex.5");
        mainPanel.add(currentField);
        y += 70;

        // 설명
        mainPanel.add(labeled("팀 설명 *","필수 입력 항목입니다.",20,y));
        descArea = new JTextArea();
        descArea.setLineWrap(true);
        descArea.setBounds(20,y+20,350,380);
        descArea.setBorder(new LineBorder(Color.decode("#D9D9D9")));
        setPlaceholder(descArea,
            "[모집 대상]\n(ex. UX/UI 디자인 공모전에 관심 있는 사람)\n\n" +
            "[역할]\n(ex. 디자인 2명, 개발 3명)\n\n" +
            "[현재 구성원]\n(ex. 기획 1명, 개발 1명 참여중)\n\n" +
            "[진행 기간]\n(ex. 2025.04.01~2025.06.30)\n\n" +
            "[진행 방법/일정]\n(ex. 주 1회 미팅)\n\n" +
            "[모임 방식(온라인/오프라인)]\n(ex. 온라인 회의 + 오프라인 발표)\n\n" +
            "[우대사항]\n(ex. 피그마 사용 가능자 우대)"
        );
        mainPanel.add(descArea);
        y += 420;

        // 완료/수정
        completeBtn = new JButton("완료");
        styleBlueButton(completeBtn);
        completeBtn.setBounds(20,y,350,40);
        mainPanel.add(completeBtn);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBounds(0,0,393,852);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane);
    }

    private void prefillFields() {
        // 이미지
        if (editingPost.getPostImagePath()!=null && !editingPost.getPostImagePath().isEmpty()) {
            selectedImageFile = new File(editingPost.getPostImagePath());
            imgLabel.setIcon(new ImageIcon(
                new ImageIcon(editingPost.getPostImagePath())
                    .getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH)
            ));
        }
        categoryBox.setSelectedItem(editingPost.getCategory());
        titleField.setText(editingPost.getTitle());
        titleField.setForeground(TEXT_COLOR);
        if ("모집중".equals(editingPost.getStatus())) recruitingBtn.setSelected(true);
        else closedBtn.setSelected(true);
        if (editingPost.getRecruitDeadline()!=null) {
            deadlineField.setText(DATE_FMT.format(editingPost.getRecruitDeadline()));
            deadlineField.setForeground(TEXT_COLOR);
        }
        maxField.setText(String.valueOf(editingPost.getMaxApplicants()));
        maxField.setForeground(TEXT_COLOR);
        currentField.setText(String.valueOf(editingPost.getCurrentApplicants()));
        currentField.setForeground(TEXT_COLOR);
        descArea.setText(editingPost.getDescription());
        descArea.setForeground(TEXT_COLOR);
    }

    private JLabel labeled(String text,String hint,int x,int y){
        JLabel l=new JLabel(String.format(
            "<html><span style='color:#4A4A4A'><b>%s</b></span> <span style='color:#BABABA'>%s</span></html>",
            text,hint));
        l.setBounds(x,y,360,20); return l;
    }

    private void styleGrayButton(AbstractButton b){
        b.setBackground(Color.decode("#D9D9D9"));
        b.setForeground(Color.decode("#4A4A4A"));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }
    private void styleBlueButton(AbstractButton b){
        b.setBackground(Color.decode("#003087"));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }
    private void styleToggleButton(JToggleButton t){
        styleGrayButton(t);
        t.addItemListener(e->{
            if(t.isSelected()){
                t.setBackground(Color.decode("#003087"));
                t.setForeground(Color.WHITE);
            } else {
                t.setBackground(Color.decode("#D9D9D9"));
                t.setForeground(Color.decode("#4A4A4A"));
            }
        });
    }
    private void setPlaceholder(JTextComponent c,String ph){
        c.setText(ph); c.setForeground(PLACEHOLDER_COLOR);
        c.addFocusListener(new FocusAdapter(){
            @Override public void focusGained(FocusEvent e){
                if(c.getText().equals(ph)){
                    c.setText(""); c.setForeground(TEXT_COLOR);
                }
            }
            @Override public void focusLost(FocusEvent e){
                if(c.getText().isEmpty()){
                    c.setText(ph); c.setForeground(PLACEHOLDER_COLOR);
                }
            }
        });
    }

    private void registerListeners(){
        // 뒤로가기
        ((JButton)mainPanel.getComponent(0)).addActionListener(e->{
            dispose(); new TeamListPage(user,manager);
        });

        // 업로드
        uploadImgBtn.addActionListener(e->{
            JFileChooser ch=new JFileChooser();
            if(ch.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
                selectedImageFile=ch.getSelectedFile();
                imgLabel.setIcon(new ImageIcon(
                    new ImageIcon(selectedImageFile.getAbsolutePath())
                        .getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH)
                ));
            }
        });

        // 완료/수정
        completeBtn.addActionListener(e->{
            warningLabel.setText("");

            // 검증
            if(categoryBox.getSelectedIndex()==0){
                warningLabel.setText("카테고리를 선택해주세요."); return;
            }
            String title=titleField.getText().trim();
            if(title.isEmpty()||title.equals("제목을 작성해 주세요.")){
                warningLabel.setText("제목을 입력해주세요."); return;
            }
            if(!recruitingBtn.isSelected()&&!closedBtn.isSelected()){
                warningLabel.setText("모집 상태를 선택해주세요."); return;
            }

            String ds=deadlineField.getText().trim();
            Date deadline=null;
            if(!ds.isEmpty()&&!ds.equals("YYYY/MM/DD")){
                String[] p=ds.split("/");
                if(p.length!=3){ warningLabel.setText("날짜 형식은 YYYY/MM/DD로 입력해주세요."); return; }
                try{
                    int mm=Integer.parseInt(p[1]), dd=Integer.parseInt(p[2]);
                    if(mm<1||mm>12||dd<1||dd>31){
                        warningLabel.setText("날짜를 확인해주세요."); return;
                    }
                    deadline=DATE_FMT.parse(ds);
                }catch(Exception ex){
                    warningLabel.setText("날짜 형식은 YYYY/MM/DD로 입력해주세요."); return;
                }
            }

            String mx=maxField.getText().trim();
            if(mx.isEmpty()||mx.equals("숫자로만 입력해 주세요. ex.10")){
                warningLabel.setText("모집 인원을 입력해주세요."); return;
            }
            int max,curr=0;
            try{ max=Integer.parseInt(mx); }
            catch(Exception ex){
                warningLabel.setText("모집 인원은 숫자만 입력해주세요."); return;
            }
            String cr=currentField.getText().trim();
            if(!cr.isEmpty()&&!cr.equals("모집된 인원을 숫자로만 입력해 주세요. ex.5")){
                try{ curr=Integer.parseInt(cr); }
                catch(Exception ex){
                    warningLabel.setText("모집된 인원은 숫자만 입력해주세요."); return;
                }
                if(curr>max){
                    warningLabel.setText("모집된 인원은 모집 인원수 이하여야 합니다."); return;
                }
            }

            String desc=descArea.getText().trim();
            if(desc.isEmpty()||desc.startsWith("[모집 대상]")){
                warningLabel.setText("팀 설명을 입력해주세요."); return;
            }

            String imgPath = selectedImageFile!=null
                ? selectedImageFile.getAbsolutePath()
                : (isEditMode ? editingPost.getPostImagePath() : "");
            String category=(String)categoryBox.getSelectedItem();
            String status=recruitingBtn.isSelected()?"모집중":"모집완료";

            if(isEditMode){
                editingPost.editPost(
                    imgPath, category, title, status,
                    deadline, max, curr, desc
                );
                JOptionPane.showMessageDialog(this,"모집글이 수정되었습니다.");
            } else {
                Post p=new Post(
                    nextPostId++,0,
                    imgPath,category,title,status,
                    deadline,max,curr,desc
                );
                Post.addPost(p);
                Team.addTeam(new Team(p.getPostID()));
                JOptionPane.showMessageDialog(this,"팀 모집글이 생성되었습니다.");
            }

            dispose();
            new TeamListPage(user,manager);
        });
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            Management mgr=new Management(new HashMap<>());
            User u=new User("테스트","id","pw","email");
            new TeamBuildPage(u,mgr);
        });
    }
}
