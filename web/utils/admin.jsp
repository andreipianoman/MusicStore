<%-- 
    Document   : admin
    Created on : Feb 19, 2019, 7:33:00 PM
    Author     : Turbotwins
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/metalStore.css">
        <title>JSP Page</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${currentUserRole == 2}">
                <a href="imagesAdmin.jsp">Images</a>
                <a href="bandsAdmin.jsp">Bands</a>
                <a href="countriesAdmin.jsp">Countries</a>
                <a href="labelsAdmin.jsp">Labels</a>
                <a href="genresAdmin.jsp">Genres</a>
                <a href="itemsAdmin.jsp">Items</a>
                <a href="usersAdmin.jsp">Users</a>
                <a href="stocksAdmin.jsp">Stocks</a>
                <a href="MainPage.jsp">Main Page</a>
            </c:when>
            <c:otherwise>
                <span>You don't have admin rights. Go to the login page </span><a href='Index.jsp'>here</a><span>.</span><br>
                <span>Return to the Home page </span><a href='MainPage.jsp'>here</a><span>.</span>
            </c:otherwise>
        </c:choose>
    </body>
</html>
