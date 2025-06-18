package lk.ijse.gdse72.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse72.model.UserDAO;
import lk.ijse.gdse72.model.pojos.UserDTO;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDTO> admins = userDAO.getAllAdmins();
        List<UserDTO> employees = userDAO.getAllEmployees();

        System.out.println("admins: " + admins);
        request.setAttribute("adminsList", admins);

        System.out.println("employees: " + employees);
        request.setAttribute("employeesList", employees);

        request.getRequestDispatcher("/view/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userId = request.getParameter("userId");

        if ("promote".equals(action) && userId != null) {
            boolean success = userDAO.promoteToAdmin(userId);
            if (!success) {
                request.getSession().setAttribute("errorMessage", "Promotion failed.");
            }
        }

        response.sendRedirect(request.getContextPath() + "/users");
    }
}
