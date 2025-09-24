package com.example.aicharactercoser.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.aicharactercoser.model.Vo.LoginUserVO;
import com.example.aicharactercoser.model.Vo.UserVO;
import com.example.aicharactercoser.model.dto.user.UserQueryRequest;
import com.example.aicharactercoser.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author ActiveknghtDM
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-09-24 00:29:38
*/
public interface UserService extends IService<User> {
    long userRegister(String userAccount, String userPassword,String checkPassword);

    String getEncryptPassword(String userPassword);

    LoginUserVO getLoginUserVO(User user);

    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    User getLoginUser(HttpServletRequest request);

    UserVO getUserVO(User user);

    List<UserVO> getUserVOList(List<User> userList);

    boolean userLogout(HttpServletRequest request);

    LambdaQueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}
