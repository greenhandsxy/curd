package preparedstatement.curd;

import org.junit.Test;
import preparedstatement.curd.bean.Order;
import util.JDBCUtil;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * 针对于Order表的通用的查询操作
 *
 *
 */

public class OrderForQuery{

    /**
     * 针对于表的字段名与类的属性名不相同的情况：
     * 1、必须声明sql时，使用类的属性名命名字段的别名
     * 2、使用ResultSetMetaData时，需要使用getColumnLabel()来替换getColumnName(),获取列的别名
     *
     *说明：如果sql中没有给字段起别名，getColumnLabel()获取的就是列名
     *
     */
    public Order orderForQuery(String sql , Object ...args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtil.getConnection();

            ps = conn.prepareStatement(sql);

            for (int i = 0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }

            //执行、获取结果集
            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取列数
            int columnCount = rsmd.getColumnCount();

            if (rs.next()){
                Order order = new Order();
                for (int i = 0;i < columnCount; i++) {
                    //获取每个列的列值，通过ResultSet
                    Object columnValue = rs.getObject(i + 1);
                    //通过ResultSetMetaData
                    //获取列的列名:getColumnName()  --不推荐使用
                    //获取列的别名：getColumnLabel()
//                    String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);


                    //通过反射，将对象的指定名columnName的属性赋给指定的值columValue
                    Field field = Order.class.getDeclaredField(columnLabel);//
                    field.setAccessible(true);//保证可以访问
                    field.set(order,columnValue);//给叫order的对象的当前名为columnName的属性赋值为columValue
                }

                return order;


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        JDBCUtil.closeResource(conn,ps,rs);
        }


        return null;
    }

    @Test
    public void testOrderForQuery(){
        String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id = ? ";
        Order order = orderForQuery(sql, 1);
        System.out.println(order);

    }




@Test
public  void testQuery1() {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
        conn = JDBCUtil.getConnection();

        String sql = "select order_id,order_name,order_date from `order` where order_id = ?";
        ps = conn.prepareStatement(sql);

        ps.setObject(1, 2);

        rs = ps.executeQuery();
        if (rs.next()) {

            int orderId = rs.getInt(1);
            String orderName = rs.getString(2);
            Date orderDate = rs.getDate(3);

            Order order = new Order(orderId, orderName, orderDate);
            System.out.println(order);


        }
    } catch (Exception e) {
        e.printStackTrace();
    }finally {

    JDBCUtil.closeResource(conn,ps,rs);

    }

}


}
