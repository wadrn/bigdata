package com.example.generate.util;

import com.alibaba.fastjson.JSON;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class MockData extends Thread{


    public static void main(String[] args) {
        for (int i=0;i<100;i++){
            new MockData().start();
        }
    }

    private static String trafficGen(Random random, int i) {
        String flow;
        if (i % 5 == 0) {
            flow = "~~~~~";
        } else {
            flow = random.nextInt(10000) + "";
        }
        return flow;
    }

    private static String platformGen(Random random) {
        String[] platforms = {"Windows", "Raspberry Pi", "Android", "IOS"};
        return platforms[random.nextInt(platforms.length)];
    }

    private static String versionGen(Random random) {
        String[] versions = {"1.2.0", "1.2.1", "1.2.2", "1.3.0"};
        return versions[random.nextInt(versions.length)];
    }

    @Override
    public void run() {
        Random random = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = LocalDateTime.now().format(formatter);
        for (int i = 1; i >0; i++) {
            StringBuilder result = new StringBuilder();
            result.append("{");
            String user = "ruozedata" + random.nextInt(100);
            String platform = platformGen(random);
            String version = versionGen(random);
            String ip = random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256);
            String traffic = trafficGen(random, i);
            result.append("\"user\":").append("\"").append(user).append("\"").append(",")
                    .append("\"platform\":").append("\"").append(platform).append("\"").append(",")
                    .append("\"version\":").append("\"").append(version).append("\"").append(",")
                    .append("\"ip\":").append("\"").append(ip).append("\"").append(",")
                    .append("\"traffic\":").append("\"").append(traffic).append("\"").append(",")
                    .append("\"time\":").append("\"").append(time).append("\"").append(",")
                    .append("\"duration\":").append("\"").append(random.nextInt(100)).append("\"").append(",")
                    .append("\"appId\":").append("\"").append(random.nextInt(5) + 1).append("\"").append("}");
            String json = JSON.toJSONString(JSON.parseObject(result.toString()));
//            System.out.println(json);
            HttpUtil.doPost("http://hadoop001:7777", json);
        }
    }

}
