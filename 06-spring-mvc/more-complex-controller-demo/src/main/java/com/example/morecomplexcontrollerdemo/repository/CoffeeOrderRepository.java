package com.example.morecomplexcontrollerdemo.repository;

import com.example.morecomplexcontrollerdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
