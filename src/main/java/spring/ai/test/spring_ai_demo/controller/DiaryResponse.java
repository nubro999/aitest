package spring.ai.test.spring_ai_demo.controller;

public class DiaryResponse {
    private String[] keywords;
    private Summary summary;
    private String question;
    private Feeling feeling;

    public static class Summary {
        private String morning;
        private String afternoon;
        private String night;

        // Getters and Setters
        public String getMorning() { return morning; }
        public void setMorning(String morning) { this.morning = morning; }
        public String getAfternoon() { return afternoon; }
        public void setAfternoon(String afternoon) { this.afternoon = afternoon; }
        public String getNight() { return night; }
        public void setNight(String night) { this.night = night; }
    }

    public static class Feeling {
        private String emotion;
        private String reason;

        // Getters and Setters
        public String getEmotion() { return emotion; }
        public void setEmotion(String emotion) { this.emotion = emotion; }
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
    }

    // Getters and Setters
    public String[] getKeywords() { return keywords; }
    public void setKeywords(String[] keywords) { this.keywords = keywords; }
    public Summary getSummary() { return summary; }
    public void setSummary(Summary summary) { this.summary = summary; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public Feeling getFeeling() { return feeling; }
    public void setFeeling(Feeling feeling) { this.feeling = feeling; }
}
