package com.mine.common;

/**
 * @Copyrights: Apr 28, 2008，SHENZHEN TIANYUAN DIC INFORMATION TECHNOLOGY
 * CO.,LTD.<br>
 * Common<br>
 * All rights reserved.<br>
 * @Filename: DataRoute.java
 * @Description：
 * @Version： V1.0<br>
 * @Author: yuhui
 * @Created： 2010-11-5<br>
 * @History: <br> [ Author Date Version Content ]<br>
 */
public class DataRoute implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    // 数据索引号
    private String index;
    // 客户标识
    private String custId;
    // 服务号码
    private String serviceNbr;
    // 区域标识
    private String regionCd;
    // 身份标识
    private String identification;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getServiceNbr() {
        return serviceNbr;
    }

    public void setServiceNbr(String serviceNbr) {
        this.serviceNbr = serviceNbr;
    }

    public String getRegionCd() {
        return regionCd;
    }

    public void setRegionCd(String regionCd) {
        this.regionCd = regionCd;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

}
