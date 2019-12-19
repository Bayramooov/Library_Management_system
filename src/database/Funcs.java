package database;

import javafx.collections.ObservableList;
import main.DataCollection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Hashtable;

public  class Funcs {

    private static boolean IsEmptyOrNull(String text)
    {
        if(text==null||text=="")
            return true;
        return false;
    }

    private static int GetTypeID(UserType type)
    {
        if(type==UserType.Librarian)
            return 1;
        else if(type==UserType.Reader)
            return 2;
        else
            return 3;
    }

    private static UserType GetUserType(int typeId)
    {
            if(typeId==1)
                return UserType.Librarian;
            else if(typeId==2)
                return UserType.Reader;
            else
                return UserType.Admin;
    }

    private static boolean IsAlreadyRegistered(Connection conn,String userId) throws Exception
    {
        String getUserFromDatabase="Select user_id as 'ID',password,user_type as 'type'" +
                " from Accounts where user_id=?";
        PreparedStatement ps=conn.prepareStatement(getUserFromDatabase);
        ps.setString(1,userId);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        { return true; }
        return  false;
    }

    public static User LoginUser(Connection conn,String userId,String password)throws Exception
    {
            if(IsEmptyOrNull(userId) || IsEmptyOrNull(password))
            {
                throw new Exception("Wrong credentials!");
            }
            String getUserFromDatabase="SELECT * FROM Accounts WHERE user_id=?";
            PreparedStatement ps=conn.prepareStatement(getUserFromDatabase);
            ps.setString(1,userId);
            ResultSet rs=ps.executeQuery();
           if(rs.next())
           {
               if(!IsEmptyOrNull(rs.getString("user_id")))
               {
                   if(rs.getString("password").compareTo(password)==0)
                   {
                       User user=new User(rs.getInt("ID"),rs.getString("name"),
                               rs.getString("user_id"),rs.getString("password"),
                               GetUserType(rs.getInt("user_type")));
                       user.setBlocked(rs.getBoolean("blocked"));
                       return  user;
                   }
               }
           }
        return null;
    }

    public static void GetAllBooks(Connection con)throws Exception
    {
        String query="SELECT * FROM Books";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        while(rs.next())
        {
           DataCollection.observableBookList.add(new Book(rs.getInt("ID"),rs.getString("title"),rs.getString("author"),
                   rs.getString("published_year"),rs.getString("subject"),rs.getInt("number_of_books")));
        }
    }

    //Gets Borrowed Books of individual student
    public static void GetBorrowedBooksOfReader(Connection con, int borrowerId)throws Exception
    {
        String getBooks="select b.*,bb.borrow_date,bb.return_date,book_id,borrower_id from BorrowedBooks as bb " +
                " JOIN Books as b " +
                " on bb.book_id=b.ID  where borrower_id=?";
        String getUser="select user_id,name from Accounts where ID=?";
        PreparedStatement pUser=con.prepareStatement(getUser);
        PreparedStatement pBook=con.prepareStatement(getBooks);
        pBook.setInt(1,borrowerId);

        ResultSet rs=pBook.executeQuery();
        ResultSet rUser;
        String userName;
        String userId;
        while(rs.next())
        {
            pUser.setInt(1,rs.getInt("borrower_id"));
            rUser=pUser.executeQuery();
            rUser.next();
            userName=rUser.getString("name");
            userId=rUser.getString("user_id");
            DataCollection.observableMyBookList.add(new BorrowedBook((new Book(rs.getInt("ID"),rs.getString("title"),rs.getString("author"),
                    rs.getString("published_year"),rs.getString("subject"),rs.getInt("number_of_books"))),
                    rs.getInt("book_id"),rs.getInt("borrower_id"),userName,userId,rs.getString("borrow_date"),rs.getString("return_date"))
            );
        }
    }

