package model;

import java.util.*;

public class Management {
    private final Map<String, SongiType> answerToQuestion;
    private final Map<SongiType, String> songiImagePath;

    public Management(Map<String, SongiType> answerToQuestion, Map<SongiType, String> songiImagePath) {
        this.answerToQuestion = answerToQuestion;
        this.songiImagePath = songiImagePath;
    }

    public SongiType evaluate(List<String> userAnswers) {
        Map<SongiType, Integer> counts = new HashMap<>();

        for (String key : userAnswers) {
            SongiType type = answerToQuestion.get(key);
            if (type != null) {
                counts.put(type, counts.getOrDefault(type, 0) + 1);
            }
        }

        return counts.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public String getImagePath(SongiType type) {
        return songiImagePath.get(type);
    }
}
