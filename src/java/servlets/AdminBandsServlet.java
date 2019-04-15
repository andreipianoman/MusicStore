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
public class AdminBandsServlet extends HttpServlet {

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
                
                
                String bandName = request.getParameter("Band name");
                String genreID = request.getParameter("genre");
                String labelID = request.getParameter("label");
                String website = request.getParameter("website");
                String countryID = request.getParameter("country");
                String imageID = request.getParameter("imageCheckbox");
                
                if ("".equals(bandName) || "".equals(website) || imageID == null) {
                    request.getRequestDispatcher("./bandsAdmin.jsp").forward(request, response);
                    return;
                }
                
                Integer count_id = 1;
                while (true) {
                    String query = "SELECT * FROM BANDS WHERE ID = " + count_id;
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
                Integer newBandId = count_id;
                
                String insertBand = "INSERT INTO METAL.BANDS (ID, NAME, GENRE_ID, LABEL_ID, WEBSITE, COUNTRY_ID, IMAGE_ID) VALUES (" + newBandId + ", '" + bandName + "', " + genreID + ", " + labelID + ", '" + website + "', " + countryID + ", " + imageID + ")";
                statement.execute(insertBand);
                
                request.getRequestDispatcher("./bandsAdmin.jsp").forward(request, response);
                
            } else if (request.getParameter("update") != null) {
                
                String[] selectedIdCheckboxes = request.getParameterValues("bandIdCheckbox");
                String bandName = request.getParameter("Band name");
                String genreID = request.getParameter("genre");
                String labelID = request.getParameter("label");
                String website = request.getParameter("website");
                String countryID = request.getParameter("country");
                String imageID = request.getParameter("imageCheckbox");
                
                if (selectedIdCheckboxes == null) {
                    request.getRequestDispatcher("./bandsAdmin.jsp").forward(request, response);
                    return;
                }
                
                for(String id : selectedIdCheckboxes){
                    String updateBand;
                    if ("".equals(bandName) && "".equals(website) && imageID == null) {
                        updateBand = "UPDATE METAL.BANDS SET METAL.BANDS.GENRE_ID = " + genreID + ", METAL.BANDS.LABEL_ID = " + labelID + ", METAL.BANDS.COUNTRY_ID = " + countryID + " WHERE METAL.BANDS.ID = " + id;
                    } else if ("".equals(bandName) && "".equals(website)) {
                        updateBand = "UPDATE METAL.BANDS SET METAL.BANDS.GENRE_ID = " + genreID + ", METAL.BANDS.LABEL_ID = " + labelID + ", METAL.BANDS.COUNTRY_ID = " + countryID + ", METAL.BANDS.IMAGE_ID = " + imageID + " WHERE METAL.BANDS.ID = " + id;
                    } else if ("".equals(bandName) && imageID == null) {
                        updateBand = "UPDATE METAL.BANDS SET METAL.BANDS.GENRE_ID = " + genreID + ", METAL.BANDS.LABEL_ID = " + labelID + ", METAL.BANDS.COUNTRY_ID = " + countryID + ", METAL.BANDS.WEBSITE = '" + website + "' WHERE METAL.BANDS.ID = " + id;
                    } else if ("".equals(website) && imageID == null) {
                        updateBand = "UPDATE METAL.BANDS SET METAL.BANDS.GENRE_ID = " + genreID + ", METAL.BANDS.LABEL_ID = " + labelID + ", METAL.BANDS.COUNTRY_ID = " + countryID + ", METAL.BANDS.NAME = '" + bandName + "' WHERE METAL.BANDS.ID = " + id;
                    } else if ("".equals(website)) {
                        updateBand = "UPDATE METAL.BANDS SET METAL.BANDS.GENRE_ID = " + genreID + ", METAL.BANDS.LABEL_ID = " + labelID + ", METAL.BANDS.COUNTRY_ID = " + countryID + ", METAL.BANDS.NAME = '" + bandName + "', METAL.BANDS.IMAGE_ID = " + imageID + " WHERE METAL.BANDS.ID = " + id;
                    } else if (imageID == null) {
                        updateBand = "UPDATE METAL.BANDS SET METAL.BANDS.GENRE_ID = " + genreID + ", METAL.BANDS.LABEL_ID = " + labelID + ", METAL.BANDS.COUNTRY_ID = " + countryID + ", METAL.BANDS.NAME = '" + bandName + "', METAL.BANDS.WEBSITE = '" + website + "' WHERE METAL.BANDS.ID = " + id;
                    } else if ("".equals(bandName)) {
                        updateBand = "UPDATE METAL.BANDS SET METAL.BANDS.GENRE_ID = " + genreID + ", METAL.BANDS.LABEL_ID = " + labelID + ", METAL.BANDS.COUNTRY_ID = " + countryID + ", METAL.BANDS.WEBSITE = '" + website + "', METAL.BANDS.IMAGE_ID = " + imageID + " WHERE METAL.BANDS.ID = " + id;
                    } else {
                        updateBand = "UPDATE METAL.BANDS SET METAL.BANDS.GENRE_ID = " + genreID + ", METAL.BANDS.LABEL_ID = " + labelID + ", METAL.BANDS.COUNTRY_ID = " + countryID + ", METAL.BANDS.NAME = '" + bandName + "', METAL.BANDS.WEBSITE = '" + website + "', METAL.BANDS.IMAGE_ID = " + imageID + " WHERE METAL.BANDS.ID = " + id;
                    }
                    statement = connection.createStatement();
                    statement.execute(updateBand);
                }
                request.getRequestDispatcher("./bandsAdmin.jsp").forward(request, response);
                
            } else if (request.getParameter("delete") != null) {
                
                String[] selectedIdCheckboxes = request.getParameterValues("bandIdCheckbox");
                
                if (selectedIdCheckboxes == null) {
                    request.getRequestDispatcher("./bandsAdmin.jsp").forward(request, response);
                    return;
                }
                
                for(String id : selectedIdCheckboxes){
                    String deleteLabel = "DELETE FROM METAL.BANDS WHERE METAL.BANDS.ID = " + id;
                    statement = connection.createStatement();
                    statement.execute(deleteLabel);
                }
                request.getRequestDispatcher("./bandsAdmin.jsp").forward(request, response);
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