    //Gets all borrowed books
    public static void GetAllBorrowedBooks(Connection con)throws Exception
    {
        String getBooks="select b.*,bb.borrow_date,bb.return_date,book_id,borrower_id from BorrowedBooks as bb " +
                " JOIN Books as b " +
                " on bb.book_id=b.ID";
        String getUser="select user_id,name from Accounts where ID=?";
//Book book,int bkId,int brwId,String userName,String userId,String borrowedDate,String returnDate
        PreparedStatement pUser=con.prepareStatement(getUser);
        PreparedStatement pBook=con.prepareStatement(getBooks);
        ResultSet rs=pBook.executeQuery();
        ResultSet rUser;
        String userName;
        String userId;
        while(rs.next())
        {
            pUser.setInt(1,rs.getInt("borrower_id"));
            rUser=pUser.executeQuery();
            rUser.next();
            userName=rUser.getString("name");
            userId=rUser.getString("user_id");
            DataCollection.observableBorrowedBookList.add(new BorrowedBook((new Book(rs.getInt("ID"),rs.getString("title"),rs.getString("author"),
                    rs.getString("published_year"),rs.getString("subject"),rs.getInt("number_of_books"))),
                    rs.getInt("book_id"),rs.getInt("borrower_id"),userName,userId,rs.getString("borrow_date"),rs.getString("return_date"))
            );
        }
    }

    public static ArrayList<Book> GetTop3Books(Connection con)throws Exception
    {
        String command="SELECT * FROM Books ORDER BY taken DESC Limit 3";
        ResultSet rs=con.createStatement().executeQuery(command);
        ArrayList<Book> bks=new ArrayList<Book>();
        while(rs.next())
        {
            bks.add(new Book(rs.getInt("ID"),rs.getString("title"),rs.getString("author"),
                    rs.getString("published_year"),rs.getString("subject"),rs.getInt("number_of_books")));
        }
        return bks;
    }

    public static void SearchBooks(Connection con, String searchPattern)throws Exception
    {
        searchPattern='%'+searchPattern+'%';
        String command="SELECT * FROM Books WHERE title LIKE ? OR author LIKE ?";
        PreparedStatement ps=con.prepareStatement(command);
        ps.setString(1,searchPattern);
        ps.setString(2,searchPattern);
        ResultSet rs=ps.executeQuery();
        DataCollection.observableSearchingBooksList.clear();
        while(rs.next())
        {
            DataCollection.observableSearchingBooksList.add(new Book(rs.getInt("ID"),rs.getString("title"),rs.getString("author"),
                    rs.getString("published_year"),rs.getString("subject"),rs.getInt("number_of_books")));
        }
    }

    public static void SearchBorrowedBooks(Connection con,String searchPattern) throws SQLException
    {
        String getBooks="SELECT b.*,bb.borrow_date,bb.return_date,book_id,borrower_id FROM BorrowedBooks AS bb " +
                " JOIN Books AS b " +
                " ON bb.book_id=b.ID WHERE b.title LIKE ? OR b.author LIKE ?";
        String getUser="SELECT user_id,name FROM Accounts WHERE ID=?";
        searchPattern='%'+searchPattern+'%';
        PreparedStatement pUser=con.prepareStatement(getUser);
        PreparedStatement pBook=con.prepareStatement(getBooks);
        pBook.setString(1,searchPattern);
        pBook.setString(2,searchPattern);
        ResultSet rs=pBook.executeQuery();
        ResultSet rUser;
        String userName;
        String userId;
        DataCollection.observableSearchingBorrowedBooksList.clear();
        while(rs.next())
        {
            pUser.setInt(1,rs.getInt("borrower_id"));
            rUser=pUser.executeQuery();
            rUser.next();
            userName=rUser.getString("name");
            userId=rUser.getString("user_id");
            DataCollection.observableSearchingBorrowedBooksList.add(new BorrowedBook((new Book(rs.getInt("ID"),rs.getString("title"),rs.getString("author"),
                    rs.getString("published_year"),rs.getString("subject"),rs.getInt("number_of_books"))),
                    rs.getInt("book_id"),rs.getInt("borrower_id"),userName,userId,rs.getString("borrow_date"),rs.getString("return_date"))
            );
        }
    }

