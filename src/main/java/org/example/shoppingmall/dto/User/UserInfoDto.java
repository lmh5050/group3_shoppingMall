package org.example.shoppingmall.dto.User;

import lombok.Data;
import java.util.Date;

@Data
public class UserInfoDto {

        private String customerId;        // 고객 ID
        private String customerFeature;   // 고객 특징
        private String name;              // 이름
        private String nickname;          // 별명
        private String sex;               // 성별
        private String phoneNumber;       // 전화번호
        private String email;             // 이메일
        private Date birth;               // 생년월일
        private String job;               // 직업
        private String profileImg;        // 프로필 이미지
        private String personalColor;     // 개인 색상
        private String bank;              // 은행
        private String allocatedAccountNumber; // 배정된 계좌번호
        private String providerId;        // 소셜 로그인 제공자 ID
        private String registerRoot;      // 등록 경로
        private String recommendId;       // 추천인 ID
        private String recentAccessIp;    // 최근 접속 IP
        private Date pwModifyDate;        // 비밀번호 수정일
        private String pw;                // 비밀번호
        private Date outDate;             // 탈퇴일
        private String outComment;        // 탈퇴 사유
        private String note;              // 추가 메모
        private boolean deleteFlag;       // 삭제 플래그
        private boolean activeFlag;       // 활성화 플래그
        private Date createdDatetime;     // 생성 일시
        private Date connectAt;           // 접속 일시
        private String address;
        private String detailAddress;
    }

