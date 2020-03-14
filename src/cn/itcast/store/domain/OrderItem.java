package cn.itcast.store.domain;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/13 9:06
 * @description：
 * @modified By：
 * @version: $
 */
public class OrderItem {
    private String itemid;
    private int quantity;
    private double total;

    //1  对象对应对象
    //2  product,order携带更多的数据
    private Product product;
    private Order order;

    public String getItemid() {
        return itemid;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }

    public Product getProduct() {
        return product;
    }

    public Order getOrder() {
        return order;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemid='" + itemid + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                ", product=" + product +
                ", order=" + order +
                '}';
    }
}
