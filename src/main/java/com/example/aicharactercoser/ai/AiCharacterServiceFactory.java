package com.example.aicharactercoser.ai;


import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ai 服务创建工厂
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class AiCharacterServiceFactory {
    private final ChatModel chatModel;
    private final StreamingChatModel streamingChatModel;

    @Bean
    public AiCharacterService createAiCharacterService(){

        return AiServices.builder(AiCharacterService.class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                .build();
    }

}

