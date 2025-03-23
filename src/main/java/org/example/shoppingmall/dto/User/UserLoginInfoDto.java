package org.example.shoppingmall.dto.User;

import lombok.Data;

@Data
public class UserLoginInfoDto {
    private String customerId;  // 고객 아이디
    private String pw;  // 비밀번호
    private boolean status;
}
