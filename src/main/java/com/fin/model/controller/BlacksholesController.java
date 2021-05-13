package com.fin.model.controller;

import com.fin.model.bo.BlacksholesBo;
import com.fin.model.entity.Stock;
import com.fin.model.service.Blacksholes;
import com.fin.model.service.StockService;
import com.fin.model.util.HttpServletRequestUtil;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/model")
public class BlacksholesController {
    @Autowired
    private Blacksholes blacksholes;

    @RequestMapping(value = "/bs", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> blacksholes(@RequestBody BlacksholesBo blacksholesBo) {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            double s = blacksholesBo.getS();
            double r = blacksholesBo.getR();
            double x = blacksholesBo.getX();
            double sigma = blacksholesBo.getSigma();
            double t = blacksholesBo.getT();
            double res = blacksholes.callPrice(s, x, r, sigma, t);
            modelMap.put("success", true);
            modelMap.put("p", res);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    public static void main(String[] args) throws IOException, ParseException {

        File file = new File("/Users/dengxiaoyu/Desktop/data.csv");
        CsvReader csvReader = new CsvReader();

        CsvContainer csv = csvReader.read(file, StandardCharsets.UTF_8);
        for (CsvRow row : csv.getRows()) {
            System.out.println("Read line: " + row);
            System.out.println("First column of line: " + row.getField(0));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (row.getOriginalLineNumber() != 0) {
                Stock stock = new Stock();
                stock.setCreateTime(formatter.parse(row.getField(0)));
                stock.setPrice(Double.valueOf(row.getField(1)));

            }
        }
    }
}
