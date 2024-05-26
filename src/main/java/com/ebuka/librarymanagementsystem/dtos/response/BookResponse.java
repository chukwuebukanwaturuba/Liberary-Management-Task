package com.ebuka.librarymanagementsystem.dtos.response;


import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String publicationYear;
    private String isbn;
    private boolean isAvailable;
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
}
