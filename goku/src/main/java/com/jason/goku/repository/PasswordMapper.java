package com.jason.goku.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.jason.goku.entity.Password;

@Mapper
public interface PasswordMapper {

    int insertSelective(@Param("pojo") Password pojo);

    int update(@Param("pojo") Password pojo);
}
