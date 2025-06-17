<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.ijse.gdse72.model.podos.ComplaintDTO" %>
<%@ page import="lk.ijse.gdse72.model.ComplaintDAO" %>
<%@ page import="lk.ijse.gdse72.model.podos.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Complaints</title>
    <link rel="stylesheet" href="../css/myComplaint.css">

</head>
<body>
<div class="peer">
    <form action="<%= request.getContextPath() %>/view/employee-dashboard.jsp" style="position: absolute; right: 10px; top: 10px;">
        <button type="submit" style="background-color: #f44336; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer;">
            🧭 Dashboard
        </button>
    </form>
    <h1>My Complaints</h1>

    <%
        String success = request.getParameter("success");
        if ("1".equals(success)) {
    %>
    <div class="success-msg">
        ✅ Complaint submitted successfully!
    </div>
    <%
        }
    %>

    <a href="<%= request.getContextPath() %>/employee/submit-complaint" class="btn">Submit New Complaint</a>

    <table>
        <thead>
        <tr>
            <th>Complaint ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Department</th>
            <th>Priority</th>
            <th>Status</th>
            <th>Created Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<ComplaintDTO> complaints = null;

            UserDTO currentUser = (UserDTO) session.getAttribute("user");
            if (currentUser == null) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

            String userId = currentUser.getUserId();
            System.out.println(" Current User ID: " + userId);

            try {
                ComplaintDAO complaintDAO = new ComplaintDAO();
                complaints = complaintDAO.getComplaintsByUser(userId);
                System.out.println("Complaints loaded: " + complaints.size());
            } catch (Exception e) {
                System.err.println(" Error loading complaints: " + e.getMessage());
                e.printStackTrace();
            }

            if (complaints != null && !complaints.isEmpty()) {
                for (ComplaintDTO complaint : complaints) {
        %>
        <tr>
            <td><%= complaint.getComplaintId() %></td>
            <td><strong><%= complaint.getTitle() %></strong></td>
            <td><%= complaint.getDescription() %></td>
            <td><%= complaint.getDepartment() %></td>
            <td>
                    <span class="priority-<%= complaint.getPriority().toLowerCase() %>">
                        <%= complaint.getPriority() %>
                    </span>
            </td>
            <td>
                    <span class="status-<%= complaint.getStatus().toLowerCase() %>">
                        <%= complaint.getStatus() %>
                    </span>
            </td>
            <td>
                <%= complaint.getCreatedAt() != null ? complaint.getCreatedAt().toString() : "N/A" %>
            </td>
            <td>
                <%
                    if ("PENDING".equalsIgnoreCase(complaint.getStatus())) {
                %>
<%--                <a href="edit-complaint.jsp?id=<%= complaint.getComplaintId() %>" class="btn">Edit</a>--%>
                <a href="<%= request.getContextPath() %>/employee/edit-complaint?id=<%= complaint.getComplaintId() %>" class="btn">Edit</a>
                <form action="<%= request.getContextPath() %>/complaint/delete" method="post" onsubmit="return confirm('Are you sure you want to delete this complaint?');">
                    <input type="hidden" name="id" value="<%= complaint.getComplaintId() %>">
                    <button type="submit" class="btn">
                        🗑 Delete
                    </button>
                </form>
                <%
                } else {
                %>
                <span style="color: #666;">No actions available</span>
                <%
                    }
                %>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="8" style="text-align: center; padding: 40px; color: #666;">
                <h3>No complaints found</h3>
                <p>You haven't submitted any complaints yet.</p>
                <a href="<%= request.getContextPath() %>/employee/submit-complaint" class="btn">Submit Your First Complaint</a>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

</div>
</body>
</html>