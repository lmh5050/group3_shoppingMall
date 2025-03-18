package org.example.shoppingmall.repository.User;

import org.apache.ibatis.annotations.Mapper;
import org.example.shoppingmall.dto.User.UserInfoDto;

import java.util.List;

@Mapper
public interface UserRepository {
    List<UserInfoDto> getTestData();


}
