package com.sr;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class CreateData {
    static List<String> company_ids = new ArrayList<>();
    static List<String> snapshot_date_report = new ArrayList<>();
    static List<String> product_name = new ArrayList<>();
    public static void init(){
        company_ids.add("9013566508965471");
        company_ids.add("9013566508965472");
        company_ids.add("9013566508965473");


        snapshot_date_report.add("2019-11-01");
        snapshot_date_report.add("2020-11-01");
        snapshot_date_report.add("2021-11-01");


        product_name.add("[演示数据]3件装保护套友好型钢化玻璃膜");
        product_name.add("[演示数据]创建对象 Random r = new Random()");
        product_name.add("[演示数据]无静态字段（No Static field）");
        product_name.add("[演示数据]的。 由于静态变量在对象之间共享，因此编译器无法确定要使用的类型。 如果允许静态类型参数，请考");
        product_name.add("[演示数据]表单提交（带附件）\n" +
                "     *\n" +
                "     *");
        product_name.add("[演示数据]se response = null;\n" +
                "        MultipartEntityBuilder");
        product_name.add("[演示数据]ost请求\n" +
                "            HttpPost httpPost =");
    }

    public static void main(String[] args) {
//        dodata(10);
    }

    public static JSONArray INSERT(String batchCode,int c,int no){
        JSONArray dataList = new JSONArray();
        Random r = new Random();
        for(int i=0;i<c;i++){
            JSONObject json = new JSONObject();
            json.put("_op","I");
            json.put("_db","xdb");
//            json.put("_db","dts");
            json.put("_tbl","fba_daily_inventory_history_report_mws");
            json.put("_no","" + (no+i));
            JSONObject data = new JSONObject();

            data.put("v_uuid", batchCode + i);//VARCHAR(100) pk
            data.put("company_id",company_ids.get(i%company_ids.size()));//VARCHAR(100) 固定3区
            data.put("id","356949313");//BIGINT
            data.put("zid","11");//INT
            data.put("sid","51");//INT
            data.put("snapshot_date","2022-04-06 07:00:00");//VARCHAR(100)
            data.put("snapshot_date_locale","2019-11-01T00:00:00-07:00");//VARCHAR(100)
            data.put("snapshot_date_timestamp","1572591600");//VARCHAR(100),
             data.put("snapshot_date_report",snapshot_date_report.get(r.nextInt(snapshot_date_report.size())));//DATETIME  固定3区
//            data.put("snapshot_date_report","2019-11-01T00:00:00-07:00");//DATETIME  固定3区
            data.put("fnsku","FNA067D5E");//VARCHAR(100),
            data.put("sku","MSKUECDE6BE");//VARCHAR(100),
            data.put("product_name",product_name.get(r.nextInt(product_name.size())));//VARCHAR(100) 汉
            data.put("quantity","1");//INT
            data.put("fulfillment_center_id","AKS1");//VARCHAR(100)
            data.put("detailed_disposition","SELLABLE");//VARCHAR(100)
            data.put("country","US");//VARCHAR(100)
            data.put("unique_index","3569493");//INT
            data.put("gmt_modified","2020-05-12 18:55:29");//DATETIME
            data.put("gmt_create","2019-11-04 21:23:05");//DATETIME

            json.put("_data",data);
            dataList.add(json);
//            createJsonFile(json,url);
        }
        return dataList;
    }

    public static JSONArray UPDATE1(String batchCode,int c,int no){
        JSONArray dataList = new JSONArray();
//        String url = "D:\\IdeaProjects\\fe2.3.0\\data\\" + c;
//
//        delete(url);
        for(int i=0;i<c;i++){
            JSONObject json = new JSONObject();
            json.put("_op","U");
            json.put("_db","xdb");
            json.put("_tbl","fba_daily_inventory_history_report_mws");
            json.put("_no","" + (no + i));
            JSONObject data = new JSONObject();

            data.put("v_uuid", batchCode + i);//VARCHAR(100) pk
            data.put("company_id",company_ids.get(i%company_ids.size()));//VARCHAR(100) 固定3区
            data.put("snapshot_date_report","2019-01-01");//BIGINT
            json.put("_data",data);
            dataList.add(json);
//            createJsonFile(json,url);
        }
        return dataList;
    }

    public static JSONArray UPDATE2(String batchCode,int c,int no){
        JSONArray dataList = new JSONArray();
        Random r = new Random();
//        String url = "D:\\IdeaProjects\\fe2.3.0\\data\\" + c;
//
//        delete(url);
        for(int i=0;i<c;i++){
            JSONObject json = new JSONObject();
            json.put("_op","U");
            json.put("_db","xdb");
            json.put("_tbl","fba_daily_inventory_history_report_mws");
            json.put("_no","" + (no + i));
            JSONObject data = new JSONObject();

            data.put("v_uuid", batchCode + i);//VARCHAR(100) pk
            data.put("company_id",company_ids.get(i%company_ids.size()));//VARCHAR(100) 固定3区
            data.put("id","" + i);//BIGINT
            json.put("_data",data);
            dataList.add(json);
//            createJsonFile(json,url);
        }
        return dataList;
    }

    public static JSONArray DELETE(String batchCode,int c,int no){
        JSONArray dataList = new JSONArray();
//        String url = "D:\\IdeaProjects\\fe2.3.0\\data\\" + c;
//
//        delete(url);
        for(int i=0;i<c;i++){
            JSONObject json = new JSONObject();
            json.put("_op","D");
            json.put("_db","xdb");
            json.put("_tbl","fba_daily_inventory_history_report_mws");
            json.put("_no","" + (no + i));
            JSONObject data = new JSONObject();

            data.put("v_uuid", batchCode + i);//VARCHAR(100) pk
            data.put("company_id",company_ids.get(i%company_ids.size()));//VARCHAR(100) 固定3区

            json.put("_data",data);
            dataList.add(json);
//            createJsonFile(json,url);
        }
        return dataList;
    }
    /**
     * 生成.json格式文件
     */
    public static boolean createJsonFile(JSONObject json, String filePath) {
        // 标记文件生成是否成功
        boolean flag = true;
        FileOutputStream fos = null;
        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(filePath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                boolean hasFile = file.createNewFile();
                fos = new FileOutputStream(file);
            } else {
                fos = new FileOutputStream(file, true);
            }
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(fos, "utf-8");
//            write.write(json.toJSONString());
            write.write(json.toJSONString());
            write.write("\r\n");
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        // 返回是否成功的标记
        return flag;
    }
    public static void delete(String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
            file.getParentFile().mkdirs();
        }
        if (file.exists()) { // 如果已存在,删除旧文件
            file.delete();
        }
    }
}
