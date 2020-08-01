package com.wanshare.crush.account.model.bean;

public class IndividualReq {


    /**
     * nationalityCode : “CHINA”
     * name : 张三
     * type : 身份证 身份证("1"),护照("2"),驾驶证("3");
     * number : 232332199003111111
     * frontPhoto : urlid
     * backPhoto : urlid
     * handHeldPhoto : urlid
     */

    private String nationalityCode;
    private String name;
    private String type;
    private String number;
    private String frontPhoto;
    private String backPhoto;
    private String handheldPhoto;

    public IndividualReq() {
    }

    public IndividualReq(String nationalityCode, String name, String type,
                         String number, String frontPhoto, String backPhoto,
                         String handheldPhoto) {
        this.nationalityCode = nationalityCode;
        this.name = name;
        this.type = type;
        this.number = number;
        this.frontPhoto = frontPhoto;
        this.backPhoto = backPhoto;
        this.handheldPhoto = handheldPhoto;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFrontPhoto() {
        return frontPhoto;
    }

    public void setFrontPhoto(String frontPhoto) {
        this.frontPhoto = frontPhoto;
    }

    public String getBackPhoto() {
        return backPhoto;
    }

    public void setBackPhoto(String backPhoto) {
        this.backPhoto = backPhoto;
    }

    public String getHandHeldPhoto() {
        return handheldPhoto;
    }

    public void setHandHeldPhoto(String handheldPhoto) {
        this.handheldPhoto = handheldPhoto;
    }
}
