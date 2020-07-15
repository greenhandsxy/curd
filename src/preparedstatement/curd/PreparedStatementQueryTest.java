package preparedstatement.curd;

import org.junit.Test;
import preparedstatement.curd.bean.Customer;
import preparedstatement.curd.bean.Order;
import util.JDBCUtil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 *  用PreparedStatement实现针对于不同表的通用的查询操作
 *
 */
public class PreparedStatementQueryTest {

    @Test
    public void testGetForList(){

        String sql = "select id,name,birth,email from customers where id < ?";
        List<Customer> customers = getForList(Customer.class, sql, 24);
//        System.out.println(customers);
//        customers.forEach(System.out::println);
        for(Customer customer :customers)
            System.out.println(customer);
    }


    /**
     *
     * @Description 针对于不同的表的通用的查询操作，返回表中的多条记录
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> getForList(Class<T> clazz, String sql, Object ...args){
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

            //创建集合对象
            ArrayList<T> list = new ArrayList<>();

            while (rs.next()){
                T t = clazz.newInstance();
                //处理结果集一行数据中的一个列：给t对象指定的属性赋值
                for (int i = 0;i < columnCount; i++) {
                    //获取每个列的列值，通过ResultSet
                    Object columnValue = rs.getObject(i + 1);
                    //通过ResultSetMetaData
                    //获取列的列名:getColumnName()  --不推荐使用
                    //获取列的别名：getColumnLabel()
//                    String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);


                    //通过反射，将t对象的指定名columnName的属性赋给指定的值columnValue
                    Field field = t.getClass().getDeclaredField(columnLabel);//
                    field.setAccessible(true);//保证可以访问
                    field.set(t,columnValue);//给叫order的对象的当前名为columnName的属性赋值为columValue
                }

                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn,ps,rs);
        }


        return null;
    }








    @Test
    public void testGetInstance(){

        String sql = "select birth,email from customers where id = ?";
        Customer customer = getInstance(Customer.class, sql, 21);
        System.out.println(customer);
        String sql1 = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id = ?";
        Order order = getInstance(Order.class, sql1, 1);
        System.out.println(order);
    }
    /**
     *
     * @Description 针对于不同的表的通用的查询操作，返回表中的一条记录
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */

    public <T> T getInstance(Class<T> clazz,String sql,Object ...args){
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
                T t = clazz.newInstance();
                for (int i = 0;i < columnCount; i++) {
                    //获取每个列的列值，通过ResultSet
                    Object columnValue = rs.getObject(i + 1);
                    //通过ResultSetMetaData
                    //获取列的列名:getColumnName()  --不推荐使用
                    //获取列的别名：getColumnLabel()
//                    String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);


                    //通过反射，将t对象的指定名columnName的属性赋给指定的值columnValue
                    Field field = t.getClass().getDeclaredField(columnLabel);//
                    field.setAccessible(true);//保证可以访问
                    field.set(t,columnValue);//给叫order的对象的当前名为columnName的属性赋值为columValue
                }

                return t;


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn,ps,rs);
        }


        return null;
    }



}
