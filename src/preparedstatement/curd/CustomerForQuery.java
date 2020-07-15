package preparedstatement.curd;

/*
*
* 针对于Customers表的查询操作
*
* */


import org.junit.Test;
import preparedstatement.curd.bean.Customer;
import util.JDBCUtil;

import java.lang.reflect.Field;
import java.sql.*;

public class CustomerForQuery {

    /**
     * 针对于customers表的通用的查询操作
     */
    public Customer queryForCustomers( String sql,Object ...args)  {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
             conn = JDBCUtil.getConnection();

             ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

             rs = ps.executeQuery();

            //获取结果集的元数据：ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();     //重点

            if (rs.next()) {
                Customer cust = new Customer();

                //处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {

                    //获取列值
                    Object columValue = rs.getObject(i + 1);

                    //获取每个列的列名
                    String columnName = rsmd.getColumnName(i + 1);     //重点

                    //给cust对象指定的某个属性，赋值为columValue,通过反射    //重点
                    Field field = Customer.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(cust, columValue);
                }

                return cust;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeResource(conn,ps,rs);
        }

        return null;
    }
    @Test
    public void testQueryForCustomers(){
        String sql = "select birth,email from customers where id = ?";
        Customer customer = queryForCustomers(sql, 12);
        System.out.println(customer);
    }





    @Test
    public void testQuery1() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtil.getConnection();

            String sql = "select * from customers where  name = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,"哪吒");


            //执行并返回结果集
            resultSet = ps.executeQuery();
            //处理结果集

            //next():判断结果集的下一条是否有数据，如果有数据返回true,并指针下移，如果返回false，指针不下移
            if (resultSet.next()){

                //获取当前这条数据的各个字段
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);


                //封装数据
                //方式一
              //  System.out.println("一条条输出");

                //方式二
    //            Object[] data = new Object[]{id,name,email,birth};

                //方式三：将数据封装为一个对象
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        //关闭资源
        JDBCUtil.closeResource(conn,ps, resultSet);

        }



    }

}
