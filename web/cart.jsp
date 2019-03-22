<%-- 
    Document   : Index
    Created on : Feb 12, 2019, 6:30:59 PM
    Author     : Turbotwins
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="utilityClasses.CartItem"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/metalStore.css">
        <title>JSP Page</title>
    </head>
    
    <sql:query dataSource="${snapshot}" var="cart">
        SELECT METAL.ITEMS.NAME, METAL.CART_ITEMS.QUANTITY, METAL.CART_ITEMS.PRICE, METAL.SIZES.SIZE AS SIZE
        FROM CART_ITEMS
        INNER JOIN METAL.ITEMS ON METAL.CART_ITEMS.ITEM_ID=METAL.ITEMS.ID
        INNER JOIN METAL.SIZES ON METAL.CART_ITEMS.SIZE_ID=METAL.SIZES.ID
        WHERE METAL.CART_ITEMS.USER_ID=?
        <sql:param value = "${user}" />
    </sql:query>
    
    <body>
        <%@ include file="./utils/menu.jsp" %>
        <table border="1">
            <thead>
                <tr>
                    <td class="cartTableCenter ">Item</td>
                    <td class="cartTableCenter ">Quantity</td>
                    <td class="cartTableCenter ">Price</td>
                    <td class="cartTableCenter ">Size</td>
                    <td></td>
                </tr>
            </thead>
            <c:set var="total" value="0" scope="page" />
            <tbody>
                <c:forEach items="${cart}" var="item">
                <tr>
                    <td class="cartTableCenter "><span>${item.name}</span></td>
                    <td class="cartTableCenter "><span>${item.quantity}</span></td>
                    <td class="cartTableCenter "><span>${item.price}</span></td>
                    <td class="cartTableCenter "><span>${item.size}</span></td>
                    <td class="cartTableCenter "><img class = "cartImage" src="${item.image}"></td>
                </tr>
                <c:set var="total" value="${total + item.price}" scope="page" />
                </c:forEach>
            </tbody>
        </table>
        <br>
        <span>Total price: ${total}</span>
        <br>
        <form action="${pageContext.request.contextPath}/CheckoutServlet" method="POST"">
            <input type="submit" value="Checkout">
        </form>
    </body>
</html>
