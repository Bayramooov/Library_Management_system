package database;

public class Book {

    public int ID;
    public String Title;
    public String Author;
    public String PublishedYear;
    public String Subject;
    public int AvailableBooks;

    public Book(int id,String title, String author, String publishedYear,String subject, int availableBooks)
    {
        this.ID=id;
        this.Title=title;
        this.Author=author;
        this.PublishedYear=publishedYear;
        this.Subject=subject;
        this.AvailableBooks=availableBooks;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPublishedYear() {
        return PublishedYear;
    }

    public void setPublishedYear(String publishedYear) {
        PublishedYear = publishedYear;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public int getAvailableBooks() {
        return AvailableBooks;
    }

    public void setAvailableBooks(int availableBooks) {
        AvailableBooks = availableBooks;
    }

    @Override
    public String toString()
    {
        return "ID:"+this.ID+" Title:"+this.Title+" Author:"+this.Author+" PublishedYear:"+this.PublishedYear+" Subject:"+this.Subject+
                " Available:"+this.AvailableBooks;
    }
}
