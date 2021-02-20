package com.fin.model.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface Blacksholes {
    public double callPrice(double s, double x, double r, double sigma, double t);
    public double call(double s, double x, double r, double sigma, double t);
}
