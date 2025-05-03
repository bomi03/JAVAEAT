package model;

import java.util.List;
import java.util.Map;

public class Test {
    private List<String> questions;
    private List<String> userAnswers;
    private Map<Integer, String> questionAnswerMap;
    private SongiType testResult;
    private String resultImagePath;

    public void takeTest(User user, Management manager) {}
    public String getResultImagePath() {
        return resultImagePath;
    }
    public SongiType getUserResultType() {
        return testResult;
    }
}