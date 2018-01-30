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
<h1>Please register</h1>
<form action="/register" method="post">
    studentID: <input type="text" firstName = "studentID" width="30"/>
    password: <input type="password" firstName="password" width="22"/>
    confirmPassword: <input type="password" firstName="confirmPassword" width="22"/>
    firstName: <input type="text" firstName="firstName" width="22"/>
    email: <input type="text" firstName="email" width="22"/>

    <input type="submit" value="Register"/>
</form>
<p style="color: red;">${errorMessage1}</p>
<p style="color: red;">${errorMessage2}</p>
<p style="color: red;">${errorMessage3}</p>
<p style="color: red;">${errorMessage4}</p>
</body>
</html>
