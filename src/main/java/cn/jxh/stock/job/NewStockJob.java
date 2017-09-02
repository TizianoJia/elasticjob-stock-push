package cn.jxh.stock.job;

import cn.jxh.stock.entity.StockNewstock;
import cn.jxh.stock.utils.DatetimeUtil;
import cn.jxh.stock.utils.Utils;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.*;


public class NewStockJob implements SimpleJob {

    private static Log log = LogFactory.getLog(NewStockJob.class);

    public void execute(ShardingContext context) {

        log.info("任务触发");


        List<StockNewstock> data = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://vip.stock.finance.sina.com.cn/corp/go.php/vRPD_NewStockIssue/page/1.phtml")
                    .userAgent("Mozilla")
                    .timeout(3000)
                    .get();

            List<StockNewstock> StockNewstockList = new ArrayList<>();

            Elements newsHeadlines = doc.select("table#NewStockTable");
            Elements trs = newsHeadlines.get(0).select("tr");

            for (int i = 3; i < trs.size(); i++) {
                Elements tds = trs.get(i).select("td");

                StockNewstock stockNewstock = new StockNewstock();
                stockNewstock.setStockcode(Utils.parseInt(tds.get(0).text(), 0));//证券代码
                stockNewstock.setPurchasecode(Utils.parseInt(tds.get(1).text(), 0));//申购代码
                stockNewstock.setStockabbr(tds.get(2).text());//证券简称
                stockNewstock.setReleasedate(DatetimeUtil.String2Date(tds.get(3).text(),null));//上网发行
                stockNewstock.setListingdate(DatetimeUtil.String2Date(tds.get(4).text(),null));//上市日期
                stockNewstock.setIssuenumber(Utils.parseInt(tds.get(5).text().replace("/*", ""), 0));//发行数量(万股)
                stockNewstock.setOnlineissuenumber(Utils.parseInt(tds.get(6).text(), 0));//上网发行数量(万股)
                stockNewstock.setIssueprice(Utils.parseDouble(tds.get(7).text(), null));//发行价格(元)
                stockNewstock.setPeratio(Utils.parseDouble(tds.get(8).text(), null));//市盈率
                stockNewstock.setPurchaselimit(Utils.parseDouble(tds.get(9).text(), null));//个人申购上限(万股)
                stockNewstock.setRaisefunds(Utils.parseDouble(tds.get(10).text(), null));//募集资金(亿元)
                stockNewstock.setSuccessrate(Utils.parseDouble(tds.get(11).text(), null));//网上中签率(%)

                StockNewstockList.add(stockNewstock);

            }

            String todayStockNewstockList = "";
            for (StockNewstock stockNewstock : StockNewstockList) {
                System.out.println(stockNewstock.getReleasedate());

                if (DatetimeUtil.diffdates(stockNewstock.getReleasedate(), DatetimeUtil.getSimpleDate(new Date())) == 0) {
                    todayStockNewstockList = todayStockNewstockList + stockNewstock.getStockabbr() + ";";
                }
            }

            if (!Utils.strIsNull(todayStockNewstockList)) {
                log.info("发送新股提醒");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


