package org.example.shoppingmall.repository;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.UserInfoDto;

import java.util.List;

@Mapper
public interface UserRepository {
    List<UserInfoDto> getTestData();


}
