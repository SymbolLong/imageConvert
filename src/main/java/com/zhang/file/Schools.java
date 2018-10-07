package com.zhang.file;

import com.zhang.file.util.CopyFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;

public class Schools {

    public static void main(String[] args) {
        String[] provinces = new String[]{"北京", "上海", "天津", "四川", "安徽", "江苏", "浙江", "辽宁", "山西", "福建", "广东", "广西", "海南", "河南", "湖南", "陕西", "湖北", "江西", "河北", "山东", "重庆", "青海", "吉林", "云南", "贵州", "甘肃", "宁夏", "新疆", "西藏", "内蒙古", "黑龙江"};
        for (String province : provinces) {
//            System.out.println("正在处理" + province);
//            handle(province, "幼儿园");
//            handle(province, "小学");
//            handle(province, "初中");
//            handle(province, "高中");
//            System.out.println(province + "处理完毕");
            System.out.println("正在删除" + province);
            remove(province, "幼儿园");
//            remove(province, "小学");
//            remove(province, "初中");
//            remove(province, "高中");
            System.out.println(province + "删除完毕");
            System.out.println();
        }
    }

    public static void remove(String province, String type) {
        try {
            CopyFile copyFile = new CopyFile();
            String path = "/Users/zhangsl/Downloads/bt/" + type + "/";
            File root = new File(path);
            File[] files = root.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    copyFile.delFolder(file.getAbsolutePath());
                } else if (file.getName().endsWith(".xls")) {
                    file.renameTo(new File("/Users/zhangsl/Downloads/bt/" + type + "/"+file.getName().replace("_result", "")));
                } else if (file.getName().endsWith(".txt") || file.getName().endsWith(".json")) {
                    file.delete();
                }
            }
            //copyFile.delFolder(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handle(String province, String type) {
        try {
            String city = "";
            String district = "";
            String path = "/Users/zhangsl/Downloads/" + type + "/" + province + "/";
            File target = new File(path + "/" + province + "_" + type + "_result.xls");
            if (target.exists()) {
                target.delete();
                target = new File(path + "/" + province + "_" + type + "_result.xls");
            }
            if (!type.equals("幼儿园")) {
                path += "xuexiao.eol.cn/";
            }
            FileUtils.writeStringToFile(target, "省份\t城市\t地区\t学校\t地址\t电话\r\n", "UTF-8", true);
            File root = new File(path);
            File[] files = root.listFiles();
            System.out.println(files.length);
            for (File file : files) {
                if (file.getName().equals("result.txt")) {
                    file.delete();
                    continue;
                }
                if (file.getName().equals(".DS_Store") || file.getName().equals(province + "_" + type + "_result.xls")) {
                    continue;
                }
                LineIterator lineIterator = FileUtils.lineIterator(file);
                JSONObject jsonObject = JSONObject.fromObject(lineIterator.nextLine());
                JSONArray array = jsonObject.getJSONArray("schools");
                for (int i = 0; i < array.size(); i++) {
                    JSONObject school = array.getJSONObject(i);
                    String name = school.getString("name");
                    String[] addresses = school.getString("address").split("：");
                    String address = addresses[1].replace("　", "").replace("邮编", "");
                    if (address.contains("省")) {
                        address = address.substring(address.indexOf("省") + 1);
                    }
                    if (address.contains("市")) {
                        city = address.substring(0, address.indexOf("市") + 1);
                        address = address.substring(address.indexOf("市") + 1);
                    }
                    if (address.contains("县")) {
                        district = address.substring(0, address.indexOf("县") + 1);
                    } else if (address.contains("区")) {
                        district = address.substring(0, address.indexOf("区") + 1);
                    }
                    address = city + address;
                    String tel = "";
                    if (addresses.length == 4) {
                        tel = addresses[3].replace("　", "");
                    }
                    FileUtils.writeStringToFile(target, province + "\t" + city + "\t" + district + "\t" + name + "\t" + address + "\t" + tel + "\r\n", "UTF-8", true);
                }
            }
            System.out.println(province + type + "结果处理完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
