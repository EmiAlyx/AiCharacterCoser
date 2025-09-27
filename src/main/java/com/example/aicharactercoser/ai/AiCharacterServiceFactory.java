package com.example.aicharactercoser.ai;


import com.example.aicharactercoser.service.ChatHistoryService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import dev.langchain4j.community.store.memory.chat.redis.RedisChatMemoryStore;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * ai 服务创建工厂
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class AiCharacterServiceFactory {
    private final ChatModel chatModel;
    private final StreamingChatModel streamingChatModel;
    private final RedisChatMemoryStore redisChatMemoryStore;
    private final ChatHistoryService chatHistoryService;

    public AiCharacterService createAiCharacterService(Long appId) {

        log.info("创建新的AI服务实例，id：{}",appId);
        MessageWindowChatMemory chatMemory=MessageWindowChatMemory
                .builder()
                .id(appId)
                .chatMemoryStore(redisChatMemoryStore)
                .maxMessages(20)
                .build();
        //从数据库加载对话历史到记忆中。
        chatHistoryService.loadChatHistoryToMemory(appId,20,chatMemory);

        return AiServices.builder(AiCharacterService .class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                //根据id构建独立的会话记忆
                .chatMemory(chatMemory)
                .build();
    }
    /**
     * 根据appid获取服务（带缓存）
     * @param appId
     * @return
     */
    public AiCharacterService  getAiCharacterService (long appId){
        return serviceCache.get(appId,this::createAiCharacterService );
    }


    /**
     * AI服务实例缓存
     * 最大缓存1000个
     * 写入后30分钟过期
     * 访问后10分钟过期
     */
    private Cache<Long,AiCharacterService > serviceCache= Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofMinutes(30))
            .expireAfterAccess(Duration.ofMinutes(10))
            .removalListener((key, value, cause) ->{
                log.debug("Ai 服务实例被移除 ,appId {},原因：{}",key,cause);
            })
            .build();


}

