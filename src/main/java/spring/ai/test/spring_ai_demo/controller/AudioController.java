package spring.ai.test.spring_ai_demo.controller;

import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AudioController {

    @Autowired
    private OpenAiAudioTranscriptionModel transcriptionModel;

    @PostMapping("/transcribe")
    public String transcribeAudio(@RequestParam("file") MultipartFile file) throws IOException {
        // 파일을 Resource로 변환
        var resource = file.getResource();

        // 기본 옵션으로 변환
        return transcriptionModel.call(resource);
    }
}