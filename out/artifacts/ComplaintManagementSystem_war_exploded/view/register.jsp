<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - Complaint Management System</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sign_up.css">
</head>
<body>
<div class="container">
    <div class="form-title">
        <h1>Register</h1>
        <h2>Complaint Management System</h2>
    </div>



    <form action="${pageContext.request.contextPath}/registerservlet" method="post">
        <label for="username">Username:</label>
        <input type="text" name="username" id="username" value="${oldUsername != null ? oldUsername : ''}" class="${not empty usernameError ? 'invalid' : ''}">
        <span class="error">${usernameError}</span>

        <label for="password">Password:</label>
        <input type="password" name="password" id="password"   class="${not empty passwordError ? 'invalid' : ''}">
        <span class="error">${passwordError}</span>

        <label for="fullName">Full Name:</label>
        <input type="text" name="fullName" id="fullName"  value="${oldFullName != null ? oldFullName : ''}" class="${not empty fullNameError ? 'invalid' : ''}">
        <span class="error">${fullNameError}</span>

        <label for="email">Email:</label>
        <input type="email" name="email" id="email" value="${oldEmail != null ? oldEmail : ''}" class="${not empty emailError ? 'invalid' : ''}">
        <span class="error">${emailError}</span>

        <label for="number">Phone Number:</label>
        <input type="text" name="number" id="number" value="${oldPhone != null ? oldPhone : ''}" class="${not empty phoneError ? 'invalid' : ''}">
        <span class="error">${phoneError}</span>

        <label for="role">Role:</label>
        <select name="role" id="role">
            <option value="EMPLOYEE">Employee</option>
            <option value="ADMIN">Admin</option>
        </select>
        <span class="error">${roleError}</span>

        <input type="submit" value="Register">
        <a href="<%= request.getContextPath() %>/index.jsp"
           style="position: absolute; right: 10px; top: 10px; background-color: #f44336; color: white; padding: 6px 12px; border-radius: 4px; text-decoration: none; cursor: pointer;">
            Back To Login ...
        </a>

    </form>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="../js/sign_upPageValidation.js">  </script>

</body>
</html>