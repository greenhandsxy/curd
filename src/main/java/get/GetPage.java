package get;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

public class GetPage {
    /**
     * 根据输入的url，读取页面内容并返回
     * 抓取南邮首页内容
     */
    public String getContent(String url) {
        // okHttpClient 实例
        OkHttpClient okHttpClient = new OkHttpClient();
        // 定义一个request
        Request request = new Request.Builder().url(url).build();
        // 使用client去请求
        Call call = okHttpClient.newCall(request);
        // 返回结果字符串
        String result = null;
        try {
            // 获得返回结果
            result = call.execute().body().string();
        } catch (IOException e) {
            // 抓取异常
            System.out.println("request " + url + " error . ");
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        GetPage getPage = new GetPage();
        String url = "http://www.njupt.edu.cn//";
        String content = getPage.getContent(url);

        System.out.println("call " + url + " , content=" + content);
    }
}


