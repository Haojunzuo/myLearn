package com.cjh.myAOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    private HashMap<String, Object> map = new HashMap<>();
    private final String filePath = "logFile.txt";


    //1.定义切面
    /**
     * 此步骤单纯定义切面,就是指定一个方法
     * 可以在这个方法运行前或运行后等位置织入额外代码
     *
     * 匹配com.cjh.controller包及其子包下的所有类的所有方法
     */
    @Pointcut("execution(public * com.cjh.controller.*.*(..))")
    // 切入点    执行    公有方法 任意返回值    包路径   任何类 任何方法 任何参数
    public void pointCut() {}



    //2.织入内容
    /**
     * 前置通知，目标方法调用前被调用
     * 向切面方法前添加代码，在方法运行前输出指定内容
     */
    @Before("pointCut()")
    public void beforeAdvice(JoinPoint joinPoint) { //JoinPoint 会包含当前切面的各种信息，连接点
        log.info("----------- 前置通知 -----------");
        Signature signature = joinPoint.getSignature(); //获得当前切面对应方法的信息

        log.info("返回目标方法的签名：{}", signature);
        log.info("代理的是哪一个方法：{}", signature.getName());
        Object[] args = joinPoint.getArgs();
        log.info("获取目标方法的参数信息：{}", Arrays.asList(args));

        map.put("方法签名", signature);
        map.put("代理方法", signature.getName());
        map.put("参数信息", Arrays.asList(args));
    }

    /**
     * 后置通知，也叫最终通知，目标方法执行完之后执行
     */
    @After("pointCut()")
    public void afterAdvice() {
        log.info("----------- 后置通知 -----------");
    }

    /**
     * 后置返回通知
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行
     *
     * @param joinPoint
     * @param keys
     */
    @AfterReturning(pointcut = "pointCut()", returning = "keys")
    public void afterReturningAdvice(JoinPoint joinPoint, String keys) {
        log.info("~~~~~~~~~~ 后置返回通知 ~~~~~~~~~~");
        log.info("后置返回通知的返回值：{}", keys);
    }

    /**
     * 后置异常通知
     * 定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     * throwing 只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     *
     * @param e
     */
    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void afterThrowingAdvice(Exception e) {
        log.info("~~~~~~~~~~ 后置异常通知 ~~~~~~~~~~");
        log.info(e.toString());
    }

    /**
     * 环绕通知
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     * 环绕通知第一个参数必须写，且是org.aspectj.lang.ProceedingJoinPoint类型  JoinPoint的子接口，拥有更多方法
     * 这个方法还要有返回值，因为调用的切面的方法可能有返回值，环绕advice如果不返回这个值，调用者就接收不到这个值了
     * @param proceedingJoinPoint
     */
    @Around(value = "pointCut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("----------- 环绕通知 -----------");
        log.info("环绕通知的目标方法名：{}", proceedingJoinPoint.getSignature().getName());
        map.put("目标方法", proceedingJoinPoint.getSignature().getName());
        try {
            return proceedingJoinPoint.proceed();   //ProceedingJoinPoint类型的参数具有调用切面方法的功能
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            log.info("---------- 环绕通知结束 -------------");
            writeLog(map);
        }
        return null;
    }

    /**
     * 打印日志
     * @param map
     */
    void writeLog(HashMap<String, Object> map) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日-hh时mm分ss秒");
            Date date = new Date(currentTimeMillis);
            FileWriter fw = new FileWriter(filePath,true);
            fw.write("----------- 环绕通知 -----------"+    format.format(date)+  "\r\n");
            fw.write("环绕通知的目标方法名：" + (String) map.get("目标方法") +      "\r\n");
            fw.write("----------- 前置通知 -----------"+                          "\r\n");
            fw.write("返回目标方法的签名：" + (String) map.get("方法签名").toString() +        "\r\n");
            fw.write("代理的是哪一个方法：" + (String) map.get("代理方法") +        "\r\n");
            fw.write("获取目标方法的参数信息：" + (String) map.get("参数信息").toString() +    "\r\n");
            fw.write("----------- 最终通知 -----------"+                          "\r\n");
            fw.write("---------- 环绕通知结束 -------------"+                     "\r\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
