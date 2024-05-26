package com.ebuka.librarymanagementsystem.service;

import com.ebuka.librarymanagementsystem.dtos.request.BookUpdateRequest;
import com.ebuka.librarymanagementsystem.dtos.request.NewBookRequest;
import com.ebuka.librarymanagementsystem.dtos.response.BookResponse;
import com.ebuka.librarymanagementsystem.dtos.response.PatronResponse;
import com.ebuka.librarymanagementsystem.model.entities.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    ResponseEntity<?> addBook(NewBookRequest request);
    List<Book> getAllBooks();
    BookResponse getBookById(Long id);
    BookResponse updateBookInfo(Long id, BookUpdateRequest request);

    String deleteBookById(Long id);
}
