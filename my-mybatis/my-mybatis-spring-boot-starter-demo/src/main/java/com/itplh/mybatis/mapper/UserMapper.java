package com.itplh.mybatis.mapper;

import com.itplh.mybatis.domain.User;

import java.util.List;

public interface UserMapper {

    User selectById(Integer id) throws Exception;

    List<User> selectAll();

}
