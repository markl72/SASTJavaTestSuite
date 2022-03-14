package markl72.SASTJavaTestSuite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/sqli-00/BenchmarkTest00008")
public class SQLi_concat_weakvalidated extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// some code
		response.setContentType("text/html;charset=UTF-8");
		
		String param = request.getParameter("param1");
		
		// URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
		param = java.net.URLDecoder.decode(param, "UTF-8");

		// Validate input
		Pattern validPattern = Pattern.compile(".*");
		if (!validPattern.matcher( param ).matches())  {
			throw new ServletException( "Failed validation rules.");
		}

		try {
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sonoo","root","root");  
						
			String sql = "select * from emp where column1 = " + param;
			PreparedStatement pstmt = connection.prepareStatement( sql );
           
            ResultSet rs = pstmt.executeQuery(sql);  
            
            while(rs.next()) {
            	System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
            }
            connection.close();  
        } 
		catch(Exception e){ 
			System.out.println(e);
		}  		
	}
}
