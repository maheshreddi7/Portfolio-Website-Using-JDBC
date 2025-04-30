package com.signup;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String umail = request.getParameter("username");
        String upass = request.getParameter("password");
        HttpSession session = request.getSession();
        RequestDispatcher rd = null;

        try {
            // Use the correct MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ram", "root", "password(enter yours)");
            PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE mail = ? AND pass = ?");
            pst.setString(1, umail);
            pst.setString(2, upass);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                // Set session attribute for user
                session.setAttribute("name", rs.getString("username"));

                // Redirect properly
                response.sendRedirect("indexx.jsp");
                return;
            } else {
                request.setAttribute("status", "failed");
                rd = request.getRequestDispatcher("login.jsp");
            }

            // Forward if login failed
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
