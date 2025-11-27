package com.ngominhthuan.product_and_order_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(name = "users")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class User {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) 
    private String username;

    @Column(nullable = false)
    private String password; 

    @Column(unique = true, nullable = false)
    private String email;

    // Role: can be "USER", "ADMIN"
    private String role; 
}
