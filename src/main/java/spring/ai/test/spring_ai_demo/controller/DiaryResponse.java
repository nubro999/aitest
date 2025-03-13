package spring.ai.test.spring_ai_demo.controller;

import lombok.Data;

@Data
public class DiaryResponse {
    private String[] keywords;
    private Summary summary;
    private String question;
    private Feeling feeling;

    @Data
    public static class Summary {
        private String morning;
        private String afternoon;
        private String night;
    }

    @Data
    public static class Feeling {
        private String emotion;
        private String reason;
    }
}
