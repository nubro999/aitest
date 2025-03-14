package spring.ai.test.spring_ai_demo.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiaryService {

    @Autowired
    private ChatModel chatModel;

    private static final String SYSTEM_PROMPT = """
            Transform a diary entry into a structured output that includes keywords, a summary, 
            a related question, and a feeling with explanation. 
            if content is not a diary message to user "please input diary format"
            
            Every output that can be answered in a json format should be answered in json format 
            this is very important rule to keep
            
            The output should be structured in JSON format as follows:
            {
              "keywords": ["keyword1", "keyword2", "keyword3"],
              "summary": {
                "morning": "A brief summary of the morning.",
                "afternoon": "A brief summary of the afternoon.",
                "night": "A brief summary of the night."
              },
              "question": "A meaningful question related to the day's events or themes.",
              "feeling": {
                "emotion": "The primary emotion felt.",
                "reason": "Explanation for choosing this emotion."
              }
            }
            """;

    public String processDiary(String diaryText) {
        // 메시지 목록 생성
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage(SYSTEM_PROMPT));
        messages.add(new UserMessage(diaryText));

        // 프롬프트 생성 및 호출
        Prompt prompt = new Prompt(messages);
        List<Generation> generations = chatModel.call(prompt).getResults();

        // 첫 번째 생성 결과 반환
        if (!generations.isEmpty()) {
            return generations.get(0).getOutput().getText();
        }

        return "응답을 생성할 수 없습니다.";
    }
}
