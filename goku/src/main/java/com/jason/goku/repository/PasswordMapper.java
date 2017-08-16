package com.jason.goku.repository;

import com.jason.goku.entity.Password;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PasswordMapper {

    int insertSelective(@Param("pojo") Password pojo);

    int update(@Param("pojo") Password pojo);

    Password findByUserId(@Param("userId") Integer userId);
}
