package com.ngominhthuan.product_and_order_api.repository;

import com.ngominhthuan.product_and_order_api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
