package com.ebuka.librarymanagementsystem.dtos.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String fullName;
    private String email;
    private String password;
    private String address;
    private String phone;
}
