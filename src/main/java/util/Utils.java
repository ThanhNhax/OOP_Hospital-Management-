package util;
import java.util.Date;
import java.text.SimpleDateFormat; // Ép kiểu khi in ngày về dd/mm/yyyy cho đẹp khi đưa vào bảng

public class Utils {
    // Viết hàm form ngày/tháng dùng cho toString()
    // Date -> String
    public static String formatDate(Date date){
        // Nếu ngày chưa có -> trả về ""
        if (date == null) return "";
        // Nếu không thì đặt form dd/MM/yyyy
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
        return sdf.format(date);
    }

    // String -> Date: kiểm tra ngày nhập vào có đúng định dạng ngày không
    public static Date parseDate(String date){
        // Nếu str nhận được là chuỗi rỗng hoặc null -> return null
        if (date == null || date.trim().isEmpty()) return null;
        // Nếu không thì xử lý chuyển String thành Date
        // Dùng SimpleDateFormat trong lib Date để tạo biến kiểm tra form
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); // setLenient Bắt buộc ngày phải chính xác, ví dụ ngày 30/02 -> false
        // Dùng try-catch để bắt ngày an toàn
        try{
            return sdf.parse(date.trim()); // Nếu true thì dùng parse trả về đúng format dd/MM/yyyy
        } catch (Exception e){
            return null; // Nếu xảy ra bất kỳ ngoại lệ nào, ví dụ sai định dạng ngày -> null
        }
    }
}
