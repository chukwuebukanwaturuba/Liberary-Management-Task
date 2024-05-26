package com.ebuka.librarymanagementsystem.model.entities;


import com.ebuka.librarymanagementsystem.utils.AuditorClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
/**
 * Entity representing a Book in the library system.
 *
 * This class is an entity that maps to the "Book" table in the database. It extends AuditorClass to
 * include auditing fields and implements Serializable for object serialization.
 *
 * Author: Chukwuebuka
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Book")
@Entity
public class Book extends AuditorClass implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String title;
    private String author;
    private String publicationYear;
    private String isbn;
    private boolean isAvailable = false;


    /**
     * Constructor to create a Book instance with specified details.
     *
     * @param id Long representing the book ID
     * @param title String representing the book title
     * @param author String representing the book author
     * @param publicationYear String representing the publication year
     * @param isbn String representing the ISBN number
     * @param isAvailable boolean representing the availability status of the book
     */
    public Book(long l, String ebukaN, String wisdomVictor, String number, String s, boolean b) {

        super();
    }

    /**
     * Sets the availability status of the book.
     *
     * @param isAvailable boolean indicating if the book is available or not
     */
    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
