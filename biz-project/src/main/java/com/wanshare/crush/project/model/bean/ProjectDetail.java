package com.wanshare.crush.project.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目详情
 * Created by xiesuichao on 2018/9/13.
 */

public class ProjectDetail implements Serializable{


    /**
     * projectInfo : {"id":"1","projectName":"项目名","whitePaper":"www.baidu.com","officialWebsite":"www.baidu.com","communities":[],"email":"longling@qq.com","appliedAt":"2018-09-11 13:40:47","listedExchange":0,"areaCode":"86","cellphone":"13312123344","telephone":"07551212121","projectPoster":"/xxx/xxx.png","description":"项目简介"}
     * coinInfo : {"id":"1","shortName":"test1","fullName":"TEST1","issuePrice":"100.00000000","issuedVolume":"1000000.00000000","circulationVolume":"900000.00000000","openAccount":"xxxxxxxxxx","isOpen":"true","issuedAt":"2018-09-14 02:04:52","transactionAccuracy":"8","blockchainType":"public_chian","dataLink":"www.baidu.com","blockBrowser":"www.baidu.com,www.google.cn","isDelayTrading":false,"logo":"xxx/xxx.png"}
     * summary : {"usdAmount24H":"8.877772424668005E8","usdMarketValue":"1.044443814666824E10","usdPrice":"275607.6017775765","cnyPrice":"1874131.6920875201","usdChangePct24H":"99.76778579550339"}
     */

    private ProjectInfoBean projectInfo;
    private CoinInfoBean coinInfo;
    private SummaryBean summary;

    public ProjectDetail() {
    }

    public ProjectDetail(ProjectInfoBean projectInfo, CoinInfoBean coinInfo, SummaryBean summary) {
        this.projectInfo = projectInfo;
        this.coinInfo = coinInfo;
        this.summary = summary;
    }

