package com.fin.model.dao;

import com.fin.model.entity.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootApplication(scanBasePackages={"com.fin.model"})
public class dao {
    @Autowired
    private StockDao stockDao;

    @Test
    public void testInsertAttendeeInfo() throws Exception {
        Stock stock = new Stock();
        stock.setPrice(1.57);
        stock.setCreateTime(new Date());
        int effectedNum = stockDao.insertStock(stock);
        System.out.println(effectedNum);
    }

    @Test
    public void testQueryAttendeeInfo() throws Exception {
        String startDate = "2013-05-04";
        String endDate = "2013-05-06";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Stock> stocks = stockDao.queryStockByTime(format.parse(startDate), format.parse(endDate));
        System.out.println(stocks.size());
    }

//    @Test
//    public void testQueryAttendeeInfo() {
//
////        String attendeeEmail = "xiaoyud98@gmail.com";
////        String password = "1";
////        AttendeeInfo attendeeInfo = attendeeInfoDao.queryAttendeeInfoByUsernameAndPwd(attendeeEmail, password, 1L);
////        System.out.println(attendeeInfo.getAttendeeId());
//    }

}