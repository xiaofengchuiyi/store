package cn.itcast.store.domain;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/10 17:03
 * @description：
 * @modified By：
 * @version: $
 */
public class Category {
    String cid;
    String cname;

    public String getCid() {
        return cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Category() {
    }

    public Category(String cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }
}
