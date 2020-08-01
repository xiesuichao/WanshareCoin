package com.wanshare.crush.market.trade.model.bean;

import java.math.BigDecimal;

/**
 * 深度相关数据的Bean
 * </br>
 * Date: 2018/8/16 16:18
 *
 * @author hemin
 */
public class DepthBean {

    // 价格
    private BigDecimal price;
    // 交易量
    private BigDecimal amount;

    public DepthBean() {
    }

    public DepthBean(BigDecimal price, BigDecimal amount) {
        this.price = price;
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
