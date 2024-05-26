package com.ebuka.librarymanagementsystem.service.Implementations;

import com.ebuka.librarymanagementsystem.dtos.request.BookUpdateRequest;
import com.ebuka.librarymanagementsystem.dtos.request.NewBookRequest;
import com.ebuka.librarymanagementsystem.dtos.response.BookResponse;
import com.ebuka.librarymanagementsystem.model.entities.Book;
import com.ebuka.librarymanagementsystem.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookImplementationTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookImplementation bookImplementation;

    @Mock
    private Authentication authentication;
    private Book book;
    private List<Book> bookList;

    @BeforeEach
    void setUp() {
        bookImplementation = new BookImplementation(bookRepository);
        book = new Book(1L, "Ebuka N", "Wisdom Victor", "2022", "978-1234567890", true);
        book.setCreatedAt(LocalDateTime.now());
        bookList = new ArrayList<>();
        bookList.add(book);


    }

    @Test
    void addBook() {
        NewBookRequest request = new NewBookRequest("48 Laws of Power", "Wisdom V", "2022", "978-1234567890");
       Book newBook = new Book(null, request.getTitle(), request.getAuthor(), request.getPublicationYear(), request.getIsbn(), true);
       newBook.setCreatedAt(LocalDateTime.now());


        when(bookRepository.save(any(Book.class))).thenReturn(newBook);

        ResponseEntity<?> responseEntity = bookImplementation.addBook(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        Book result = (Book) responseEntity.getBody();
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getPublicationYear(), result.getPublicationYear());
        assertEquals(book.getIsbn(), result.getIsbn());
        assertTrue(result.isAvailable());

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void getAllBooks() {
        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> result = bookImplementation.getAllBooks();

        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookResponse result = bookImplementation.getBookById(1L);

        assertNotNull(result);
        assertEquals(book.getId(), result.getId());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getPublicationYear(), result.getPublicationYear());
        assertEquals(book.getIsbn(), result.getIsbn());
        assertEquals(book.isAvailable(), result.isAvailable());
    }


    @Test
    void deleteBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        String result = bookImplementation.deleteBookById(1L);

        assertEquals("Book successfully deleted", result);
        verify(bookRepository, times(1)).delete(book);
    }
}