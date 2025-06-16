package lk.ijse.gdse72.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.ijse.gdse72.model.ComplaintDAO;
import lk.ijse.gdse72.model.podos.ComplaintDTO;

import java.io.IOException;
import java.util.List;

@WebServlet("/employee/my-complaints")
public class MyComplaintsServlet extends HttpServlet {

    private ComplaintDAO complaintDAO = new ComplaintDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        String userId = ((lk.ijse.gdse72.model.podos.UserDTO) session.getAttribute("user")).getUserId();

        System.out.println("Before Call complainDAO.getComplainByUser: " + userId);
        List<ComplaintDTO> complaints = complaintDAO.getComplaintsByUser(userId);
        System.out.println("After Call complaint.getComplaintByUser");

        req.setAttribute("complaints", complaints);
        req.getRequestDispatcher("/view/my-complaints.jsp").forward(req, resp);
    }
}
