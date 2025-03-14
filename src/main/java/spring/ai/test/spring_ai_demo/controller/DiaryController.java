package spring.ai.test.spring_ai_demo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.ai.test.spring_ai_demo.service.DiaryService;

import java.io.IOException;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    @Autowired
    private AudioController audioController;

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/process-text")
    public ResponseEntity<DiaryResponse> processDiaryText(@RequestBody TextRequest textRequest) throws IOException {
        // 텍스트를 AI에 전달하여 처리
        String aiResponse = diaryService.processDiary(textRequest.getText());

        // JSON 응답 처리 및 DiaryResponse 객체 반환
        return ResponseEntity.ok(parseAiResponse(aiResponse));
    }

    @PostMapping("/process")
    public ResponseEntity<DiaryResponse> processDiaryAudio(@RequestParam("file") MultipartFile file) throws IOException {
        // 1. 음성 파일을 텍스트로 변환
        String transcribedText = audioController.transcribeAudio(file);

        // 2. 변환된 텍스트를 AI에 전달하여 처리
        String aiResponse = diaryService.processDiary(transcribedText);

        // JSON 응답 처리 및 DiaryResponse 객체 반환
        return ResponseEntity.ok(parseAiResponse(aiResponse));
    }

    /**
     * AI 응답을 파싱하여 DiaryResponse 객체로 변환하는 메서드
     */
    private DiaryResponse parseAiResponse(String aiResponse) throws IOException {
        // JSON 응답 파싱
        JsonNode jsonResponse = objectMapper.readTree(aiResponse);

        // 응답 객체 생성
        DiaryResponse response = new DiaryResponse();

        // 키워드 추출
        JsonNode keywords = jsonResponse.get("keywords");
        if (keywords != null && keywords.isArray()) {
            String[] keywordArray = new String[keywords.size()];
            for (int i = 0; i < keywords.size(); i++) {
                keywordArray[i] = keywords.get(i).asText();
            }
            response.setKeywords(keywordArray);
        }

        // 요약 추출
        JsonNode summary = jsonResponse.get("summary");
        if (summary != null) {
            DiaryResponse.Summary summaryObj = new DiaryResponse.Summary();
            summaryObj.setMorning(summary.get("morning").asText());
            summaryObj.setAfternoon(summary.get("afternoon").asText());
            summaryObj.setNight(summary.get("night").asText());
            response.setSummary(summaryObj);
        }

        // 질문 추출
        if (jsonResponse.has("question")) {
            response.setQuestion(jsonResponse.get("question").asText());
        }

        // 감정 추출
        JsonNode feeling = jsonResponse.get("feeling");
        if (feeling != null) {
            DiaryResponse.Feeling feelingObj = new DiaryResponse.Feeling();
            feelingObj.setEmotion(feeling.get("emotion").asText());
            feelingObj.setReason(feeling.get("reason").asText());
            response.setFeeling(feelingObj);
        }

        return response;
    }
}


