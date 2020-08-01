package com.wanshare.crush.setting.model.bean;

/**
 * Created by Jason on 2018/9/18.
 */

public class ValuationBean {
    private String valuation;
    private String shortening;
    private boolean choseValuation;

    public ValuationBean(String valuation, String shortening, boolean choseValuation) {
        this.valuation = valuation;
        this.shortening = shortening;
        this.choseValuation = choseValuation;
    }


    public String getValuation() {
        return valuation;
    }

    public void setValuation(String valuation) {
        this.valuation = valuation;
    }

    public boolean isChoseValuation() {
        return choseValuation;
    }

    public void setChoseValuation(boolean choseValuation) {
        this.choseValuation = choseValuation;
    }

    public String getShortening() {
        return shortening;
    }

    public void setShortening(String shortening) {
        this.shortening = shortening;
    }
}