    public static void SearchUsers(Connection con,UserType userType,boolean blocked,String searchPattern) throws SQLException
    {
        int userTypeId=GetTypeID(userType);
        searchPattern='%'+searchPattern+'%';
        String command="SELECT * FROM Accounts WHERE user_type=? AND blocked=? AND (user_id LIKE ? OR name LIKE ?)";
        PreparedStatement ps=con.prepareStatement(command);
        ps.setInt(1,userTypeId);
        ps.setBoolean(2,blocked);
        ps.setString(3,searchPattern);
        ps.setString(4,searchPattern);
        ResultSet rs=ps.executeQuery();
        DataCollection.observableSearchingUsersList.clear();
        while(rs.next())
        {
            //int tableId,String name,String id,String password,UserType type
            DataCollection.observableSearchingUsersList.add(new User(rs.getInt("ID"),rs.getString("name"),
                    rs.getString("user_id"),rs.getString("password"),GetUserType(rs.getInt("user_type"))));
        }
    }

    public static void GetBlockedUsers(Connection con)throws Exception
    {
        String select="SELECT * FROM Accounts WHERE blocked=1";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(select);
        while(rs.next())
        {
//int tableId, String name, String ID, String password, boolean blocked, int numberOfBooks, UserType  type
            DataCollection.observableBlockedReadersList.add(new User(rs.getInt("ID"),rs.getString("name"),
                    rs.getString("user_id"),rs.getString("password"),GetUserType(rs.getInt("user_type"))));
        }
    }

    public static void GetReaders(Connection con)throws Exception
    {
        String select="SELECT * FROM Accounts WHERE user_type=2";
        String getBrwdBooks="SELECT COUNT(*) as count FROM BorrowedBooks WHERE borrower_id=?";
        PreparedStatement ps=con.prepareStatement(getBrwdBooks);
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(select);
        ResultSet rnob;
        int nob;
        while(rs.next())
        {
            ps.setInt(1,rs.getInt("ID"));
            rnob=ps.executeQuery();
            if(rnob.next())
            {
                nob=rnob.getInt("count");
            }
            else
            {
                nob=0;
            }
//int tableId, String name, String ID, String password, boolean blocked, int numberOfBooks, UserType  type
            DataCollection.observableReadersList.add(new User(rs.getInt("ID"),rs.getString("name"),
                    rs.getString("user_id"),rs.getString("password"),
                    rs.getBoolean("blocked"),nob,GetUserType(rs.getInt("user_type"))));
        }
    }

    public static void GetLibrarians(Connection con)throws Exception
    {
        String command="SELECT * FROM Accounts WHERE user_type=1";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(command);
        while(rs.next())
        {
            //int tableId,String name,String id,String password,UserType type
            DataCollection.observableLibrarianList.add(new User(rs.getInt("ID"),rs.getString("name"),
                    rs.getString("user_id"),rs.getString("password"),GetUserType(rs.getInt("user_type"))));
        }
    }

