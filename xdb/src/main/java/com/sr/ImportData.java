package com.sr;

import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.lucene.util.RamUsageEstimator;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;


public class ImportData {
    public static List<byte[]> recordList = new ArrayList<>();
    public static long recordLength = 0L;

    public static long batchNum;


    public static void main(String[] args) {
        /**
         *  1 million records have 750MB data packet, make run on 10.49.1.34,  network rate is  30+MB/s (270Mb+/s), need cost 25s to transmission.
         */
        // LB proxy connection
        // String we_http_service = "10.49.19.15:80";    // 98s

        // random direct other connection(non comp_id=1 partition work node)
        // String we_http_service = "10.49.25.45:9877";     // 318s

        //  direct connection comp_id=1 partition work node
        String we_http_service = "10.49.24.135:9877"; // 95s
        String loadUrl = String.format("http://%s/api/importData", we_http_service);
        batchNum = 100 * 10000;
        for (int i = 0; i < batchNum; i++) {
            JSONObject singleData = mockSingleData(i + 1);
            byte[] recordByte = singleData.toJSONString().getBytes(StandardCharsets.UTF_8);
            recordLength += recordByte.length;
            recordList.add(recordByte);
        }
        long sendBefore = System.currentTimeMillis();
        send(loadUrl);
        long sendAfter = System.currentTimeMillis();
        System.out.println("send load request cost time " + (sendAfter - sendBefore) / 1000 + "s");
    }

    public static void send(String loadUrl) {
        try {
            CloseableHttpClient httpClient = getHttpClient();
            // HttpPut httpPut = new HttpPut(loadUrl);
            HttpPost httpPost = new HttpPost(loadUrl);
            ByteBuffer bos = ByteBuffer.allocate((int) recordLength + (recordList.size() + 1));
            bos.put("[".getBytes(StandardCharsets.UTF_8));
            boolean isFirstElement = true;
            byte[] jsonDelimiter = ",".getBytes(StandardCharsets.UTF_8);
            for (byte[] record : recordList) {
                if (!isFirstElement) {
                    bos.put(jsonDelimiter);
                }
                bos.put(record);
                isFirstElement = false;
            }
            bos.put("]".getBytes(StandardCharsets.UTF_8));
            // System.out.println(new String(bos.array()));
            // reduce usage of jvm heap space
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(bos.array());
            System.err.println("The current batch size is " + batchNum + " records, memory usage is " + RamUsageEstimator.humanSizeOf(bos.array()));
            // httpPut.setEntity(byteArrayEntity);
            httpPost.setEntity(byteArrayEntity);
            //  exec http load request
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity resEntity = response.getEntity();
                    String message = EntityUtils.toString(resEntity, "utf-8");
                    System.out.println(message);
                } else {
                    System.out.println("请求失败");
                }
            } finally {
                httpClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient heepClient = HttpClients
                .custom()
                .setRedirectStrategy(new DefaultRedirectStrategy() {
                    @Override
                    protected boolean isRedirectable(String method) {
                        return true;
                    }
                })
                .build();
        return heepClient;
    }


    public static JSONObject mockSingleData(int no) {
        // PK
        String v_uuid = UUID.randomUUID().toString();
        // 一级分区: company_id
        String company_id = "9000000000000001";
        // 二级分区: 时间范围 2017 -> 2026
        LocalDate date_partition = LocalDate.of(
                // 2017,
                new Random().nextInt(10) + 2017,
                new Random().nextInt(12) + 1,
                1);

        JSONObject rowJson = new JSONObject();
        // 外层元数据
        rowJson.put("_op", "I");
        rowJson.put("_db", "erp");
        rowJson.put("_tbl", "fba_daily_inventory_history_report_mws");
        rowJson.put("_v_uuid", v_uuid);
        rowJson.put("_company_id", company_id);
        rowJson.put("_no", String.valueOf(no));

        // 内层数据体
        JSONObject data = new JSONObject();
        data.put("v_uuid", v_uuid);
        data.put("company_id", company_id);//VARCHAR(100) 固定3区
        data.put("id", "356949313");//BIGINT
        data.put("zid", "11");//INT
        data.put("sid", "51");//INT
        data.put("snapshot_date", "2022-04-06 07:00:00");//VARCHAR(100)
        data.put("snapshot_date_locale", "2019-11-01T00:00:00-07:00");//VARCHAR(100)
        data.put("snapshot_date_timestamp", "1572591600");//VARCHAR(100),
        data.put("snapshot_date_report", date_partition);//DATETIME  固定3区
        // data.put("snapshot_date_report","2019-11-01T00:00:00-07:00");//DATETIME  固定3区
        data.put("fnsku", "FNA067D5E");//VARCHAR(100),
        data.put("sku", "MSKUECDE6BE");//VARCHAR(100),
        data.put("product_name", "[演示数据]3件装保护套友好型钢化玻璃膜");//VARCHAR(100) 汉
        data.put("quantity", "1");//INT
        data.put("fulfillment_center_id", "AKS1");//VARCHAR(100)
        data.put("detailed_disposition", "SELLABLE");//VARCHAR(100)
        data.put("country", "US");//VARCHAR(100)
        data.put("unique_index", "3569493");//INT
        data.put("gmt_modified", "2020-05-12 18:55:29");//DATETIME
        data.put("gmt_create", "2019-11-04 21:23:05");//DATETIME

        rowJson.put("_data", data);
        return rowJson;
    }


}

