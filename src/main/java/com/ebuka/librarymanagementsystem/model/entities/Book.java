package com.ebuka.librarymanagementsystem.model.entities;


import com.ebuka.librarymanagementsystem.utils.AuditorClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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

//    @OneToOne(mappedBy = "book")
//    private BorrowBook borrowedBook;

    public Book(long l, String ebukaN, String wisdomVictor, String number, String s, boolean b) {
        super();
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
