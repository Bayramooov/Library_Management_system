package database;

public class Reader extends Librarian{

    public int NumberOfBorrowedBooks;
    public Reader(int tableId,String name,String id,int nob)
    {
        super(tableId,name,id);
        this.NumberOfBorrowedBooks=nob;
    }

    @Override
    public String toString()
    {
        return super.toString()+this.NumberOfBorrowedBooks;
    }
}
