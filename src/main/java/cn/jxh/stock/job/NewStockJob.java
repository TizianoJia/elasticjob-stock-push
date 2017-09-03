package cn.jxh.stock.job;

import cn.jxh.stock.entity.SinaNewStock;
import cn.jxh.stock.task.NewStockTask;
import cn.jxh.stock.utils.DatetimeUtil;
import cn.jxh.stock.utils.DingtalkUtil;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.io.UnsupportedEncodingException;
import java.util.*;


public class NewStockJob implements SimpleJob {

    private static Log log = LogFactory.getLog(NewStockJob.class);

    @Value(value = "#{propertiesReader['send.mail']}")
    private String sendmail;

    @Value(value = "#{propertiesReader['send.dingtalk']}")
    private String senddingtalk;

    @Value(value = "#{propertiesReader['mail.recipients']}")
    private String recipients;

    @Value(value = "#{propertiesReader['mail.nick']}")
    private String nick;

    @Value(value = "#{propertiesReader['mail.username']}")
    private String username;

    @Autowired
    private NewStockTask newStockTask;

    @Autowired
    private MailSender mailSender;

    public void execute(ShardingContext context) {

        log.info("触发 邮件发送任务");

        //判断是否需要发送
        if (newStockTask.getNewStockList().size() == 0) {
            log.info("无通知需要发送");
            return;
        }

        //组织内容
        StringBuffer subject = new StringBuffer();
        subject.append(DatetimeUtil.Date2String(new Date()) + "日股票提醒推送");

        StringBuffer text = new StringBuffer();
        text.append("今日股票申购\n\r");
        for (SinaNewStock SinaNewStock : newStockTask.getNewStockList()) {
            text.append(SinaNewStock.getStockabbr() + "\n");
        }
        text.append("------------------------------");

        //判断是否需要发送邮件
        if (sendmail.equals("Y")) {
            //发送邮件
            for (String to : recipients.split(";")) {
                try {
                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setFrom(javax.mail.internet.MimeUtility.encodeText(nick) + " <" + username + ">");
                    mailMessage.setSubject(subject.toString());
                    mailMessage.setText(text.toString());
                    mailMessage.setTo(to);
                    mailSender.send(mailMessage);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        //发送钉钉
        if (senddingtalk.equals("Y")) {
            DingtalkUtil.sendText(text.toString());
        }

    }
}


