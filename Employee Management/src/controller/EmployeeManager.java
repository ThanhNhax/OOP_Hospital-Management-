package controller; // Khai báo file thuộc tầng điều khiển (Controller). Lý do: Chứa nghiệp vụ quản lý danh sách.

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Employee;          // Import các lớp model tương ứng
import model.OfficeEmployee;
import model.TechnicalEmployee;
import utils.ValidationUtils;

public class EmployeeManager {
    // Khai báo danh sách lưu trữ đối tượng dạng List<Employee>.
    // Thể hiện Tính đa hình (Polymorphism). Một danh sách kiểu Employee có thể chứa cả OfficeEmployee và TechnicalEmployee.
    private final List<Employee> employeeList = new ArrayList<>();

    // thêm mới nhân viên vào danh sách.
    public void addEmployee(Scanner sc) {
        ValidationUtils.NoticeLine("\n--- CHỌN LOẠI NHÂN VIÊN ---");
        ValidationUtils.NoticeLine("1. Nhân viên Văn phòng (Office Employee)");
        ValidationUtils.NoticeLine("2. Nhân viên Kỹ thuật (Technical Employee)");
        System.out.print("Lựa chọn của bạn: ");
        
        String choice = sc.nextLine().trim();
        Employee emp = null; // Khai báo con trỏ kiểu lớp cha

        // Khởi tạo đối tượng con cụ thể dựa trên lựa chọn.
        // Thể hiện tính đa hình trong quá trình khởi tạo.
        if (choice.equals("1")) {
            emp = new OfficeEmployee();
        } else if (choice.equals("2")) {
            emp = new TechnicalEmployee();
        } else {
            ValidationUtils.NoticeLine("Lựa chọn không hợp lệ!");
            return;
        }

        emp.inputData(sc); // Gọi phương thức nhập liệu tương ứng của đối tượng
        
        // Kiểm tra trùng lặp ID trong danh sách trước khi thêm.
        // Lý do: Đảm bảo tính duy nhất cho mã nhân viên.
        if (findEmployeeById(emp.getId()) != null) {
            ValidationUtils.NoticeLine("Lỗi: ID nhân viên đã tồn tại trong hệ thống!");
            return;
        }

        employeeList.add(emp); // Thêm nhân viên vào List
        ValidationUtils.NoticeLine("-> Thêm nhân viên thành công!");
    }

    // Phương thức cập nhật thông tin nhân viên theo ID.
    public void updateEmployee(Scanner sc) {
        ValidationUtils.Notice("\nNhập ID nhân viên cần cập nhật: ");
        String id = sc.nextLine().trim();
        
        Employee emp = findEmployeeById(id);
        if (emp == null) {
            ValidationUtils.NoticeLine("Không tìm thấy nhân viên có ID: " + id);
            return;
        }

        ValidationUtils.NoticeLine("--- CẬP NHẬT THÔNG TIN (Để trống nếu muốn giữ nguyên giá trị cũ) ---");
        emp.updateData(sc); // Đa hình: Tự động chạy hàm updateData() của lớp con tương ứng
        ValidationUtils.NoticeLine("-> Cập nhật thông tin nhân viên thành công!");
    }

    // Phương thức hiển thị toàn bộ danh sách nhân viên.
    public void displayAllEmployees() {
        if (employeeList.isEmpty()) {
            ValidationUtils.NoticeLine("\nDanh sách nhân viên đang trống!");
            return;
        }

        ValidationUtils.NoticeLine("\n==================================== DANH SÁCH NHÂN VIÊN ====================================");
        // Vòng lặp duyệt qua từng nhân viên trong danh sách.
        // Lý do (Đa hình): Java tự động gọi hàm display() tương ứng với loại nhân viên thực tế tại thời điểm thực thi.
        for (Employee emp : employeeList) {
            emp.display();
        }
        ValidationUtils.NoticeLine("=============================================================================================");
    }

    // Hàm phụ trợ tìm kiếm nhân viên theo ID trong danh sách.
    // Để tái sử dụng ở cả chức năng thêm (chống trùng ID) lẫn chức năng cập nhật.
    private Employee findEmployeeById(String id) {
        for (Employee emp : employeeList) {
            if (emp.getId().equalsIgnoreCase(id)) { // Không phân biệt chữ hoa/thường
                return emp;
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }
}