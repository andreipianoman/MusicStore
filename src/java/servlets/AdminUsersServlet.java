/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Turbotwins
 */
public class AdminUsersServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        Connection connection = null;
        String user = "metal";
        String password = "metal";
        String url = "jdbc:derby://localhost:1527/metal;create=true";
        String driver = "org.apache.derby.jdbc.ClientDriver";
        
        if (request.getParameter("insert") != null) {
            Statement statement;
            try {
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            
            
                Integer count_id = 1;
                while (true) {
                    String query = "SELECT * FROM USERS WHERE ID = " + count_id;
                    statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    boolean resultSetHasRows = resultSet.next();
                    if (resultSetHasRows) {
                        count_id = count_id + 1;
                    }
                    else {
                        break;
                    }
                }
                Integer newUserId = count_id;
                String username = request.getParameter("username");
                String userPass = request.getParameter("password");
                String role_id = request.getParameter("role");
                
                String insertUser = "INSERT INTO METAL.USERS (ID, USERNAME, PASSWORD, ROLE_ID) VALUES (" + newUserId + ", '" + username + "', '" + userPass + "', " + role_id + ")";
                statement.execute(insertUser);
                
                request.getRequestDispatcher("./usersAdmin.jsp").forward(request, response);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AdminUsersServlet.class.getName()).log(Level.SEVERE, null, ex);
                throw new SQLException();
            }
        } else if (request.getParameter("update") != null) {
            Statement statement;
            try {
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            
                String[] selectedIdCheckboxes = request.getParameterValues("userIdCheckbox");
                String username = request.getParameter("username");
                String userPass = request.getParameter("password");
                String role_id = request.getParameter("role");
                
                for(String id : selectedIdCheckboxes){
                    String updateUser;
                    if ("".equals(username) && "".equals(userPass)) {
                        updateUser = "UPDATE METAL.USERS SET METAL.USERS.ROLE_ID = " + role_id + " WHERE METAL.USERS.ID = " + id;
                    } else if ("".equals(username)) {
                        updateUser = "UPDATE METAL.USERS SET METAL.USERS.PASSWORD = '" + userPass + "', METAL.USERS.ROLE_ID = " + role_id + " WHERE METAL.USERS.ID = " + id;
                    } else if ("".equals(userPass)) {
                        updateUser = "UPDATE METAL.USERS SET METAL.USERS.USERNAME = '" + username + "', METAL.USERS.ROLE_ID = " + role_id + " WHERE METAL.USERS.ID = " + id;
                    } else {
                        updateUser = "UPDATE METAL.USERS SET METAL.USERS.USERNAME = '" + username + "', METAL.USERS.PASSWORD = '" + userPass + "', METAL.USERS.ROLE_ID = " + role_id + "  WHERE METAL.USERS.ID = " + id;
                    }
                    statement = connection.createStatement();
                    statement.execute(updateUser);
                }
                request.getRequestDispatcher("./usersAdmin.jsp").forward(request, response);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AdminUsersServlet.class.getName()).log(Level.SEVERE, null, ex);
                throw new SQLException();
            }
        } else if (request.getParameter("delete") != null) {
            Statement statement;
            try {
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            
                String[] selectedIdCheckboxes = request.getParameterValues("userIdCheckbox");
                
                for(String id : selectedIdCheckboxes){
                    String deleteUser = "DELETE FROM METAL.USERS WHERE METAL.USERS.ID = " + id;
                    statement = connection.createStatement();
                    statement.execute(deleteUser);
                }
                request.getRequestDispatcher("./usersAdmin.jsp").forward(request, response);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(AdminUsersServlet.class.getName()).log(Level.SEVERE, null, ex);
                throw new SQLException();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
