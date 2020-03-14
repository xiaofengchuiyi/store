package cn.itcast.store.domain;

import java.util.Date;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/11 11:51
 * @description：
 * @modified By：
 * @version: $
 */
public class Product {
    private  String pid; //商品编号
    private  String pname; //商品名称
    private  double market_price; //商品市场价格
    private  double shop_price; //商品商城价格
    private  String pimage; //商品图片路径
    private Date pdate; // 商品上架日日期
    private  int is_hot; //商品是否热门
    private  String pdesc; //商品描述
    private  int pflag; //  商品是否在货架上  0：在货架上  1；下架
    private  String cid; //商品所在分类id

    public String getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public double getMarket_price() {
        return market_price;
    }

    public double getShop_price() {
        return shop_price;
    }

    public String getPimage() {
        return pimage;
    }

    public Date getPdate() {
        return pdate;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public String getPdesc() {
        return pdesc;
    }

    public int getPflag() {
        return pflag;
    }

    public String getCid() {
        return cid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setMarket_price(double market_price) {
        this.market_price = market_price;
    }

    public void setShop_price(double shop_price) {
        this.shop_price = shop_price;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public void setPdate(Date pdate) {
        this.pdate = pdate;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public void setPflag(int pflag) {
        this.pflag = pflag;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Product() {
    }

    public Product(String pid, String pname, double market_price, double shop_price, String pimage, Date pdate, int is_hot, String pdesc, int pflag, String cid) {
        this.pid = pid;
        this.pname = pname;
        this.market_price = market_price;
        this.shop_price = shop_price;
        this.pimage = pimage;
        this.pdate = pdate;
        this.is_hot = is_hot;
        this.pdesc = pdesc;
        this.pflag = pflag;
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", market_price=" + market_price +
                ", shop_price=" + shop_price +
                ", pimage='" + pimage + '\'' +
                ", pdate=" + pdate +
                ", is_hot=" + is_hot +
                ", pdesc='" + pdesc + '\'' +
                ", pflag=" + pflag +
                ", cid='" + cid + '\'' +
                '}';
    }
}