    public static Hashtable<String, Integer> GetCountStatistics(Connection con)throws Exception
    {
        String countA="SELECT SUM(number_of_books) as sumOfAll,COUNT(*) AS ct FROM Books";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(countA);

        String  countB="SELECT COUNT(*) AS ct FROM BorrowedBooks";

        String countDS="SELECT COUNT(*) AS ct FROM (SELECT DISTINCT subject FROM Books) AS drv";

        String countReaders="SELECT COUNT(*) as ct FROM Accounts WHERE user_type=2";

        Hashtable<String,Integer> table=new Hashtable<String,Integer>();
        int totalNumberOfBooks=0;
        int borrowedBooks=0;
        int types=0;
        int subjects=0;
        int numberOfReaders=0;
        if(rs.next())
        {
             totalNumberOfBooks+=rs.getInt("sumOfAll");
             types=rs.getInt("ct");
        }
        rs=st.executeQuery(countB);
        if(rs.next())
        {
            borrowedBooks=rs.getInt("ct");
            totalNumberOfBooks+=borrowedBooks;
        }
        rs=st.executeQuery(countDS);
        if(rs.next())
        {
            subjects=rs.getInt("ct");
        }
        rs=st.executeQuery(countReaders);
        if(rs.next())
        {
            numberOfReaders=rs.getInt("ct");
        }
        table.put("allBooks",totalNumberOfBooks);
        table.put("borrowedBooks",borrowedBooks);
        table.put("types",types);
        table.put("subjects",subjects);
        table.put("readers",numberOfReaders);
        return table;
    }

    private static boolean IsAlreadyBorrowed(Connection con,int bookId,int borrowerId)throws Exception
    {
        String cmd="Select * from BorrowedBooks where borrower_id=? and book_id=?";
        PreparedStatement ps=con.prepareStatement(cmd);
        ps.setInt(1,borrowerId);
        ps.setInt(2,bookId);
        ResultSet rs=ps.executeQuery();

        if(rs.next())
        {
            rs.close();
            return true;
        }
        return false;
    }

    public static boolean BorrowBook(Connection con,int bookId,int borrowerId)throws Exception
    {
        if(IsAlreadyBorrowed(con,bookId,borrowerId))
            return false;
        String getBook="SELECT * FROM Books WHERE ID=?";
        String updateBooks="UPDATE Books SET number_of_books=?,taken=? WHERE ID=?";
        String insertToBorwBooks="INSERT INTO BorrowedBooks (book_id,borrower_id,borrow_date,return_date)" +
                " VALUES (?,?,?,?)";
        PreparedStatement pGetBook=con.prepareStatement(getBook);
        pGetBook.setInt(1,bookId);
        ResultSet rGetBook=pGetBook.executeQuery();
        if(rGetBook.next())
        {
            int numberOfBooks=rGetBook.getInt("number_of_books");
            int taken=rGetBook.getInt("taken");
            if(numberOfBooks>0)
            {
                PreparedStatement pUpdate=con.prepareStatement(updateBooks);
                pUpdate.setInt(1,numberOfBooks-1);
                pUpdate.setInt(2,taken+1);
                pUpdate.setInt(3,bookId);
                pUpdate.executeUpdate();
                //Inserting to Borrowed Books table
                PreparedStatement pInsert=con.prepareStatement(insertToBorwBooks);
                pInsert.setInt(1,bookId);
                pInsert.setInt(2,borrowerId);
                pInsert.setString(3, LocalDate.now().toString());
                pInsert.setString(4,LocalDate.now().plusDays(10).toString());
                pInsert.executeUpdate();

                String select="SELECT user_id,name FROM Accounts WHERE ID=?";
                PreparedStatement pSelect=con.prepareStatement(select);
                pSelect.setInt(1,borrowerId);
                ResultSet rSelect = pSelect.executeQuery();
                String userId="undefined";
                String name="undefined";
                if(rSelect.next())
                {
                    userId=rSelect.getString("user_id");
                    name=rSelect.getString("name");
                }
                String log="INSERT INTO ReaderLog (title,author,published_year,subject,borrower_id," +
                        "borrower_name,borrow_date,return_date) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement pLog=con.prepareStatement(log);
                pLog.setString(1,rGetBook.getString("title"));
                pLog.setString(2,rGetBook.getString("author"));
                pLog.setString(3,rGetBook.getString("published_year"));
                pLog.setString(4,rGetBook.getString("subject"));

                pLog.setString(5,userId);
                pLog.setString(6,name);
                pLog.setString(7,LocalDate.now().toString());
                pLog.setString(8,LocalDate.now().plusDays(10).toString());
                pLog.executeUpdate();
                // title 	author 	published_year 	subject 	borrower_id 	borrower_name 	borrow_date 	return_date 	returned
                return true;
            }
        }
        return false;
    }

