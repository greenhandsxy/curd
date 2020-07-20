package http;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class User_Agent {

    /**
     * 根据输入的url，读取页面内容并返回
     */
    public String getContent(String url) {
        // okHttpClient 实例
        OkHttpClient okHttpClient = new OkHttpClient();
        // 定义一个request
        Request request = new Request.Builder()
                .url(url)
                // 添加一个 header 信息
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1")
                .build();
        // 返回结果字符串
        String result = null;
        try {
            // 执行请求
            Response response = okHttpClient.newCall(request).execute();
            // 获取响应内容
            result = response.body().string();
        } catch (IOException e) {
            // 抓取异常
            System.out.println("request " + url + " error . ");
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String url = "https://www.baidu.com/";
        User_Agent asker = new User_Agent();
        String content = asker.getContent(url);

        System.out.println("查询结果文本：" + content);
    }
}