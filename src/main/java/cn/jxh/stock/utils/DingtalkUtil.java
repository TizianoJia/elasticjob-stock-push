package cn.jxh.stock.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.CorpMessageCorpconversationAsyncsendRequest;
import com.dingtalk.api.response.CorpMessageCorpconversationAsyncsendResponse;
import com.taobao.api.ApiException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service(value = "dingtalkUtil")
public class DingtalkUtil {

    private static String corpid;

    @Value(value = "#{propertiesReader['dingtalk.corpid']}")
    public void setCorpid(String corpid) {
        DingtalkUtil.corpid = corpid;
    }

    private static String corpsecret;

    @Value(value = "#{propertiesReader['dingtalk.corpsecret']}")
    public void setCorpsecret(String corpsecret) {
        DingtalkUtil.corpsecret = corpsecret;
    }

    private static String agentid;

    @Value(value = "#{propertiesReader['dingtalk.agentid']}")
    public void setAgentid(String agentid) {
        DingtalkUtil.agentid = agentid;
    }

    public static String getToken() {
        String host = "https://oapi.dingtalk.com";
        String path = "/gettoken";
        String method = "GET";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        querys.put("corpid", corpid);
        querys.put("corpsecret", corpsecret);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String json = (EntityUtils.toString(response.getEntity()));
            Map<String, String> tokenMap = JSON.parseObject((json), new TypeReference<Map<String, String>>() {
            });
            return tokenMap.get("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendText(String content) {
        try {
            Map<String, String> msgcontent = new HashMap<>();
            msgcontent.put("content", content);
            DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
            CorpMessageCorpconversationAsyncsendRequest req = new CorpMessageCorpconversationAsyncsendRequest();
            req.setMsgtype("text");
            req.setAgentId(Utils.parseLong(agentid, null));
            req.setToAllUser(true);

            req.setMsgcontent(JSON.toJSONString(msgcontent));
            CorpMessageCorpconversationAsyncsendResponse rsp = client.execute(req, getToken());
            System.out.println(rsp.getBody());

        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }


}