    public static boolean ReturnBook(Connection con,int bookId,int borrowerId)throws Exception
    {
        String selectbb="SELECT * FROM BorrowedBooks WHERE book_id=? AND borrower_id=?";
        String selectNob="SELECT number_of_books AS nob,title FROM Books WHERE ID=?";
        String update="UPDATE Books SET number_of_books=? WHERE ID=?";
        String delete="DELETE FROM BorrowedBooks WHERE ID=?";
        boolean res=false;
        PreparedStatement pSelectbb=con.prepareStatement(selectbb);
        pSelectbb.setInt(1,bookId);
        pSelectbb.setInt(2,borrowerId);

        ResultSet rSelectbb=pSelectbb.executeQuery();
        if(rSelectbb.next())
        {
            int id=rSelectbb.getInt("ID");
            PreparedStatement pDelete=con.prepareCall(delete);
            pDelete.setInt(1,id);
            pDelete.executeUpdate();
            res=true;
        }
        else
        {
            return res;
        }
        PreparedStatement pSelectNob=con.prepareStatement(selectNob);
        pSelectNob.setInt(1,bookId);
        ResultSet rNob=pSelectNob.executeQuery();
        int nob;
        String title;
        if(rNob.next())
        {
            nob=rNob.getInt("nob");
            title=rNob.getString("title");
            PreparedStatement pUpdate=con.prepareStatement(update);
            pUpdate.setInt(1,nob+1);
            pUpdate.setInt(2,bookId);
            pUpdate.executeUpdate();

            String st="select user_id from Accounts where ID=?";
            pUpdate=con.prepareStatement(st);
            pUpdate.setInt(1,borrowerId);
            ResultSet rs=pUpdate.executeQuery();
            if(rs.next())
            {
                String updateLog="UPDATE ReaderLog SET returned=1 WHERE title=? AND borrower_id=?";
                pUpdate=con.prepareStatement(updateLog);
                pUpdate.setString(1,title);
                pUpdate.setString(2,rs.getString("user_id"));
                pUpdate.executeUpdate();
            }
        }
        return res;
    }

    public static boolean BlockOrUnblockUser(Connection con, int userId, boolean doBlock)throws Exception
    {
        String block="UPDATE Accounts SET blocked=? WHERE ID=?";
        String select="SELECT * FROM Accounts WHERE ID=?";
        int setValue=doBlock?1:0;
        PreparedStatement pSelect=con.prepareStatement(select);
        pSelect.setInt(1,userId);
        ResultSet rSelect=pSelect.executeQuery();
        if(rSelect.next())
        {
            PreparedStatement pUpdate=con.prepareStatement(block);
            pUpdate.setInt(1,setValue);
            pUpdate.setInt(2,userId);
            pUpdate.executeUpdate();
            return true;
        }
        return false;
    }

    public static boolean AddNewUser(Connection con,String userId,String password,String userName,UserType userType) throws Exception
    {
        if(IsAlreadyRegistered(con,userId))
            return false;

        String insert="INSERT INTO Accounts (user_id,password,name,user_type) VALUES (?,?,?,?)";
        PreparedStatement pInsert=con.prepareStatement(insert);
        pInsert.setString(1,userId);
        pInsert.setString(2,password);
        pInsert.setString(3,userName);
        pInsert.setInt(4,GetTypeID(userType));
        pInsert.executeUpdate();
        return true;
    }

