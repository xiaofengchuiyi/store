package cn.itcast.store.dao.daoImp;

import cn.itcast.store.dao.CategoryDao;
import cn.itcast.store.domain.Category;
import cn.itcast.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/10 17:02
 * @description：
 * @modified By：
 * @version: $
 */
public class CategoryDaoImp implements CategoryDao {

    @Override
    public List<Category> getAllCats() throws SQLException {
        String sql="select * from category";
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
    }

    @Override
    public void addCategory(Category c) throws SQLException {
        String sql="insert into category values (?,?)";
        QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
        qr.update(sql,c.getCid(),c.getCname());
    }
}
