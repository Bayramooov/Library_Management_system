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

    public String getBook() {
        return Book.toString();
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

    public String getBorrowedDate() {
        return BorrowedDate.toString();
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        BorrowedDate = borrowedDate;
    }

    public String getReturnDate() {
        return ReturnDate.toString();
    }

    public void setReturnDate(LocalDate returnDate) {
        ReturnDate = returnDate;
    }
}
