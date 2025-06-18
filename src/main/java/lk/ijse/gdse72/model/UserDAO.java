package lk.ijse.gdse72.model;

import lk.ijse.gdse72.model.pojos.UserDTO;
import lk.ijse.gdse72.util.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public UserDTO authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE LOWER(username) = LOWER(?)";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbUsername = rs.getString("username");
                String dbPass = rs.getString("password");

                if (dbPass.equals(password)) {
                    return extractUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean createUser(UserDTO user) {
        String sql = "INSERT INTO users (user_id, username, password, full_name, email, number, role, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getFullName());
            stmt.setString(5, user.getEmail());
            stmt.setString(6,user.getNumber());
            stmt.setString(7, user.getRole());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public UserDTO getUserById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UserDTO> getAllAdmins() {
        String sql = "SELECT * FROM users WHERE role = 'ADMIN'";
        List<UserDTO> admins = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                admins.add(extractUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    private UserDTO extractUser(ResultSet rs) throws SQLException {
        UserDTO user = new UserDTO();

        user.setUserId(rs.getString("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(null);
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setNumber(rs.getString("number"));
        user.setRole(rs.getString("role"));
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            user.setCreatedAt(createdAt.toLocalDateTime());
        }
        return user;
    }

    public boolean promoteToAdmin(String userId) {
        String sql = "UPDATE users SET role = 'ADMIN' WHERE user_id = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UserDTO> getAllEmployees() {
        String sql = "SELECT * FROM users WHERE role = 'EMPLOYEE'";
        List<UserDTO> employees = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                employees.add(extractUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}

