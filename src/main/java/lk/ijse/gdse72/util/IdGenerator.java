package lk.ijse.gdse72.util;

import java.util.UUID;

public class IdGenerator {

    public static String generateUserId() {
        return "USER_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static String generateComplaintId() {
        return "CMP_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
