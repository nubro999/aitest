package spring.ai.test.spring_ai_demo.service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
        // 시스템 메시지와 사용자 메시지 생성
        SystemMessage systemMessage = new SystemMessage(SYSTEM_PROMPT);
        UserMessage userMessage = new UserMessage(diaryText);

        // 메시지 목록 생성
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        // 최신 API에서는 prompt() 메서드를 사용하고 content()로 결과를 가져옴
        String response = chatModel.call();

        return response;
    }
}


