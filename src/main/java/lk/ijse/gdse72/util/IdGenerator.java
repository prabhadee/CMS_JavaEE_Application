package lk.ijse.gdse72.util;

public class IdGenerator {
    private static int userCounter = 1;
    private static int complaintCounter = 1;

    public static String generateUserId() {
        return String.format("USER_%03d", userCounter++);
    }

    public static String generateComplaintId() {
        return String.format("CMP_%03d", complaintCounter++);
    }
}
