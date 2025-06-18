package lk.ijse.gdse72.model.pojos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserDTO {
    private String userId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String number;
    private String role;
    private LocalDateTime createdAt;

    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }

    public boolean isEmployee() {
        return "EMPLOYEE".equals(role);
    }
}
