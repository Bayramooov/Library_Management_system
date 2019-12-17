package database;

import java.sql.*;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        try
        {
            Scanner sc=new Scanner(System.in);
            String url="jdbc:mysql://37.59.55.185:3306/RWX3BuLgCH";
            String userName="RWX3BuLgCH";
            String password="2WMYapbyjH";

//            String statement="insert into Accounts (user_id,password,user_type) values (?,?,?)";
//            String select="Select a.ID, a.user_id,t.type from Accounts as a inner join AccountType as t  on a.user_type=t.ID";
//            String tableName=sc.next();
//            String createTable = "CREATE TABLE IF NOT EXISTS "+tableName+" (" +
//                    " ID int auto_increment," +
//                    "borrowed_book_id int not null," +
//                    "taken int not null default 0," +
//                    "PRIMARY KEY(ID));" ;
//
//                   String alter= " ALTER TABLE " +tableName+
//                    " ADD FOREIGN KEY FK_BorrowedId (borrowed_book_id)" +
//                    " REFERENCES BorrowedBooks(ID);";

            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(url,userName,password);
           // System.out.println(Funcs.LoginUser(conn,"U181d0054","sss",UserType.Student));
//            for (Book bk:Funcs.GetBooks(conn)) {
//                System.out.println(bk.toString());
//            }

//            for (Book bk:Funcs.Search(conn,"Savitch")) {
//                System.out.println(bk.toString());
//            }

//            for(Reader reader:Funcs.GetReaders(conn))
//            {
//                System.out.println(reader.toString());
//            }

//            if(Funcs.BorrowBook(conn,2,4))
//            {
//                System.out.println("Borrowed");
//            }
//            else
//            {
//                System.out.println("Not Borrowed");
//            }

//            if(Funcs.ReturnBook(conn,2,4))
//            {
//                System.out.println("Returned");
//            }
//            else
//            {
//                System.out.println("Not Returned");
//            }


//            if(Funcs.BlocOrUnblockkUser(conn,75,false))
//            {
//                System.out.println( "User is blocked");
//            }
//            else
//            {
//                System.out.println( "User is Unblocked");
//            }
//
//            for(User user:Funcs.GetBlockedUsers(conn))
//            {
//                System.out.println(user.toString());
//            }



//            Hashtable<String,Integer> stats=Funcs.GetCountStatistics(conn);
//            System.out.println("Number of Books: "+stats.get("allBooks"));
//            System.out.println("Number of Borrowed Books: "+stats.get("borrowedBooks"));
//            System.out.println("Types: "+stats.get("types"));
//            System.out.println("Subjects: "+stats.get("subjects"));

            //            PreparedStatement ps=conn.prepareStatement(createTable);
//             ps.setString(1,tableName);
//            System.out.println( ps.executeUpdate()+" done");
//            ps=conn.prepareStatement(alter);
//            System.out.println( ps.executeUpdate()+" done");

//            Statement st=conn.createStatement();
//            ResultSet rs=st.executeQuery(select);
//            while(rs.next())
//            {
//                System.out.println("ID:"+rs.getInt("ID")+" User_ID:"+rs.getString("user_id")+" Type:"+rs.getString("type"));
//            }
//            PreparedStatement ps=conn.prepareStatement(statement);
//            ps.setString(1,"U1810036");
//            ps.setString(2,"kkk");
//            ps.setInt(3,2);
//            System.out.println(ps.executeUpdate()+" row(s) affected");

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close ();
                    System.out.println ("Database connection terminated");
                }
                catch (Exception e) {  }
            }
        }
    }
//    37.59.55.185

}


//ALTER USER 'RWX3BuLgCH'@'%' IDENTIFIED BY '7691445s';