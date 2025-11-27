package com.ngominhthuan.product_and_order_api.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
