package model;

import java.util.List;
import java.util.Map;

public class Management {
    private Map<String, SongiType> answerToQuestion;
    private List<SongiType> songiTypes;
    private Map<SongiType, String> songiImagePath;

    public SongiType evaluate(List<String> userAnswers) {
        return null; // 로직 미구현
    }

    public String getImagePath(SongiType type) {
        return songiImagePath.get(type);
    }
}