package cn.jxh.stock.entity;


import java.util.Date;

public class StockNewstock {

    private Integer id;

    private Integer stockcode;//证券代码

    private Integer purchasecode;//申购代码

    private String stockabbr;//证券简称

    private Date releasedate;//上网发行

    private Date listingdate;//上市日期

    private Integer issuenumber;//发行数量(万股)

    private Integer onlineissuenumber;//上网发行数量(万股)

    private Double issueprice;//发行价格(元)

    private Double peratio;//市盈率

    private Double purchaselimit;//个人申购上限(万股)

    private Double raisefunds;//募集资金(亿元)

    private Double successrate;//网上中签率(%)

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStockcode() {
        return this.stockcode;
    }

    public void setStockcode(Integer stockcode) {
        this.stockcode = stockcode;
    }

    public Integer getPurchasecode() {
        return this.purchasecode;
    }

    public void setPurchasecode(Integer purchasecode) {
        this.purchasecode = purchasecode;
    }

    public String getStockabbr() {
        return this.stockabbr;
    }

    public void setStockabbr(String stockabbr) {
        this.stockabbr = stockabbr;
    }

    public Date getReleasedate() {
        return this.releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }

    public Date getListingdate() {
        return this.listingdate;
    }

    public void setListingdate(Date listingdate) {
        this.listingdate = listingdate;
    }

    public Integer getIssuenumber() {
        return this.issuenumber;
    }

    public void setIssuenumber(Integer issuenumber) {
        this.issuenumber = issuenumber;
    }

    public Integer getOnlineissuenumber() {
        return this.onlineissuenumber;
    }

    public void setOnlineissuenumber(Integer onlineissuenumber) {
        this.onlineissuenumber = onlineissuenumber;
    }

    public Double getIssueprice() {
        return this.issueprice;
    }

    public void setIssueprice(Double issueprice) {
        this.issueprice = issueprice;
    }

    public Double getPeratio() {
        return this.peratio;
    }

    public void setPeratio(Double peratio) {
        this.peratio = peratio;
    }

    public Double getPurchaselimit() {
        return this.purchaselimit;
    }

    public void setPurchaselimit(Double purchaselimit) {
        this.purchaselimit = purchaselimit;
    }

    public Double getRaisefunds() {
        return this.raisefunds;
    }

    public void setRaisefunds(Double raisefunds) {
        this.raisefunds = raisefunds;
    }

    public Double getSuccessrate() {
        return this.successrate;
    }

    public void setSuccessrate(Double successrate) {
        this.successrate = successrate;
    }

}
