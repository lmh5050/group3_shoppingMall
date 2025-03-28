package org.example.shoppingmall.dto.User;

import lombok.Data;
import java.util.Date;

@Data
public class UserInfoDto {
        private String customerFeature;
        private String name;
        private String nickname;
        private String sex;
        private String phoneNumber;
        private String email;
        private String birth;
        private String job;
        private String weight;
        private String height;
        private String profileImg;
        private String personalColor;
        private String outDate;
        private String outComment;
        private String note;
        private String registerRoot;
        private String recommendId;
        private String recentAccessIp;
        private String pwModifyDate;
        private String pw;
        private String providerId;
        private String allocatedAccountNumber;
        private String bank;
        private String customerId;
        private String address;
        private String detailAddress;

        // Getter, Setter 생략

        // 빈 문자열을 null로 변환하는 메서드
        public void sanitize() {
                if (this.customerFeature != null && this.customerFeature.isEmpty()) {
                        this.customerFeature = null;
                }
                if (this.name != null && this.name.isEmpty()) {
                        this.name = null;
                }
                if (this.nickname != null && this.nickname.isEmpty()) {
                        this.nickname = null;
                }
                if (this.sex != null && this.sex.isEmpty()) {
                        this.sex = null;
                }
                if (this.phoneNumber != null && this.phoneNumber.isEmpty()) {
                        this.phoneNumber = null;
                }
                if (this.email != null && this.email.isEmpty()) {
                        this.email = null;
                }
                if (this.birth != null && this.birth.isEmpty()) {
                        this.birth = null;
                }
                if (this.job != null && this.job.isEmpty()) {
                        this.job = null;
                }
                if (this.weight != null && this.weight.isEmpty()) {
                        this.weight = null;
                }
                if (this.height != null && this.height.isEmpty()) {
                        this.height = null;
                }
                if (this.profileImg != null && this.profileImg.isEmpty()) {
                        this.profileImg = null;
                }
                if (this.personalColor != null && this.personalColor.isEmpty()) {
                        this.personalColor = null;
                }
                if (this.outDate != null && this.outDate.isEmpty()) {
                        this.outDate = null;
                }
                if (this.outComment != null && this.outComment.isEmpty()) {
                        this.outComment = null;
                }
                if (this.note != null && this.note.isEmpty()) {
                        this.note = null;
                }
                if (this.registerRoot != null && this.registerRoot.isEmpty()) {
                        this.registerRoot = null;
                }
                if (this.recommendId != null && this.recommendId.isEmpty()) {
                        this.recommendId = null;
                }
                if (this.recentAccessIp != null && this.recentAccessIp.isEmpty()) {
                        this.recentAccessIp = null;
                }
                if (this.pwModifyDate != null && this.pwModifyDate.isEmpty()) {
                        this.pwModifyDate = null;
                }
                if (this.pw != null && this.pw.isEmpty()) {
                        this.pw = null;
                }
                if (this.providerId != null && this.providerId.isEmpty()) {
                        this.providerId = null;
                }
                if (this.allocatedAccountNumber != null && this.allocatedAccountNumber.isEmpty()) {
                        this.allocatedAccountNumber = null;
                }
                if (this.bank != null && this.bank.isEmpty()) {
                        this.bank = null;
                }
        }
}

