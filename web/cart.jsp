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
            SELECT METAL.ITEMS.NAME AS NAME, METAL.CART_ITEMS.QUANTITY, METAL.CART_ITEMS.PRICE, METAL.SIZES.SIZE AS SIZE
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
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" varStatus="loop" items="${cart.rows}">
                    <tr>
                        <td class="cartTableCenter ">${item.name}</td>
                        <td class="cartTableCenter ">${item.size}</td>
                        <td class="cartTableCenter ">${item.quantity}</td>
                        <td class="cartTableCenter ">${item.price}</td>
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
