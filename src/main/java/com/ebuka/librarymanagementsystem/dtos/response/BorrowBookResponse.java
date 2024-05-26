package com.ebuka.librarymanagementsystem.dtos.response;

import com.ebuka.librarymanagementsystem.model.entities.BorrowBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowBookResponse {
    private Long id;
    private LocalDateTime borrowDate;
    private LocalDateTime returnedDate;
    private Long bookId;
    private Long patronId;

    public BorrowBookResponse(BorrowBook savedBorrowBook) {
        this.id = savedBorrowBook.getId();
        this.borrowDate = savedBorrowBook.getBorrowDate();
        this.returnedDate = savedBorrowBook.getReturnedDate();
        this.bookId = savedBorrowBook.getBook().getId();
        this.patronId = savedBorrowBook.getPatron().getId();
    }



}
