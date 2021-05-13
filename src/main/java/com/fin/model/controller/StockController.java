package com.fin.model.controller;

import com.fin.model.entity.Stock;
import com.fin.model.service.StockService;
import com.fin.model.util.HttpServletRequestUtil;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

import de.siegmar.fastcsv.writer.CsvWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    private Map<String, Object> importExcel(MultipartHttpServletRequest request) throws IOException, ParseException {
        Map<String, Object> modelMap = new HashMap<>();
        MultipartFile file = request.getFile("excel");
        CsvReader csvReader = new CsvReader();
        File file1 = new File("/Users/dengxiaoyu/Desktop/data.csv");
        CsvContainer csv = csvReader.read(file1, StandardCharsets.UTF_8);
        for (CsvRow row : csv.getRows()) {
            System.out.println("Read line: " + row);
            System.out.println("First column of line: " + row.getField(0));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (row.getOriginalLineNumber() != 0) {
                Stock stock = new Stock();
                stock.setCreateTime(formatter.parse(row.getField(0)));
                stock.setPrice(Double.valueOf(row.getField(1)));
                stockService.addStock(stock);
                modelMap.put(row.getField(0), "success");
            }
        }

        return modelMap;
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportToCSV(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String startTime = HttpServletRequestUtil.getString(request, "startDate");
        String endTime = HttpServletRequestUtil.getString(request, "endDate");
        List<Stock> stockList = stockService.getStockList(format.parse(startTime), format.parse(endTime));


        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Stock Price", "Time"};
        String[] nameMapping = {"price", "createTime"};


        for (Stock stock : stockList) {
            csvWriter.write(stock, nameMapping);
        }

        csvWriter.close();
    }
}
