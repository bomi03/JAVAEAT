package model;

import java.util.*;

public class Management {
    // 답변 ID(String)와 송이 타입(SongiType) 간의 매핑
    private final Map<String, SongiType> answerToQuestion;

    // 생성자: 답변-타입 매핑만 받음
    public Management(Map<String, SongiType> answerToQuestion) {
        this.answerToQuestion = answerToQuestion;
    }

    // 사용자 답변 리스트를 기반으로 가장 많이 선택된 송이 타입을 평가
    // 동점일 경우 랜덤으로 하나 선택
    public SongiType evaluate(List<String> userAnswers) {
        Map<SongiType, Integer> counts = new HashMap<>();

        for (String key : userAnswers) {
            SongiType type = answerToQuestion.get(key);
            if (type != null) {
                counts.put(type, counts.getOrDefault(type, 0) + 1);
            }
        }

        int maxCount = counts.values().stream().max(Integer::compareTo).orElse(0);

        List<SongiType> topTypes = new ArrayList<>();
        for (Map.Entry<SongiType, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == maxCount) {
                topTypes.add(entry.getKey());
            }
        }

        if (!topTypes.isEmpty()) {
            Random random = new Random();
            return topTypes.get(random.nextInt(topTypes.size()));
        }

        return null;
    }

    // 특정 송이 타입에 해당하는 이미지 경로를 반환 (enum 내부에서 경로 조합)
    public String getImagePath(SongiType type) {
        return type.getImagePath();
    }
} 
