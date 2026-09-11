package controller; // Khai báo file thuộc tầng điều khiển (Controller). Lý do: Chứa nghiệp vụ quản lý danh sách.

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Employee; // Import các lớp model tương ứng
import model.OfficeEmployee;
import model.TechnicalEmployee;
import utils.ValidationUtils;

public class EmployeeManager {
    // Khai báo danh sách lưu trữ đối tượng dạng List<Employee>.
    // Thể hiện Tính đa hình (Polymorphism). Một danh sách kiểu Employee có thể chứa
    // cả OfficeEmployee và TechnicalEmployee.
    private final List<Employee> employeeList = new ArrayList<>();

    // thêm mới nhân viên vào danh sách.
    public void addEmployee(Scanner sc) {
        ValidationUtils.NoticeLine("\n--- SELECT EMPLOYEE TYPE ---");
        ValidationUtils.NoticeLine("1. Office Employee");
        ValidationUtils.NoticeLine("2. Technical Employee");
        System.out.print("Your choice: ");

        String choice = sc.nextLine().trim();
        Employee emp = null; // Khai báo con trỏ kiểu lớp cha

        // Khởi tạo đối tượng con cụ thể dựa trên lựa chọn.
        // Thể hiện tính đa hình trong quá trình khởi tạo.
        if (choice.equals("1")) {
            emp = new OfficeEmployee();
        } else if (choice.equals("2")) {
            emp = new TechnicalEmployee();
        } else {
            ValidationUtils.NoticeLine("Invalid choice!");
            return;
        }

        while (true) {
            ValidationUtils.Notice("Enter ID (cannot be empty): ");
            String id = sc.nextLine().trim();
            if (id.isEmpty()) {
                ValidationUtils.NoticeLine("ID cannot be empty!");
                continue;
            }
            if (findEmployeeById(id) != null) {
                ValidationUtils.NoticeLine("Error: Employee ID already exists! Please enter another ID.");
                continue;
            }
            emp.setId(id);
            break;
        }

        emp.inputData(sc);

        employeeList.add(emp);
        ValidationUtils.NoticeLine("-> Employee added successfully!");
    }

    // Phương thức cập nhật thông tin nhân viên theo ID.
    public void updateEmployee(Scanner sc) {
        ValidationUtils.Notice("\nEnter Employee ID to update: ");
        String id = sc.nextLine().trim();

        Employee emp = findEmployeeById(id);
        if (emp == null) {
            ValidationUtils.NoticeLine("Employee with ID " + id + " not found!");
            return;
        }

        ValidationUtils.NoticeLine("--- UPDATE INFORMATION (Press Enter to keep old values) ---");
        emp.updateData(sc);
        ValidationUtils.NoticeLine("-> Employee updated successfully!");
    }

    // Phương thức hiển thị toàn bộ danh sách nhân viên.
    public void displayAllEmployees() {
        if (employeeList.isEmpty()) {
            ValidationUtils.NoticeLine("\nEmployee list is empty!");
            return;
        }

        ValidationUtils.NoticeLine(
                "\n==================================== EMPLOYEE LIST ====================================");
        for (Employee emp : employeeList) {
            emp.display();
        }
        ValidationUtils
                .NoticeLine("=======================================================================================");
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