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
public class AdminStocksServlet extends HttpServlet {

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
        
        
        String[] noSizeStockIdCheckboxes = request.getParameterValues("noSizeStockIdCheckbox");
        String[] sizeStockIdCheckboxes = request.getParameterValues("sizeStockIdCheckbox");
        String amount = request.getParameter("amount");
        
        if (noSizeStockIdCheckboxes == null && sizeStockIdCheckboxes == null || "".equals(amount)) {
            request.getRequestDispatcher("./stocksAdmin.jsp").forward(request, response);
            return;
        }
        
        try {
            Class driverClass = Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            
            if (request.getParameter("add") != null) {
                
                if (noSizeStockIdCheckboxes != null) {
                    for(String id : noSizeStockIdCheckboxes){
                        String currentStock = request.getParameter(id + "stock");
                        String addAmount = "UPDATE METAL.ITEMS SET METAL.ITEMS.STOCK = " + currentStock + " + " + amount + " WHERE ID = " + id;
                        statement.execute(addAmount);
                    }
                }
                
                if (sizeStockIdCheckboxes != null) {
                    for(String id : sizeStockIdCheckboxes){
                        String currentSizeStock = request.getParameter(id + "sizeStock");
                        String addSizeAmount = "UPDATE METAL.CLOTHING_SIZE_STOCKS SET METAL.CLOTHING_SIZE_STOCKS.STOCK = " + currentSizeStock + " + " + amount + " WHERE ID = " + id;

                        
                        String itemID = request.getParameter(id + "itemID");
                        String itemStockQuery = "SELECT METAL.ITEMS.STOCK FROM METAL.ITEMS WHERE ID = " + itemID;
                        String addAmount = "UPDATE METAL.ITEMS SET METAL.ITEMS.STOCK = (" + itemStockQuery + ") + " + amount + " WHERE ID = " + itemID;
                        statement.execute(addSizeAmount);
                        statement.execute(addAmount);
                    }
                }
                request.getRequestDispatcher("./stocksAdmin.jsp").forward(request, response);
                
            } else if (request.getParameter("remove") != null) {
                connection.setAutoCommit(false);
                if (noSizeStockIdCheckboxes != null) {
                    for (String id : noSizeStockIdCheckboxes){
                        String currentStock = request.getParameter(id + "stock");
                        
                        if (Integer.parseInt(currentStock) < Integer.parseInt(amount)) {
                            throw new SQLException();
                        }
                        
                        String addAmount = "UPDATE METAL.ITEMS SET METAL.ITEMS.STOCK = " + currentStock + " - " + amount + " WHERE ID = " + id;
                        statement.execute(addAmount);
                    }
                }
                
                if (sizeStockIdCheckboxes != null) {
                    for(String id : sizeStockIdCheckboxes){
                        String currentSizeStock = request.getParameter(id + "sizeStock");
                        
                        if (Integer.parseInt(currentSizeStock) < Integer.parseInt(amount)) {
                            throw new SQLException();
                        }
                        
                        String addSizeAmount = "UPDATE METAL.CLOTHING_SIZE_STOCKS SET METAL.CLOTHING_SIZE_STOCKS.STOCK = " + currentSizeStock + " - " + amount + " WHERE ID = " + id;

                        String itemID = request.getParameter(id + "itemID");
                        String itemStockQuery = "SELECT METAL.ITEMS.STOCK FROM METAL.ITEMS WHERE ID = (" + itemID + ")";
                        String addAmount = "UPDATE METAL.ITEMS SET METAL.ITEMS.STOCK = (" + itemStockQuery + ") - " + amount + " WHERE ID = " + itemID;
                        statement.execute(addSizeAmount);
                        statement.execute(addAmount);
                    }
                }
                connection.commit();
                connection.setAutoCommit(true);
                
                request.getRequestDispatcher("./stocksAdmin.jsp").forward(request, response);
                
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdminUsersServlet.class.getName()).log(Level.SEVERE, null, ex);
            if (connection != null){
                try {
                    connection.rollback();
                    request.getRequestDispatcher("./stocksAdmin.jsp").forward(request, response);
                } catch (SQLException ex1) {
                    Logger.getLogger(AdminStocksServlet.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
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
