package com.fin.model.dao;

import com.fin.model.entity.Stock;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Mapper
public interface StockDao {

    List<Stock> queryStockByTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    int insertStock(Stock stock);
}
