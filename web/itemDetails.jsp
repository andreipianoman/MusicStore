<%-- 
    Document   : labelDetails
    Created on : Feb 22, 2019, 3:47:15 PM
    Author     : Turbotwins
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/metalStore.css">
        <script type="text/javascript" src="./JavaScript/script.js"></script>
        <title>Item Details Page</title>
    </head>
    <body onload="displayQuantityInput()">
        <h1>${item}</h1>
        <c:set var = "item" value ="${item}" />
        <sql:setDataSource 
        var="snapshot" 
        driver="org.apache.derby.jdbc.ClientDriver"
        url="jdbc:derby://localhost:1527/metal;create=true;"
        user="metal"  
        password="metal"/>
        <sql:query dataSource="${snapshot}" var="currentItem">
            SELECT METAL.ITEMS.ID, METAL.ITEMS.NAME, METAL.ITEMS.PRICE, METAL.ITEMS.STOCK, METAL.CATEGORIES.NAME AS CATEGORY, METAL.IMAGES.ADDRESS AS IMAGE,
            METAL.BANDS.NAME AS BAND, METAL.GENRES.NAME AS GENRE, METAL.LABELS.NAME AS LABEL
            FROM METAL.ITEMS
            INNER JOIN METAL.CATEGORIES ON METAL.ITEMS.CATEGORY_ID=METAL.CATEGORIES.ID
            INNER JOIN METAL.IMAGES ON METAL.ITEMS.IMAGE_ID=METAL.IMAGES.ID
            INNER JOIN METAL.BANDS ON METAL.ITEMS.BAND_ID=METAL.BANDS.ID
            INNER JOIN METAL.GENRES ON METAL.ITEMS.GENRE_ID=METAL.GENRES.ID
            INNER JOIN METAL.LABELS ON METAL.ITEMS.LABEL_ID=METAL.LABELS.ID
            WHERE METAL.ITEMS.NAME=?
            <sql:param value = "${item}" />
        </sql:query>
            
        <sql:query dataSource="${snapshot}" var="sizes">
            SELECT METAL.ITEMS.NAME AS ITEM, METAL.SHIRT_SIZES.SIZE AS SIZE, METAL.CLOTHING_SIZE_STOCKS.STOCK
            FROM METAL.CLOTHING_SIZE_STOCKS
            INNER JOIN METAL.ITEMS ON METAL.CLOTHING_SIZE_STOCKS.ITEM_ID=METAL.ITEMS.ID
            INNER JOIN METAL.SHIRT_SIZES ON METAL.CLOTHING_SIZE_STOCKS.SIZE_ID=METAL.SHIRT_SIZES.ID
            WHERE METAL.ITEMS.NAME=?
            <sql:param value = "${item}" />
        </sql:query>
                    
                    
        <c:forEach var="row" varStatus="loop" items="${currentItem.rows}">
            <img src="${row.image}"><br>
            <span>Item type: </span><c:out value="${row.category}"/><br>
            <span>Price: </span><c:out value="${row.price}"/><br>
            <span>Band: </span><form action="${pageContext.request.contextPath}/BandDetailServlet" method="GET"><input class="submitLink" type="submit" value="${row.band}" name="band"/></form><br><br>
            <span>Label: </span><form action="${pageContext.request.contextPath}/LabelDetailServlet" method="GET"><input class="submitLink" type="submit" value="${row.label}" name="label"/></form><br><br>
            
            <form action="${pageContext.request.contextPath}/AddToCartServlet" method="POST">
                <c:choose>
                    <c:when test="${row.category!='T-Shirt' && row.category!='Girlie' && row.category!='Longsleeve' && row.category!='Jacket/Hoodie' && row.category!='Girlie Longsleeve'}">
                        <span>Quantity: </span><input type="number" max="${row.stock}" min="0" name="quantity"><br>
                        <input class="hidden" type="text" name="size" value="N/A">
                        <input class="hidden" type="text" name="id" value="${row.id}">
                    </c:when>

                    <c:when test="${row.category=='T-Shirt' || row.category=='Girlie' || row.category=='Longsleeve' || row.category=='Jacket/Hoodie' || row.category=='Girlie Longsleeve'}">
                        <c:forEach var="rowSize" items="${sizes.rows}">
                            <c:choose>
                                <c:when test="${rowSize.stock!=0}">
                                    <div id="quantity${rowSize.size}" class="hidden">
                                        <span>Quantity: ${rowSize.size}</span><input type="number" max="${rowSize.stock}" min="0" name="quantity"><br>
                                    </div>
                                    <input class="hidden" type="text" name="id" value="${row.id}">
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </c:when>
                </c:choose>


                <c:choose>
                    <c:when test="${row.category=='T-Shirt' || row.category=='Girlie' || row.category=='Longsleeve' || row.category=='Jacket/Hoodie' || row.category=='Girlie Longsleeve'}">
                        <select id="selectSizes" name="size_option" required="true" onchange="updateQuantityInput()">
                            <c:forEach var="rowSize" items="${sizes.rows}">
                                <c:choose>
                                    <c:when test="${rowSize.stock!=0}">
                                        <option name="size" value="${rowSize.size}" name="size">${rowSize.size}</option>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </c:when>
                </c:choose>
                        
                <div id="dataForCart" class="hidden">
                    <input type="text" name="name" value="${row.name}">
                    <input type="text" name="category" value="${row.category}">
                    <input type="number" name="price" step="0.01" value="${row.price}">
                </div>
                        
                <input type="submit" value="Add to Cart">
            </form>
        </c:forEach>
    </body>
</html>
