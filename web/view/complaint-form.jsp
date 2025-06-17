<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Submit Complaint</title>
    <link rel="stylesheet" href="../css/complainForm.css">

</head>
<body>
<div class="container">
    <form action="<%= request.getContextPath() %>/view/employee-dashboard.jsp" style="position: absolute; right: 10px; top: 10px;">
        <button type="submit" style="background-color: #f44336; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer;">
            🧭 Dashboard
        </button>
    </form>
    <h1>Submit Complaint</h1>
    <form action="${pageContext.request.contextPath}/employee/submit-complaint" method="post">
        <label for="title">Title:</label>
        <input type="text" name="title" id="title" class="${not empty titleError ? 'invalid' : ''}">
        <span class="error" id="titleError">${titleError != null ? titleError : ''}</span>

        <label for="description">Description:</label>
        <textarea name="description" rows="4" id="description"  class="${not empty descriptionError ? 'invalid' : ''}"></textarea>
        <span class="error" id="descriptionError">${descriptionError != null ? descriptionError : ''}</span>

        <label for="department">Department:</label>
        <input type="text" name="department" id="department" class="${not empty departmentError ? 'invalid' : ''}">
        <span class="error" id="departmentError">${departmentError != null ? departmentError : ''}</span>

        <label for="priority">Priority:</label>
        <select name="priority" id="priority" class="${not empty priorityError ? 'invalid' : ''}">
            <option value="LOW">Low</option>
            <option value="MEDIUM">Medium</option>
            <option value="HIGH">High</option>
        </select>
        <span class="error" id="priorityError">${priorityError != null ? priorityError : ''}</span>

        <input type="submit" value="Submit">
    </form>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/complaintFormValidation.js"></script>
   </div>
</body>
</html>