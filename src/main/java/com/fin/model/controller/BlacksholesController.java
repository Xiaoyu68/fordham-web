package com.fin.model.controller;

import com.fin.model.service.Blacksholes;
import com.fin.model.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/model")
public class BlacksholesController {
    @Autowired
    private Blacksholes blacksholes;

    @RequestMapping(value = "/bs", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> blacksholes(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        double s = HttpServletRequestUtil.getDouble(request, "s");
        double r = HttpServletRequestUtil.getDouble(request, "r");
        double x = HttpServletRequestUtil.getDouble(request, "x");
        double sigma = HttpServletRequestUtil.getDouble(request, "sigma");
        double t = HttpServletRequestUtil.getDouble(request, "t");
        double res = blacksholes.call(s, x, r, sigma, t);
        modelMap.put("success", true);
        modelMap.put("p", res);
        return modelMap;
    }
}
