<%--
  Created by IntelliJ IDEA.
  User: Hossein
  Date: 25/01/2018
  Time: 03:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>سلام</h1>
    <form action="/login" method="post">
        username: <input type="text" name = "username" width="30"/>
        password: <input type="password" name="password" width="22"/>
        <input type="submit" value="Login"/>
    </form>
    <p style="color: red;">${errorMessage}</p>
</body>
</html>
