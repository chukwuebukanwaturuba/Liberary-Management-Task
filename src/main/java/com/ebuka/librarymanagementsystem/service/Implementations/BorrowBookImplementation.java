package com.ebuka.librarymanagementsystem.service.Implementations;

import com.ebuka.librarymanagementsystem.dtos.request.BorrowBookRequest;
import com.ebuka.librarymanagementsystem.dtos.response.BorrowBookResponse;
import com.ebuka.librarymanagementsystem.exception.ErrorStatus;
import com.ebuka.librarymanagementsystem.exception.LibraryManagementException;
import com.ebuka.librarymanagementsystem.model.entities.Book;
import com.ebuka.librarymanagementsystem.model.entities.BorrowBook;
import com.ebuka.librarymanagementsystem.model.entities.Patron;
import com.ebuka.librarymanagementsystem.repositories.BookRepository;
import com.ebuka.librarymanagementsystem.repositories.BorrowBookRepository;
import com.ebuka.librarymanagementsystem.repositories.PatronRepository;
import com.ebuka.librarymanagementsystem.service.BorrowBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BorrowBookImplementation implements BorrowBookService {

    private final BorrowBookRepository repository;
    private final BookRepository bookRepository;

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

    @Override
    public BorrowBookResponse returnBook(Long bookId) {
        Book book = findBookById(bookId);
        if (book.isAvailable()) {
            throw new LibraryManagementException(ErrorStatus.BOOK_ALREADY_AVAILABLE_ERROR, "Book is already available for borrowing");
        }


        Patron patron = getLoggedInPatron();


        // Assuming we have a method to find the borrow record by book and patron
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

    private Patron getLoggedInPatron(){
        return (Patron) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    private Book findBookById(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new LibraryManagementException(ErrorStatus.BOOK_NOT_FOUND_ERROR, "Book not found"));
    }

}
