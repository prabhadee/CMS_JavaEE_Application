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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/complaint/delete")
public class ComplaintDeleteServlet extends HttpServlet {

    private final ComplaintDAO complaintDAO = new ComplaintDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String complaintId = req.getParameter("id");
        HttpSession session = req.getSession(false);

        if (complaintId == null || complaintId.isEmpty()) {
            redirectWithError(req, resp, "missingComplaintId");
            return;
        }

        if (session == null) {
            redirectWithError(req, resp, "unauthorized");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            redirectWithError(req, resp, "unauthorized");
            return;
        }

        boolean allowedToDelete = false;

        if (user.isAdmin()) {
            allowedToDelete = true;
        } else if (user.isEmployee()) {
            ComplaintDTO complaint = complaintDAO.getComplaintById(complaintId);
            if (complaint != null
                    && "PENDING".equalsIgnoreCase(complaint.getStatus())
                    && complaint.getSubmittedBy().equals(user.getUserId())) {
                allowedToDelete = true;
            }
        }

        if (allowedToDelete) {
            boolean deleted = user.isAdmin()
                    ? complaintDAO.deleteComplaint(complaintId)
                    : complaintDAO.deleteComplaintByEmployee(complaintId, user.getUserId());

            if (deleted) {
                redirectWithSuccess(req, resp, "deleted");
            } else {
                redirectWithError(req, resp, "deleteFailed");
            }
        } else {
            redirectWithError(req, resp, "unauthorized");
        }
    }

    private void redirectWithError(HttpServletRequest req, HttpServletResponse resp, String errorCode) throws IOException {
        redirectToReferer(req, resp, "error", errorCode);
    }

    private void redirectWithSuccess(HttpServletRequest req, HttpServletResponse resp, String message) throws IOException {
        redirectToReferer(req, resp, "success", message);
    }

    private void redirectToReferer(HttpServletRequest req, HttpServletResponse resp, String param, String value) throws IOException {
        String referer = req.getHeader("referer");
        if (referer == null || referer.isEmpty()) {
            referer = req.getContextPath() + "/complaints";
        }
        String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8);
        resp.sendRedirect(referer + "?" + param + "=" + encodedValue);
    }
}
