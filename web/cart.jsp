<%-- 
    Document   : Index
    Created on : Feb 12, 2019, 6:30:59 PM
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
        <title>JSP Page</title>
    </head>
    
    
    <body>
        <%@ include file="./utils/menu.jsp" %>
        
        <c:set var = "user" value ="${currentUser}" />
        
        <sql:setDataSource 
            var="snapshot" 
            driver="org.apache.derby.jdbc.ClientDriver"
            url="jdbc:derby://localhost:1527/metal;create=true;"
            user="metal"  
            password="metal"/>
        
        <sql:query dataSource="${snapshot}" var="cart">
            SELECT METAL.ITEMS.ID, METAL.ITEMS.NAME AS NAME, METAL.CART_ITEMS.QUANTITY, METAL.CART_ITEMS.PRICE, METAL.SIZES.SIZE AS SIZE
            FROM CART_ITEMS
            INNER JOIN METAL.ITEMS ON METAL.CART_ITEMS.ITEM_ID=METAL.ITEMS.ID
            INNER JOIN METAL.SIZES ON METAL.CART_ITEMS.SIZE_ID=METAL.SIZES.ID
            WHERE METAL.CART_ITEMS.USER_ID=?
            <sql:param value = "${user}" />
        </sql:query>
        <c:set var = "total" value = "0" />
        
        <table border="1">
            <thead>
                <tr>
                    <td class="cartTableCenter ">Item</td>
                    <td class="cartTableCenter ">Size</td>
                    <td class="cartTableCenter ">Quantity</td>
                    <td class="cartTableCenter ">Price</td>
                    <td class="cartTableCenter "></td>
                </tr>
            </thead>
            <tbody>
                
                <c:forEach var="item" varStatus="loop" items="${cart.rows}">
                    <c:set var = "itemID" value = "${item.id}" />
                    <sql:query dataSource="${snapshot}" var="itemStockQuery">
                        SELECT METAL.ITEMS.STOCK 
                        FROM ITEMS
                        WHERE METAL.ITEMS.ID=?
                        <sql:param value = "${itemID}" />
                    </sql:query>
                    
                    <tr>
                        <td class="cartTableCenter ">${item.name}</td>
                        <td class="cartTableCenter ">${item.size}</td>
                        <td class="cartTableCenter ">
                            <c:forEach var = "itemStock" varStatus="loop" items="${itemStockQuery.rows}">
                                <c:choose>
                                    <c:when test="${itemStock.stock > 0}">
                                        <form action="${pageContext.request.contextPath}/EditCartServlet" method="POST"">
                                            <input type="submit" value="+" name="operation">
                                            <input class="hidden" type="text" value ="${item.name}" name="name">
                                            <input class="hidden" type="text" value ="${item.size}" name="size">
                                        </form>
                                    </c:when>
                                </c:choose>
                                ${item.quantity}
                                <c:choose>
                                    <c:when test="${item.quantity > 0}">
                                        <form action="${pageContext.request.contextPath}/EditCartServlet" method="POST"">
                                            <input type="submit" value="-" name="operation">
                                            <input class="hidden" type="text" value ="${item.name}" name="name">
                                            <input class="hidden" type="text" value ="${item.size}" name="size">
                                        </form>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </td>
                        <td class="cartTableCenter ">${item.price}</td>
                        <td class="cartTableCenter ">
                            <form action="${pageContext.request.contextPath}/EditCartServlet" method="POST"">
                                <input type="submit" value="remove" name="operation">
                                <input class="hidden" type="text" value ="${item.name}" name="name">
                                <input class="hidden" type="text" value ="${item.size}" name="size">
                            </form>
                        </td>
                    </tr>
                    <c:set var = "total" value = "${total + item.price}" />
                </c:forEach>
                
            </tbody>
        </table>
        <br>
        <span>Total price: ${total}</span>
        <form action="${pageContext.request.contextPath}/CheckoutServlet" method="POST"">
            <input type="submit" value="Checkout">
        </form>
    </body>
</html>
