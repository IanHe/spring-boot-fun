package com.example.simplecontrollerdemo.controller;

import com.example.simplecontrollerdemo.controller.request.NewOrderRequest;
import com.example.simplecontrollerdemo.model.Coffee;
import com.example.simplecontrollerdemo.model.CoffeeOrder;
import com.example.simplecontrollerdemo.service.CoffeeOrderService;
import com.example.simplecontrollerdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {
    @Autowired
    private CoffeeOrderService orderService;

    @Autowired
    private CoffeeService coffeeService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest request) {
        log.info("Receive new Order {}", request);
        Coffee[] coffees = coffeeService.getCoffeeByName(request.getItems()).toArray(new Coffee[]{});
        return orderService.createOrder(request.getCustomer(), coffees);
    }
}
