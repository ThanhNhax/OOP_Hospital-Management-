package utils; // Ý nghĩa: Khai báo file thuộc gói tiện ích (utils). Lý do: Để các package khác có thể import và tái sử dụng.

public class ValidationUtils {

    // Kiểm tra chuỗi nhập vào có đúng định dạng email hay không.
    // tránh viết lại chuỗi Regex nhiều lần trong dự án.
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$"); // Match chuỗi dạng abc@domain.com
    }

    // Kiểm tra chuỗi nhập vào có phải là số điện thoại từ 10-11 chữ số.
    public static boolean isValidPhone(String phone) {
        return phone.matches("^\\d{10,11}$"); // \\d đại diện cho chữ số, {10,11} yêu cầu độ dài 10 đến 11 ký tự
    }

    // Tạo thông báo cho chuỗi 
    public static void Notice(String text){
        System.out.print(text);
    }

    // Tạo thông báo có chèn ký tự xuống dòng ở cuối chuỗi
    public static void NoticeLine(String text){
        System.out.println(text);
    }

    // Tạo thông báo có định dạng chuỗi
    public static void NoticeFormat(String format, Object... args){
        System.out.printf(format, args);
    }
}