package database;

public class Librarian {
    public int TableId;
    public String Name;
    public String ID;
    public Librarian(int tableId,String name,String id)
    {
        this.TableId=tableId;
        this.Name=name;
        this.ID=id;
    }

    @Override
    public String toString()
    {
        return this.ID+"    "+this.Name;
    }
}
