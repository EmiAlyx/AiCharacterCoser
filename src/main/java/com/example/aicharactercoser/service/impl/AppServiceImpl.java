package com.example.aicharactercoser.service.impl;

import cn.hutool.core.collection.CollUtil;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.example.aicharactercoser.ai.AiCharacterServiceFacade;
import com.example.aicharactercoser.exception.BusinessException;
import com.example.aicharactercoser.exception.ErrorCode;
import com.example.aicharactercoser.exception.ThrowUtils;
import com.example.aicharactercoser.mapper.AppMapper;
import com.example.aicharactercoser.model.dto.app.AppQueryRequest;
import com.example.aicharactercoser.model.entity.App;
import com.example.aicharactercoser.model.entity.User;

import com.example.aicharactercoser.model.enums.AiCosTypeEnum;
import com.example.aicharactercoser.model.Vo.AppVO;
import com.example.aicharactercoser.model.Vo.UserVO;
import com.example.aicharactercoser.service.AppService;

import com.example.aicharactercoser.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 应用 服务层实现。
 *
 * @author EmiAlyx
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    private final UserService userService;
    private final AiCharacterServiceFacade  aiCharacterServiceFacade;



    @Override
    public AppVO getAppVO(App app) {
        if (app == null) {
            return null;
        }
        AppVO appVO = new AppVO();
        BeanUtils.copyProperties(app, appVO);
        //关联用户信息
        Long userId = app.getUserId();
        if (userId != null) {
            User user = userService.getById(userId);
            UserVO userVO = userService.getUserVO(user);
            appVO.setUser(userVO);
        }
        return appVO;
    }

    @Override
    public List<AppVO> getAppVOList(List<App> appList) {
        if (CollUtil.isEmpty(appList)) {
            return new ArrayList<>();
        }
        // 批量获取用户信息，避免 N+1 查询问题
        Set<Long> userIds = appList.stream()
                .map(App::getUserId)
                .collect(Collectors.toSet());
        Map<Long, UserVO> userVOMap = userService.listByIds(userIds).stream()
                .collect(Collectors.toMap(User::getId, userService::getUserVO));
        return appList.stream().map(app -> {
            AppVO appVO = getAppVO(app);
            UserVO userVO = userVOMap.get(app.getUserId());
            appVO.setUser(userVO);
            return appVO;
        }).collect(Collectors.toList());
    }
    //
    @Override
    public Flux<String> chatCharacter(Long appId, String message, User loginUser) {
        //1.参数校验
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "appId错误");
        ThrowUtils.throwIf(StrUtil.isBlank(message), ErrorCode.PARAMS_ERROR, "提示词为空");
        //2.查询应用信息
        App app = this.getById(appId);
        ThrowUtils.throwIf(app == null, ErrorCode.PARAMS_ERROR, "应用不存在");
        //3.验证用户是否有权访问该应用
        if (!app.getUserId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        //4.获得应用类型
        String aiCos = app.getCosType();
        AiCosTypeEnum aiCosTypeEnum = AiCosTypeEnum.getEnumByValue(aiCos);
        ThrowUtils.throwIf(aiCosTypeEnum == null, ErrorCode.SYSTEM_ERROR, "不支持的应用类型");
        //5.调用ai生成代码
        Flux<String> stringFlux = aiCharacterServiceFacade.generateChatStream(message, aiCosTypeEnum);
        //6.收集ai响应内容并且添加到对话记录
        StringBuilder stringBuilder = new StringBuilder();
        return stringFlux.map(chunk ->
        {
            //收集响应信息
            stringBuilder.append(chunk);
            return chunk;
        }).doOnComplete(() -> {
            //
            String aiResult = stringBuilder.toString();
            if (StrUtil.isNotBlank(aiResult)) {
                log.info("生成结果为：{}",aiResult);
            }
        }).doOnError(error -> {
            String aiResult ="AI回复失败"+ error.toString();
            log.error(aiResult);
        });

    }



    @Override
    public LambdaQueryWrapper<App> getQueryWrapper(AppQueryRequest appQueryRequest) {
        if (appQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = appQueryRequest.getId();
        String appName = appQueryRequest.getAppName();
        String cover = appQueryRequest.getCover();
        String initPrompt = appQueryRequest.getInitPrompt();
        String cosType = appQueryRequest.getCosType();
        Integer priority = appQueryRequest.getPriority();
        Long userId = appQueryRequest.getUserId();

        return new LambdaQueryWrapper<App>()
                .eq(id!=null,App::getId,id)
                .like(StrUtil.isNotBlank(appName),App::getAppName, appName)
                .like(StrUtil.isNotBlank(cover),App::getCover, cover)
                .like(StrUtil.isNotBlank(initPrompt),App::getInitPrompt,initPrompt)
                .eq(StrUtil.isNotBlank(cosType),App::getCosType ,cosType)
                .eq(priority!=null,App::getPriority, priority)
                .eq(userId!=null,App::getUserId, userId);
    }

}
