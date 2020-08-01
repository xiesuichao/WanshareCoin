package com.wanshare.crush.account.model.bean;

import java.io.Serializable;

/**
 * Created by Richard on 2018/9/12
 */
public class AccountInfoBean implements Serializable {


    /**
     * accountInfo : {"accountId":"92222371577987072","ventureId":"15","tenantId":"0","investorId":"0","email":{"email":"1028607782@qq.com"},"phoneNumber":{"areaCode":"+60","phone":"019-335 7082","geocoderEN":"Malaysia","geocoderCH":"马来西亚","phoneNumber":"+60193357082"},"googleAuthenticator":"1","createdAt":"2018-09-12 11:38:47"}
     * country : 马来西亚
     * traPassword : false
     * certificationAudit : {"id":4,"accountId":"92222371577987072","certificationStatus":"rejected","rejectedType":"身份证过期","rejectedReason":"身份证不清楚","certificationType":"individual","applicationDate":"2018-09-12 18:56:51"}
     */

    private AccountInfo accountInfo;
    private String country;
    private boolean traPassword;
    private CertificationAuditBean certificationAudit;

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isTraPassword() {
        return traPassword;
    }

    public void setTraPassword(boolean traPassword) {
        this.traPassword = traPassword;
    }

    public CertificationAuditBean getCertificationAudit() {
        return certificationAudit;
    }

    public void setCertificationAudit(CertificationAuditBean certificationAudit) {
        this.certificationAudit = certificationAudit;
    }

    public static class AccountInfo implements Serializable {
        /**
         * accountId : 92222371577987072
         * ventureId : 15
         * tenantId : 0
         * investorId : 0
         * email : {"email":"1028607782@qq.com"}
         * phoneNumber : {"areaCode":"+60","phone":"019-335 7082","geocoderEN":"Malaysia","geocoderCH":"马来西亚","phoneNumber":"+60193357082"}
         * googleAuthenticator : 1
         * createdAt : 2018-09-12 11:38:47
         */

        private String accountId;
        private String ventureId;
        private String tenantId;
        private String investorId;
        private EmailBean email;
        private PhoneNumberBean phoneNumber;
        private String googleAuthenticator;
        private String createdAt;

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getVentureId() {
            return ventureId;
        }

        public void setVentureId(String ventureId) {
            this.ventureId = ventureId;
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getInvestorId() {
            return investorId;
        }

        public void setInvestorId(String investorId) {
            this.investorId = investorId;
        }

        public EmailBean getEmail() {
            return email;
        }

        public void setEmail(EmailBean email) {
            this.email = email;
        }

        public PhoneNumberBean getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(PhoneNumberBean phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getGoogleAuthenticator() {
            return googleAuthenticator;
        }

        public void setGoogleAuthenticator(String googleAuthenticator) {
            this.googleAuthenticator = googleAuthenticator;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public static class EmailBean implements Serializable {
            /**
             * email : 1028607782@qq.com
             */

            private String email;

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }

        public static class PhoneNumberBean implements Serializable {
            /**
             * areaCode : +60
             * phone : 019-335 7082
             * geocoderEN : Malaysia
             * geocoderCH : 马来西亚
             * phoneNumber : +60193357082
             */

            private String areaCode;
            private String phone;
            private String geocoderEN;
            private String geocoderCH;
            private String phoneNumber;

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getGeocoderEN() {
                return geocoderEN;
            }

            public void setGeocoderEN(String geocoderEN) {
                this.geocoderEN = geocoderEN;
            }

            public String getGeocoderCH() {
                return geocoderCH;
            }

            public void setGeocoderCH(String geocoderCH) {
                this.geocoderCH = geocoderCH;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }
        }
    }

    public static class CertificationAuditBean implements Serializable {
        /**
         * id : 4
         * accountId : 92222371577987072
         * certificationStatus : rejected
         * rejectedType : 身份证过期
         * rejectedReason : 身份证不清楚
         * certificationType : individual
         * applicationDate : 2018-09-12 18:56:51
         */

        private int id;
        private String accountId;
        private String certificationStatus;
        private String rejectedType;
        private String rejectedReason;
        private String certificationType;
        private String applicationDate;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getCertificationStatus() {
            return certificationStatus;
        }

        public void setCertificationStatus(String certificationStatus) {
            this.certificationStatus = certificationStatus;
        }

        public String getRejectedType() {
            return rejectedType;
        }

        public void setRejectedType(String rejectedType) {
            this.rejectedType = rejectedType;
        }

        public String getRejectedReason() {
            return rejectedReason;
        }

        public void setRejectedReason(String rejectedReason) {
            this.rejectedReason = rejectedReason;
        }

        public String getCertificationType() {
            return certificationType;
        }

        public void setCertificationType(String certificationType) {
            this.certificationType = certificationType;
        }

        public String getApplicationDate() {
            return applicationDate;
        }

        public void setApplicationDate(String applicationDate) {
            this.applicationDate = applicationDate;
        }
    }
}
