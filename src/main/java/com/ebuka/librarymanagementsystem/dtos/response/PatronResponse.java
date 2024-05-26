package com.ebuka.librarymanagementsystem.dtos.response;

import com.ebuka.librarymanagementsystem.model.enums.Role;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PatronResponse {
    private Long id;
    private String fullName;
    private String email;
    private String address;
    private String phone;

}
