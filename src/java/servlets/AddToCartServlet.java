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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilityClasses.CartItem;

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
        
        Connection connection = null;
        PreparedStatement pstmnt = null;
        PreparedStatement pstmntSize = null;
        String user = "metal";
        String password = "metal";
        String url = "jdbc:derby://localhost:1527/metal;create=true";
        String driver = "org.apache.derby.jdbc.ClientDriver";
        
        String itemID = request.getParameter("id");
        String itemName = request.getParameter("name");
        String itemCategory = request.getParameter("category");
        Double itemPrice = Double.parseDouble(request.getParameter("price"));
        String itemSize = request.getParameter("size_option");
        Integer itemQuantity = Integer.parseInt(request.getParameter("quantity"));
        String itemImage = request.getParameter("imageAddress");
        
        if (!(itemCategory.equals("T-Shirt")) && !(itemCategory.equals("Girlie"))  && !(itemCategory.equals("Longsleeve")) && !(itemCategory.equals("Jacket/Hoodie")) && !(itemCategory.equals("Girlie Longsleeve"))) {
        
            if (request.getSession().getAttribute("cart") == null) {
                ArrayList<CartItem> cart = new ArrayList<CartItem>();
                CartItem cartItem = new CartItem(1, itemName, itemPrice * itemQuantity, itemQuantity, itemSize, itemImage);
                cart.add(cartItem);
                request.getSession().setAttribute("cart", cart);
                request.getSession().setAttribute("item", itemName);
            } else {
                ArrayList<CartItem> cart = (ArrayList) request.getSession().getAttribute("cart");

                boolean found = false;
                for (CartItem x : cart) {
                    if (x.getName().equals(itemName)) {
                        x.setQuantity(x.getQuantity() + itemQuantity);
                        x.setPrice(x.getQuantity() * itemPrice);
                        found = true;
                        break;
                    } else {
                        continue;
                    }
                }

                if (found == false) {
                    CartItem cartItem = new CartItem(cart.size() + 3, itemName, itemPrice * itemQuantity, itemQuantity, itemSize, itemImage);
                    cart.add(cartItem);
                }
                request.getSession().setAttribute("cart", cart);
                request.getSession().setAttribute("item", itemName);
            }

            try
            {
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                String DML = "UPDATE METAL.ITEMS SET METAL.ITEMS.STOCK = (SELECT METAL.ITEMS.STOCK FROM METAL.ITEMS WHERE METAL.ITEMS.NAME = '" + itemName + "') - " + itemQuantity + " WHERE METAL.ITEMS.NAME = '" + itemName + "'";
                pstmnt = connection.prepareStatement(DML);
                pstmnt.execute();
            } catch (ClassNotFoundException | SQLException e) {
                Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, e);
                throw new SQLException();
            }
            
            request.getRequestDispatcher("./itemDetails.jsp").forward(request, response);
            
        } else {
            
            if (null != request.getSession().getAttribute("cart")) {
                
                ArrayList<CartItem> cart = (ArrayList) request.getSession().getAttribute("cart");

                boolean found = false;
                for (CartItem x : cart) {
                    if (x.getName().equals(itemName) && x.getSize().equals(itemSize)) {
                        x.setQuantity(x.getQuantity() + itemQuantity);
                        x.setPrice(x.getQuantity() * itemPrice);
                        found = true;
                        break;
                    } else {
                        continue;
                    }
                }

                if (found == false) {
                    CartItem cartItem = new CartItem(cart.size() + 3, itemName, itemPrice * itemQuantity, itemQuantity, itemSize, itemImage);
                    cart.add(cartItem);
                }
                request.getSession().setAttribute("cart", cart);
                request.getSession().setAttribute("item", itemName);
                
            } else {
                ArrayList<CartItem> cart = new ArrayList<CartItem>();
                CartItem cartItem = new CartItem(1, itemName, itemPrice * itemQuantity, itemQuantity, itemSize, itemImage);
                cart.add(cartItem);
                request.getSession().setAttribute("cart", cart);
                request.getSession().setAttribute("item", itemName);
            }

            try
            {
                Class driverClass = Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                String itemStockQuery = "SELECT METAL.ITEMS.STOCK FROM METAL.ITEMS WHERE METAL.ITEMS.NAME = '" + itemName + "'";
                String updateItemStock = "UPDATE METAL.ITEMS SET METAL.ITEMS.STOCK = (" + itemStockQuery + ") - " + itemQuantity + " WHERE METAL.ITEMS.NAME = '" + itemName + "'";
                
                String sizeIDQuery = "SELECT METAL.SHIRT_SIZES.ID FROM METAL.SHIRT_SIZES WHERE METAL.SHIRT_SIZES.SIZE = '" + itemSize + "'";
                String itemSizeStockQuery = "SELECT METAL.CLOTHING_SIZE_STOCKS.STOCK FROM METAL.CLOTHING_SIZE_STOCKS WHERE CLOTHING_SIZE_STOCKS.ITEM_ID = " + itemID + " AND METAL.CLOTHING_SIZE_STOCKS.SIZE_ID = (" + sizeIDQuery + ")";
                String updateItemSizeStock = "UPDATE METAL.CLOTHING_SIZE_STOCKS SET METAL.CLOTHING_SIZE_STOCKS.STOCK = (" + itemSizeStockQuery + ") - " + itemQuantity + " WHERE ITEM_ID = " + itemID + " AND SIZE_ID = (" + sizeIDQuery + ")";
                //String updateItemSizeStock = "UPDATE METAL.CLOTHING_SIZE_STOCKS SET METAL.CLOTHING_SIZE_STOCKS.STOCK = 10 - 3 WHERE ITEM_ID = 2 AND SIZE_ID = (SELECT METAL.SHIRT_SIZES.ID FROM METAL.SHIRT_SIZES WHERE METAL.SHIRT_SIZES.SIZE = 'XS')";
                
                pstmnt = connection.prepareStatement(updateItemStock);
                pstmntSize = connection.prepareStatement(updateItemSizeStock);
                pstmntSize.execute();
                pstmnt.execute();
            } catch (ClassNotFoundException | SQLException e) {
                Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, e);
                throw new SQLException();
            }
            
            request.getRequestDispatcher("./itemDetails.jsp").forward(request, response);
            
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
