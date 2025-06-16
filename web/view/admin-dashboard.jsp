<%@ page import="lk.ijse.gdse72.model.podos.ComplaintDTO" %>
<%@ page import="lk.ijse.gdse72.model.ComplaintDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.ijse.gdse72.model.podos.UserDTO" %>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="../css/addminDashboard.css">
</head>
<body>
<div class="dashboard-container" id="dashboard">
    <%
        UserDTO user = (UserDTO) session.getAttribute("user");
        List<ComplaintDTO> users = null;
        if (user != null) {
            ComplaintDAO complaintDAO = new ComplaintDAO();
            users = complaintDAO.getComplaintsByUser(user.getUserId());
        }
    %>

    <nav class="sidebar">
        <div>
            <div class="logo">
                <h1>Complaint Management System</h1>
            </div>

            <div class="welcome-section">
                <h2>Welcome</h2>
                <p><%= user != null ? user.getUsername() : "Guest" %></p>
            </div>

            <ul class="nav-menu">
                <li><a href="#dashboard" class="nav-link">Dashboard</a></li>
                <li><a href="${pageContext.request.contextPath}/view/view-all-complaints.jsp" class="nav-link">All Complaints</a></li>
                <li><a href="${pageContext.request.contextPath}/users" class="nav-link">Users</a></li>
            </ul>
        </div>

        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Logout</a>
    </nav>

    <main class="main-content">
        <div class="header">
            <h1>Admin Dashboard</h1>
            <p>Overview of complaint statuses</p>
        </div>

        <div class="stats-grid">
            <div class="stat-card pending">
                <div class="stat-number">0</div>
                <div class="stat-label">Pending</div>
            </div>
            <div class="stat-card in-progress">
                <div class="stat-number">0</div>
                <div class="stat-label">In Progress</div>
            </div>
            <div class="stat-card resolved">
                <div class="stat-number">0</div>
                <div class="stat-label">Resolved</div>
            </div>
            <div class="stat-card rejected">
                <div class="stat-number">0</div>
                <div class="stat-label">Rejected</div>
            </div>
        </div>
    </main>
</div>
</body>
</html>
