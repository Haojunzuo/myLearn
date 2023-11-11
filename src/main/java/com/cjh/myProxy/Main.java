package com.cjh.myProxy;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

public class Main {
    // 如果是静态代理，需要为每一个真实类编写代理类，比较麻烦。
    // 动态代理，通过反射动态生成代理类对象，代理类对象调用真实类对象的方法。
    public static void main(String[] args) {
        /*
        * JDK动态代理有接口、真实类、调用处理类和代理类四个角色。
        * 1、实现InvocationHandler接口创建调用处理器。
        * 2、通过为Proxy类指定ClassLoader对象和一组interface，以及调用处理器来创建动态代理对象。
        * 3、通过反射机制获得动态代理类的构造函数，其唯一参数是调用处理器的接口类型。
        * 4、通过构造函数创建动态代理类，构造时调用处理器对象作为参数被传入。
        * */
        // 真实对象
        StockServiceImpl stockService = new StockServiceImpl();
        Class<?> iServiceClass = stockService.getClass();

        // 调用处理类，即：要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用方法。
        JDKDynamicProxy jdkDynamicProxy = new JDKDynamicProxy(stockService);

        // 代理类，newProxyInstance用于指定类装载器、一组接口以及调用处理器生成动态代理类实例。
        IStockService so = (IStockService) Proxy.
                newProxyInstance(iServiceClass.getClassLoader(), iServiceClass.getInterfaces(), jdkDynamicProxy);
        so.reduceStock();
    }

    public static void main3(String[] args) {
        // 静态代理
        StockServiceImpl stockService = new StockServiceImpl();
        StaticProxy staticProxy = new StaticProxy(stockService);
        staticProxy.reduceStock();
    }

    // CGLIB
    public static void main4(String[] args) {
        // 静态代理
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CGLIBStockServiceImpl.class);
        enhancer.setCallback(new CGLIBDynamicProxy());
        CGLIBStockServiceImpl stockService = (CGLIBStockServiceImpl)enhancer.create();
        stockService.reduceStock();
    }
}
