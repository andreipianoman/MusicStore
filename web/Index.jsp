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
        <title>JSP Page</title>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/Index" method="POST">
            <span>Username:</span><input type="text" name="username"><br>
            <span>Password:</span><input type="password" name="password"><br>
            <input type="submit" name="login" value="login">
        </form>
    </body>
</html>
