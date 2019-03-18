<%-- 
    Document   : Index
    Created on : Feb 12, 2019, 6:30:59 PM
    Author     : Turbotwins
--%>

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
        
        <br>
        
        <h1>ID: ${cart[0].getID()}</h1>
        <h1>Name: ${cart[0].getName()}</h1>
        <h1>Price: ${cart[0].getPrice()}</h1>
        <h1>Quantity: ${cart[0].getQuantity()}</h1>
        
        <br>
        
        <h1>ID: ${cart[1].getID()}</h1>
        <h1>Name: ${cart[1].getName()}</h1>
        <h1>Price: ${cart[1].getPrice()}</h1>
        <h1>Quantity: ${cart[1].getQuantity()}</h1>
    </body>
</html>
