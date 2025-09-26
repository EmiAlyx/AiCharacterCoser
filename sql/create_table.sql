-- 创建数据库
create database ai_character;

use ai_character;
-- 用户
create table user
(
    id            bigint auto_increment comment 'id'
        primary key,
    userAccount   varchar(256)                           not null comment '账户',
    userPassword  varchar(512)                           not null comment '密码',
    userName      varchar(256)                           null comment '用户昵称',
    userAvatarUrl varchar(1024)                          null comment '用户头像',
    userProfile   varchar(512)                           null comment '用户简介',
    editTime      datetime     default CURRENT_TIMESTAMP not null comment '编辑时间/手动修改',
    createTime    datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint      default 0                 not null comment '是否删除 0是未删除',
    userRole      varchar(256) default 'user'            not null comment '用户角色 user/admin',
    INDEX idx_userName (userName),
    UNIQUE KEY uk_userAccount (userAccount)
)
    comment '用户表' collate = utf8mb4_unicode_ci;

use ai_character;
-- 应用表
create table app
(
    id         bigint auto_increment comment 'id' primary key,
    appName    varchar(256)                       null comment '应用名称',
    cover      varchar(512)                       null comment '应用封面',
    initPrompt text                               null comment '应用初始化的 prompt',
    cosType    varchar(64)                        null comment 'ai角色扮演类型（枚举）',
    priority   int      default 0                 not null comment '优先级',
    userId     bigint                             not null comment '创建用户id',
    editTime   datetime default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    INDEX idx_appName (appName),
    INDEX idx_userId (userId)
) comment '应用' collate = utf8mb4_unicode_ci;
