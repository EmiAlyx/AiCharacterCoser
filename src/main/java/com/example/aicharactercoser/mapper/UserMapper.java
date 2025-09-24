package com.example.aicharactercoser.mapper;

import com.example.aicharactercoser.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ActiveknghtDM
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2025-09-24 00:29:38
* @Entity com.example.aicharactercoser.model.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




