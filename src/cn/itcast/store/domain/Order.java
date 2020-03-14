package cn.itcast.store.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/13 9:06
 * @description：
 * @modified By：
 * @version: $
 */
public class Order {
    private String oid;
    private Date ordertime;
    private double total;
    private int state;
    private String address;
    private String name;
    private String telephone;

    //private String uid;
    //1 程序对象和对象发生关系，而不是对象和对象的属性发生关系
    //2 设计Order目的：让Order携带订单上的数据向service，dao传递，user对象时可以携带更多的数据
    private User user;

    //程序中体现订单对象和订单项之间的关系，我们在项目部分中有类似的需求：查询订单的同时还需要获取订单下所有的订单项
    private List<OrderItem> list=new ArrayList<OrderItem>();

    public String getOid() {
        return oid;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public double getTotal() {
        return total;
    }

    public int getState() {
        return state;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public User getUser() {
        return user;
    }

    public List<OrderItem> getList() {
        return list;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setList(List<OrderItem> list) {
        this.list = list;
    }


}
