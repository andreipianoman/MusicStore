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
public class AdminLabelsServlet extends HttpServlet {

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
                
                String labelName = request.getParameter("Label name");
                String website = request.getParameter("website");
                String countryID = request.getParameter("country");
                String imageID = request.getParameter("imageCheckbox");

                if ("".equals(labelName) || "".equals(website) || imageID == null) {
                    request.getRequestDispatcher("./labelsAdmin.jsp").forward(request, response);
                    return;
                }
                
                Integer count_id = 1;
                while (true) {
                    String query = "SELECT * FROM LABELS WHERE ID = " + count_id;
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
                Integer newLabelId = count_id;
                
                String insertLabel = "INSERT INTO METAL.LABELS (ID, NAME, WEBSITE, COUNTRY_ID, IMAGE_ID) VALUES (" + newLabelId + ", '" + labelName + "', '" + website + "', " + countryID + ", " + imageID + ")";
                statement.execute(insertLabel);
                
                request.getRequestDispatcher("./labelsAdmin.jsp").forward(request, response);
                
            } else if (request.getParameter("update") != null) {
                
                String[] selectedIdCheckboxes = request.getParameterValues("labelIdCheckbox");
                String labelName = request.getParameter("Label name");
                String website = request.getParameter("website");
                String countryID = request.getParameter("country");
                String imageID = request.getParameter("imageCheckbox");
                
                if (selectedIdCheckboxes == null) {
                    request.getRequestDispatcher("./labelsAdmin.jsp").forward(request, response);
                    return;
                }
                
                for(String id : selectedIdCheckboxes){
                    String updateLabel;
                    if ("".equals(labelName) && "".equals(website) && imageID == null) {
                        updateLabel = "UPDATE METAL.LABELS SET METAL.LABELS.COUNTRY_ID = " + countryID + " WHERE METAL.LABELS.ID = " + id;
                    } else if ("".equals(labelName) && "".equals(website)) {
                        updateLabel = "UPDATE METAL.LABELS SET METAL.LABELS.COUNTRY_ID = " + countryID + ", METAL.LABELS.IMAGE_ID = " + imageID + " WHERE METAL.LABELS.ID = " + id;
                    } else if ("".equals(labelName) && imageID == null) {
                        updateLabel = "UPDATE METAL.LABELS SET METAL.LABELS.COUNTRY_ID = " + countryID + ", METAL.LABELS.WEBSITE = '" + website + "' WHERE METAL.LABELS.ID = " + id;
                    } else if ("".equals(website) && imageID == null) {
                        updateLabel = "UPDATE METAL.LABELS SET METAL.LABELS.COUNTRY_ID = " + countryID + ", METAL.LABELS.NAME = '" + labelName + "' WHERE METAL.LABELS.ID = " + id;
                    } else if ("".equals(website)) {
                        updateLabel = "UPDATE METAL.LABELS SET METAL.LABELS.COUNTRY_ID = " + countryID + ", METAL.LABELS.NAME = '" + labelName + "', METAL.LABELS.IMAGE_ID = " + imageID + " WHERE METAL.LABELS.ID = " + id;
                    } else if (imageID == null) {
                        updateLabel = "UPDATE METAL.LABELS SET METAL.LABELS.COUNTRY_ID = " + countryID + ", METAL.LABELS.NAME = '" + labelName + "', METAL.LABELS.WEBSITE = '" + website + "' WHERE METAL.LABELS.ID = " + id;
                    } else if ("".equals(labelName)) {
                        updateLabel = "UPDATE METAL.LABELS SET METAL.LABELS.COUNTRY_ID = " + countryID + ", METAL.LABELS.IMAGE_ID = " + imageID + ", METAL.LABELS.WEBSITE = '" + website + "' WHERE METAL.LABELS.ID = " + id;
                    } else {
                        updateLabel = "UPDATE METAL.LABELS SET METAL.LABELS.COUNTRY_ID = " + countryID + ", METAL.LABELS.NAME = '" + labelName + "', METAL.LABELS.WEBSITE = '" + website + "', METAL.LABELS.IMAGE_ID = " + imageID + " WHERE METAL.LABELS.ID = " + id;
                    }
                    statement = connection.createStatement();
                    statement.execute(updateLabel);
                }
                request.getRequestDispatcher("./labelsAdmin.jsp").forward(request, response);
            } else if (request.getParameter("delete") != null) {
                String[] selectedIdCheckboxes = request.getParameterValues("labelIdCheckbox");
                if (selectedIdCheckboxes == null) {
                    request.getRequestDispatcher("./labelsAdmin.jsp").forward(request, response);
                    return;
                }
                
                for(String id : selectedIdCheckboxes){
                    String deleteLabel = "DELETE FROM METAL.LABELS WHERE METAL.LABELS.ID = " + id;
                    statement = connection.createStatement();
                    statement.execute(deleteLabel);
                }
                request.getRequestDispatcher("./labelsAdmin.jsp").forward(request, response);
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
