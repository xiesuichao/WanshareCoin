package com.wanshare.crush.account.model.bean;

/**
 * Created by Richard on 2018/9/14
 */
public class LoginAuthBean {


    /**
     * baseToken : eyJhbGciOiJIUzUxMiJ9.eyJjcmVhdGVkIjoxNTM3MTkxMjAwNzY3LCJleHAiOjE1MzczNjQwMDAsInVzZXJpZCI6IjkyMjIyMzcxNTc3OTg3MDcyIiwidXNlcm5hbWUiOiIifQ.fjDi4aKVJHI0WnSEaTq24-Nau6zoFHOscUp5dzjHHEEgPxCwyTgXrAtJ_yaT4PW5GsOUy1Um78Ii8HXMiH_gDg
     * validationRestrict : {"accountId":"92222371577987072","googleAuthenticator":1,"phoneAuthenticator":1,"emailAuthenticator":1}
     * authMessage : {"accountId":"92222371577987072","ventureId":"15","tenantId":"4","investorId":"0","email":{"email":"1028607782@qq.com"},"phoneNumber":{"areaCode":"+60","phone":"019-335 7082","geocoderEN":"Malaysia","geocoderCH":"马来西亚","phoneNumber":"+60193357082"},"googleAuthenticator":"1","createdAt":"2018-09-12 11:38:47"}
     * type : login
     */

    private String baseToken;
    private BindStatusBean validationRestrict;
    private AuthMessageBean authMessage;

    public String getBaseToken() {
        return baseToken;
    }

    public void setBaseToken(String baseToken) {
        this.baseToken = baseToken;
    }

    public BindStatusBean getBindStatusBean() {
        return validationRestrict;
    }

    public void setBindStatusBean(BindStatusBean bindStatusBean) {
        this.validationRestrict = bindStatusBean;
    }

    public AuthMessageBean getAuthMessage() {
        return authMessage;
    }

    public void setAuthMessage(AuthMessageBean authMessage) {
        this.authMessage = authMessage;
    }


    public static class AuthMessageBean {
        /**
         * accountId : 92222371577987072
         * ventureId : 15
         * tenantId : 4
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

        public static class EmailBean {
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

        public static class PhoneNumberBean {
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
}
