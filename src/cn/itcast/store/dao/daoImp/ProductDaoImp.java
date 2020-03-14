package cn.itcast.store.dao.daoImp;

import cn.itcast.store.dao.ProductDao;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.utils.JDBCUtils;
import jdk.nashorn.internal.scripts.JD;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/11 11:55
 * @description：
 * @modified By：
 * @version: $
 */
public class ProductDaoImp implements ProductDao {

    @Override
    public List<Product> findByHot() throws SQLException {
        String sql="select * from product where is_hot=? and pflag=? order by pdate desc limit ?,?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql, new BeanListHandler<Product>(Product.class),1,0,0,9);
    }

    @Override
    public List<Product> findByNew() throws SQLException {
        String sql="select * from product where pflag=? order by pdate desc limit ?,?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql, new BeanListHandler<Product>(Product.class),0,0,9);
    }

    @Override
    public Product findById(String pid) throws SQLException {
        String sql="select * from product where pid=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql, new BeanHandler<Product>(Product.class),pid);
    }

    @Override
    //查询符合cid的总个数
    public int findTotalRecordByCid(String cid) throws SQLException {
        String sql="select count(*) from product where cid=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        Long count=(long)queryRunner.query(sql,new ScalarHandler(),cid);
        return count.intValue();
    }

    @Override
    //查询指定分类的数据内容
    public List<Product> findByCid(String cid, int startIndex, int pageSize) throws SQLException {
        System.out.println("########");
        String sql="select * from product where cid=? and pflag=? order by pdate desc limit ?,? ";
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        List<Product> list=queryRunner.query(sql,new BeanListHandler<Product>(Product.class),cid,0,startIndex,pageSize);
        return list;
    }

    //后台查询商品总数
    @Override
    public int findTotalRecords() throws SQLException {
        String sql="select count(*) from product";
        QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
        Long num=(Long)qr.query(sql, new ScalarHandler());
        return num.intValue();
    }

    //后台查询商品分页信息
    @Override
    public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws SQLException {
        String sql="select * from product limit ?,?";
        QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql,new BeanListHandler<Product>(Product.class),startIndex,pageSize);
    }
}
