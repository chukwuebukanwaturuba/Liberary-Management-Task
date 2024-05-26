package com.ebuka.librarymanagementsystem.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatronUpdateRequest {
    private String fullName;
    private String address;
    private String phone;


}
