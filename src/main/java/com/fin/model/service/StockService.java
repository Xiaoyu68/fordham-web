package com.fin.model.service;

import com.fin.model.entity.Stock;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface StockService {

    List<Stock> getStockList(Date startTime, Date endTime);

    int addStock(Stock stock);
}
