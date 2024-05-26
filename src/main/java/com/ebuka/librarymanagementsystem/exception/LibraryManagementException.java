package com.ebuka.librarymanagementsystem.exception;

public class LibraryManagementException extends RuntimeException{

    private ErrorStatus errorStatus;
    private String message;
    private int errorCode;


    public LibraryManagementException(ErrorStatus errorStatus, String message){
        this.errorStatus=errorStatus;
        if(message == null){
            this.message=errorStatus.getErrorMessage();
        }else{
            this.message=message;
        }
        this.errorCode = errorStatus.getErrorCode();
    }


}
