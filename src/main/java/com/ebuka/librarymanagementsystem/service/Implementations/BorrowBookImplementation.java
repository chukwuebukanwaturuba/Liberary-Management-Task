package com.ebuka.librarymanagementsystem.service.Implementations;

import com.ebuka.librarymanagementsystem.dtos.response.BorrowBookResponse;
import com.ebuka.librarymanagementsystem.exception.ErrorStatus;
import com.ebuka.librarymanagementsystem.exception.LibraryManagementException;
import com.ebuka.librarymanagementsystem.model.entities.Book;
import com.ebuka.librarymanagementsystem.model.entities.BorrowBook;
import com.ebuka.librarymanagementsystem.model.entities.Patron;
import com.ebuka.librarymanagementsystem.repositories.BookRepository;
import com.ebuka.librarymanagementsystem.repositories.BorrowBookRepository;
import com.ebuka.librarymanagementsystem.service.BorrowBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service responsible for managing book borrowing and returning operations.
 * Implements the BorrowBookService interface to handle borrowing and returning of books.
 *
 * This service ensures that books can only be borrowed if they are available and handles the
 * process of marking books as borrowed or returned. It interacts with repositories to manage
 * the underlying data and maintains the integrity of book availability status.
 *
 * Author: Chukwuebuka
 */


@Service
@RequiredArgsConstructor
public class BorrowBookImplementation implements BorrowBookService {

    private final BorrowBookRepository repository;
    private final BookRepository bookRepository;

    /**
     * Borrows a book for the logged-in patron.
     *
     * @param bookId Long representing the ID of the book to be borrowed
     * @return BorrowBookResponse containing details of the borrowed book
     */
    @Override
    public BorrowBookResponse borrow(Long bookId) {
        Book book = findBookById(bookId);

        if (!book.isAvailable()) {
            throw new LibraryManagementException(ErrorStatus.BOOK_NOT_AVAILABLE_ERROR, "Book is not available for borrowing");
        }

        Patron patron = getLoggedInPatron();

        BorrowBook borrowBook = new BorrowBook();
        borrowBook.setBook(book);
        borrowBook.setPatron(patron);
        borrowBook.setBorrowDate(LocalDateTime.now());
        BorrowBook savedBorrowBook = repository.save(borrowBook);

        book.setIsAvailable(false);
        bookRepository.save(book);
        return new BorrowBookResponse(savedBorrowBook);

    }

    /**
     * Returns a borrowed book for the logged-in patron.
     *
     * @param bookId Long representing the ID of the book to be returned
     * @return BorrowBookResponse containing details of the returned book
     */
    @Override
    public BorrowBookResponse returnBook(Long bookId) {
        Book book = findBookById(bookId);
        if (book.isAvailable()) {
            throw new LibraryManagementException(ErrorStatus.BOOK_ALREADY_AVAILABLE_ERROR, "Book is already available for borrowing");
        }


        Patron patron = getLoggedInPatron();


        // Find the borrow record by book and patron
        Optional<BorrowBook> borrowBookOptional = repository.findByBook_IdAndPatron_id(bookId, patron.getId());

        if (!borrowBookOptional.isPresent()) {
            throw new LibraryManagementException(ErrorStatus.BORROW_RECORD_NOT_FOUND_ERROR, "Borrow record not found");
        }

        BorrowBook borrowBook = borrowBookOptional.get();

        borrowBook.setReturnedDate(LocalDateTime.now());
        BorrowBook savedBorrowBook = repository.save(borrowBook);

        book.setIsAvailable(true);
        bookRepository.save(book);

        return new BorrowBookResponse(savedBorrowBook);
    }

    /**
     * Retrieves the logged-in patron from the security context.
     *
     * @return Patron object representing the logged-in user
     */
    private Patron getLoggedInPatron(){
        return (Patron) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

}
