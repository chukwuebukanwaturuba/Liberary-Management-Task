package com.ebuka.librarymanagementsystem.repositories;

import com.ebuka.librarymanagementsystem.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
