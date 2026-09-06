package util;

import java.util.Scanner;
import java.util.function.Predicate; // Functional Interface giúp đặt điều kiện bắt buộc kiểm tra (như không rỗng + không trùng)
import java.util.Date;
import java.text.SimpleDateFormat; // Ép kiểu khi in ngày về dd/mm/yyyy cho đẹp khi đưa vào bảng

public class Validator {
    // tạo biến nhập dùng ở mọi nơi bằng static và final để chỉ cho dùng ở class này
    private static final Scanner scn = new Scanner(System.in);

    // Tạo hàm thông báo dùng chung mọi nơi
    public static void Notice(String text){
        System.out.println(text);
    }

    // Tạo các hàm nhập
    // 1.  Tạo hàm nhận chuổi -> chỉ nhận chuỗi, có thể là rỗng để xử lý tùy tình huống Add hay Update
    public static String readString(String msg){
        // In câu msg muốn hiển thị, ví dụ "Nhap ten"
        System.out.println(msg);
        return scn.nextLine().trim(); // nextLine() để nhập chuỗi từ bàn phím, trim() để cắt bỏ khoảng trắng thừa ở đầu và cuối.
    }

    // 2. Tạo hàm bắt buộc không rỗng để dùng cho Add.
    public static String readNonEmptyString(String msg){
        // Nếu là rỗng thì phải nhập lại cho đến khi không rỗng
        while (true){
            String input = readString(msg);
            // Nếu input đúng thì trả kết quả về -> kết thúc nhập
            if (!input.isEmpty()) return input;
            // Nếu không thì in câu báo lỗi rồi lặp lại bắt nhập
            Notice("Loi: Khong duoc de trong! Vui long nhap lai!");
        }
    }

    // 3. Tạo hàm nhận chuỗi có điều kiện tùy biến (VD như kiểm trùng ID của Dept và Doc)
    public static String readPattenString(String msg, Predicate<String> condition, String errorMsg){
        while(true){
            // Nhập bằng nonEmpty trước để chống rỗng
            String input = readNonEmptyString(msg);
            // Đặt điều kiện tùy biến để làm phương thức
            if (condition.test(input)) return input;
            Notice(errorMsg);
            /*VD khi dùng:
            String deptID = InputValidator.readPatternString(
            "Nhap Department ID: ", // nhập msg của việc đang cần làm
            id -> !deptManager.isDuplicateID(id), // Điều kiện: DEPT ID PHẢI CHƯA TỒN TẠI
            "Loi: Department ID nay da ton tai! Vui long nhap lai."
            ); // Nếu xảy ra lỗi thì trả về errorMsg*/
        }
    }

    // 4. Viết hàm confirm Y/N: Cho phép user nhập Y hay Yes đều nhận -> boolean Y = true, N = false
    public static boolean readConfirm(String msg){
        while (true){
            // Kiểm tra không rỗng
            String input = readNonEmptyString(msg);
            // Nếu msg là Y hoặc Yes -> return true
            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("Yes")) return true;
            // Nếu msg là N hoặc No -> return false
            if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("No")) return false;
            // Nếu nhập gì đó khác -> báo lỗi
            Notice("Loi: Chi nhap Y/N hooac Yes/No de xac nhan!");
        }
    }

    // 5. Viết hàm đọc Gender, nếu thuộc Nam -> return Male, nếu thuộc Nữ -> return Female để kết quả in ra đồng bộ.
    public static String readGender(String msg){
        while(true){
            String input = readNonEmptyString(msg);
            if (input.equalsIgnoreCase("Nam") || input.equalsIgnoreCase("M") || input.equalsIgnoreCase("Male")) return "Male";
            if (input.equalsIgnoreCase("Nu") || input.equalsIgnoreCase("F") || input.equalsIgnoreCase("Female")) return "Female";
            // Nếu nhập khác thì báo lỗi
            Notice("Loi: Gioi tinh khong hop le! Chi nhap M/F, Male/Female hoac Nam/Nu!!");
        }
    }

    // 6. Viết hàm form ngày/tháng dùng cho toString()
    public static String formatDate(Date date){
        // Nếu ngày chưa có -> trả về ""
        if (date == null) return "";
        // Nếu không thì đặt form dd/MM/yyyy
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
        return sdf.format(date);
    }
}
