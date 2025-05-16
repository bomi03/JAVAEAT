package model;

import java.util.HashMap;
import java.util.Map;

public class Test {

    private Map<Integer, String> questionAnswerMap = new HashMap<>();//질문 번호, 응답 결과를 저장해 Management에게 넘겨줌줌
    private SongiType testResult;
    private String resultImagePath;

    //사용자 응답 추가 하는 메서드 기존에 없는데 추가함//
    public void addAnswer(int questionNumber, String answer){
        questionAnswerMap.put(questionNumber, answer);
    }
    public void takeTest(User user, Management manager) {
        
        this.testResult = manager.evaluate(questionAnswerMap);
        this.resultImagePath = manager.getImagePath(testResult);

    }
    public String getResultImagePath() {
        return resultImagePath;
    }
    public SongiType getUserResultType() {
        return testResult;
    }
    // 테스트 다시하기 위해서 응답 초기화하는 메서드 추가함
    // clearAnswers() - 유림 수정
    public void clearAnswers() {
        questionAnswerMap.clear();
        testResult = null;
        resultImagePath = null;
    }        
}
