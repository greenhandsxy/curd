package post;
import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonPoster {

    public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset = utf-8");

    /**
     * 向指定的 url 提交数据，以 json 的方式
     */
    public String postContent(String url, Map<String, String> datas) {
        // okHttpClient 实例
        OkHttpClient okHttpClient = new OkHttpClient();

        // 数据对象转换成 json 格式字符串
        String param = JSON.toJSONString(datas);
        //post方式提交的数据
        RequestBody requestBody = RequestBody.create(JSON_TYPE,param);
        Request request = new Request.Builder().post(requestBody).url(url).build();

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

    //注册
    public static void main(String[] args) {
        String url = "https://www.fastmock.site/mock/3d95acf3f26358ef032d8a23bfdead99/api/posts";
        Map<String, String> datas = new HashMap();
        datas.put("num","B19031518");
        datas.put("name","肖逸");
        datas.put("gender","男");
        datas.put("major","软件工程");
        datas.put("class","B190315");
        datas.put("startYear","2019");

        JsonPoster j = new JsonPoster();
        String content = j.postContent(url,datas);

        System.out.println("API调用结果");
        System.out.println(content);
    }
}
