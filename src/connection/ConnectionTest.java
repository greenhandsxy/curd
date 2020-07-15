package connection;

import com.mysql.jdbc.Driver;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {

   //方式一
    @Test
    public void testConnection1() throws SQLException {

        //获取Driver实现类对象
        //用到了第三方
        Driver driver = new com.mysql.jdbc.Driver();

        //数据库
        String url = "jdbc:mysql://localhost:3306/jdbc?useUnicode=true&serverTimezone=GMT%2b8";

        //封装用户名密码
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");



        Connection conn = driver.connect(url,info);

        System.out.println(conn);

    }
    //方式二：对方式一的迭代:在如下的程序中不出现第三方的api，使得程序具有更好的可移植性
    //反射（动态性）
    @Test
    public void testConnection2() throws Exception {
        //1、获取Driver实现类对象，使用反射
        //替换方法一的那一行代码
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        //2、提供要连接的数据库
        String url = "jdbc:mysql://localhost:3306/jdbc?useUnicode=true&serverTimezone=GMT%2b8";

        //3、提供连接需要的用户名和密码
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");

        //4、获取连接
        Connection conn = driver.connect(url,info);

        System.out.println(conn);

    }


    //方式三:使用DriverManger替换Driver
    @Test
    public void testConnection3() throws Exception {

        //1、获取Driver实现类的对象
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        //2、提供另外三个连接的基本信息
        String url = "jdbc:mysql://localhost:3306/jdbc?useUnicode=true&serverTimezone=GMT%2b8";

        String user = "root";
        String password = "root";


        //注册驱动
        DriverManager.registerDriver(driver);

        //获取连接
        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);

    }

    //方式四:优化方式三,可以zhi
    @Test
    public void testConnection4() throws Exception {
        //1、提供三个连接的基本信息
        String url = "jdbc:mysql://localhost:3306/jdbc?useUnicode=true&serverTimezone=GMT%2b8";
        String user = "root";
        String password = "root";

        //2、加载Driver
          Class.forName("com.mysql.jdbc.Driver");
          //相较于方式三，可以省略如下操作
//        Driver driver = (Driver) clazz.newInstance();
//        //注册驱动
//        DriverManager.registerDriver(driver);



        //3、获取连接
        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);



    }

    //方式五：（fianl）将数据库连接需要的4个基本信息声明在配置文件中，通过读取配置文件的方式，获取连接
    /*
    * 好处：
    * 1、耦合性低，实现数据和代码的分离
    * 2、如果需要修改配置文件信息，可以避免程序重新打包
    *
    * */
    @Test
    public void getConnection5() throws Exception {

        //1、读取配置文件中的4个基本信息
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");

        //2、加载驱动
        Class.forName(driverClass);

        //3、获取连接
        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);


    }


//    public static final String url = "jdbc:mysql://localhost:3306/jdbc?useUnicode=true&serverTimezone=GMT%2b8";
//    public static final String user = "root";
//    public static final String password = "root";
//
//       public static  Connection conn = null;
//
//    static {
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//
//            conn = DriverManager.getConnection(url,user,password);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static Connection getConnection(){
//
//            return conn;
//
//    }

}

