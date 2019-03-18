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
        <link rel="stylesheet" type="text/css" href="./css/metalStore.css">
        <title>Products page</title>
    </head>
    <body>
        <%@ include file="./utils/menu.jsp" %>
        <h1>Items Page</h1>
        <sql:setDataSource 
        var="snapshot" 
        driver="org.apache.derby.jdbc.ClientDriver"
        url="jdbc:derby://localhost:1527/metal;create=true;"
        user="metal"  
        password="metal"/>
        <sql:query dataSource="${snapshot}" var="items">
            SELECT METAL.ITEMS.NAME, METAL.ITEMS.PRICE, METAL.CATEGORIES.NAME AS CATEGORY, METAL.IMAGES.ADDRESS AS IMAGE
            FROM METAL.ITEMS
            INNER JOIN  METAL.CATEGORIES ON METAL.ITEMS.CATEGORY_ID=METAL.CATEGORIES.ID 
            INNER JOIN METAL.IMAGES ON METAL.ITEMS.IMAGE_ID=METAL.IMAGES.ID
        </sql:query>
            <table border="1">
                <thead>
                    <tr>
                        <td>Name</td>
                        <td>Price</td>
                        <td>Category</td>
                        <td>Image</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="row" varStatus="loop" items="${items.rows}">
                        <tr>
                            <td><form action="${pageContext.request.contextPath}/ItemDetailServlet" method="GET"><input class="submitLink" type="submit" value="${row.name}" name="item"/></form></td>
                            <td><c:out value="${row.price}"/></td>
                            <td><c:out value="${row.category}"/></td>
                            <td><img src="${row.image}"></td>
                        </tr>
                    </c:forEach> 
                </tbody>
            </table>
    </body>
</html>
