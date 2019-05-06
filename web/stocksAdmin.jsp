<%-- 
    Document   : genresAdmin
    Created on : Feb 19, 2019, 7:55:39 PM
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
        <script type="text/javascript" src="./JavaScript/script.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <%@ include file="./utils/admin.jsp" %>
        <c:choose>
            <c:when test="${currentUserRole == 2}">
                <sql:setDataSource 
                var="snapshot" 
                driver="org.apache.derby.jdbc.ClientDriver"
                url="jdbc:derby://localhost:1527/metal;create=true;"
                user="metal"  
                password="metal"/>
                <sql:query dataSource="${snapshot}" var="noSizeStocks">
                    SELECT METAL.ITEMS.ID, METAL.ITEMS.NAME AS ITEM, METAL.ITEMS.STOCK
                    FROM METAL.ITEMS
                    INNER JOIN METAL.CATEGORIES ON METAL.ITEMS.CATEGORY_ID = METAL.CATEGORIES.ID
                    WHERE METAL.CATEGORIES.NAME != 'T-Shirt'
                </sql:query>
                <sql:query dataSource="${snapshot}" var="sizeStocks">
                    SELECT METAL.CLOTHING_SIZE_STOCKS.ID, METAL.ITEMS.NAME AS ITEM, METAL.SIZES.SIZE, METAL.CLOTHING_SIZE_STOCKS.ITEM_ID, METAL.CLOTHING_SIZE_STOCKS.STOCK
                    FROM METAL.CLOTHING_SIZE_STOCKS
                    INNER JOIN METAL.ITEMS ON METAL.CLOTHING_SIZE_STOCKS.ITEM_ID = METAL.ITEMS.ID
                    INNER JOIN METAL.SIZES ON METAL.CLOTHING_SIZE_STOCKS.SIZE_ID = METAL.SIZES.ID
                </sql:query>
                <h1>Admin Stocks</h1>
                <form action="${pageContext.request.contextPath}/AdminStocksServlet" method="POST">
                <table border="1">
                    <thead>
                        <tr>
                            <td></td>
                            <td>Item</td>
                            <td>Size</td>
                            <td>Stock</td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="row" varStatus="loop" items="${noSizeStocks.rows}">
                            <tr>
                                <td><input type="checkbox" name="noSizeStockIdCheckbox" value="${row.id}"></td>
                                <td>${row.item}</td>
                                <td>N/A</td>
                                <td>${row.stock} <input type="text" value="${row.stock}" class="hidden" name="${row.id}stock"></td>
                            </tr>
                        </c:forEach>
                        <c:forEach var="row" varStatus="loop" items="${sizeStocks.rows}">
                            <tr>
                                <td><input type="checkbox" name="sizeStockIdCheckbox" value="${row.id}"><input type="text" value="${row.item_id}" class="hidden" name="${row.id}itemID"></td>
                                <td>${row.item}</td>
                                <td>${row.size}</td>
                                <td>${row.stock} <input type="text" value="${row.stock}" class="hidden" name="${row.id}sizeStock"></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table><br>

                <span>Amount: </span><input type="number" min="1" value="1" name="amount">


                <br>
                <input type="submit" value="Add" name="add">
                <br>
                <input type="submit" value="Remove" name="remove">
                </form>
            </c:when>
        </c:choose>
    </body>
</html>
