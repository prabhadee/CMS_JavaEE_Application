package lk.ijse.gdse72.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.ijse.gdse72.model.ComplaintDAO;
import lk.ijse.gdse72.model.podos.ComplaintDTO;
import lk.ijse.gdse72.model.podos.UserDTO;

import java.io.IOException;

@WebServlet("/employee/edit-complaint")
public class ComplaintEditServlet extends HttpServlet {

    private ComplaintDAO complaintDAO = new ComplaintDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp?error=unauthorized");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        String complaintId = req.getParameter("id");
        if (complaintId == null || complaintId.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/view/my-complaints.jsp?error=missingId");
            return;
        }

        ComplaintDTO complaint = complaintDAO.getComplaintById(complaintId);

        if (complaint == null) {
            resp.sendRedirect(req.getContextPath() + "/view/my-complaints.jsp?error=notfound");
            return;
        }

        if (!complaint.getSubmittedBy().equals(user.getUserId()) && !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/view/my-complaints.jsp?error=unauthorized");
            return;
        }

        req.setAttribute("complaint", complaint);
        req.getRequestDispatcher("/view/edit-complaint.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp?error=unauthorized");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        String complaintId = req.getParameter("complaintId");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String department = req.getParameter("department");
        String priority = req.getParameter("priority");

        if (complaintId == null || complaintId.isEmpty()
                || title == null || title.isEmpty()
                || description == null || description.isEmpty()
                || department == null || department.isEmpty()
                || priority == null || priority.isEmpty()) {
            req.setAttribute("error", "All fields are required.");
            req.setAttribute("complaint", complaintDAO.getComplaintById(complaintId));
            req.getRequestDispatcher("/view/edit-complaint.jsp").forward(req, resp);
            return;
        }

        ComplaintDTO complaint = complaintDAO.getComplaintById(complaintId);

        if (complaint == null || !"PENDING".equalsIgnoreCase(complaint.getStatus())) {
            resp.sendRedirect(req.getContextPath() + "/view/my-complaints.jsp?error=invalid");
            return;
        }

        if (!complaint.getSubmittedBy().equals(user.getUserId()) && !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/view/my-complaints.jsp?error=unauthorized");
            return;
        }

        complaint.setTitle(title);
        complaint.setDescription(description);
        complaint.setDepartment(department);
        complaint.setPriority(priority);

        System.out.println("Before Call complaintDAO eke updateComplaint ");
        boolean updated = complaintDAO.updateComplaint(complaint);
        System.out.println("After Call complaintDAO eke updateComplaint ");

        if (updated) {
            System.out.println("Updated Complaint ..!");
            resp.sendRedirect(req.getContextPath() + "/view/my-complaints.jsp?success=updated");
        } else {
            System.out.println("Some thing went Wrong in Editing Complaint ...");

            req.setAttribute("error", "Failed to update complaint.");
            req.setAttribute("complaint", complaint);
            req.getRequestDispatcher("/view/edit-complaint.jsp").forward(req, resp);
        }
    }
}
