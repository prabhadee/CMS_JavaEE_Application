<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.ijse.gdse72.model.pojos.ComplaintDTO" %>
<%@ page import="lk.ijse.gdse72.model.pojos.UserDTO" %>
<%@ page import="lk.ijse.gdse72.model.ComplaintDAO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>All Complaints - Admin Panel</title>
    <link rel="stylesheet" href="../css/viewAll.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
</head>
<body>
<div class="container">

    <div style="text-align: center; position: relative;">
        <form action="<%= request.getContextPath() %>/view/admin-dashboard.jsp" style="position: absolute; right: 10px; top: 10px;">
            <button type="submit" style="background-color: #f44336; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer;">
                🧭 Dashboard
            </button>
        </form>
    </div>

    <h1>All Complaints Management</h1>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <div class="error-message" style="color: red; margin-bottom: 20px;"><%= error %></div>
    <%
        }
    %>

    <%
        ComplaintDAO complaintDAO = new ComplaintDAO();
        List<ComplaintDTO> complaints = complaintDAO.getAllComplaints();

        int totalComplaints = 0;
        int pendingCount = 0;
        int resolvedCount = 0;
        int inProgressCount = 0;
        int rejectedCount = 0;

        if (complaints != null) {
            totalComplaints = complaints.size();
            for (ComplaintDTO complaint : complaints) {
                String status = complaint.getStatus() != null ? complaint.getStatus().toLowerCase() : "";
                if ("pending".equals(status)) pendingCount++;
                else if ("resolved".equals(status)) resolvedCount++;
                else if ("in_progress".equals(status)) inProgressCount++;
                else if ("rejected".equals(status)) rejectedCount++;
            }
        }
    %>


    <div class="table-wrapper">
        <table>
            <thead>
            <tr>
                <th style="display: none">ID</th>
                <th>Title</th>
                <th>Description</th>
                <th>Department</th>
                <th>Status</th>
                <th>Submitted By</th>
                <th>Priority</th>
                <th>Created At</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (complaints != null && !complaints.isEmpty()) {
                    for (ComplaintDTO complaint : complaints) {
                        String statusClass = complaint.getStatus() != null ? complaint.getStatus().toLowerCase() : "";
                        String priorityClass = complaint.getPriority() != null ? complaint.getPriority().toLowerCase() : "";
            %>
            <tr>
                <td style="display: none"><%= complaint.getComplaintId() %></td>
                <td class="title-cell"><strong><%= complaint.getTitle() %></strong></td>
                <td><%= complaint.getDescription() %></td>
                <td><%= complaint.getDepartment() %></td>
                <td>
                    <span class="status-<%= statusClass %>">
                        <%= complaint.getStatus() != null ? complaint.getStatus().replace("_", " ") : "Unknown" %>
                    </span>
                </td>
                <td><%= complaint.getSubmittedByName() != null ? complaint.getSubmittedByName() : complaint.getSubmittedBy() %></td>
                <td>
                    <span class="priority-<%= priorityClass %>">
                        <%= complaint.getPriority() != null ? complaint.getPriority() : "N/A" %>
                    </span>
                </td>
                <td><%= complaint.getCreatedAt() %></td>
                <td class="actions-cell">
                    <div class="action-form">
                        <form action="<%= request.getContextPath() %>/admin/update-complaint-status" method="post" style="display:inline-block;">
                            <input type="hidden" name="complaintId" value="<%= complaint.getComplaintId() %>" />
                            <select name="status" required>
                                <option value="PENDING" <%= "PENDING".equals(complaint.getStatus()) ? "selected" : "" %>>Pending</option>
                                <option value="IN_PROGRESS" <%= "IN_PROGRESS".equals(complaint.getStatus()) ? "selected" : "" %>>In Progress</option>
                                <option value="RESOLVED" <%= "RESOLVED".equals(complaint.getStatus()) ? "selected" : "" %>>Resolved</option>
                                <option value="REJECTED" <%= "REJECTED".equals(complaint.getStatus()) ? "selected" : "" %>>Rejected</option>
                            </select>
                            <input type="text" name="adminRemarks" placeholder="Admin remarks..." value="<%= complaint.getAdminRemarks() != null ? complaint.getAdminRemarks() : "" %>">
                            <input type="submit" value="Update">
                        </form>
                    </div>
                    <form action="<%= request.getContextPath() %>/complaint/delete" method="post" onsubmit="return confirm('Are you sure you want to delete this complaint?');" style="display:inline-block; margin-left: 10px;">
                        <input type="hidden" name="id" value="<%= complaint.getComplaintId() %>">
                        <button type="submit" class="delete-link">🗑 Delete</button>
                    </form>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="9" class="no-complaints">
                    <h3>No complaints found</h3>
                    <p>There are currently no complaints in the system.</p>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <div style="margin-top: 20px; text-align: center;">
        <a href="<%= request.getContextPath() %>/view/admin-dashboard.jsp" style="padding: 8px 16px; background-color: #4a00e0; color: #fff; text-decoration: none; border-radius: 4px;">
            Back to Dashboard
        </a>
    </div>
</div>
</body>
</html>
