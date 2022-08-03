package com.sr;

import com.alibaba.fastjson2.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.sql.SQLException;

public class SRPut {
    public static void main(String[] args) throws Exception {
        int insertcount = 1 * 10000;
        int updatecount1 = 0;
        int updatecount2 = 0;
        int deletecount = 0;
        String ip = "10.49.1.34:9877";
        CreateData.init();
        for (int i = 0; i < 1; i++) {
            String batchCode = i + "!";
            JSONArray json = new JSONArray();
            json.addAll(CreateData.INSERT(batchCode, insertcount, 1));
            json.addAll(CreateData.UPDATE1(batchCode, updatecount1, insertcount + 1));
            json.addAll(CreateData.UPDATE2(batchCode, updatecount2, insertcount + updatecount1 + 1));
            json.addAll(CreateData.DELETE(batchCode, deletecount, (insertcount + updatecount1 + updatecount2 + 1)));
//            System.out.println(json);
            SRPut.doit(ip, json.toJSONString());
        }
    }
//    分区语句
//    ALTER TABLE fba_daily_inventory_history_report_mws ADD PARTITION 12019 VALUE ('9013566508965471','2019');
//    ALTER TABLE fba_daily_inventory_history_report_mws ADD PARTITION 22019 VALUE ('9013566508965472','2019');
//    ALTER TABLE fba_daily_inventory_history_report_mws ADD PARTITION 32019 VALUE ('9013566508965473','2019');
//    ALTER TABLE fba_daily_inventory_history_report_mws ADD PARTITION 12020 VALUE ('9013566508965471','2020');
//    ALTER TABLE fba_daily_inventory_history_report_mws ADD PARTITION 22020 VALUE ('9013566508965472','2020');
//    ALTER TABLE fba_daily_inventory_history_report_mws ADD PARTITION 32020 VALUE ('9013566508965473','2020');
//    ALTER TABLE fba_daily_inventory_history_report_mws ADD PARTITION 12021 VALUE ('9013566508965471','2021');
//    ALTER TABLE fba_daily_inventory_history_report_mws ADD PARTITION 22021 VALUE ('9013566508965472','2021');
//    ALTER TABLE fba_daily_inventory_history_report_mws ADD PARTITION 32021 VALUE ('9013566508965473','2021');

    public static void doit(String ip, String Str) throws Exception {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)//一、连接超时：connectionTimeout-->指的是连接一个url的连接等待时间
                .setSocketTimeout(5000)// 二、读取数据超时：SocketTimeout-->指的是连接上一个url，获取response的返回等待时间
                .setConnectionRequestTimeout(5000)
                .build();
//        System.out.println(Str);
        HttpEntity reqEntity = new StringEntity(Str, "utf-8");
//        System.out.println(reqEntity);
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://" + ip + "/api/importData");
        post.setEntity(reqEntity);
        post.setConfig(requestConfig);
        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity resEntity = response.getEntity();
            String message = EntityUtils.toString(resEntity, "utf-8");
            System.out.println(message);
        } else {
            System.out.println("请求失败");
        }
    }

}
