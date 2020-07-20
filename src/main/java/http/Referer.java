package http;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Referer {

    /**
     * 根据输入的url，读取页面内容并返回
     */
    public void getContent(String url) {
        // okHttpClient 实例
        OkHttpClient okHttpClient = new OkHttpClient();
        // 定义一个request
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Referer","http://photo.yupoo.com/")
                .build();
        try {
            // 执行请求
            Response response = okHttpClient.newCall(request).execute();
            // 打印最终响应的地址
            String realUrl = response.request().url().toString();
            System.out.println("最终响应地址：" + realUrl);
            // 打印状态码
            int code = response.code();
            System.out.println("状态码：" + code);
        } catch (IOException e) {
            // 抓取异常
            System.out.println("request " + url + " error . ");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "http://photo.yupoo.com/vibius/GkRSowXr/medish.jpg";
        Referer asker = new Referer();
        asker.getContent(url);
    }
}

