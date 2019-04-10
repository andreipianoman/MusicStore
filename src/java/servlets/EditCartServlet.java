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
import java.sql.PreparedStatement;
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
public class EditCartServlet extends HttpServlet {

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
        
        String operation = request.getParameter("operation");
        String item = request.getParameter("name");
        String size = request.getParameter("size");
        Integer userID = (Integer) request.getSession().getAttribute("currentUser");
        
        try{
            Class driverClass = Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            
            //Get Size id
            String sizeIDQuery = "SELECT METAL.SIZES.ID FROM METAL.SIZES WHERE METAL.SIZES.SIZE = '" + size + "'";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sizeIDQuery);
            Integer sizeID = null;
            if (resultSet.next()) {
                sizeID = ((Integer) resultSet.getObject(1));
            }
            
            //Get Item id
            String itemIDQuery = "SELECT METAL.ITEMS.ID FROM METAL.ITEMS WHERE METAL.ITEMS.NAME = '" + item + "'";
            resultSet = statement.executeQuery(itemIDQuery);
            Integer itemID = null;
            if (resultSet.next()) {
                itemID = ((Integer) resultSet.getObject(1));
            }
            
            String cartItemQuantityQuery = "SELECT METAL.CART_ITEMS.QUANTITY FROM METAL.CART_ITEMS WHERE METAL.CART_ITEMS.ITEM_ID = " + itemID + " AND METAL.CART_ITEMS.SIZE_ID = " + sizeID + " AND METAL.CART_ITEMS.USER_ID = " + userID;
            String cartItemPriceQuery = "SELECT METAL.CART_ITEMS.PRICE FROM METAL.CART_ITEMS WHERE METAL.CART_ITEMS.ITEM_ID = " + itemID + " AND METAL.CART_ITEMS.SIZE_ID = " + sizeID + " AND METAL.CART_ITEMS.USER_ID = " + userID;
            String itemStockQuery = "SELECT METAL.ITEMS.STOCK FROM METAL.ITEMS WHERE METAL.ITEMS.ID = " + itemID;
            String itemSizeStockQuery = "SELECT METAL.CLOTHING_SIZE_STOCKS.STOCK FROM METAL.CLOTHING_SIZE_STOCKS WHERE METAL.CLOTHING_SIZE_STOCKS.ITEM_ID = " + itemID + " AND METAL.CLOTHING_SIZE_STOCKS.SIZE_ID = " + sizeID;
            String itemPriceQuery = "SELECT METAL.ITEMS.PRICE FROM METAL.ITEMS WHERE METAL.ITEMS.ID = " + itemID;
            
            if (operation.equals("+")) {
                String increaseCartItemQuantity = "UPDATE METAL.CART_ITEMS SET METAL.CART_ITEMS.QUANTITY = (" + cartItemQuantityQuery + ") + 1 WHERE METAL.CART_ITEMS.ITEM_ID = " + itemID + " AND METAL.CART_ITEMS.SIZE_ID = " + sizeID + " AND METAL.CART_ITEMS.USER_ID = " + userID;
                String increaseCartItemPrice = "UPDATE METAL.CART_ITEMS SET METAL.CART_ITEMS.PRICE = (" + cartItemPriceQuery + ") + (" + itemPriceQuery + ") WHERE METAL.CART_ITEMS.ITEM_ID = " + itemID + " AND METAL.CART_ITEMS.SIZE_ID = " + sizeID + " AND METAL.CART_ITEMS.USER_ID = " + userID;
                String decreaseItemStock = "UPDATE METAL.ITEMS SET METAL.ITEMS.STOCK = (" + itemStockQuery + ") - 1 WHERE METAL.ITEMS.ID = " + itemID;
                String decreaseItemSizeStock = "UPDATE METAL.CLOTHING_SIZE_STOCKS SET METAL.CLOTHING_SIZE_STOCKS.STOCK = (" + itemSizeStockQuery + ") - 1 WHERE METAL.CLOTHING_SIZE_STOCKS.ITEM_ID = " + itemID + " AND METAL.CLOTHING_SIZE_STOCKS.SIZE_ID = " + sizeID;
                
                statement.execute(increaseCartItemQuantity);
                statement.execute(increaseCartItemPrice);
                statement.execute(decreaseItemStock);
                statement.execute(decreaseItemSizeStock);
                
                request.getRequestDispatcher("./cart.jsp").forward(request, response);
                
            } else if (operation.equals("-")) {
                String decreaseCartItemQuantity = "UPDATE METAL.CART_ITEMS SET METAL.CART_ITEMS.QUANTITY = (" + cartItemQuantityQuery + ") - 1 WHERE METAL.CART_ITEMS.ITEM_ID = " + itemID + " AND METAL.CART_ITEMS.SIZE_ID = " + sizeID + " AND METAL.CART_ITEMS.USER_ID = " + userID;
                String decreaseCartItemPrice = "UPDATE METAL.CART_ITEMS SET METAL.CART_ITEMS.PRICE = (" + cartItemPriceQuery + ") - (" + itemPriceQuery + ") WHERE METAL.CART_ITEMS.ITEM_ID = " + itemID + " AND METAL.CART_ITEMS.SIZE_ID = " + sizeID + " AND METAL.CART_ITEMS.USER_ID = " + userID;
                String increaseItemStock = "UPDATE METAL.ITEMS SET METAL.ITEMS.STOCK = (" + itemStockQuery + ") + 1 WHERE METAL.ITEMS.ID = " + itemID;
                String increaseItemSizeStock = "UPDATE METAL.CLOTHING_SIZE_STOCKS SET METAL.CLOTHING_SIZE_STOCKS.STOCK = (" + itemSizeStockQuery + ") + 1 WHERE METAL.CLOTHING_SIZE_STOCKS.ITEM_ID = " + itemID + " AND METAL.CLOTHING_SIZE_STOCKS.SIZE_ID = " + sizeID;
                
                statement.execute(decreaseCartItemQuantity);
                statement.execute(decreaseCartItemPrice);
                statement.execute(increaseItemStock);
                statement.execute(increaseItemSizeStock);
                
                request.getRequestDispatcher("./cart.jsp").forward(request, response);
            } else {
                String addToItemStock = "UPDATE METAL.ITEMS SET METAL.ITEMS.STOCK = (" + itemStockQuery + ") + (" + cartItemQuantityQuery + ") WHERE METAL.ITEMS.ID = " + itemID;;
                String addToSizeStock = "UPDATE METAL.CLOTHING_SIZE_STOCKS SET METAL.CLOTHING_SIZE_STOCKS.STOCK = (" + itemSizeStockQuery + ") + (" + cartItemQuantityQuery + ") WHERE METAL.CLOTHING_SIZE_STOCKS.ITEM_ID = " + itemID + " AND METAL.CLOTHING_SIZE_STOCKS.SIZE_ID = " + sizeID;
                String deleteFromCart = "DELETE FROM METAL.CART_ITEMS WHERE METAL.CART_ITEMS.ITEM_ID = " + itemID + " AND METAL.CART_ITEMS.SIZE_ID = " + sizeID + " AND METAL.CART_ITEMS.USER_ID = " + userID;
                
                statement.execute(addToItemStock);
                statement.execute(addToSizeStock);
                statement.execute(deleteFromCart);
                
                request.getRequestDispatcher("./cart.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EditCartServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException();
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
