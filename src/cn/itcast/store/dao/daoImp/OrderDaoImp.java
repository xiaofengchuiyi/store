package cn.itcast.store.dao.daoImp;

import cn.itcast.store.dao.OrderDao;
import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.Product;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.JDBCUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/13 9:03
 * @description：
 * @modified By：
 * @version: $
 */
   public class OrderDaoImp implements OrderDao {

    @Override
    public void saveOrder(Connection conn, Order order) throws SQLException {
        System.out.println("将订单开始放入数据库");
        String sql="insert into orders values(?,?,?,?,?,?,?,?)";
        QueryRunner queryRunner=new QueryRunner();       //用到事务，括号里面不需要参数
        System.out.println(order.getOid());
        System.out.println(order.getOrdertime());
        System.out.println(order.getTotal());
        System.out.println(order.getState());
        System.out.println(order.getAddress());
        System.out.println(order.getTelephone());
        System.out.println(order.getUser());
        System.out.println(order.getUser().getUid());
        Object[] params={order.getOid(),order.getOrdertime(),order.getTotal(),
            order.getState(),order.getAddress(),order.getName(),
            order.getTelephone(),order.getUser().getUid()};
        System.out.println("将订单正在放入数据库");
        queryRunner.update(conn,sql,params);
        System.out.println("将订单已经放入数据库");
    }

    @Override
    public void saveOrderItem(Connection conn, OrderItem item) throws SQLException {
        System.out.println("将订单项放入数据库");
        String sql="insert into orderitem values(?,?,?,?,?)";
        QueryRunner queryRunner=new QueryRunner();
        Object[] params={item.getItemid(),item.getQuantity(),item.getTotal(),
                item.getProduct().getPid(),item.getOrder().getOid()};
        queryRunner.update(conn,sql,params);
    }

    @Override
    public int getTotalRecords(User loginUser) throws SQLException {
        String sql="select count(*) from orders where uid=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        Long num=(Long)queryRunner.query(sql, new ScalarHandler(),loginUser.getUid());
        return num.intValue();
    }

    @Override
    public List findByUid(User loginUser, int startIndex, int pageSize) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql="select * from orders where uid=? order by ordertime limit ?,?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        List<Order> list=queryRunner.query(sql,new BeanListHandler<Order>(Order.class),loginUser.getUid(),startIndex,pageSize);

        //遍历所有订单
        for (Order order:list) {
            //获取到每笔订单oid    查询每笔订单下地订单项以及订单项对应的商品信息
            String oid=order.getOid();
            sql="select * from orderitem o,product p where o.pid=p.pid and oid=?";
            List<Map<String,Object>> list02=queryRunner.query(sql,new MapListHandler(), oid);
            //遍历list02
            for (Map<String,Object> map:list02) {
                OrderItem orderItem=new OrderItem();
                Product product=new Product();
                //由于BeanUtils将字符串"1992-3-3"向user对象地setBirthday();方法传递参数有问题，手动向BeanUtils注册一个时间转换器
                //1 创建时间类型地转换器
                DateConverter dt=new DateConverter();
                //2 设置转换的格式
                dt.setPattern("yyyy-MM-dd");
                //3 注册转换器
                ConvertUtils.register(dt,java.util.Date.class );

                //将map中属于orderItem的数据自动填充到orderItem对象上
                BeanUtils.populate(orderItem, map);
                //将map中属于product的数据自动填充到product对象上
                BeanUtils.populate(product,map );

                //重要
                //让每个订单项和商品发生关联关系
                orderItem.setProduct(product);
                //将每个订单项存入订单下的集合中
                order.getList().add(orderItem);

            }
        }
        return list;
    }

    @Override
    public Order findByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql="select * from orders  where oid=? order by ordertime ";
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        Order order=queryRunner.query(sql, new BeanHandler<Order>(Order.class),oid);

        //根据订单id查询订单下所有订单项及其订单项对应的商品信息
        sql="select * from orderitem o,product p where o.pid=p.pid and oid=?";
        List<Map<String,Object>> list=queryRunner.query(sql,new MapListHandler(), oid);
        //遍历list02
        for (Map<String,Object> map:list) {
            OrderItem orderItem=new OrderItem();
            Product product=new Product();
            //由于BeanUtils将字符串"1992-3-3"向user对象地setBirthday();方法传递参数有问题，手动向BeanUtils注册一个时间转换器
            //1 创建时间类型地转换器
            DateConverter dt=new DateConverter();
            //2 设置转换的格式
            dt.setPattern("yyyy-MM-dd");
            //3 注册转换器
            ConvertUtils.register(dt,java.util.Date.class );

            //将map中属于orderItem的数据自动填充到orderItem对象上
            BeanUtils.populate(orderItem, map);
            //将map中属于product的数据自动填充到product对象上
            BeanUtils.populate(product,map );

            //重要
            //让每个订单项和商品发生关联关系
            orderItem.setProduct(product);
            //将每个订单项存入订单下的集合中
            order.getList().add(orderItem);
        }
        return order;
    }

    @Override
    public void updateOrder(Order order) throws SQLException {
        String sql="update orders set ordertime=?,total=?,state=?,address=?,name=?,telephone=? where oid=? ";
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        Object[] params={order.getOrdertime(),order.getTotal(),
                order.getState(),order.getAddress(),order.getName(),
                order.getTelephone(),order.getOid(),};
        queryRunner.update(sql,params);
    }

}
