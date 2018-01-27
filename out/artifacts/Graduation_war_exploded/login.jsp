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
    <h1>Please login to your account</h1>
    <form action="/login" method="post">
        studentID: <input type="text" name = "studentID" width="30"/>
        password: <input type="password" name="password" width="22"/>
        <input type="submit" value="Login"/>
    </form>
    <p style="color: red;">${errorMessage}</p>
    <p style="color: green;">${successfulRegister}</p>
</body>
</html>
