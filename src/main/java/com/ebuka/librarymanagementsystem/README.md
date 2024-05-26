Below is the swagger url, for testing the apis:
http://localhost:8080/swagger-ui/index.html?urls.primaryName=Auth#/authentication-controller/signUpAdmin


In this application, only the ADMIN role, which has its own endpoint for registration, can add a book.

Each book has a boolean isAvailable field that is automatically set to true when the admin adds a new book.

When a Patron (meaning a user who borrows books) borrows a book, the isAvailable field will automatically switch to false in the database, 
and the borrow date and time will be saved automatically. This change will trigger an "ErrorStatus.BOOK_NOT_AVAILABLE_ERROR"
with the message "Book is not available for borrowing" if another patron tries to borrow the same book before it is returned by the first borrower.

Once the first patron returns the book, the isAvailable field will automatically revert to true, and the return date and time will be saved in the borrowed book table along with the patron_id and book_id.