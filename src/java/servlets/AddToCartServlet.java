/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
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
public class AddToCartServlet extends HttpServlet {

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
        
        Connection connection;
        PreparedStatement pstmnt;
        PreparedStatement pstmntSize;
        PreparedStatement pstmntAddToCart;
        PreparedStatement pstmntUpdateCartQuantity;
        PreparedStatement pstmntUpdateCartPrice;
        Statement statement;
        Statement sizeStatement;
        Statement userStatement;
        Statement searchItemStatement;
        String user = "metal";
        String password = "metal";
        String url = "jdbc:derby://localhost:1527/metal;create=true";
        String driver = "org.apache.derby.jdbc.ClientDriver";
        
        String itemID = request.getParameter("id");
        String itemName = request.getParameter("name");
        String itemCategory = request.getParameter("category");
        String itemSize = request.getParameter("size_option");
        Integer itemQuantity = Integer.parseInt(request.getParameter("quantity" + itemSize));
        Double itemPrice = Double.parseDouble(request.getParameter("price")) * itemQuantity;
        String itemImage = request.getParameter("imageAddress");
        
        request.getSession().setAttribute("item", itemName);
            
        if (!(itemCategory.equals("T-Shirt")) && !(itemCategory.equals("Girlie"))  && !(itemCategory.equals("Longsleeve")) && !(itemCategory.equals("Jacket/Hoodie")) && !(itemCategory.equals("Girlie Longsleeve"))) {
            
            try{
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                
                //Get Size id
                String sizeIDQuery = "SELECT METAL.SIZES.ID FROM METAL.SIZES WHERE METAL.SIZES.SIZE = '" + itemSize + "'";
                sizeStatement = connection.createStatement();
                ResultSet resultSetSizeID = sizeStatement.executeQuery(sizeIDQuery);
                Integer sizeID = null;
                if (resultSetSizeID.next()) {
                    sizeID = ((Integer) resultSetSizeID.getObject(1));
                }
                
                //Get User id
                String username = (String) request.getSession().getAttribute("currentUser");
                String userIDQuery = "SELECT METAL.USERS.ID FROM METAL.USERS WHERE METAL.USERS.USERNAME = '" + username + "'";
                userStatement = connection.createStatement();
                ResultSet resultSetUserID = userStatement.executeQuery(userIDQuery);
                Integer userID = null;
                if (resultSetUserID.next()) {
                    userID = ((Integer) resultSetUserID.getObject(1));
                }
                
                //Check if the item is already in cart
                String searchItemQuery = "SELECT * FROM CART_ITEMS WHERE ITEM_ID = " + itemID + " AND SIZE_ID = " + sizeID + " AND USER_ID = " + userID;
                searchItemStatement = connection.createStatement();
                ResultSet foundItemResultSet = searchItemStatement.executeQuery(searchItemQuery);
                boolean foundItemResultSetHasRows = foundItemResultSet.next();
                if (foundItemResultSetHasRows) {
                    
                    // Update quantity and price in CART_ITEMS table
                    String cartItemQuantityQuery = "SELECT CART_ITEMS.QUANTITY FROM CART_ITEMS WHERE ITEM_ID = " + itemID + " AND SIZE_ID = " + sizeID + " AND USER_ID = " + userID;
                    String updateItemQuantity = "UPDATE METAL.CART_ITEMS SET METAL.CART_ITEMS.QUANTITY = (" + cartItemQuantityQuery + ") + " + itemQuantity + " WHERE ITEM_ID = " + itemID + " AND SIZE_ID = " + sizeID + " AND USER_ID = " + userID;
                    pstmntUpdateCartQuantity = connection.prepareStatement(updateItemQuantity);
                    pstmntUpdateCartQuantity.execute();
                    
                    String cartItemPriceQuery = "SELECT CART_ITEMS.PRICE FROM CART_ITEMS WHERE ITEM_ID = " + itemID + " AND SIZE_ID = " + sizeID + " AND USER_ID = " + userID;
                    String updateItemPrice = "UPDATE METAL.CART_ITEMS SET METAL.CART_ITEMS.PRICE = (" + cartItemPriceQuery + ") + " + itemPrice + " WHERE ITEM_ID = " + itemID + " AND SIZE_ID = " + sizeID + " AND USER_ID = " + userID;
                    pstmntUpdateCartPrice = connection.prepareStatement(updateItemPrice);
                    pstmntUpdateCartPrice.execute();
                    
                } else {
                    
                    //Find smallest unused id in CART_ITEMS
                    Integer count_id = 1;
                    while (true) {
                        String query = "SELECT * FROM CART_ITEMS WHERE ID = " + count_id;
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

                    //Add to CART_ITEMS table with found id
                    String addToCart = "INSERT INTO METAL.CART_ITEMS (ID, ITEM_ID, QUANTITY, PRICE, SIZE_ID, USER_ID) VALUES (?, ?, ?, ?, ?, ?)";
                    pstmntAddToCart = connection.prepareStatement(addToCart);
                    pstmntAddToCart.setInt(1, count_id);
                    pstmntAddToCart.setString(2, itemID);
                    pstmntAddToCart.setInt(3, itemQuantity);
                    pstmntAddToCart.setDouble(4, itemPrice);
                    pstmntAddToCart.setInt(5, sizeID);
                    pstmntAddToCart.setInt(6, userID);
                    pstmntAddToCart.execute();
                    
                }
                //Update item stock
                String updateItemStock = "UPDATE METAL.ITEMS SET METAL.ITEMS.STOCK = (SELECT METAL.ITEMS.STOCK FROM METAL.ITEMS WHERE METAL.ITEMS.NAME = '" + itemName + "') - " + itemQuantity + " WHERE METAL.ITEMS.NAME = '" + itemName + "'";
                pstmnt = connection.prepareStatement(updateItemStock);
                pstmnt.execute();
                    
                request.getRequestDispatcher("./itemDetails.jsp").forward(request, response);
            
            } catch (ClassNotFoundException | SQLException e) {
                Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, e);
                throw new SQLException();
            }
            
        } else {

            try{
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                
                //Get Size id
                String sizeIDQuery = "SELECT METAL.SIZES.ID FROM METAL.SIZES WHERE METAL.SIZES.SIZE = '" + itemSize + "'";
                sizeStatement = connection.createStatement();
                ResultSet resultSetSizeID = sizeStatement.executeQuery(sizeIDQuery);
                Integer sizeID = null;
                if (resultSetSizeID.next()) {
                    sizeID = ((Integer) resultSetSizeID.getObject(1));
                }
                
                //Get User id
                String username = (String) request.getSession().getAttribute("currentUser");
                String userIDQuery = "SELECT METAL.USERS.ID FROM METAL.USERS WHERE METAL.USERS.USERNAME = '" + username + "'";
                userStatement = connection.createStatement();
                ResultSet resultSetUserID = userStatement.executeQuery(userIDQuery);
                Integer userID = null;
                if (resultSetUserID.next()) {
                    userID = ((Integer) resultSetUserID.getObject(1));
                }
                
                //Check if the item is already in cart
                String searchItemQuery = "SELECT * FROM CART_ITEMS WHERE ITEM_ID = " + itemID + " AND SIZE_ID = " + sizeID + " AND USER_ID = " + userID;
                searchItemStatement = connection.createStatement();
                ResultSet foundItemResultSet = searchItemStatement.executeQuery(searchItemQuery);
                boolean foundItemResultSetHasRows = foundItemResultSet.next();
                if (foundItemResultSetHasRows) {
                    
                    // Update quantity and price in CART_ITEMS table
                    String cartItemQuantityQuery = "SELECT CART_ITEMS.QUANTITY FROM CART_ITEMS WHERE ITEM_ID = " + itemID + " AND SIZE_ID = " + sizeID + " AND USER_ID = " + userID;
                    String updateItemQuantity = "UPDATE METAL.CART_ITEMS SET METAL.CART_ITEMS.QUANTITY = (" + cartItemQuantityQuery + ") + " + itemQuantity + " WHERE ITEM_ID = " + itemID + " AND SIZE_ID = " + sizeID + " AND USER_ID = " + userID;
                    pstmntUpdateCartQuantity = connection.prepareStatement(updateItemQuantity);
                    pstmntUpdateCartQuantity.execute();
                    
                    String cartItemPriceQuery = "SELECT CART_ITEMS.PRICE FROM CART_ITEMS WHERE ITEM_ID = " + itemID + " AND SIZE_ID = " + sizeID + " AND USER_ID = " + userID;
                    String updateItemPrice = "UPDATE METAL.CART_ITEMS SET METAL.CART_ITEMS.PRICE = (" + cartItemPriceQuery + ") + " + itemPrice + " WHERE ITEM_ID = " + itemID + " AND SIZE_ID = " + sizeID + " AND USER_ID = " + userID;
                    pstmntUpdateCartPrice = connection.prepareStatement(updateItemPrice);
                    pstmntUpdateCartPrice.execute();
                    
                } else {
                    
                    //Find smallest unused id in CART_ITEMS
                    Integer count_id = 1;
                    while (true) {
                        String query = "SELECT * FROM CART_ITEMS WHERE ID = " + count_id;
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

                    //Add to CART_ITEMS table with found id
                    String addToCart = "INSERT INTO METAL.CART_ITEMS (ID, ITEM_ID, QUANTITY, PRICE, SIZE_ID, USER_ID) VALUES (?, ?, ?, ?, ?, ?)";
                    pstmntAddToCart = connection.prepareStatement(addToCart);
                    pstmntAddToCart.setInt(1, count_id);
                    pstmntAddToCart.setString(2, itemID);
                    pstmntAddToCart.setInt(3, itemQuantity);
                    pstmntAddToCart.setDouble(4, itemPrice);
                    pstmntAddToCart.setInt(5, sizeID);
                    pstmntAddToCart.setInt(6, userID);
                    pstmntAddToCart.execute();
                
                }
                //Update item stock and size stock
                String itemStockQuery = "SELECT METAL.ITEMS.STOCK FROM METAL.ITEMS WHERE METAL.ITEMS.NAME = '" + itemName + "'";
                String updateItemStock = "UPDATE METAL.ITEMS SET METAL.ITEMS.STOCK = (" + itemStockQuery + ") - " + itemQuantity + " WHERE METAL.ITEMS.NAME = '" + itemName + "'";

                String itemSizeStockQuery = "SELECT METAL.CLOTHING_SIZE_STOCKS.STOCK FROM METAL.CLOTHING_SIZE_STOCKS WHERE CLOTHING_SIZE_STOCKS.ITEM_ID = " + itemID + " AND METAL.CLOTHING_SIZE_STOCKS.SIZE_ID = (" + sizeIDQuery + ")";
                String updateItemSizeStock = "UPDATE METAL.CLOTHING_SIZE_STOCKS SET METAL.CLOTHING_SIZE_STOCKS.STOCK = (" + itemSizeStockQuery + ") - " + itemQuantity + " WHERE ITEM_ID = " + itemID + " AND SIZE_ID = (" + sizeIDQuery + ")";
                pstmnt = connection.prepareStatement(updateItemStock);
                pstmntSize = connection.prepareStatement(updateItemSizeStock);
                pstmntSize.execute();
                pstmnt.execute();
                request.getRequestDispatcher("./itemDetails.jsp").forward(request, response);
            
            } catch (ClassNotFoundException | SQLException e) {
                Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, e);
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
