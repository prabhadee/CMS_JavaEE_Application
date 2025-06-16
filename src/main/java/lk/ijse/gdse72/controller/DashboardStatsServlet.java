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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/dashboard-stats")
public class DashboardStatsServlet extends HttpServlet {

    private ComplaintDAO complaintDAO = new ComplaintDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get methid ekata awa AdminViewComplaintServlet ");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("session eka null welada koheda ..!");

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
            System.out.println("Some thing went wrong ..." + e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load complaints at this time.");
        }
    }

    private Map<String, Object> calculateStatistics(List<ComplaintDTO> complaints) {
        Map<String, Object> stats = new HashMap<>();

        Map<String, Integer> statusCounts = new HashMap<>();
        statusCounts.put("pending", 0);
        statusCounts.put("inProgress", 0);
        statusCounts.put("resolved", 0);
        statusCounts.put("rejected", 0);

        Map<String, Integer> departmentCounts = new HashMap<>();

        Map<String, Integer> priorityCounts = new HashMap<>();
        priorityCounts.put("LOW", 0);
        priorityCounts.put("MEDIUM", 0);
        priorityCounts.put("HIGH", 0);

        for (ComplaintDTO complaint : complaints) {

            String status = complaint.getStatus().toLowerCase();
            if (status.equals("in_progress")) {
                status = "inProgress";
            }
            statusCounts.put(status, statusCounts.getOrDefault(status, 0) + 1);

            String department = complaint.getDepartment();
            departmentCounts.put(department, departmentCounts.getOrDefault(department, 0) + 1);

            String priority = complaint.getPriority();
            priorityCounts.put(priority, priorityCounts.getOrDefault(priority, 0) + 1);
        }

        List<Map<String, String>> recentActivity = complaints.stream()
                .limit(5)
                .map(this::mapComplaintToActivity)
                .toList();


        stats.put("statusCounts", statusCounts);
        stats.put("departmentCounts", departmentCounts);
        stats.put("priorityCounts", priorityCounts);
        stats.put("recentActivity", recentActivity);
        stats.put("totalComplaints", complaints.size());

        return stats;
    }

    private Map<String, String> mapComplaintToActivity(ComplaintDTO complaint) {
        Map<String, String> activity = new HashMap<>();

        String text = String.format("Complaint #%s - %s (%s)",
                complaint.getComplaintId().substring(0, Math.min(8, complaint.getComplaintId().length())),
                complaint.getTitle().length() > 30 ?
                        complaint.getTitle().substring(0, 30) + "..." : complaint.getTitle(),
                complaint.getStatus());

        activity.put("text", text);
        activity.put("time", getTimeAgo(complaint.getCreatedAt()));

        return activity;
    }

    private String getTimeAgo(java.time.LocalDateTime dateTime) {
        if (dateTime == null) return "Unknown";

        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.Duration duration = java.time.Duration.between(dateTime, now);

        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();

        if (minutes < 1) {
            return "Just now";
        } else if (minutes < 60) {
            return minutes + " min ago";
        } else if (hours < 24) {
            return hours + " hour" + (hours > 1 ? "s" : "") + " ago";
        } else {
            return days + " day" + (days > 1 ? "s" : "") + " ago";
        }
    }
}