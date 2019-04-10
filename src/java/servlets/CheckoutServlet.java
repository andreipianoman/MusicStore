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
import java.util.ArrayList;
import java.util.List;
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
public class CheckoutServlet extends HttpServlet {

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
        PreparedStatement pstmntOrderItems = null;
        PreparedStatement pstmntCreateOrder = null;
        String user = "metal";
        String password = "metal";
        String url = "jdbc:derby://localhost:1527/metal;create=true";
        String driver = "org.apache.derby.jdbc.ClientDriver";
        
        Integer userID = (Integer) request.getSession().getAttribute("currentUser");
        
        
        try{
            Class driverClass = Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            
            //Find smallest unused id in ORDERS and create order.
            Integer count_id = 1;
            while (true) {
                String query = "SELECT * FROM ORDERS WHERE ID = " + count_id;
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
            Integer orderID = count_id;
            String createOrder = "INSERT INTO METAL.ORDERS VALUES (" + orderID + ", " + userID + ")";
            pstmntCreateOrder = connection.prepareStatement(createOrder);
            String cartItemsQuery = "SELECT * FROM CART_ITEMS WHERE USER_ID = " + userID;
            resultSet = statement.executeQuery(cartItemsQuery);
            if (resultSet.next()) {
                pstmntCreateOrder.execute();
                do {
                    Integer itemID = resultSet.getInt(2);
                    Integer quantity = resultSet.getInt(3);
                    Integer sizeID = resultSet.getInt(5);
                    Double price = resultSet.getDouble(4);
                    String insertOrderedItem = "INSERT INTO METAL.ORDERED_ITEMS VALUES (?, ?, ?, ?, ?)";
                    pstmntOrderItems = connection.prepareStatement(insertOrderedItem);
                    pstmntOrderItems.setInt(1, itemID);
                    pstmntOrderItems.setInt(2, orderID);
                    pstmntOrderItems.setInt(3, sizeID);
                    pstmntOrderItems.setInt(4, quantity);
                    pstmntOrderItems.setDouble(5, price);
                    pstmntOrderItems.execute();
                } while (resultSet.next());


                String removeFromCart = "DELETE FROM METAL.CART_ITEMS WHERE METAL.CART_ITEMS.USER_ID = " + userID;
                statement.execute(removeFromCart);
            }

            request.getRequestDispatcher("./MainPage.jsp").forward(request, response);
            
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, e);
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
                if (pstmntOrderItems != null) {
                    try
                    {
                       pstmntOrderItems.close();
                    }
                    catch (SQLException ex) {Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);}
                }
                if (pstmntCreateOrder != null) {
                    try
                    {
                       pstmntCreateOrder.close();
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
