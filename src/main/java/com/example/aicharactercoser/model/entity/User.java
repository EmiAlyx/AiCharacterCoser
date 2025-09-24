package com.example.aicharactercoser.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    /**
     * id
     */
    //雪花算法
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账户
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatarUrl;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 编辑时间/手动修改
     */
    private Date editTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 0是未删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 用户角色 user/admin
     */
    private String userRole;
}