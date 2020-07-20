package get;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ApiAsker {

    /**
     * 根据输入的url，读取页面内容并返回
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
        ApiAsker a = new ApiAsker();
        String url = "https://www.fastmock.site/mock/3d95acf3f26358ef032d8a23bfdead99/api/getJoke?page=1&count=2&type=video";

        String content = a.getContent(url);

        System.out.println("API调用结果");
        System.out.println(content);
    }
}

