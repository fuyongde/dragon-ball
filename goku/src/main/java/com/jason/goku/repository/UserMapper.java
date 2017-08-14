package com.jason.goku.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.jason.goku.entity.User;

@Mapper
public interface UserMapper {

    int insertSelective(@Param("pojo") User pojo);

    int update(@Param("pojo") User pojo);
}