    public ProjectInfoBean getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfoBean projectInfo) {
        this.projectInfo = projectInfo;
    }

    public CoinInfoBean getCoinInfo() {
        return coinInfo;
    }

    public void setCoinInfo(CoinInfoBean coinInfo) {
        this.coinInfo = coinInfo;
    }

    public SummaryBean getSummary() {
        return summary;
    }

    public void setSummary(SummaryBean summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "ProjectDetail{" +
                "projectInfo=" + projectInfo +
                ", coinInfo=" + coinInfo +
                ", summary=" + summary +
                '}';
    }

    public static class ProjectInfoBean {
        /**
         * id : 1
         * projectName : 项目名
         * whitePaper : www.baidu.com
         * officialWebsite : www.baidu.com
         * communities : []
         * email : longling@qq.com
         * appliedAt : 2018-09-11 13:40:47
         * listedExchange : 0
         * areaCode : 86
         * cellphone : 13312123344
         * telephone : 07551212121
         * projectPoster : /xxx/xxx.png
         * description : 项目简介
         */

        private String id;
        private String projectName;
        private String whitePaper;
        private String officialWebsite;
        private String email;
        private String appliedAt;
        private int listedExchange;
        private String areaCode;
        private String cellphone;
        private String telephone;
        private String projectPoster;
        private String description;
        private List<Communities> communities;

        public ProjectInfoBean() {
        }

        public ProjectInfoBean(String id, String projectName, String whitePaper, String officialWebsite, String email, String appliedAt, int listedExchange, String areaCode, String cellphone, String telephone, String projectPoster, String description, List<Communities> communities) {
            this.id = id;
            this.projectName = projectName;
            this.whitePaper = whitePaper;
            this.officialWebsite = officialWebsite;
            this.email = email;
            this.appliedAt = appliedAt;
            this.listedExchange = listedExchange;
            this.areaCode = areaCode;
            this.cellphone = cellphone;
            this.telephone = telephone;
            this.projectPoster = projectPoster;
            this.description = description;
            this.communities = communities;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getWhitePaper() {
            return whitePaper;
        }

        public void setWhitePaper(String whitePaper) {
            this.whitePaper = whitePaper;
        }

        public String getOfficialWebsite() {
            return officialWebsite;
        }

        public void setOfficialWebsite(String officialWebsite) {
            this.officialWebsite = officialWebsite;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAppliedAt() {
            return appliedAt;
        }

        public void setAppliedAt(String appliedAt) {
            this.appliedAt = appliedAt;
        }

        public int getListedExchange() {
            return listedExchange;
        }

        public void setListedExchange(int listedExchange) {
            this.listedExchange = listedExchange;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getProjectPoster() {
            return projectPoster;
        }

        public void setProjectPoster(String projectPoster) {
            this.projectPoster = projectPoster;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<Communities> getCommunities() {
            return communities;
        }

        public void setCommunities(List<Communities> communities) {
            this.communities = communities;
        }

        public static class Communities{

        }

        @Override
        public String toString() {
            return "ProjectInfoBean{" +
                    "id='" + id + '\'' +
                    ", projectName='" + projectName + '\'' +
                    ", whitePaper='" + whitePaper + '\'' +
                    ", officialWebsite='" + officialWebsite + '\'' +
                    ", email='" + email + '\'' +
                    ", appliedAt='" + appliedAt + '\'' +
                    ", listedExchange=" + listedExchange +
                    ", areaCode='" + areaCode + '\'' +
                    ", cellphone='" + cellphone + '\'' +
                    ", telephone='" + telephone + '\'' +
                    ", projectPoster='" + projectPoster + '\'' +
                    ", description='" + description + '\'' +
                    ", communities=" + communities +
                    '}';
        }
    }

    public static class CoinInfoBean {
        /**
         * id : 1
         * shortName : test1
         * fullName : TEST1
         * issuePrice : 100.00000000
         * issuedVolume : 1000000.00000000
         * circulationVolume : 900000.00000000
         * openAccount : xxxxxxxxxx
         * isOpen : true
         * issuedAt : 2018-09-14 02:04:52
         * transactionAccuracy : 8
         * blockchainType : public_chian
         * dataLink : www.baidu.com
         * blockBrowser : www.baidu.com,www.google.cn
         * isDelayTrading : false
         * logo : xxx/xxx.png
         */

        private String id;
        private String shortName;
        private String fullName;
        private String issuePrice;
        private String issuedVolume;
        private String circulationVolume;
        private String openAccount;
        private String isOpen;
        private String issuedAt;
        private String transactionAccuracy;
        private String blockchainType;
        private String dataLink;
        private String blockBrowser;
        private boolean isDelayTrading;
        private String logo;

        public CoinInfoBean() {
        }

        public CoinInfoBean(String id, String shortName, String fullName, String issuePrice, String issuedVolume, String circulationVolume, String openAccount, String isOpen, String issuedAt, String transactionAccuracy, String blockchainType, String dataLink, String blockBrowser, boolean isDelayTrading, String logo) {
            this.id = id;
            this.shortName = shortName;
            this.fullName = fullName;
            this.issuePrice = issuePrice;
            this.issuedVolume = issuedVolume;
            this.circulationVolume = circulationVolume;
            this.openAccount = openAccount;
            this.isOpen = isOpen;
            this.issuedAt = issuedAt;
            this.transactionAccuracy = transactionAccuracy;
            this.blockchainType = blockchainType;
            this.dataLink = dataLink;
            this.blockBrowser = blockBrowser;
            this.isDelayTrading = isDelayTrading;
            this.logo = logo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getIssuePrice() {
            return issuePrice;
        }

        public void setIssuePrice(String issuePrice) {
            this.issuePrice = issuePrice;
        }

        public String getIssuedVolume() {
            return issuedVolume;
        }

        public void setIssuedVolume(String issuedVolume) {
            this.issuedVolume = issuedVolume;
        }

        public String getCirculationVolume() {
            return circulationVolume;
        }

        public void setCirculationVolume(String circulationVolume) {
            this.circulationVolume = circulationVolume;
        }

        public String getOpenAccount() {
            return openAccount;
        }

        public void setOpenAccount(String openAccount) {
            this.openAccount = openAccount;
        }

        public String getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(String isOpen) {
            this.isOpen = isOpen;
        }

        public String getIssuedAt() {
            return issuedAt;
        }

        public void setIssuedAt(String issuedAt) {
            this.issuedAt = issuedAt;
        }

        public String getTransactionAccuracy() {
            return transactionAccuracy;
        }

        public void setTransactionAccuracy(String transactionAccuracy) {
            this.transactionAccuracy = transactionAccuracy;
        }

        public String getBlockchainType() {
            return blockchainType;
        }

        public void setBlockchainType(String blockchainType) {
            this.blockchainType = blockchainType;
        }

        public String getDataLink() {
            return dataLink;
        }

        public void setDataLink(String dataLink) {
            this.dataLink = dataLink;
        }

        public String getBlockBrowser() {
            return blockBrowser;
        }

        public void setBlockBrowser(String blockBrowser) {
            this.blockBrowser = blockBrowser;
        }

        public boolean isIsDelayTrading() {
            return isDelayTrading;
        }

        public void setIsDelayTrading(boolean isDelayTrading) {
            this.isDelayTrading = isDelayTrading;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        @Override
        public String toString() {
            return "CoinInfoBean{" +
                    "id='" + id + '\'' +
                    ", shortName='" + shortName + '\'' +
                    ", fullName='" + fullName + '\'' +
                    ", issuePrice='" + issuePrice + '\'' +
                    ", issuedVolume='" + issuedVolume + '\'' +
                    ", circulationVolume='" + circulationVolume + '\'' +
                    ", openAccount='" + openAccount + '\'' +
                    ", isOpen='" + isOpen + '\'' +
                    ", issuedAt='" + issuedAt + '\'' +
                    ", transactionAccuracy='" + transactionAccuracy + '\'' +
                    ", blockchainType='" + blockchainType + '\'' +
                    ", dataLink='" + dataLink + '\'' +
                    ", blockBrowser='" + blockBrowser + '\'' +
                    ", isDelayTrading=" + isDelayTrading +
                    ", logo='" + logo + '\'' +
                    '}';
        }
    }

    public static class SummaryBean {
        /**
         * usdAmount24H : 8.877772424668005E8
         * usdMarketValue : 1.044443814666824E10
         * usdPrice : 275607.6017775765
         * cnyPrice : 1874131.6920875201
         * usdChangePct24H : 99.76778579550339
         */

        private String usdAmount24H;
        private String usdMarketValue;
        private String usdPrice;
        private String cnyPrice;
        private String usdChangePct24H;

        public SummaryBean() {
        }

        public SummaryBean(String usdAmount24H, String usdMarketValue, String usdPrice, String cnyPrice, String usdChangePct24H) {
            this.usdAmount24H = usdAmount24H;
            this.usdMarketValue = usdMarketValue;
            this.usdPrice = usdPrice;
            this.cnyPrice = cnyPrice;
            this.usdChangePct24H = usdChangePct24H;
        }

        public String getUsdAmount24H() {
            return usdAmount24H;
        }

        public void setUsdAmount24H(String usdAmount24H) {
            this.usdAmount24H = usdAmount24H;
        }

        public String getUsdMarketValue() {
            return usdMarketValue;
        }

        public void setUsdMarketValue(String usdMarketValue) {
            this.usdMarketValue = usdMarketValue;
        }

        public String getUsdPrice() {
            return usdPrice;
        }

        public void setUsdPrice(String usdPrice) {
            this.usdPrice = usdPrice;
        }

        public String getCnyPrice() {
            return cnyPrice;
        }

        public void setCnyPrice(String cnyPrice) {
            this.cnyPrice = cnyPrice;
        }

        public String getUsdChangePct24H() {
            return usdChangePct24H;
        }

        public void setUsdChangePct24H(String usdChangePct24H) {
            this.usdChangePct24H = usdChangePct24H;
        }

        @Override
        public String toString() {
            return "SummaryBean{" +
                    "usdAmount24H='" + usdAmount24H + '\'' +
                    ", usdMarketValue='" + usdMarketValue + '\'' +
                    ", usdPrice='" + usdPrice + '\'' +
                    ", cnyPrice='" + cnyPrice + '\'' +
                    ", usdChangePct24H='" + usdChangePct24H + '\'' +
                    '}';
        }
    }
}
