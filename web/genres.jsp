<%-- 
    Document   : labels
    Created on : Feb 19, 2019, 7:34:24 PM
    Author     : Turbotwins
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Genres Page</title>
    </head>
    <body>
        <h1>Genres Page</h1>
        <sql:setDataSource 
        var="snapshot" 
        driver="org.apache.derby.jdbc.ClientDriver"
        url="jdbc:derby://localhost:1527/metal;create=true;"
        user="metal"  
        password="metal"/>
        <sql:query dataSource="${snapshot}" var="genres">
            SELECT METAL.GENRES.NAME FROM METAL.GENRES
        </sql:query>
            <table>
                    <c:forEach var="row" varStatus="loop" items="${genres.rows}">
                        <tr>
                            <td><c:out value="${row.name}"/></td>
                        </tr>
                    </c:forEach> 
                </tbody>
            </table>
    </body>
</html>
