package cn.itcast.store.service.serviceImp;

import cn.itcast.store.dao.ProductDao;
import cn.itcast.store.dao.daoImp.ProductDaoImp;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import redis.clients.jedis.BinaryClient;

import java.sql.SQLException;
import java.util.List;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/11 11:54
 * @description：
 * @modified By：
 * @version: $
 */
public class ProductServiceImp implements ProductService {

    @Override
    public List<Product> findByHot() throws SQLException {
        return new ProductDaoImp().findByHot();
    }

    @Override
    public List<Product> findByNew() throws SQLException {
        return new ProductDaoImp().findByNew();
    }

    @Override
    public Product findById(String pid) throws SQLException {
        return new ProductDaoImp().findById(pid);
    }

    @Override
    public PageModel findByCid(String cid, int pageNumber, int pageSize) throws SQLException {
        System.out.println("-----");
        //1 获得总记录数
        int totalRecord=new ProductDaoImp().findTotalRecordByCid(cid);
        //2 封装数据
        PageModel pm=new PageModel(pageNumber,totalRecord,pageSize );
        //3 分页数据
        List<Product> list=new ProductDaoImp().findByCid(cid,pm.getStartIndex(),pm.getPageSize());
        pm.setList(list);
        //4 关联url
        pm.setUrl("ProductServlet?method=findByCid&cid="+cid);


        return pm;
    }

    @Override
    public PageModel findAllProductsWithPage(int curNum) throws SQLException {
        //1.创建对象
        int totalRecords= new ProductDaoImp().findTotalRecords();
        PageModel pm=new PageModel(curNum, totalRecords, 5);
        //2.关联集合 select * from product limit ？，？
        List<Product> list=new ProductDaoImp().findAllProductsWithPage(pm.getStartIndex(),pm.getPageSize());
        pm.setList(list);
        //3.关联url
        pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
        return pm;
    }


}
