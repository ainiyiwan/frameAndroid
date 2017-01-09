package com.arlen.frame.common.thirdsdk.pay.model;

import com.google.gson.annotations.SerializedName;

public class PayInfo {
    private String info;

    /*微信*/
    private String appId;
    private String partnerId;
    private String prepayId;
    private String nonceStr;
    private String timeStamp;
    @SerializedName("package")
    private String Package;
    private String paySignature;


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    public String getAppId() {
        return appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String package1) {
        Package = package1;
    }

    public String getPaySignature() {
        return paySignature;
    }
}
