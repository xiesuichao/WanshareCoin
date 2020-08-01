package com.wanshare.crush.market.trade.model.bean;

public class CoinAssetsBean {


    /**
     * description:
     * 获取单个币种资产信息
     *
     * coinId	string
     * example: 20
     * 币种id
     *
     * coinName	string
     * example: fadsf4654
     * 币种名称
     *
     * total	string
     * example: 10
     * 总计
     *
     * balance	string
     * example: 11
     * 可用金额
     *
     * frozen	string
     * example: 12
     * 冻结金额
     *
     * estimates	{
     * description:
     * 资产估算信息
     *
     * usdt	string
     * example: 1564988.651651
     * 折算usdt
     *
     * cny	string
     * example: 1564988.651651
     * 折算cny
     *
     * btc	string
     * example: 1564988.651651
     * 折算btc
     *
     *   }
     * }
     */

    private String coinId;
    private String coinName;
    private int total;
    private int balance;
    private int frozen;
    private EstimatesBean estimates;

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getFrozen() {
        return frozen;
    }

    public void setFrozen(int frozen) {
        this.frozen = frozen;
    }

    public EstimatesBean getEstimates() {
        return estimates;
    }

    public void setEstimates(EstimatesBean estimates) {
        this.estimates = estimates;
    }

    public static class EstimatesBean {
        /**
         * usdt : 1564988.651651
         * cny : 1564988.651651
         * btc : 1564988.651651
         */

        private double usdt;
        private double cny;
        private double btc;

        public double getUsdt() {
            return usdt;
        }

        public void setUsdt(double usdt) {
            this.usdt = usdt;
        }

        public double getCny() {
            return cny;
        }

        public void setCny(double cny) {
            this.cny = cny;
        }

        public double getBtc() {
            return btc;
        }

        public void setBtc(double btc) {
            this.btc = btc;
        }
    }
}
