package com.jason.goku.repository;

import com.jason.goku.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    int insertSelective(@Param("pojo") User pojo);

    int update(@Param("pojo") User pojo);

    User findByUsername(String username);
}
