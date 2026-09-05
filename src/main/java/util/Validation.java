package util;

import java.util.Scanner;

public class Validation {

    // UTF-8 để nhập tiếng Việt khớp với console đã setupUtf8Console()
    private static final Scanner scanner = new Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8);

    // Nhập chuỗi không rỗng; để trống thì báo lỗi EMPTY_INPUT
    public static String readNonEmptyString(String msg) {
        return readNonEmptyString(msg, Language.EMPTY_INPUT);
    }

    // Đọc một dòng (cho phép rỗng) — dùng cho menu chọn ngôn ngữ
    public static String readLine(String msg) {
        System.out.print(msg);
        return scanner.nextLine().trim();
    }

    // Nhập chuỗi không rỗng; emptyErrorCode là mã câu lỗi trong file ngôn ngữ
    public static String readNonEmptyString(String msg, String emptyErrorCode) {
        String result;
        while (true) {
            System.out.print(msg);
            result = scanner.nextLine().trim();
            if (!result.isEmpty()) {
                return result;
            }
            System.out.println(Language.get(emptyErrorCode));
        }
    }

    // Nhập giới tính; để trống thì báo EMPTY_GENDER
    public static String readGender(String msg) {
        return readGender(msg, Language.EMPTY_GENDER);
    }

    // Nhập giới tính (Nam/Nu/Male/Female/M/F); sai thì báo INVALID_GENDER
    public static String readGender(String msg, String emptyErrorCode) {
        while (true) {
            String result = readNonEmptyString(msg, emptyErrorCode);
            if (result.equalsIgnoreCase("Nam") || result.equalsIgnoreCase("Nu") ||
                result.equalsIgnoreCase("Male") || result.equalsIgnoreCase("Female") ||
                result.equalsIgnoreCase("M") || result.equalsIgnoreCase("F")) {
                return result;
            }
            System.out.println(Language.get(Language.INVALID_GENDER));
        }
    }

    // Nhập Y/N; để trống thì báo EMPTY_CONFIRM
    public static boolean readConfirm(String msg) {
        return readConfirm(msg, Language.EMPTY_CONFIRM);
    }

    // Nhập xác nhận Y/Yes -> true, N/No -> false; giá trị khác thì báo INVALID_CONFIRM
    public static boolean readConfirm(String msg, String emptyErrorCode) {
        while (true) {
            String result = readNonEmptyString(msg, emptyErrorCode);
            if (result.equalsIgnoreCase("Y") || result.equalsIgnoreCase("Yes")) {
                return true;
            }
            if (result.equalsIgnoreCase("N") || result.equalsIgnoreCase("No")) {
                return false;
            }
            System.out.println(Language.get(Language.INVALID_CONFIRM));
        }
    }

    // Đổi Date thành chuỗi dd/MM/yyyy; null thì trả về rỗng
    public static String formatDate(java.util.Date date) {
        if (date == null) return "";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
