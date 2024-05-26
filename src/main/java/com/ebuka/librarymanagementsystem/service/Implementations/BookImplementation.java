package com.ebuka.librarymanagementsystem.service.Implementations;

import com.ebuka.librarymanagementsystem.dtos.request.BookUpdateRequest;
import com.ebuka.librarymanagementsystem.dtos.request.NewBookRequest;
import com.ebuka.librarymanagementsystem.dtos.response.BookResponse;
import com.ebuka.librarymanagementsystem.dtos.response.PatronResponse;
import com.ebuka.librarymanagementsystem.exception.ErrorStatus;
import com.ebuka.librarymanagementsystem.exception.LibraryManagementException;
import com.ebuka.librarymanagementsystem.model.entities.Book;
import com.ebuka.librarymanagementsystem.model.entities.Patron;
import com.ebuka.librarymanagementsystem.model.enums.Role;
import com.ebuka.librarymanagementsystem.repositories.BookRepository;
import com.ebuka.librarymanagementsystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
/**
 * Service responsible for managing book-related operations.
 * Implements the BookService interface to handle book addition, retrieval, updating, and deletion.
 *
 * This service ensures that only users with the ADMIN role can add new books and provides methods to fetch,
 * update, and delete book information. It utilizes a repository to interact with the underlying database
 * and logs operations for monitoring purposes.
 *
 * This service includes transactional methods to ensure data consistency and integrity.
 * In case of errors, appropriate exceptions are thrown or responses are returned.
 *
 * Author: Chukwuebuka
 */

@Service
@RequiredArgsConstructor
public class BookImplementation implements BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    /**
     * Adds a new book to the library.
     *
     * @param request NewBookRequest object containing book details
     * @return ResponseEntity with the status of the book addition and related information
     */
    @Override
    @Transactional
    public ResponseEntity<?> addBook(NewBookRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Patron patron = (Patron) authentication.getPrincipal();

        if (patron.getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only ADMIN  can add a new book.");
        }
        try {
            Book book = new Book();
            book.setTitle(request.getTitle());
            book.setAuthor(request.getAuthor());
            book.setPublicationYear(request.getPublicationYear());
            book.setIsbn(request.getIsbn());
            book.setCreatedAt(LocalDateTime.now());
            book.setIsAvailable(true);

            Book savedBook = bookRepository.save(book);
            return ResponseEntity.ok(savedBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving book: " + e.getMessage());
            //throw new RuntimeException("Error saving book: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves all books from the library.
     *
     * @return List of all books
     */
    @Override
    public List<Book> getAllBooks() {
        try {
            List<Book> books = bookRepository.findAll();
            logger.info("fetched {} books", books.size());
            return books;
        } catch (Exception e){
            logger.error("Error fetching books", e);
            throw new LibraryManagementException(ErrorStatus.GENERAL_ERROR, "Error fetching patrons");
        }
    }


    /**
     * Retrieves a book by its ID.
     *
     * @param id Long representing the book ID
     * @return BookResponse containing book details
     */
    @Override
    public BookResponse getBookById(Long id) {
        Book book = findBookById(id);
        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getIsbn(), book.isAvailable(), book.getCreatedAt(), book.getUpdatedAt());
    }

    /**
     * Updates the information of an existing book.
     *
     * @param id Long representing the book ID
     * @param request BookUpdateRequest object containing updated book details
     * @return BookResponse with updated book details
     */
    @Override
    public BookResponse updateBookInfo(Long id, BookUpdateRequest request) {
        Book book = findBookById(id);

        if (request.getTitle() == null) {
            request.setTitle(book.getTitle());
        }
        if (request.getAuthor() == null) {
            request.setAuthor(book.getAuthor());
        }
        if (request.getPublicationYear() == null) {
            request.setPublicationYear(book.getPublicationYear());
        }
        if (request.getIsbn() == null){
            request.setIsbn(book.getIsbn());
        }

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublicationYear(request.getPublicationYear());
        book.setIsbn(request.getIsbn());
        book.setCreatedAt(book.getCreatedAt());
        book.setUpdatedAt(LocalDateTime.now());

        Book updatedBook = bookRepository.save(book);
        return mapBookToBookResponse(updatedBook);
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id Long representing the book ID
     * @return String indicating the status of the deletion
     */
    @Override
    public String deleteBookById(Long id) {
        try {
            Book book = findBookById(id);
            bookRepository.delete(book);
            return "Book successfully deleted";
        } catch (Exception e) {
            System.err.println("Error deleting patron: " + e.getMessage());
            return "Sorry book does not exist with the id: "+ id;
        }
    }


    /**
     * Finds a book by its ID.
     *
     * @param id Long representing the book ID
     * @return Book object if found
     */
    private Book findBookById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new LibraryManagementException(ErrorStatus.BOOK_NOT_FOUND_ERROR, "Book not found"));
    }


    /**
     * Maps a Book object to a BookResponse object.
     *
     * @param book Book object to be mapped
     * @return BookResponse containing book details
     */
    private BookResponse mapBookToBookResponse(Book book){
        return new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(),
                book.getPublicationYear(), book.isAvailable(), book.getCreatedAt(), book.getUpdatedAt());
    }
}

