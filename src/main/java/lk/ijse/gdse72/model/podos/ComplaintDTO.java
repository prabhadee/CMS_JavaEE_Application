package lk.ijse.gdse72.model.podos;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ComplaintDTO {
    private String complaintId;
    private String title;
    private String description;
    private String department;
    private String priority;
    private String status;
    private String submittedBy;
    private String submittedByName;
    private String assignedTo;
    private String assignedToName;
    private String adminRemarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    public boolean canBeEditedByEmployee() {
//        return "PENDING".equals(status);
//    }
//
//    public String getStatusBadgeClass() {
//        switch (status) {
//            case "PENDING": return "badge-warning";
//            case "IN_PROGRESS": return "badge-info";
//            case "RESOLVED": return "badge-success";
//            case "REJECTED": return "badge-danger";
//            default: return "badge-secondary";
//        }
//    }
}
