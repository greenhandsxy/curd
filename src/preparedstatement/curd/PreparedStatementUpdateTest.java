package preparedstatement.curd;
/*
* 使用PreparedStatement实现对数据表的增删改操作
*
* */


import connection.ConnectionTest;
import org.junit.Test;
import util.JDBCUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class PreparedStatementUpdateTest {

    //向customers表中添加一条记录
    @Test
    public void testInsert() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            //1、读取配置文件中的4个基本信息
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

            Properties pros = new Properties();
            pros.load(is);

            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");

            //2、加载驱动
            Class.forName(driverClass);

            //3、获取连接
            conn = DriverManager.getConnection(url,user,password);
//            System.out.println(conn);


            //4、预编预处理sql语句，返回P S的实例
            String sql = "insert into jdbc.customers(name, email, birth)values (?,?,current_date())";//?是占位符
            ps = conn.prepareStatement(sql);
            //5、填充占位符
            ps.setString(1,"哪吒");
            ps.setString(2,"nezha@gmial.com");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date data = sdf.parse("1000-01-01");
//        ps.setString(3, String.valueOf(new Date(data.getTime())));

            //6、执行sql操作，PreparedStatement是信使
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        //7、资源的关闭，连接&PS
            try {
                if (ps != null)
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null)
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    }
    @Test

    public void testDel()  {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

            Properties pros = new Properties();

            pros.load(is);

            conn = JDBCUtil.getConnection();

            String sql = "delete from customers where id=?";

            ps = conn.prepareStatement(sql);
            ps.setString(1,"19");

            ps.execute();
        } catch (Exception e) {
             e.printStackTrace();
        }

        JDBCUtil.closeResource(conn,ps);


    }

    @Test
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            //1、获取数据库连接
            conn = JDBCUtil.getConnection();


            //2、编译预处理sql语句，返回PreparedStament的实例
            String sql = "update customers set name = ? where id = ?";
            ps = conn.prepareStatement(sql);

            //3、填充占位符
            ps.setObject(1,"pplai熊");
            ps.setInt(2,21);

            //4、执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        //5、资源的关闭
        JDBCUtil.closeResource(conn,ps);
        }






    }




    //通用的增删改操作
    public void update(String sql,Object ...args)  {
        Connection conn = null;
        PreparedStatement ps = null;
        try {

            conn = JDBCUtil.getConnection();

            ps = conn.prepareStatement(sql);

            //sql中占位符个数应与可变形参的长度相同
            for(int i = 0;i<args.length;i++){
                ps.setObject(i+1,args[i]);//小心参数声明错误
            }

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        JDBCUtil.closeResource(conn,ps);

        }

    }

    @Test
    public void testCommonUptate(){
        String sql = "update `ppp` set name = ?,age = ? where id = ?";
        update(sql,"小丽","13",1);


    }


}
