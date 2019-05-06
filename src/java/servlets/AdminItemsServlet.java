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
public class AdminItemsServlet extends HttpServlet {

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
                
                
                String itemName = request.getParameter("Item name");
                String price = request.getParameter("price");
                String categoryID = request.getParameter("category");
                String bandID = request.getParameter("band");
                String genreID = request.getParameter("genre");
                String labelID = request.getParameter("label");
                String imageID = request.getParameter("imageCheckbox");
                
                if ("".equals(itemName) || "".equals(price) || imageID == null) {
                    request.getRequestDispatcher("./bandsAdmin.jsp").forward(request, response);
                    return;
                }
                
                Integer count_id = 1;
                while (true) {
                    String query = "SELECT * FROM ITEMS WHERE ID = " + count_id;
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
                Integer newItemId = count_id;
                
                String insertItem = "INSERT INTO METAL.ITEMS (ID, NAME, PRICE, STOCK, CATEGORY_ID, IMAGE_ID, BAND_ID, GENRE_ID, LABEL_ID) VALUES (" + newItemId + ", '" + itemName + "', " + price + ", 0," + categoryID + ", " + imageID + ", " + bandID + ", " + genreID + ", " + labelID + ")";
                statement.execute(insertItem);
                
                request.getRequestDispatcher("./itemsAdmin.jsp").forward(request, response);
            } else if (request.getParameter("update") != null) {
                String[] selectedIdCheckboxes = request.getParameterValues("itemIdCheckbox");
                String itemName = request.getParameter("Item name");
                String price = request.getParameter("price");
                String categoryID = request.getParameter("category");
                String bandID = request.getParameter("band");
                String genreID = request.getParameter("genre");
                String labelID = request.getParameter("label");
                String imageID = request.getParameter("imageCheckbox");
                
                if (selectedIdCheckboxes == null) {
                    request.getRequestDispatcher("./itemsAdmin.jsp").forward(request, response);
                    return;
                }
                
                for(String id : selectedIdCheckboxes){
                    String updateItem = "UPDATE METAL.ITEMS SET METAL.ITEMS.CATEGORY_ID = " + categoryID + ", METAL.ITEMS.BAND_ID = " + bandID + ", METAL.ITEMS.GENRE_ID = " + genreID + ", METAL.ITEMS.LABEL_ID = " + labelID;
                    String updateItemEnd =  " WHERE METAL.ITEMS.ID = " + id;
                    
                    if (!("".equals(itemName))) {
                        updateItem = updateItem + ", METAL.ITEMS.NAME = '" + itemName + "'";
                    }
                    if (!("".equals(price))) {
                        updateItem = updateItem + ", METAL.ITEMS.PRICE = " + price;
                    }
                    if (!(imageID == null)) {
                        updateItem = updateItem + ", METAL.ITEMS.IMAGE_ID = " + imageID;
                    }
                    
                    updateItem = updateItem + updateItemEnd;
                    statement = connection.createStatement();
                    statement.execute(updateItem);
                }
                request.getRequestDispatcher("./itemsAdmin.jsp").forward(request, response);
            } else if (request.getParameter("delete") != null) {
                String[] selectedIdCheckboxes = request.getParameterValues("itemIdCheckbox");
                
                if (selectedIdCheckboxes == null) {
                    request.getRequestDispatcher("./itemsAdmin.jsp").forward(request, response);
                    return;
                }
                
                for(String id : selectedIdCheckboxes){
                    String deleteItem = "DELETE FROM METAL.ITEMS WHERE METAL.ITEMS.ID = " + id;
                    statement = connection.createStatement();
                    statement.execute(deleteItem);
                }
                request.getRequestDispatcher("./itemsAdmin.jsp").forward(request, response);
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
