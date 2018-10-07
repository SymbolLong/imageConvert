package com.zhang.file;

import com.zhang.file.util.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;

public class MiddleSchool {

    public static void main(String[] args) {
        String city = "";
        String district = "";
        String te = "北京市朝阳区安贞西里二区18楼";
        if (te.contains("市")) {
            city = te.substring(0, te.indexOf("市")+1);
            te = te.substring(te.indexOf("市") +1 );
        }
        if (te.contains("区")) {
            district = te.substring(0, te.indexOf("区")+1);
            te = te.substring(te.indexOf("区")+1);
        }
        System.out.println(city);
        System.out.println(district);
        System.out.println(city + district + te);
    }

    public static void load() {
        try {
            String path = "/Users/zhangsl/Downloads/schools.txt";
            File file = new File(path);
            FileUtils.writeStringToFile(file, "省份\t城市\t学校\r\n", "UTF-8", true);
            HttpUtil httpUtil = new HttpUtil();
            JSONArray array = getCity();
            int total = array.size();
            for (int i = 0; i < array.size(); i++) {
                JSONObject province = array.getJSONObject(i);
                JSONArray cities = province.getJSONArray("cities");
                for (int j = 0; j < cities.size(); j++) {
                    JSONObject city = cities.getJSONObject(j);
                    System.out.println(city.getString("name") + "正在收集中学部" + i + "/" + total);
                    Thread.sleep(2000);
                    String response = httpUtil.doGet("https://api.shensz.cn/api/1/school/get_school_by_city?city_id=" + city.getInt("id"));
                    JSONObject jsonObject = JSONObject.fromObject(response);
                    JSONArray schools = jsonObject.getJSONObject("data").getJSONArray("schools");
                    for (int k = 0; k < schools.size(); k++) {
                        JSONObject school = schools.getJSONObject(k);
                        FileUtils.writeStringToFile(file, province.getString("name") + "\t" + city.getString("name") + "\t" + schools.getJSONObject(k).getString("name") + "\r\n", "UTF-8", true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static JSONArray getCity() {
        try {
            HttpUtil httpUtil = new HttpUtil();
            String response = httpUtil.doGet("https://api.shensz.cn/api/1/city/province_cities");
            JSONObject jsonObject = JSONObject.fromObject(response);
            JSONArray provinces = jsonObject.getJSONObject("data").getJSONArray("provinces");
            for (int i = 0; i < provinces.size(); i++) {
                JSONObject province = provinces.getJSONObject(i);
                province.remove("status");
                province.remove("pinyin");
                province.remove("search_keyword");
                JSONArray cities = province.getJSONArray("cities");
                if (cities.isEmpty()) {
                    continue;
                }
                JSONArray array2 = new JSONArray();
                for (int j = 0; j < cities.size(); j++) {
                    JSONObject city = cities.getJSONObject(j);
                    city.remove("parent_id");
                    city.remove("pinyin");
                    city.remove("search_keyword");
                }
            }
            return provinces;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
