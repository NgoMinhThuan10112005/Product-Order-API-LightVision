package com.ngominhthuan.product_and_order_api.DTO;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;

    private String role;
}
