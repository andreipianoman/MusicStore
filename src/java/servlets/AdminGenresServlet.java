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
public class AdminGenresServlet extends HttpServlet {

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
        Statement statement = null;
        ResultSet resultSet = null;
        String user = "metal";
        String password = "metal";
        String url = "jdbc:derby://localhost:1527/metal;create=true";
        String driver = "org.apache.derby.jdbc.ClientDriver";
        
        try {
            Class driverClass = Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            
            if (request.getParameter("insert") != null) {
                
                String genreName = request.getParameter("Genre name");
                
                if ("".equals(genreName)) {
                    request.getRequestDispatcher("./genresAdmin.jsp").forward(request, response);
                    return;
                }
                
                Integer count_id = 1;
                while (true) {
                    String query = "SELECT * FROM GENRES WHERE ID = " + count_id;
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery(query);
                    boolean resultSetHasRows = resultSet.next();
                    if (resultSetHasRows) {
                        count_id = count_id + 1;
                    }
                    else {
                        break;
                    }
                }
                Integer newGenreId = count_id;
                
                String insertGenre = "INSERT INTO METAL.GENRES (ID, NAME) VALUES (" + newGenreId + ", '" + genreName + "')";
                statement.execute(insertGenre);
                
                request.getRequestDispatcher("./genresAdmin.jsp").forward(request, response);
            } else if (request.getParameter("update") != null) {
                String[] selectedIdCheckboxes = request.getParameterValues("genreIdCheckbox");
                String genreName = request.getParameter("Genre name");
                
                if (selectedIdCheckboxes == null) {
                    request.getRequestDispatcher("./genresAdmin.jsp").forward(request, response);
                    return;
                }
                
                for(String id : selectedIdCheckboxes){
                    String updateGenre;
                    if ("".equals(genreName)) {
                        ;
                    } else {
                        updateGenre = "UPDATE METAL.GENRES SET METAL.GENRES.NAME = '" + genreName + "' WHERE METAL.GENRES.ID = " + id;
                        statement = connection.createStatement();
                        statement.execute(updateGenre);
                    }
                }
                request.getRequestDispatcher("./genresAdmin.jsp").forward(request, response);
            } else if (request.getParameter("delete") != null) {
                String[] selectedIdCheckboxes = request.getParameterValues("genreIdCheckbox");
                
                if (selectedIdCheckboxes == null) {
                    request.getRequestDispatcher("./genresAdmin.jsp").forward(request, response);
                    return;
                }
                
                for(String id : selectedIdCheckboxes){
                    String deleteGenre = "DELETE FROM METAL.GENRES WHERE METAL.GENRES.ID = " + id;
                    statement = connection.createStatement();
                    statement.execute(deleteGenre);
                }
                request.getRequestDispatcher("./genresAdmin.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminUsersServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (resultSet != null) {
                try
                {
                    resultSet.close();
                }
                catch (SQLException ex) {Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);}
            }
            if (statement != null) {
                try
                {
                   statement.close();
                }
                catch (SQLException ex) {Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);}
            }
            if (connection != null) {
                try
                {
                    connection.close();
                }
                catch (SQLException ex) {Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);}
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
