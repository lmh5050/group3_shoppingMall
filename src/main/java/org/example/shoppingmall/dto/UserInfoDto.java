package org.example.shoppingmall.dto;

import lombok.Data;
import java.util.Date;

@Data
public class UserInfoDto {

        private String customerId;  // 고객 아이디
        private String grade;  // 등급 (일반, VIP, VVIP)
        private String password;  // 패스워드
        private String profileImage;  // 프로필 사진
        private String name;  // 이름
        private String nickname;  // 유저 별명
        private String email;  // 이메일
        private String phoneNumber;  // 전화번호
        private Date birthDate;  // 생년월일
        private String gender;  // 성별 (남, 여)
        private Integer height;  // 키
        private Integer weight;  // 몸무게
        private String personalColor;  // 퍼스널 컬러
        private String referrerId;  // 추천인 아이디
        private String registrationPath;  // 가입 경로
        private String job;  // 직업
        private Boolean isSocialLogin;  // 소셜 로그인 여부
        private String socialLoginId;  // 소셜 로그인 아이디
        private Date registrationDate;  // 가입일
        private Date lastLoginDate;  // 최근 접속 날짜
        private Date passwordUpdateDate;  // 비밀번호 수정일
        private Boolean isActive;  // 활성여부 (0: 비활성, 1: 활성)
        private Boolean isWithdrawn;  // 탈퇴여부 (0: 사용 중, 1: 탈퇴)
        private String withdrawalReason;  // 탈퇴 사유
        private Date withdrawalDate;  // 탈퇴날짜
        private String note;  // 특이사항
        private String remark;  // 비고
        private String bankAccountNumber;  // 배정 무통장 번호
        private String bankName;  // 은행
        private String lastLoginIp;  // 최근 접속 아이피
        private String serviceProvider;  // 제공 업체 명
    }

