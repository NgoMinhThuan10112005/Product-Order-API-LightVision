package com.ngominhthuan.product_and_order_api.DTO;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private Integer stock;
}
