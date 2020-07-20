package Response;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.Map;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
            // 执行请求
            Response response = call.execute();
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
        String url = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?aggr=1&cr=1&flag_qc=0&p=1&n=30&w=西海情歌&format=json";
        ApiAsker asker = new ApiAsker();
        String content = asker.getContent(url);

        System.out.println("查询结果文本：" + content);

        //JSON 格式字符串转换为 Map 对象
        Map map = JSON.parseObject(content,Map.class);

        System.out.println("JSON 格式字符串转换为 Map 对象");
    }
}
