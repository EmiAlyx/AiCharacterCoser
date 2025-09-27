package com.example.aicharactercoser.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.aicharactercoser.model.dto.chatHistory.ChatHistoryQueryRequest;
import com.example.aicharactercoser.model.entity.ChatHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aicharactercoser.model.entity.User;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;

import java.time.LocalDateTime;

/**
* @author ActiveknghtDM
* @description 针对表【chat_history(对话历史)】的数据库操作Service
* @createDate 2025-09-27 19:36:46
*/
public interface ChatHistoryService extends IService<ChatHistory> {

    int loadChatHistoryToMemory(Long appId, int maxCount,
                                MessageWindowChatMemory messageWindowChatMemory);

    boolean addChatMessage(Long appId, String message, String messageType, Long userId);

    boolean deleteByAppId(Long appId);

    Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                               LocalDateTime lastCreateTime,
                                               User loginUser);



    LambdaQueryWrapper<ChatHistory> getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);
}
