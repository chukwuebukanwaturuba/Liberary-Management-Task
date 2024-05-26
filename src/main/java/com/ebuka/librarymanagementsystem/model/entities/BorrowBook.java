package com.ebuka.librarymanagementsystem.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity representing a record of a borrowed book in the library system.
 *
 * This class is an entity that maps to the "books_borrowed" table in the database.
 * It contains information about the borrowing and returning of books by patrons.
 *
 * Author: Chukwuebuka
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books_borrowed")
@Entity
public class BorrowBook implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "borrowed_on")
    private LocalDateTime borrowDate;

    @Column(name = "returned_on")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime returnedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patron_id", nullable = false )
    private Patron patron;
}
