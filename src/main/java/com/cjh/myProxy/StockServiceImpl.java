package com.cjh.myProxy;

public class StockServiceImpl implements IStockService{

    @Override
    public void reduceStock() {
        System.out.println("扣减库存开始");
    }
}
