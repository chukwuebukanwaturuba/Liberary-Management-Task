package com.ebuka.librarymanagementsystem.exception;

import lombok.Getter;

@Getter
public enum ErrorStatus {

    EMAIL_TAKEN_ERROR(409, "Email is taken" ),
    USER_NOT_FOUND_ERROR(404, "user not found"),
    VALIDATION_ERROR(400, "Invalid input"),
    GENERAL_ERROR(500, "oops something went wrong"),
    PATRON_NOT_FOUND_ERROR(404, "Patron not found"),
    BOOK_NOT_FOUND_ERROR(404, "Book not found"),
    BOOK_NOT_AVAILABLE_ERROR(404, "Book not available for borrow"),
    BOOK_ALREADY_AVAILABLE_ERROR(206, "Book is already available for borrow."),
    BORROW_RECORD_NOT_FOUND_ERROR(404, "Borrow record not found");

    final int errorCode;

    final String errorMessage;

    ErrorStatus(int errorCode, String errorMessage){
        this.errorCode  = errorCode;
        this.errorMessage = errorMessage;
    }
}
