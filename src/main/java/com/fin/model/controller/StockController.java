package com.fin.model.controller;

import com.fin.model.entity.Stock;
import com.fin.model.service.StockService;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

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
        CsvContainer csv = csvReader.read((Path) file.getInputStream(), StandardCharsets.UTF_8);
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
}
