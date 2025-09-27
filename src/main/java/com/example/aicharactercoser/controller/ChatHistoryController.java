package com.example.aicharactercoser.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.aicharactercoser.annotation.AuthCheck;
import com.example.aicharactercoser.common.BaseResponse;
import com.example.aicharactercoser.common.ResultUtils;
import com.example.aicharactercoser.constant.UserConstant;
import com.example.aicharactercoser.exception.ErrorCode;
import com.example.aicharactercoser.exception.ThrowUtils;
import com.example.aicharactercoser.model.dto.chatHistory.ChatHistoryQueryRequest;
import com.example.aicharactercoser.model.entity.App;
import com.example.aicharactercoser.model.entity.ChatHistory;
import com.example.aicharactercoser.model.entity.User;
import com.example.aicharactercoser.service.ChatHistoryService;
import com.example.aicharactercoser.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 对话历史 控制层。
 *
 * @author EmiAlyx
 */
@RestController
@RequestMapping("/chatHistory")
@RequiredArgsConstructor
public class ChatHistoryController {

    private final ChatHistoryService chatHistoryService;
    private final UserService userService;

    /**
     * 对话历史查询
     * @param appId 应用id
     * @param pageSize 页面大小
     * @param lastCreateTime 游标
     * @param request
     * @return
     */
    @Operation(summary = "对话历史查询（游标）")
    @GetMapping("/app/{appId}")
    public BaseResponse<Page<ChatHistory>> getAppChatHistory(@PathVariable Long appId,
                                                             @RequestParam(defaultValue = "10") int pageSize,
                                                             @RequestParam(required = false) LocalDateTime lastCreateTime,
                                                             HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        Page<ChatHistory> result=chatHistoryService.listAppChatHistoryByPage(appId,pageSize,lastCreateTime,loginUser);
        return ResultUtils.success(result);
    }
    /**
     * 管理员分页查询所有对话历史
     *
     * @param chatHistoryQueryRequest 查询请求
     * @return 对话历史分页
     */
    @Operation(summary = "分页查询所有对话历史（管理员）")
    @PostMapping("/admin/list/page/vo")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<ChatHistory>> listAllChatHistoryByPageForAdmin(@RequestBody ChatHistoryQueryRequest chatHistoryQueryRequest) {
        ThrowUtils.throwIf(chatHistoryQueryRequest == null, ErrorCode.PARAMS_ERROR);
        long pageNum = chatHistoryQueryRequest.getPageNum();
        long pageSize = chatHistoryQueryRequest.getPageSize();

        Page<ChatHistory> page=Page.of(pageNum, pageSize);
        String sortField = chatHistoryQueryRequest.getSortField();
        String sortOrder = chatHistoryQueryRequest.getSortOrder();
        OrderItem orderItem = new OrderItem();
        if(StrUtil.isNotEmpty(sortField)){
            orderItem.setColumn(sortField);
            orderItem.setAsc("ascend".equals(sortOrder));
            //默认排序
        }else{
            orderItem.setColumn("createTime");
            orderItem.setAsc(true);
        }
        // 查询数据
        page.addOrder(orderItem);
        LambdaQueryWrapper<ChatHistory> queryWrapper = chatHistoryService.getQueryWrapper(chatHistoryQueryRequest);
        Page<ChatHistory> result = chatHistoryService.page(page, queryWrapper);
        return ResultUtils.success(result);
    }
    /**
     * 保存对话历史。
     *
     * @param chatHistory 对话历史
     * @return {@code true} 保存成功，{@code false} 保存失败
     */
    @Operation(summary = "保存对话历史")
    @PostMapping("save")
    public boolean save(@RequestBody ChatHistory chatHistory) {
        return chatHistoryService.save(chatHistory);
    }

    /**
     * 根据主键删除对话历史。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @Operation(summary = "根据主键删除对话历史（管理员）")
    @DeleteMapping("remove/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public boolean remove(@PathVariable Long id) {
        return chatHistoryService.removeById(id);
    }

    /**
     * 根据主键更新对话历史。
     *
     * @param chatHistory 对话历史
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @Operation(summary = "根据主键更新对话历史（管理员）")
    @PutMapping("update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public boolean update(@RequestBody ChatHistory chatHistory) {
        return chatHistoryService.updateById(chatHistory);
    }

    /**
     * 查询所有对话历史。
     *
     * @return 所有数据
     */
    @Operation(summary = "查询所有对话历史（管理员）")
    @GetMapping("list")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public List<ChatHistory> list() {
        return chatHistoryService.list();
    }

    /**
     * 根据主键获取对话历史。
     *
     * @param id 对话历史主键
     * @return 对话历史详情
     */
    @Operation(summary = "根据主键获取对话历史（管理员）")
    @GetMapping("getInfo/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public ChatHistory getInfo(@PathVariable Long id) {
        return chatHistoryService.getById(id);
    }

    /**
     * 分页查询对话历史。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询对话历史（管理员）")
    @GetMapping("page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Page<ChatHistory> page(Page<ChatHistory> page) {
        return chatHistoryService.page(page);
    }

}
