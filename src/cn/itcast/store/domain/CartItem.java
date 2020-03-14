package cn.itcast.store.domain;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/12 11:34
 * @description：
 * @modified By：
 * @version: $
 */
public class CartItem {
    private Product product;    //获取购物项的三种参数（图片路径、商品名称、商品路径），提高代码复用
    private int num;            //购买商品个数
    private  double subTotal;   //小计

    //小计是经过计算可以获取的
    public double getSubTotal() {
        return product.getShop_price()*num;
    }

    public Product getProduct() {
        return product;
    }

    public int getNum() {
        return num;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
