package cn.itcast.store.service.serviceImp;

import cn.itcast.store.dao.CategoryDao;
import cn.itcast.store.dao.daoImp.CategoryDaoImp;
import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.utils.JedisUtils;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.List;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/10 17:01
 * @description：
 * @modified By：
 * @version: $
 */
public class CategoryServiceImp implements CategoryService {

    CategoryDao categoryDao=new CategoryDaoImp();

    @Override
    public List<Category> getAllCats() throws SQLException {
        return categoryDao.getAllCats();
    }

    @Override
    public void addCategory(Category c) throws SQLException {
        //本质是向Mysql插入一条数据
        categoryDao.addCategory(c);
        //更新redis缓存
        Jedis jedis= JedisUtils.getJedis();
        jedis.del("allCats");
        JedisUtils.closeJedis(jedis);
    }
}
