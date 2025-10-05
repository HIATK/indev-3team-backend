package _3team.indev3teambackend.Llama.service;

import _3team.indev3teambackend.ChatMessage.dto.ChatMessagesDto;
import _3team.indev3teambackend.ChatMessage.entity.ChatMessagesEntity;
import _3team.indev3teambackend.ChatMessage.repository.ChatMessagesRepository;
import _3team.indev3teambackend.ChatRoom.entity.ChatRoomEntity;
import _3team.indev3teambackend.users.entity.User;
import _3team.indev3teambackend.users.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode; // 💡 JSON 파싱용
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class AILlamaService {

    private final ChatMessagesRepository chatMessagesRepository;
    private final UserRepository userRepository;
    private final WebClient webClient; // 💡 Ollama API 통신을 위한 WebClient

    // 💡 application.properties 또는 yml에 설정
    @Value("${llama.api.url:http://localhost:11434/api/generate}")
    private String llamaApiUrl;

    @Value("${llama.model.name:llama3.1:8b}")
    private String modelName;

    // 💡 Llama Bot ID (UserService에서 생성한 ID와 일치해야 함)
    private static final Long LLAMA_BOT_ID = 1L;

    // 💡 Llama에게 질문하고 응답을 받아 DB에 저장하는 핵심 메서드
    @Transactional
    public void getAndSaveLlamaResponse(ChatMessagesDto userMessageDto, ChatRoomEntity chatRoom) {

        String userContent = userMessageDto.getContent();

        // 1. Llama 모델에게 질의
        String llamaResponseText = callLlamaApi(userContent);

        // 2. Llama Bot 사용자 조회 (ID=1)
        User llamaBotUser = userRepository.findById(LLAMA_BOT_ID)
                .orElseThrow(() -> new IllegalStateException("Llama Bot ID " + LLAMA_BOT_ID + "를 찾을 수 없습니다."));

        // 3. 응답 메시지 Entity 생성
        ChatMessagesEntity responseEntity = new ChatMessagesEntity();
        responseEntity.setChatRoom(chatRoom); // 사용자 메시지와 동일한 채팅방
        responseEntity.setUser(llamaBotUser);  // Llama Bot이 보낸 메시지
        responseEntity.setContent(llamaResponseText);
        responseEntity.setCreateAt(LocalDateTime.now());

        // 4. 응답 메시지 DB 저장
        chatMessagesRepository.save(responseEntity);
    }

    // 💡 Ollama API 호출 로직
    private String callLlamaApi(String prompt) {
        try {
            // Ollama Generate API 요청 Body 구성
            String jsonRequest = String.format("""
                {
                    "model": "%s",
                    "prompt": "%s",
                    "stream": false
                }
                """, modelName, prompt.replace("\"", "\\\"")); // prompt 이스케이프 처리

            // WebClient를 이용한 동기(블로킹) 호출
            JsonNode response = webClient.post()
                    .uri(llamaApiUrl)
                    .body(BodyInserters.fromValue(jsonRequest))
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block(); // 동기적으로 응답 대기

            if (response != null && response.has("response")) {
                return response.get("response").asText();
            } else {
                return "Llama API에서 유효한 응답을 받지 못했습니다. 요청: " + prompt;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Llama API 통신 오류: " + e.getMessage();
        }
    }
}