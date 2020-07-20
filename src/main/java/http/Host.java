package http;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.Map;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Host {

    /**
     * 根据输入的url，读取页面内容并返回
     */
    public String getContent(String url) {
        // okHttpClient 实例
        OkHttpClient okHttpClient = new OkHttpClient();
        // 定义一个request
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1")
                .addHeader("Referer","http://sug.qianqian.com/")
                .addHeader("Host","sug.qianqian.com")
                .build();
        // 返回结果字符串
        String result = null;
        try {
            // 执行请求
            Response response = okHttpClient.newCall(request).execute();
            // 获取响应内容
            result = response.body().string();
        } catch (IOException e) {
            System.out.println("request " + url + " error . ");
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String url = "http://sug.qianqian.com/info/suggestion?format=json&version=2&from=0&word=刺心&third_type=0&client_type=0";
        Host asker = new Host();
        String content = asker.getContent(url);

        Map m = JSON.parseObject(content,Map.class);
        Map data = (Map)m.get("data");
        List song = (List)data.get("song");
        for (int i = 0;i<song.size();i++){
            Map map = (Map)song.get(i);
            String songname = (String)map.get("songname");
            String artistname = (String)map.get("artistname");
            System.out.println(songname+" - "+artistname);
        }

    }

}
