package com.cjh.myProxy;

public class StaticProxy implements IStockService{
    private IStockService stockService;
    public StaticProxy(IStockService stockService){
        this.stockService = stockService;
    }

    @Override
    public void reduceStock() {
        System.out.println("静态代理开始");
        stockService.reduceStock();
        System.out.println("静态代理结束");
    }
}
