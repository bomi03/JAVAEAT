package model;

import java.util.HashMap;
import java.util.Map;

public class AnswerMapFactory {

    public static Map<String, SongiType> createAnswerMap() {
        Map<String, SongiType> map = new HashMap<>();

        map.put("Q1A", SongiType.똑송이);
        map.put("Q1B", SongiType.샘송이);
        map.put("Q1C", SongiType.평화송이);
        map.put("Q1D", SongiType.저요송이);

        map.put("Q2A", SongiType.저요송이);
        map.put("Q2B", SongiType.꾸꾸송이);
        map.put("Q2C", SongiType.논리송이);
        map.put("Q2D", SongiType.침착송이);

        map.put("Q3A", SongiType.논리송이);
        map.put("Q3B", SongiType.샘송이);
        map.put("Q3C", SongiType.평화송이);
        map.put("Q3D", SongiType.침착송이);

        map.put("Q4A", SongiType.저요송이);
        map.put("Q4B", SongiType.똑송이);
        map.put("Q4C", SongiType.꾸꾸송이);
        map.put("Q4D", SongiType.논리송이);

        map.put("Q5A", SongiType.평화송이);
        map.put("Q5B", SongiType.저요송이);
        map.put("Q5C", SongiType.피티송이);
        map.put("Q5D", SongiType.똑송이);

        map.put("Q6A", SongiType.피티송이);
        map.put("Q6B", SongiType.샘송이);
        map.put("Q6C", SongiType.침착송이);
        map.put("Q6D", SongiType.꾸꾸송이);

        map.put("Q7A", SongiType.피티송이);
        map.put("Q7B", SongiType.꾸꾸송이);
        map.put("Q7C", SongiType.평화송이);
        map.put("Q7D", SongiType.샘송이);

        map.put("Q8A", SongiType.저요송이);
        map.put("Q8B", SongiType.샘송이);
        map.put("Q8C", SongiType.논리송이);
        map.put("Q8D", SongiType.피티송이);

        map.put("Q9A", SongiType.똑송이);
        map.put("Q9B", SongiType.피티송이);
        map.put("Q9C", SongiType.침착송이);
        map.put("Q9D", SongiType.평화송이);

        map.put("Q10A", SongiType.똑송이);
        map.put("Q10B", SongiType.논리송이);
        map.put("Q10C", SongiType.침착송이);
        map.put("Q10D", SongiType.꾸꾸송이);

        return map;
    }

    public static Map<SongiType, String> createImageMap() {
        Map<SongiType, String> imageMap = new HashMap<>();

        for (SongiType type : SongiType.values()) {
            imageMap.put(type, type.getImagePath());
        }

        return imageMap;
    }
} 
