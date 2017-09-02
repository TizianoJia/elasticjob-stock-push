package cn.jxh.stock.task;

import java.util.*;

import cn.jxh.stock.entity.SinaNewStock;
import cn.jxh.stock.utils.DatetimeUtil;
import cn.jxh.stock.utils.Utils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;


@Service(value = "newStockTask")
public class NewStockTask {

    Logger log = Logger.getLogger(NewStockTask.class);

    private List<SinaNewStock> newStockList = new ArrayList<>();

    public List<SinaNewStock> getNewStockList() {
        return newStockList;
    }

    public synchronized void crawlNewStockList() {
        List<SinaNewStock> tmpNewStockList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://vip.stock.finance.sina.com.cn/corp/go.php/vRPD_NewStockIssue/page/1.phtml")
                    .userAgent("Mozilla")
                    .timeout(3000)
                    .get();

            Elements newsHeadlines = doc.select("table#NewStockTable");
            Elements trs = newsHeadlines.get(0).select("tr");

            for (int i = 3; i < trs.size(); i++) {
                Elements tds = trs.get(i).select("td");

                if (DatetimeUtil.diffdates(DatetimeUtil.String2Date(tds.get(3).text(), null), DatetimeUtil.getSimpleDate(new Date())) == 0) {
                    SinaNewStock sinaNewStock = new SinaNewStock();
                    sinaNewStock.setStockcode(Utils.parseInt(tds.get(0).text(), 0));//证券代码
                    sinaNewStock.setPurchasecode(Utils.parseInt(tds.get(1).text(), 0));//申购代码
                    sinaNewStock.setStockabbr(tds.get(2).text());//证券简称
                    sinaNewStock.setReleasedate(DatetimeUtil.String2Date(tds.get(3).text(), null));//上网发行
                    sinaNewStock.setListingdate(DatetimeUtil.String2Date(tds.get(4).text(), null));//上市日期
                    sinaNewStock.setIssuenumber(Utils.parseInt(tds.get(5).text().replace("/*", ""), 0));//发行数量(万股)
                    sinaNewStock.setOnlineissuenumber(Utils.parseInt(tds.get(6).text(), 0));//上网发行数量(万股)
                    sinaNewStock.setIssueprice(Utils.parseDouble(tds.get(7).text(), null));//发行价格(元)
                    sinaNewStock.setPeratio(Utils.parseDouble(tds.get(8).text(), null));//市盈率
                    sinaNewStock.setPurchaselimit(Utils.parseDouble(tds.get(9).text(), null));//个人申购上限(万股)
                    sinaNewStock.setRaisefunds(Utils.parseDouble(tds.get(10).text(), null));//募集资金(亿元)
                    sinaNewStock.setSuccessrate(Utils.parseDouble(tds.get(11).text(), null));//网上中签率(%)

                    tmpNewStockList.add(sinaNewStock);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        newStockList = tmpNewStockList;
        log.info("爬取新浪新股列表完成，条数：" + this.newStockList.size());
    }


}
