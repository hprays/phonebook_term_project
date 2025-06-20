package util;

/**
 * 이메일/전화번호 유효성 검사 도구
 */
public class Validator {
    // 이메일이 올바른 형식인지 확인
    public static boolean isValidEmail(String email) {
        return email.matches("^\\S+@\\S+\\.\\S+$");
    }

    // 전화번호가 형식에 맞는지 확인 (010-1234-5678 또는 01012345678)
    public static boolean isValidPhone(String phone) {
        return phone.matches("^01[0-9]-?\\d{3,4}-?\\d{4}$");
    }
}
