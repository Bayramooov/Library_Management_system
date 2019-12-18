package database;

public class User extends Librarian {
    private UserType userType;
    public User(int tableId,String name,String id,UserType type)
    {
        super(tableId,name,id);
        this.userType=type;
    }

}
