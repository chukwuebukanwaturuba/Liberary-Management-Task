package com.ebuka.librarymanagementsystem.service;

import com.ebuka.librarymanagementsystem.dtos.request.BorrowBookRequest;
import com.ebuka.librarymanagementsystem.dtos.response.BorrowBookResponse;
import com.ebuka.librarymanagementsystem.model.entities.BorrowBook;

public interface BorrowBookService {

    BorrowBookResponse borrow(Long bookId);

    BorrowBookResponse returnBook(Long bookId);
}
