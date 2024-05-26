package com.ebuka.librarymanagementsystem.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class AuthenticationRequest {

    private String email;
    private String password;
}
