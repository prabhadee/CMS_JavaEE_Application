<%@ page import="lk.ijse.gdse72.model.pojos.UserDTO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <title>All User List</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userpage.css">
</head>
<body>

<div class="page-header">
  <a href="<%=request.getContextPath()%>/view/admin-dashboard.jsp" class="back-btn"> Back to Dashboard</a>
  <h1>All User List</h1>
</div>

<div class="container">

  <div class="user-table">
    <h2>Admins</h2>
    <table>
      <tr>
        <th>User ID</th>
        <th>Username</th>
        <th>Full Name</th>
        <th>Email</th>
        <th>Number</th>
      </tr>
      <%
        List<UserDTO> admins = (List<UserDTO>) request.getAttribute("adminsList");

        if (admins == null) {
      %>
      <tr><td colspan="5" class="error-msg">Admins list is NULL</td></tr>
      <%
      } else {
        for (UserDTO u : admins) {
      %>
      <tr>
        <td><%= u.getUserId() %></td>
        <td><%= u.getUsername() %></td>
        <td><%= u.getFullName() %></td>
        <td><%= u.getEmail() %></td>
        <td><%= u.getNumber() %></td>
      </tr>
      <%
          }
        }
      %>
    </table>
  </div>

  <div class="user-table">
    <h2>Employees</h2>
    <table>
      <tr>
        <th>User ID</th>
        <th>Username</th>
        <th>Full Name</th>
        <th>Email</th>
        <th>Number</th>
        <th>Actions</th>
      </tr>
      <%
        List<UserDTO> employees = (List<UserDTO>) request.getAttribute("employeesList");

        if (employees == null) {
      %>
      <tr><td colspan="6" class="error-msg">Employees list is NULL</td></tr>
      <%
      } else {
        for (UserDTO u : employees) {
      %>
      <tr>
        <td><%= u.getUserId() %></td>
        <td><%= u.getUsername() %></td>
        <td><%= u.getFullName() %></td>
        <td><%= u.getEmail() %></td>
        <td><%= u.getNumber() %></td>
        <td>
          <form action="<%=request.getContextPath()%>/users" method="post" style="margin:0;">
            <input type="hidden" name="userId" value="<%= u.getUserId() %>">
            <input type="hidden" name="action" value="promote">
            <input type="submit" value="Make Admin" class="action-btn" onclick="return confirm('Promote this user to Admin?');">
          </form>
        </td>
      </tr>
      <%
          }
        }
      %>
    </table>
  </div>

</div>

</body>
</html>
