package com.example.aicharactercoser.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.aicharactercoser.model.dto.app.AppQueryRequest;
import com.example.aicharactercoser.model.entity.App;
import com.example.aicharactercoser.model.entity.User;
import com.example.aicharactercoser.model.Vo.AppVO;

import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 应用 服务层。
 *
 * @author EmiAlyx
 */
public interface AppService extends IService<App> {
    AppVO getAppVO(App app);

    //
    Flux<String> chatCharacter(Long appId, String message, User loginUser);

    LambdaQueryWrapper<App> getQueryWrapper(AppQueryRequest appQueryRequest);

    List<AppVO> getAppVOList(List<App> appList);


}
