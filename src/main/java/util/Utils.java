package util;

import java.util.Date;
import java.text.SimpleDateFormat; // Ép kiểu khi in ngày về dd/mm/yyyy cho đẹp khi đưa vào bảng

public class Utils {
    // Viết hàm form ngày/tháng dùng cho toString()
    // Date -> String
    public static String formatDate(Date date) {
        // Nếu ngày chưa có -> trả về ""
        if (date == null)
            return "";
        // Nếu không thì đặt form dd/MM/yyyy
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    // String -> Date: kiểm tra ngày nhập vào có đúng định dạng ngày không
    public static Date parseDate(String date) {
        // Nếu str nhận được là chuỗi rỗng hoặc null -> return null
        // Nếu không thì xử lý chuyển String thành Date
        // Dùng SimpleDateFormat trong lib Date để tạo biến kiểm tra form
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // Bắt buộc ngày phải chính xác, ví dụ ngày 30/02 -> false
        try {
            Date parsed = sdf.parse(date.trim());
            // Kiểm tra thêm năm phải trong khoảng hợp lý [1900 -> năm hiện tại]
            int year = Integer.parseInt(date.trim().split("/")[2]);
            int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
            if (year < 1900 || year > currentYear)
                return null;
            return parsed;
        } catch (Exception e) {
            return null; // Nếu xảy ra bất kỳ ngoại lệ nào -> null
        }
    }
}
