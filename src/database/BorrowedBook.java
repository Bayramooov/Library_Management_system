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



}
