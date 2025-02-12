
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil
{
	    static String url = "jdbc:mysql://localhost:3306/feeReport";

	    public static Connection getConnection() 
	    {
	        Connection conn = null;
	        try 
	        {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            conn = DriverManager.getConnection(url, "root", "root");
	        } 
	        catch (ClassNotFoundException | SQLException e) 
	        {
	            System.out.println("Database connection failed: " + e.getMessage());
	        }
			return conn;
	        
	    }
}
