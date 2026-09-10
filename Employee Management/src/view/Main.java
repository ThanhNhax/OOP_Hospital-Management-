package view; // Khai báo file thuộc gói giao diện (view). Nơi hiển thị Menu tương tác người dùng.

import java.util.Scanner;
import controller.EmployeeManager; // Import Controller để gọi các chức năng xử lý
import utils.ValidationUtils;

public class Main {
    
    // Tạo hàm main khởi chạy chương trình.
    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager(); // Tạo đối tượng điều khiển
        Scanner sc = new Scanner(System.in); // Tạo Scanner dùng chung cho menu

        // Vòng lặp vô hạn điều khiển menu.
        // Giữ chương trình liên tục hoạt động cho đến khi người dùng chủ động chọn thoát (mục 4).
        while (true) {
            ValidationUtils.NoticeLine("\n========= EMPLOYEE MANAGEMENT MENU =========");
            ValidationUtils.NoticeLine("1. Thêm nhân viên (Add Employee)");
            ValidationUtils.NoticeLine("2. Cập nhật thông tin nhân viên (Update Employee)");
            ValidationUtils.NoticeLine("3. Hiển thị danh sách nhân viên (Display List)");
            ValidationUtils.NoticeLine("4. Thoát chương trình (Exit)");
            ValidationUtils.Notice("Chọn chức năng (1-4): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    manager.addEmployee(sc); // Gọi xử lý thêm ở controller\EmploymentManager
                    break;
                case "2":
                    manager.updateEmployee(sc); // Gọi xử lý cập nhật ở controller\EmploymentManager
                    break;
                case "3":
                    manager.displayAllEmployees(); // Gọi xử lý hiển thị danh sách ở controller\EmploymentManager
                    break;
                case "4":
                    ValidationUtils.NoticeLine("Đã thoát chương trình.");
                    sc.close(); // Đóng luồng nhập liệu Scanner
                    return; // Kết thúc hàm main, dừng chương trình
                default:
                    ValidationUtils.NoticeLine("Lựa chọn không hợp lệ! Vui lòng chọn từ 1 đến 4.");
            }
        }
    }
}