    public static boolean EditUser(Connection con,int tableId,String userId,String password,String userName,UserType userType) throws Exception
    {
        if(!IsAlreadyRegistered(con,userId))
        return false;
        if(userType==UserType.Reader)
        {
            String select="SELECT user_id,name FROM Accounts WHERE ID=?";
            PreparedStatement ps=con.prepareStatement(select);
            ps.setInt(1,tableId);
            ResultSet rSelect=ps.executeQuery();
            rSelect.next();
            String upLog="update ReaderLog set borrower_id=?,borrower_name=? where borrower_id=?";
            ps=con.prepareStatement(upLog);
            ps.setString(1,userId);
            ps.setString(2,userName);
            ps.setString(3,rSelect.getString("user_id"));
            ps.executeUpdate();
        }

        String update="UPDATE Accounts SET user_id=?,password=?,name=?,user_type=? WHERE ID=?";
        PreparedStatement pInsert=con.prepareStatement(update);
        pInsert.setString(1,userId);
        pInsert.setString(2,password);
        pInsert.setString(3,userName);
        pInsert.setInt(4,GetTypeID(userType));
        pInsert.setInt(5,tableId);
        pInsert.executeUpdate();

        return true;
    }

    public static boolean DeleteUser(Connection con,int userIdInTable,UserType userType)throws Exception
    {
        String delete="DELETE FROM Accounts WHERE ID=?";
        PreparedStatement pDelete=con.prepareStatement(delete);
        pDelete.setInt(1,userIdInTable);
        int res=pDelete.executeUpdate();
        if(res==0)
        {
            return false;
        }
        return true;
    }

    private static boolean IsBookAlreadyAdded(Connection con,String title)throws Exception
    {
        String st="SELECT * FROM Books WHERE title=?";
        PreparedStatement ps=con.prepareStatement(st);
        ps.setString(1,title);
        if(ps.executeQuery().next())
        {
            return true;
        }
        return false;
    }

    public static boolean AddNewBook(Connection con,String title,String author,LocalDate publishedYear,String subject,int numberOfBooks)throws Exception
    {
        if(IsBookAlreadyAdded(con,title))
            return false;
        String insert="INSERT INTO Books (title,author,published_year,subject,number_of_books) VALUES" +
                "(?,?,?,?,?)";
        PreparedStatement ps=con.prepareStatement(insert);
        ps.setString(1,title);
        ps.setString(2,author);
        ps.setString(3,publishedYear.toString());
        ps.setString(4,subject);
        ps.setInt(5,numberOfBooks);
        ps.executeUpdate();
        return true;
    }

    public static boolean EditBook(Connection con,int tableId,String title,String author,LocalDate publishedYear,String subject,int numberOfBooks)throws Exception
    {
        String sel="SELECT * FROM Books WHERE ID=?";
        PreparedStatement ps=con.prepareStatement(sel);
        ps.setInt(1,tableId);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
            String update="UPDATE ReaderLog SET title=?,author=?,published_year=?,subject=? WHERE title=?";
            ps=con.prepareStatement(update);
            ps.setString(1,title);
            ps.setString(2,author);
            ps.setString(3,publishedYear.toString());
            ps.setString(4,subject);
            ps.setString(5,rs.getString("title"));
            ps.executeUpdate();
        }
        else
        {
            return false;
        }
        String edit="UPDATE Books SET title=?,author=?,published_year=?,subject=?,number_of_books=? WHERE ID=?";
        ps=con.prepareStatement(edit);
        ps.setString(1,title);
        ps.setString(2,author);
        ps.setString(3,publishedYear.toString());
        ps.setString(4,subject);
        ps.setInt(5,numberOfBooks);
        ps.setInt(6,tableId);
        ps.executeUpdate();
        return true;
    }

    public static boolean DeleteBook(Connection con,int id)throws Exception
    {
        String delete="DELETE FROM Books WHERE ID=? ";
        PreparedStatement ps=con.prepareStatement(delete);
        ps.setInt(1,id);
        if(ps.executeUpdate()==0)
        {
            return false;
        }
        return true;
    }
}