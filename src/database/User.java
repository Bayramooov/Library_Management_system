package database;

import main.common.Login;

public class User  {
    public int TableId;
    public String Name;
    public String ID;
    public String Password;
    public boolean Blocked=false;
    public int NumberOfBooks=0;
    private UserType UType;

    public User(int tableId,String name,String id,String password,UserType type)
    {
        this.TableId=tableId;
        this.Name= Login.capitalizeString(name).trim();
        this.ID=id;
        this.Password=password;
        this.UType=type;
    }

    public User(int tableId, String name, String ID, String password, boolean blocked, int numberOfBooks, UserType  type) {
        this(tableId,name,ID,password,type);
        NumberOfBooks = numberOfBooks;
        this.Blocked=blocked;
    }

    public int getTableId() {
        return TableId;
    }

    public void setTableId(int tableId) {
        TableId = tableId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isBlocked() {
        return Blocked;
    }

    public void setBlocked(boolean blocked) {
        Blocked = blocked;
    }

    public int getNumberOfBooks() {
        return NumberOfBooks;
    }

    public void setNumberOfBooks(int numberOfBooks) {
        NumberOfBooks = numberOfBooks;
    }

    public UserType getUType() {
        return UType;
    }

    public void setUType(UserType UType) {
        this.UType = UType;
    }

    @Override
    public String toString()
    {
        return this.ID+"    "+this.Name;
    }
}
