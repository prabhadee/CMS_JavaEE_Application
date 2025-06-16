package lk.ijse.gdse72.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    // Email regex: basic but effective
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");

    // Username: letters, numbers, underscore, 3-20 chars
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[A-Za-z0-9_]{3,20}$");

    // Password: Minimum 6 chars, at least one letter and one number
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$");

    // Phone number: Digits only, 10 chars stating with 0
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^0\\d{9}$");

    // Complaint title: allow letters, numbers, spaces, punctuation, 5-100 chars
    private static final Pattern TITLE_PATTERN =
            Pattern.compile("^[\\w\\s.,'-]{5,100}$");

    // Complaint description: allow letters, numbers, spaces, punctuation, 10-1000 chars
    private static final Pattern DESCRIPTION_PATTERN =
            Pattern.compile("^[\\w\\s.,'-]{10,1000}$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidTitle(String title) {
        return title != null && TITLE_PATTERN.matcher(title).matches();
    }

    public static boolean isValidDescription(String description) {
        return description != null && DESCRIPTION_PATTERN.matcher(description).matches();
    }

    // Generic check for null or empty
    public static boolean isValidString(String str) {
        return str != null && !str.isEmpty();
    }

    // Generic length check
    public static boolean isValidLength(String str, int minLength, int maxLength) {
        if (str == null) return false;
        int length = str.length();
        return length >= minLength && length <= maxLength;
    }
}
