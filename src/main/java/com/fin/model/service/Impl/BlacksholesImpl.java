package com.fin.model.service.Impl;

import com.fin.model.service.Blacksholes;
import com.fin.model.util.Gaussian;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BlacksholesImpl implements Blacksholes {
    public double callPrice(double s, double x, double r, double sigma, double t) {
        double d1 = (Math.log(s/x) + (r + sigma * sigma/2) * t) / (sigma * Math.sqrt(t));
        double d2 = d1 - sigma * Math.sqrt(t);
        return s * Gaussian.cdf(d1) - x * Math.exp(-r*t) * Gaussian.cdf(d2);
    }

    // estimate by Monte Carlo simulation
    public double call(double s, double x, double r, double sigma, double t) {
        int n = 10000;
        Random random = new Random();
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            double eps = random.nextGaussian();
            double price = s * Math.exp(r*t - 0.5*sigma*sigma*t + sigma*eps*Math.sqrt(t));
            double value = Math.max(price - x, 0);
            sum += value;
        }
        double mean = sum / n;

        return Math.exp(-r*t) * mean;
    }

}
