package com.example.aicharactercoser.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 应用表
 * @TableName app
 */
@Data
@TableName(value ="app")

public class App {


    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 应用名称
     */

    private String appName;

    /**
     * 应用封面
     */
    private String cover;

    /**
     * 应用初始化的 prompt
     */

    private String initPrompt;

    /**
     * 代码生成类型（枚举）
     */

    private String cosType;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 创建用户id
     */

    private Long userId;

    /**
     * 编辑时间
     */

    private LocalDateTime editTime;

    /**
     * 创建时间
     */

    private LocalDateTime createTime;

    /**
     * 更新时间
     */

    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

}
