package com.wanshare.crush.account.model.bean;

public class IndividualBean {


    /**
     * name : 张三
     * nationalityCode : “CHINA”
     * number : 232332199003111111
     * type : 身份证
     */

    private String name;
    private String nationalityCode;
    private String number;
    private String idType;

    public IndividualBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return idType;
    }

    public void setType(String idType) {
        this.idType = idType;
    }
}
