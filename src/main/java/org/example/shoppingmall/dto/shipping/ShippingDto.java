package org.example.shoppingmall.dto.shipping;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;


@Data
public class ShippingDto {
        private String shippingCompanyId; // `shipping_company_id`
        private String employeeId;        // `employee_id`
        private String companyName;       // `company_name`
        private String managerName;       // `manager_name`
        private String phoneNumber;       // `phone_number`
        private String email;             // `email`
        private String companyPhone;      // `company_phone`
        private String location;          // `location`
        private String note;              // `note`
        private Date managerRegisterDatetime; // `manager_register_datetime`
        private Date employeeRegisterDatetime; // `employee_register_datetime`
        private Date updatedAt;  // `updated_at`
        private int shippingCompanyNo;

        public String getShippingCompanyId() {
                return shippingCompanyId;
        }

        public void setShippingCompanyId(String shippingCompanyId) {
                this.shippingCompanyId = shippingCompanyId;
        }

        public String getEmployeeId() {
                return employeeId;
        }

        public void setEmployeeId(String employeeId) {
                this.employeeId = employeeId;
        }

        public String getCompanyName() {
                return companyName;
        }

        public void setCompanyName(String companyName) {
                this.companyName = companyName;
        }

        public String getManagerName() {
                return managerName;
        }

        public void setManagerName(String managerName) {
                this.managerName = managerName;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getCompanyPhone() {
                return companyPhone;
        }

        public void setCompanyPhone(String companyPhone) {
                this.companyPhone = companyPhone;
        }

        public String getLocation() {
                return location;
        }

        public void setLocation(String location) {
                this.location = location;
        }

        public String getNote() {
                return note;
        }

        public void setNote(String note) {
                this.note = note;
        }

        public Date getManagerRegisterDatetime() {
                return managerRegisterDatetime;
        }

        public void setManagerRegisterDatetime(Date managerRegisterDatetime) {
                this.managerRegisterDatetime = managerRegisterDatetime;
        }

        public Date getEmployeeRegisterDatetime() {
                return employeeRegisterDatetime;
        }

        public void setEmployeeRegisterDatetime(Date employeeRegisterDatetime) {
                this.employeeRegisterDatetime = employeeRegisterDatetime;
        }

        public Date getUpdatedAt() {
                return updatedAt;
        }

        public void setUpdatedAt(Date updatedAt) {
                this.updatedAt = updatedAt;
        }

        public int getShippingCompanyNo() {
                return shippingCompanyNo;
        }

        public void setShippingCompanyNo(int shippingCompanyNo) {
                this.shippingCompanyNo = shippingCompanyNo;
        }
}



