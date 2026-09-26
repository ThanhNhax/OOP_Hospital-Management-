package view;

import java.util.Scanner;
import java.util.function.Predicate; // Functional Interface giúp đặt điều kiện bắt buộc kiểm tra (như không rỗng + không trùng)
import util.Utils;

public class InputValidator {
    // tạo biến nhập dùng ở mọi nơi bằng static và final để chỉ cho dùng ở class này
    private static final Scanner scn = new Scanner(System.in);

    // Tạo các hàm nhập
    // 1.  Hàm nhận chuổi -> chỉ nhận chuỗi, có thể là rỗng để xử lý tùy tình huống Add hay Update
    public static String readString(String msg){
        // In câu msg muốn hiển thị, ví dụ "Nhap ten"
        OutputViewer.Notice(msg);
        return scn.nextLine().trim(); // nextLine() để nhập chuỗi từ bàn phím, trim() để cắt bỏ khoảng trắng thừa ở đầu và cuối.
    }

    // 2.1 Hàm bắt buộc không rỗng để dùng cho Add.
    public static String readNonEmptyString(String msg){
        // Nếu là rỗng thì phải nhập lại cho đến khi không rỗng
        while (true){
            String input = readString(msg);
            // Nếu input đúng thì trả kết quả về -> kết thúc nhập
            if (!input.isEmpty()) return input;
            // Nếu không thì in câu báo lỗi rồi lặp lại bắt nhập
            OutputViewer.Notice("Error: Input cannot be empty! Please try again.");
        }
    }

    // 2.2 Hàm nhận infor cũ khi rỗng dùng cho Update
    public static String readUpdateString(String field, String oldValue){
        // Nhập field đang cần upd
        String input = readString("Enter new " + field + " (Enter to keep " + oldValue + "): ");
        // Nếu rỗng -> giữ nguyên
        return (input.isEmpty() ? oldValue : input);
    }

    // 3. Tạo hàm điều khiển nhập để quyết định đang nhập new hay update
    // Dùng Predicate để điều khiển điều kiện tùy biến
    public static String readValidString(String field, String oldValue, String mode, Predicate<String> condition, String errMsg){
        while (true){
            // Tạo thông báo phù hợp add/upd
            String prompt = mode.equalsIgnoreCase("update") ? field + " (Enter to keep " + oldValue + ", 0 to cancel):" : field + "(Enter 0 to cancel): ";

            // Nhập chuỗi đã trim(), không bắt rỗng hay không với câu thông báo theo mode
            String input = readString(prompt);

            // Nếu nhập 0 -> thoát luôn
            if (input.equals("0")) return null;

            // Nếu là UPDATE ENTER RỖNG -> trả về oldValue
            if (mode.equalsIgnoreCase("update") && input.isEmpty()) return oldValue;

            // Nếu là ADD hoặc UPDATE CÓ NHẬP CHUỖI -> kiểm tra định dạng và trả về input
            if (condition.test(input)) return input;

            // Nếu RỖNG VỚI ADD hoặc SAI ĐỊNH DẠNG -> Báo lỗi -> lặp nhập lại
            OutputViewer.Notice(errMsg);
        }
        /*VD khi dùng:
        String deptID = InputValidator.readPatternString(
        "Department ID: ", // nhập msg của việc đang cần làm
        null, // Add nên không có oldValue
        "add", // Báo mode đang dùng là add
        id -> !deptManager.isDuplicateID(id), // Điều kiện: DEPT ID PHẢI CHƯA TỒN TẠI
        "Loi: Department ID nay da ton tai! Vui long nhap lai."); // Nếu xảy ra lỗi thì trả về errorMsg*/
    }

    // 4. Hàm confirm Y/N: Cho phép user nhập Y hay Yes đều nhận -> boolean Y = true, N = false; confirm không có add hay update
    public static boolean readConfirm(String function){
        while (true){
            // Kiểm tra không rỗng
            String input = readNonEmptyString("Do you want to " + function + "? (Y/N): ");
            // Nếu msg là Y hoặc Yes -> return true
            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("Yes")) return true;
            // Nếu msg là N hoặc No -> return false
            if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("No")) return false;
            // Nếu nhập gì đó khác -> báo lỗi
            OutputViewer.Notice("Error: Must enter Y/N or Yes/No!");
        }
    }

    // 5. Hàm đọc Gender, nếu thuộc Nam -> return Male, nếu thuộc Nữ -> return Female để kết quả in ra đồng bộ. (xử lý để add hoặc upd)
    public static String readGender(String field, String oldSex, String mode){
        String input = readValidString(field, oldSex, mode, g -> g.matches("(?i)^(Nam|Nu|M|F|Male|Female)$"), "Error: Must enter M/F, Male/Female or Nam/Nu!!"); // regex để i chỉ được là một trong các lựa chọn
        if (input.equalsIgnoreCase("Nam") || input.equalsIgnoreCase("M") || input.equalsIgnoreCase("Male")) return "Male";
        if (input.equalsIgnoreCase("Nu") || input.equalsIgnoreCase("F") || input.equalsIgnoreCase("Female")) return "Female";
        return input;
    }

    // 6. Hảm xử lý phone (dùng cho add và upd)
    public static String readPhone(String msg, String oldPhone, String mode){
        // Xử lý regex: ^0\\d{9}$ nghĩa là ký tự đầu tiên bắt buộc là 0, sau đó phải có đúng 9 số int
        return readValidString(msg, oldPhone, mode, phone -> phone.matches("^0\\d{9}$"), "Error: Invalid phone number! Must be 10 digits starting with 0.");
        /*
        ^: Đánh dấu Bắt đầu chuỗi.
        $: Đánh dấu Kết thúc chuỗi.
        Phải có cả ^ và $ vì nếu không có ^ và $, chuỗi "abc0123456789xyz" vẫn sẽ thỏa mãn vì bên trong nó có chứa 10 chữ số bắt đầu bằng 0. Có ^ và $ là để bắt buộc toàn bộ chuỗi từ đầu tới cuối chỉ được chứa đúng 10 ký tự đó!
        \d: tương tự int trong C, dùng %d để quy định đó là int, 0 đến 9 (tương đương [0-9]).
        \\: Trong Java, dấu \ là ký tự đặc biệt (escape character). Nếu chỉ gõ \d, Java sẽ báo lỗi syntax. Do đó phải gõ \\d để Java hiểu đang muốn dùng ký hiệu \d của Regex.*/
    }

    // 7. Hàm xử lý date (dùng cho add và upd)
    public static String readDate(String field, String oldDate, String mode){
        return readValidString(field, oldDate, mode, dateStr -> Utils.parseDate(dateStr) != null, "Error: Invalid date! Please enter valid date in dd/MM/yyyy format (e.g. 28/02/2026).");
    }

    // 8. Hàm xử lý admission status với 3 trạng thái: Admitted/Discharged/In Treatment (dùng cho add và upd)
    public static String readAdmissionStatus(String field, String oldStatus, String mode){
        while (true){
            // Nhập status không rỗng
            String status = readValidString(field, oldStatus, mode, s -> s.equalsIgnoreCase("Admitted") || s.equalsIgnoreCase("Discharged") || s.equalsIgnoreCase("In Treatment"), "Error: Must enter Admitted or Discharged or In Treatment!!");
            if (status.equalsIgnoreCase("Admitted")) return "Admitted";
            if (status.equalsIgnoreCase("Discharged")) return "Discharged";
            if (status.equalsIgnoreCase("In Treatment")) return "In Treatment";
            return status;
        }
    }
}
