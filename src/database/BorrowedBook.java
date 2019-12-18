package database;

import java.time.LocalDate;

public class BorrowedBook {
    public Book Book;
    public int BookId;
    public int BorrowerId;
    public String UserName;
    public String UserId;
    public LocalDate BorrowedDate;
    public LocalDate ReturnDate;

    public BorrowedBook(Book book,int bkId,int brwId,String userName,String userId,String borrowedDate,String returnDate)
    {
        this.Book=book;
        this.BorrowedDate= LocalDate.parse(borrowedDate);
        this.ReturnDate=LocalDate.parse(returnDate);
        this.BookId=bkId;
        this.BorrowerId=brwId;
        this.UserName=userName;
        this.UserId=userId;
    }

    public database.Book getBook() {
        return Book;
    }

    public void setBook(database.Book book) {
        Book = book;
    }

    public int getBookId() {
        return BookId;
    }

    public void setBookId(int bookId) {
        BookId = bookId;
    }

    public int getBorrowerId() {
        return BorrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        BorrowerId = borrowerId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public LocalDate getBorrowedDate() {
        return BorrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        BorrowedDate = borrowedDate;
    }

    public LocalDate getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        ReturnDate = returnDate;
    }
}
