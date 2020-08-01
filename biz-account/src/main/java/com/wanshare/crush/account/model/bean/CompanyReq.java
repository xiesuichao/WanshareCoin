package com.wanshare.crush.account.model.bean;

public class CompanyReq {


    /**
     * companyName : 中国银行
     * nationalityCode : CHINA
     * area : 朝阳区
     * address : 北京丰台科技园
     * logo : logoid
     * socialCode : 121212
     * taxNumber : df4548787
     * organizationCode : 5454545
     * businessLicense : 图片路径
     * name : 李二狗
     * phoneNumber : 18644444444
     */

    private String companyName;
    private String nationalityCode;
    private String area;
    private String address;
    private String logo;
    private String socialCode;
    private String taxNumber;
    private String organizationCode;
    private String businessLicense;
    private String name;
    private String phoneNumber;

    public CompanyReq() {
    }

    public CompanyReq(String companyName, String nationalityCode, String area,
                      String address, String logo, String socialCode,
                      String taxNumber, String organizationCode, String businessLicense,
                      String name, String phoneNumber) {
        this.companyName = companyName;
        this.nationalityCode = nationalityCode;
        this.area = area;
        this.address = address;
        this.logo = logo;
        this.socialCode = socialCode;
        this.taxNumber = taxNumber;
        this.organizationCode = organizationCode;
        this.businessLicense = businessLicense;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSocialCode() {
        return socialCode;
    }

    public void setSocialCode(String socialCode) {
        this.socialCode = socialCode;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
