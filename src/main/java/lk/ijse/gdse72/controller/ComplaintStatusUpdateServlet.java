package lk.ijse.gdse72.controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse72.model.ComplaintDAO;
import lk.ijse.gdse72.model.podos.ComplaintDTO;

import java.io.IOException;

@WebServlet("/admin/update-complaint-status")
public class ComplaintStatusUpdateServlet extends HttpServlet {

    private ComplaintDAO complaintDAO = new ComplaintDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String complaintId = req.getParameter("complaintId");
        String status = req.getParameter("status");
        String adminRemarks = req.getParameter("adminRemarks");

        ComplaintDTO complaint = complaintDAO.getComplaintById(complaintId);
        if (complaint == null) {
            resp.sendRedirect(req.getContextPath() + "/view/view-all-complaints.jsp?error=notfound");
            return;
        }

        complaint.setStatus(status);
        complaint.setAdminRemarks(adminRemarks);

        boolean updated = complaintDAO.updateComplaintStatus(complaintId,status, complaint.getAssignedTo(), adminRemarks);

        if (updated) {
            System.out.println("Updated status and add a mark also ...!");
            resp.sendRedirect(req.getContextPath() + "/view/view-all-complaints.jsp?success=updated");
        } else {
            System.out.println("Failed to update status and could not add a mark also ...!");
            resp.sendRedirect(req.getContextPath() + "/view/view-all-complaints.jsp?error=updatefailed");
        }
    }
}