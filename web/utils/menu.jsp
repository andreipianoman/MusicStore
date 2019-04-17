<%-- 
    Document   : menu
    Created on : Feb 12, 2019, 6:59:40 PM
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
                <a href='./mainAdmin.jsp'>admin</a>
                <a href='./items.jsp'>all products</a>
                <a href='./bands.jsp'>bands</a>
                <a href='./labels.jsp'>labels</a>
                <a href='./cart.jsp'>cart</a>
            </c:when>
            <c:when test="${currentUserRole == 1}">
                <a href='./items.jsp'>all products</a>
                <a href='./bands.jsp'>bands</a>
                <a href='./labels.jsp'>labels</a>
                <a href='./cart.jsp'>cart</a>
            </c:when>
        </c:choose>
                
        <form action="${pageContext.request.contextPath}/SearchServlet" method="GET">
            <input type="submit" value="search"><input type="text" name="search">
            <select name="type">
                 <option value="ITEMS">item</option>
                 <option value="LABELS">label</option>
                 <option value="BANDS">band</option>
            </select>
        </form>
    </body>
</html>