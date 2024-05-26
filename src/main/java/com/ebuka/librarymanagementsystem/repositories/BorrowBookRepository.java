package com.ebuka.librarymanagementsystem.repositories;

import com.ebuka.librarymanagementsystem.model.entities.Book;
import com.ebuka.librarymanagementsystem.model.entities.BorrowBook;
import com.ebuka.librarymanagementsystem.model.entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowBookRepository extends JpaRepository<BorrowBook, Long> {
   Optional<BorrowBook> findByBook_IdAndPatron_id(Long bookId, Long patronId);
}
