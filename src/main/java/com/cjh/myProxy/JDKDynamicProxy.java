package com.cjh.myProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// 调用处理类
public class JDKDynamicProxy implements InvocationHandler {

    private Object targetObject;

    public JDKDynamicProxy(Object targetObject){
        this.targetObject = targetObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("日志开始");
        Object invoke = method.invoke(targetObject, args);
        System.out.println("日志结束");
        return invoke;
    }
}
