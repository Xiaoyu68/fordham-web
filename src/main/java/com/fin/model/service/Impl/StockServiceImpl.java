package com.fin.model.service.Impl;

import com.fin.model.dao.StockDao;
import com.fin.model.entity.Stock;
import com.fin.model.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public List<Stock> getStockList(Date startTime, Date endTime) {
        return stockDao.queryStockByTime(startTime, endTime);
    }

    @Override
    public int addStock(Stock stock) {
        if (stock != null) {
            try {
                int effectedNum = stockDao.insertStock(stock);
                return effectedNum;
            } catch (Exception e) {
                throw new RuntimeException("failed");
            }
        } else {
            return -1;
        }
    }
}
