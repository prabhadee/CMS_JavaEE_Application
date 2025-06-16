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

@WebServlet("/admin/view-all-complaints")
public class AdminViewComplaintServlet extends HttpServlet {

    private final ComplaintDAO complaintDAO = new ComplaintDAO();
//    private static final Logger logger = Logger.getLogger(AdminViewComplaintServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("get methid ekata awa AdminViewComplaintServlet ");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("Maybe Session is null..!");

            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        try {
            System.out.println("Before Call getAllComplaints In complaintDAO ...");
            List<ComplaintDTO> complaints = complaintDAO.getAllComplaints();
            System.out.println("After Call getAllComplaints In complaintDAO ..." + complaints);

            req.setAttribute("complaints", complaints);

            req.getRequestDispatcher("/view/view-all-complaints.jsp").forward(req, resp);

        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Something went wrong ..." + e.getMessage());
//            logger.log(Level.SEVERE, "Failed to load complaints", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load complaints at this time.");
        }
    }
}
