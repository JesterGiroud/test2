package com.cskaoyan.test;

 import com.cskaoyan.bean.Order;
 import com.cskaoyan.bean.User;
 import com.sun.org.apache.xpath.internal.operations.Or;
 import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
 import org.junit.After;
 import org.junit.Before;
 import org.junit.Test;

import java.net.URL;
 import java.util.HashSet;
 import java.util.Set;

public class UserTest {

    private Session session;
    private SessionFactory sessionFactory;
    private Transaction transaction;

    @Before
    public void before() {
        URL resource = this.getClass().getClassLoader().getResource("hibernate.cfg.xml");

        Configuration configure = new Configuration().configure(resource);

        sessionFactory = configure.buildSessionFactory();

        session = sessionFactory.openSession();

        transaction = session.beginTransaction();

    }

    @After
    public void after() {

        transaction.commit();
        session.close();

    }


    //查询id为1的用户
    @Test
    public void test1() {


        Configuration configure = new Configuration().configure();
        //session工厂
        SessionFactory sessionFactory = configure.buildSessionFactory();

        //获取session
        Session session = sessionFactory.openSession();


        //================================================


        //================================================


        session.close();
    }


    //增
    @Test
    public void testAdd() {

        //新增加一个用户

        User user = new User();
        user.setUsername("wangwu");
        session.save(user);

        //新增加一个订单
        Order order = new Order();
        order.setReceiver("王五");

        //在面向对象的层次上，维护外键关系

        //为了说明当前的order是属于哪个user的
        //方法1：从多的一方去维护关系 order去维护
        order.setUser(user);

        //方法2：从一的一方去维护关系 user去维护
        /* Set<Order> orders =new HashSet<Order>();
         orders.add(order);
         user.setOrders(orders);*/

        //一边去维护外键
        session.save(order);

        //默认不行
        //save the transient instance (临时态)before flushing
    }

    //删
    @Test
    public void testDelete() {

        //删除user

        //默认个情况删除的行为，删除一方
        //会先去解除外键约束，然后在把自己删除
        /*User user = session.get(User.class, 2);
        session.delete(user);*/


        //删除order

        //删除多的一方，默认的行为
        //直接把自己删除，不会影响一方
        Order order = session.get(Order.class, 3);
        session.delete(order);


    }

    //改
    @Test
    public void testupdate() {


        //由一的一方去更新多的一方 是可以更新的
        //但是不能新建一个order由user去自动插入
       /* User user = session.get(User.class, 4);

        user.setPassword("345");

        Set<Order> orders = user.getOrders();

       *//* Order order =new Order();
        order.setReceiver("王五");

        orders.add(order);*//*

        for (Order o:orders) {

            o.setMoney(0f);
        }

        session.update(user);*/


        //由多方去更改一方 ,也是可以的
        Order order = session.get(Order.class, 2);
        System.out.println("user"+order.getUser());

        order.getUser().setUsername("wangdao");

        session.update(order);


    }

    //查
    @Test
    public void testquery() {

      /*  User user = session.get(User.class, 2);

        *//*默认情况下是否可以把user里关联的order查询出来 可以*//*
        Set<Order> orders = user.getOrders();
        System.out.println("orders:"+orders);*/


        //默认情况，查一方，会自动关联另一方
        Order order = session.get(Order.class, 2);
        System.out.println("user"+order.getUser());


    }


}
