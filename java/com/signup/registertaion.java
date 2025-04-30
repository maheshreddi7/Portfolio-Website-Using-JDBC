package com.signup;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class registertaion
 */
@WebServlet("/register")
public class registertaion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		
		String uname = request.getParameter("name");
		String umail = request.getParameter("email");
		String upass = request.getParameter("password");
		String umobile = request.getParameter("contact");
		RequestDispatcher dispacher = null;
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ram","root","king5656");
			PreparedStatement pst = con.prepareStatement("insert into users(username,pass,mail,phone) values(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, upass);
			pst.setString(3, umail);
			pst.setString(4, umobile);
			int rowCount = pst.executeUpdate();
			dispacher  =request.getRequestDispatcher("registration.jsp");
			if(rowCount>0) {
				request.setAttribute("status", "sucess");
				
				
			}else {
				request.setAttribute("status", "failed");
				
			}
			dispacher.forward(request, response);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
