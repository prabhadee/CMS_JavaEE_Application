<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Complaint Management System</title>
    <link rel="stylesheet" href="../css/sign_in.css">
</head>
<body>
<div class="container">
    <h1>Login</h1>

    <% String error = request.getParameter("error"); %>
    <% if (error != null) { %>
    <p style="color: red; text-align: center;"><%= error %></p>
    <% } %>

    <form action="login" method="post">
        <label for="username">Username:</label>
        <input type="text" name="username" id="username" required>

        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required>

        <input type="submit" value="Login">
    </form>

    <p style="text-align: center; margin-top: 1rem;">
        Don't have an account? <a href="register.jsp">Register here</a>
    </p>
</div>
</body>
</html>
