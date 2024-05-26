package com.ebuka.librarymanagementsystem.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewBookRequest {

    private String title;
    private String author;
    private String publicationYear;
    private String isbn;
}
