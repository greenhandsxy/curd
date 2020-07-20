package Response;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageAsker {

    /**
     * 根据输入的url，读取页面内容并返回图片字节大小
     */
    public void getImage(String url) {
        // okHttpClient 实例
        OkHttpClient okHttpClient = new OkHttpClient();
        // 定义一个request
        Request request = new Request.Builder().url(url).build();
        try {
            // 执行请求
            Response response = okHttpClient.newCall(request).execute();
            byte[] bytes = response.body().bytes();
            System.out.println("图片大小为： " + bytes + " 字节");
        } catch (IOException e) {
            // 抓取异常
            System.out.println("request " + url + " error . ");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "https://style.youkeda.com/img/ham/course/py2/douban2.png";
        ImageAsker asker = new ImageAsker();
        asker.getImage(url);
    }
}
