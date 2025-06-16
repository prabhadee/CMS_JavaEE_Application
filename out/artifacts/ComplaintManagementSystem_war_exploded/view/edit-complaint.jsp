<%@ page session="true" %>
<%@ page import="lk.ijse.gdse72.model.podos.ComplaintDTO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Complaint</title>
    <link rel="stylesheet" href="../css/complainForm.css">
</head>
<body>
<div class="container">
    <h1>Edit Complaint</h1>
    <form action="<%= request.getContextPath() %>/employee/edit-complaint" method="post">
        <%
            ComplaintDTO complaint = (ComplaintDTO) request.getAttribute("complaint");
        %>
        <input type="hidden" name="complaintId" value="<%= complaint.getComplaintId() %>">

        <label for="title">Title:</label>
        <input type="text" name="title" id="title" value="<%= complaint.getTitle() %>" required>

        <label for="description">Description:</label>
        <textarea name="description" rows="4" id="description" required><%= complaint.getDescription() %></textarea>

        <label for="department">Department:</label>
        <input type="text" name="department" id="department" value="<%= complaint.getDepartment() %>" required>

        <label for="priority">Priority:</label>
        <select name="priority" id="priority">
            <option value="LOW" <%= "LOW".equals(complaint.getPriority()) ? "selected" : "" %>>Low</option>
            <option value="MEDIUM" <%= "MEDIUM".equals(complaint.getPriority()) ? "selected" : "" %>>Medium</option>
            <option value="HIGH" <%= "HIGH".equals(complaint.getPriority()) ? "selected" : "" %>>High</option>
        </select>

        <input type="submit" value="Update">
    </form>
</div>
</body>
</html>
