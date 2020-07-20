package Response;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetPage {

    /**
     * 根据输入的url，读取页面内容并返回状态码
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
            // 执行请求
            Response rep = call.execute();
            // 获取响应状态码
            int code = rep.code();

            System.out.println("状态码：" +code );
            // 获取响应内容
            result = rep.body().string();
        } catch (IOException e) {
            // 抓取异常
            System.out.println("request " + url + " error . ");
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String url = "https://www.baidu.com/";
        GetPage getPage = new GetPage();
        String content = getPage.getContent(url);

        System.out.println("页面请求结果：");
        System.out.println(content);
    }
}
