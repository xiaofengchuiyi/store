package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.utils.JedisUtils;
import cn.itcast.store.web.base.BaseServlet;

/*import net.sf.json.JSONArray;*/
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import redis.clients.jedis.Jedis;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/10 16:59
 * @description：
 * @modified By：
 * @version: $
 */
public class CategoryServlet extends BaseServlet {

    //查询所有的分类信息
    public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        //在redis中获取全部分类信息
        Jedis jedis= JedisUtils.getJedis();
        String jsonStr=jedis.get("allCats");
        if (null==jsonStr ||"".equals(jsonStr)){
            //调取业务层获取全部分类
            CategoryService categoryService=new CategoryServiceImp();
            List<Category> list= categoryService.getAllCats();
            System.out.println(list);

            //将全部数据转换为JSON格式的数据
            //String jsonStr= JSONArray.fromObject(list).toString();        //json
            jsonStr= JSONArray.toJSONString(list);    //fastjson
            System.out.println(jsonStr);
            //将获取到的JSON格式的数据存入redis
            jedis.set("allCats",jsonStr);

            System.out.println("redis缓存中没有数据");
            //将全部分类信息响应到客户端
            //告诉浏览器本次响应的数据是JSON格式的字符串
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().println(jsonStr);
        }else{
            System.out.println("redis缓存中有数据");
            //将全部分类信息响应到客户端
            //告诉浏览器本次响应的数据是JSON格式的字符串
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().println(jsonStr);
        }


        return null;
    }
}
