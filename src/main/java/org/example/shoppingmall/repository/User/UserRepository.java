package org.example.shoppingmall.repository.User;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserRepository {
    String getCustomerId(String customerId);
}
