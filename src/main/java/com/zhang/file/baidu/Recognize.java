package com.zhang.file.baidu;

import com.baidu.aip.speech.AipSpeech;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

public class Recognize {
    //设置APPID/AK/SK
    public static final String APP_ID = "11439582";
    public static final String API_KEY = "Ffh85cce9eVdewsOM4yFiDWE";
    public static final String SECRET_KEY = "sLcQ6N5IPLYpXhuMj69OVyjIbRy6eN17";

    private static AipSpeech aipSpeech;

    public static AipSpeech getAipSpeech() {
        if (aipSpeech == null) {
            aipSpeech = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        }
        return aipSpeech;
    }

    //识别纯英文
    public static void recognizeEnglish(String path, String target, Integer rate) {
        recognize(path, target, 1737, rate);
    }

    //识别普通话
    public static void recognizeChinese(String path, String target, Integer rate) {
        recognize(path, target, 1537, rate);
    }

    //识别粤语
    public static void recognizeCantonese(String path, String target, Integer rate) {
        recognize(path, target, 1637, rate);
    }

    //识别四川话
    public static void recognizeSichuan(String path, String target, Integer rate) {
        recognize(path, target, 1837, rate);
    }

    private static void recognize(String path, String target, int devPid, Integer rate) {
        try {
            HashMap<String, Object> options = new HashMap<String, Object>();
            options.put("dev_pid", devPid);
            if (rate == null) {
                rate = 8000;
            }
            JSONObject res = getAipSpeech().asr(path, "amr", rate, options);
            StringBuffer result = new StringBuffer(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + path.substring(path.lastIndexOf("/")) + "\t");
            if (res.has("err_no") && res.getInt("err_no") == 0) {
                result.append(res.getJSONArray("result").get(0) + "\r\n");
                File file = new File(target);
                FileUtils.writeStringToFile(file, result.toString(), "UTF-8", true);
            } else {
                result.append(res.toString());
                System.out.println(result.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        recognizeEnglish("/Users/zhangsl/Downloads/01.amr", "/Users/zhangsl/Downloads/01.txt", 8000);
        recognizeEnglish("/Users/zhangsl/Downloads/02.amr", "/Users/zhangsl/Downloads/02.txt", 8000);
    }
}
