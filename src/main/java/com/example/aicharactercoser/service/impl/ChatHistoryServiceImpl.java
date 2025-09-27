package com.example.aicharactercoser.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.aicharactercoser.constant.UserConstant;
import com.example.aicharactercoser.exception.ErrorCode;
import com.example.aicharactercoser.exception.ThrowUtils;
import com.example.aicharactercoser.model.dto.chatHistory.ChatHistoryQueryRequest;
import com.example.aicharactercoser.model.entity.App;
import com.example.aicharactercoser.model.entity.ChatHistory;
import com.example.aicharactercoser.model.entity.User;
import com.example.aicharactercoser.model.enums.ChatHistoryMessageTypeEnum;
import com.example.aicharactercoser.service.AppService;
import com.example.aicharactercoser.service.ChatHistoryService;
import com.example.aicharactercoser.mapper.ChatHistoryMapper;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
* @author ActiveknghtDM
* @description 针对表【chat_history(对话历史)】的数据库操作Service实现
* @createDate 2025-09-27 19:36:46
*/
@Slf4j
@Service
public class ChatHistoryServiceImpl extends ServiceImpl<ChatHistoryMapper, ChatHistory>
    implements ChatHistoryService{
    //这里使用lazy来防止循环依赖（也可以使用Setter注入代替构造函数）
    @Lazy
    @Autowired
    private AppService appService;

    @Override
    public int loadChatHistoryToMemory(Long appId, int maxCount,
                                       MessageWindowChatMemory messageWindowChatMemory){
        try{
            Page<ChatHistory> page = new Page<>(1, maxCount);
            // 执行分页查询
            IPage<ChatHistory> result = this.page(page,
                    new QueryWrapper<ChatHistory>().lambda()
                            .eq(ChatHistory::getAppId, appId)
                            .orderByDesc(ChatHistory::getCreateTime)
            );
            // 获取查询结果列表
            List<ChatHistory> chatHistoryList = result.getRecords();

            if(CollUtil.isEmpty(chatHistoryList)){
                return 0;
            }
            //反转列表，按时间正序
            Collections.reverse(chatHistoryList);
            int loadedCount=0;
            //清理历史缓存
            messageWindowChatMemory.clear();
            //加载缓存
            for(ChatHistory chatHistory:chatHistoryList){
                if(ChatHistoryMessageTypeEnum.USER.getValue().equals(chatHistory.getMessageType())){
                    messageWindowChatMemory.add(UserMessage.from(chatHistory.getMessage()));
                    loadedCount++;
                }else if(ChatHistoryMessageTypeEnum.AI.getValue().equals(chatHistory.getMessageType())){
                    messageWindowChatMemory.add(AiMessage.from(chatHistory.getMessage()));
                    loadedCount++;
                }
            }
            log.info("成功为appId {}加载了{}条历史记录",appId,loadedCount);
            return loadedCount;
        }catch(Exception e){
            log.error("加载历史对话失败，appId:{},错误:{}",appId,e.getMessage(),e);
            return 0;
        }
    }
    @Override
    public boolean addChatMessage(Long appId, String message, String messageType, Long userId) {
        // 基础校验
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        ThrowUtils.throwIf(StrUtil.isBlank(message), ErrorCode.PARAMS_ERROR, "消息内容不能为空");
        ThrowUtils.throwIf(StrUtil.isBlank(messageType), ErrorCode.PARAMS_ERROR, "消息类型不能为空");
        ThrowUtils.throwIf(userId == null || userId <= 0, ErrorCode.PARAMS_ERROR, "用户ID不能为空");
        // 验证消息类型是否有效
        ChatHistoryMessageTypeEnum messageTypeEnum = ChatHistoryMessageTypeEnum.getEnumByValue(messageType);
        ThrowUtils.throwIf(messageTypeEnum == null, ErrorCode.PARAMS_ERROR, "不支持的消息类型");
        // 插入数据库
        ChatHistory chatHistory =new ChatHistory();
        chatHistory.setAppId(appId);
        chatHistory.setMessage(message);
        chatHistory.setMessageType(messageTypeEnum.getValue());
        chatHistory.setUserId(userId);
        return this.save(chatHistory);
    }
    @Override
    public boolean deleteByAppId(Long appId) {
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        LambdaQueryWrapper<ChatHistory> queryWrapper = new QueryWrapper<ChatHistory>().lambda()
                .eq(ChatHistory::getAppId, appId);
        return this.remove(queryWrapper);
    }

    @Override
    public Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                                      LocalDateTime lastCreateTime,
                                                      User loginUser) {
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        ThrowUtils.throwIf(pageSize <= 0 || pageSize > 50, ErrorCode.PARAMS_ERROR, "页面大小必须在1-50之间");
        ThrowUtils.throwIf(loginUser == null, ErrorCode.NOT_LOGIN_ERROR);
        // 验证权限：只有应用创建者和管理员可以查看
        App app = appService.getById(appId);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR, "应用不存在");
        boolean isAdmin = UserConstant.ADMIN_ROLE.equals(loginUser.getUserRole());
        boolean isCreator = app.getUserId().equals(loginUser.getId());
        ThrowUtils.throwIf(!isAdmin && !isCreator, ErrorCode.NO_AUTH_ERROR, "无权查看该应用的对话历史");
        // 构建查询条件
        ChatHistoryQueryRequest queryRequest = new ChatHistoryQueryRequest();
        queryRequest.setAppId(appId);
        queryRequest.setLastCreateTime(lastCreateTime);
        LambdaQueryWrapper<ChatHistory> queryWrapper = this.getQueryWrapper(queryRequest);
        // 查询数据
        return this.page(Page.of(1, pageSize), queryWrapper);
    }


    /**
     * 获取查询包装类
     *
     * @param chatHistoryQueryRequest
     * @return
     */
    @Override
    public LambdaQueryWrapper<ChatHistory> getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest) {
        LambdaQueryWrapper<ChatHistory> queryWrapper = new QueryWrapper<ChatHistory>().lambda();
        if (chatHistoryQueryRequest == null) {
            return queryWrapper;
        }
        Long id = chatHistoryQueryRequest.getId();
        String message = chatHistoryQueryRequest.getMessage();
        String messageType = chatHistoryQueryRequest.getMessageType();
        Long appId = chatHistoryQueryRequest.getAppId();
        Long userId = chatHistoryQueryRequest.getUserId();
        LocalDateTime lastCreateTime = chatHistoryQueryRequest.getLastCreateTime();

        // 拼接查询条件
        queryWrapper.eq(id!=null,ChatHistory::getId, id)
                .like(StrUtil.isNotBlank(message),ChatHistory::getMessage, message)
                .eq(StrUtil.isNotBlank(messageType),ChatHistory::getMessageType, messageType)
                .eq(appId!=null,ChatHistory::getAppId, appId)
                .eq(userId!=null,ChatHistory::getUserId, userId);
        // 游标查询逻辑 - 只使用 createTime 作为游标
        if (lastCreateTime != null) {
            queryWrapper.lt(ChatHistory::getCreateTime, lastCreateTime);
        }

        return queryWrapper;
    }

}




