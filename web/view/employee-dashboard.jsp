<%@ page import="lk.ijse.gdse72.model.ComplaintDAO" %>
<%@ page import="lk.ijse.gdse72.model.podos.ComplaintDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="lk.ijse.gdse72.model.podos.UserDTO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Employee Dashboard</title>
    <link rel="stylesheet" href="../css/employeeDashBoard.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
</head>
<body>
<%
    UserDTO user = (UserDTO) session.getAttribute("user");
    ComplaintDAO complaintDAO = new ComplaintDAO();
    List<ComplaintDTO> complaints = complaintDAO.getComplaintsByUser(user.getUserId());

    int pendingCount = 0;
    int resolvedCount = 0;
    int inProgressCount = 0;
    int rejectedCount = 0;

    List<ComplaintDTO> complaintsWithRemarks = new ArrayList<>();

    if (complaints != null && !complaints.isEmpty()) {
        for (ComplaintDTO complaint : complaints) {
            String status = complaint.getStatus() != null ? complaint.getStatus().toLowerCase() : "pending";

            if ("pending".equals(status)) {
                pendingCount++;
            } else if ("resolved".equals(status)) {
                resolvedCount++;
            } else if ("in_progress".equals(status)) {
                inProgressCount++;
            } else if ("rejected".equals(status)) {
                rejectedCount++;
            }

            if (complaint.getAdminRemarks() != null && !complaint.getAdminRemarks().isEmpty()) {
                complaintsWithRemarks.add(complaint);
            }
        }
    }
%>

<div class="dashboard-container">

    <aside class="sidebar">
        <div class="logo">
            <h1><i class="fas fa-building"></i> Employee Dashboard</h1>

        </div>

        <div class="welcome-section">
            <h2>Hello,</h2>
            <span><%= user.getFullName() != null ? user.getFullName() : "User" %></span>
        </div>

        <nav>
            <ul class="nav-menu">
                <li class="nav-item">
                    <a href="#dashboard" class="nav-link active">
                        <i class="nav-icon fas fa-tachometer-alt"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="complaint-form.jsp" class="nav-link">
                        <i class="nav-icon fas fa-plus-circle"></i>
                        <span>New Complaint</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="my-complaints.jsp" class="nav-link">
                        <i class="nav-icon fas fa-list-alt"></i>
                        <span>My Complaints</span>
                    </a>
                </li>
            </ul>
        </nav>

        <a href="<%= request.getContextPath() %>/logout" class="logout-btn">
            <i class="fas fa-sign-out-alt"></i> Logout
        </a>
    </aside>
    </main>
</div>
</body>
</html>
