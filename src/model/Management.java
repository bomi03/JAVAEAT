package model;

import java.util.List;
import java.util.Map;

public class Management {
    private Map<String, SongiType> answerToQuestion;
    private List<SongiType> songiTypes;
    private Map<SongiType, String> songiImagePath;

    public String getImagePath(SongiType type) {
        return songiImagePath.get(type);
    }

    public SongiType evaluate(Map<Integer, questionAnswerMap>) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'evaluate'");
    }
}
