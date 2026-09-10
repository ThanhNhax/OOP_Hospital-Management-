package model; // Khai báo file thuộc lớp dữ liệu (model). Phân tách thành phần chứa thông tin dữ liệu riêng biệt.

import java.util.Scanner; // Nạp thư viện nhập liệu từ bàn phím.
import utils.ValidationUtils; // Nạp lớp tiện ích kiểm tra dữ liệu từ package utils.

// Khai báo lớp trừu tượng Employee.
// Áp dụng Tính trừu tượng (Abstraction) - ngăn không cho khởi tạo đối tượng Employee chung chung bằng từ khóa new.
public abstract class Employee {
    
    // Khai báo các thuộc tính nhân viên với phạm vi truy cập private.
    // Áp dụng Tính đóng gói (Encapsulation) để bảo vệ dữ liệu, tránh bị chỉnh sửa tự do từ bên ngoài.
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private double salary;

    // Constructor không tham số. Khởi tạo đối tượng rỗng trước khi nhập thông tin.
    public Employee() {}

    // Constructor đầy đủ tham số. Hỗ trợ tạo nhanh đối tượng khi đã có sẵn thông tin.
    public Employee(String id, String name, String email, String phoneNumber, double salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
    }

    // Ý nghĩa các hàm Getter/Setter: Đọc và cập nhật thuộc tính private.
    // Đảm bảo truy cập dữ liệu an toàn theo chuẩn Encapsulation.
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    // Hàm nhập dữ liệu dùng chung cho nhân viên.
    // Gom nhóm logic nhập và kiểm tra dữ liệu hợp lệ (Data Validation) ngay khi người dùng thao tác.
    public void inputData(Scanner sc) {
        ValidationUtils.Notice("Nhập ID: ");
        this.id = sc.nextLine().trim(); // trim() để loại bỏ khoảng trắng thừa ở đầu/cuối

        System.out.print("Nhập Họ tên: ");
        this.name = sc.nextLine().trim();

        // Vòng lặp kiểm tra định dạng email.
        // Ép người dùng nhập lại cho đến khi email hợp lệ mới cho tiếp tục.
        while (true) {
            ValidationUtils.Notice("Nhập Email: ");
            String inputEmail = sc.nextLine().trim();
            if (ValidationUtils.isValidEmail(inputEmail)) { // Gọi hàm hỗ trợ từ ValidationUtils
                this.email = inputEmail;
                break; // Đúng định dạng thì thoát vòng lặp
            }
            ValidationUtils.NoticeLine("Email không hợp lệ! Vui lòng nhập lại (ví dụ: abc@gmail.com).");
        }

        // Vòng lặp kiểm tra số điện thoại.
        // Đảm bảo SĐT đủ 10-11 chữ số theo đúng yêu cầu bài toán.
        while (true) {
            ValidationUtils.Notice("Nhập Số điện thoại (10-11 số): ");
            String inputPhone = sc.nextLine().trim();
            if (ValidationUtils.isValidPhone(inputPhone)) {
                this.phoneNumber = inputPhone;
                break;
            }
            ValidationUtils.NoticeLine("Số điện thoại không hợp lệ! Phải gồm 10 đến 11 chữ số.");
        }

        // Vòng lặp kiểm tra lương.
        // Bắt lỗi ngoại lệ NumberFormatException nếu người dùng nhập chữ thay vì nhập số.
        while (true) {
            ValidationUtils.NoticeLine("Nhập Lương (> 0): ");
            try {
                double inputSalary = Double.parseDouble(sc.nextLine().trim());
                if (inputSalary > 0) {
                    this.salary = inputSalary;
                    break;
                }
            } catch (NumberFormatException e) {
                // Tránh lỗi dừng chương trình đột ngột khi nhập sai kiểu dữ liệu
            }
            ValidationUtils.NoticeLine("Lương phải lớn hơn 0!");
        }
    }

    // Hàm cập nhật thông tin nhân viên.
    // Đáp ứng yêu cầu "Nếu nhấn Enter (để trống) thì giữ nguyên giá trị cũ".
    public void updateData(Scanner sc) {
        ValidationUtils.Notice("Nhập Họ tên mới (Nhấn Enter để giữ nguyên [" + name + "]): ");
        String inputName = sc.nextLine().trim();
        if (!inputName.isEmpty()) this.name = inputName; // Chỉ cập nhật khi chuỗi không rỗng

        while (true) {
            ValidationUtils.Notice("Nhập Email mới (Nhấn Enter để giữ nguyên [" + email + "]): ");
            String inputEmail = sc.nextLine().trim();
            if (inputEmail.isEmpty()) break; // Nhấn Enter -> Bỏ qua và giữ nguyên email cũ
            if (ValidationUtils.isValidEmail(inputEmail)) {
                this.email = inputEmail;
                break;
            }
            ValidationUtils.NoticeLine("Email không hợp lệ!");
        }

        while (true) {
            ValidationUtils.Notice("Nhập Số điện thoại mới (Nhấn Enter để giữ nguyên [" + phoneNumber + "]): ");
            String inputPhone = sc.nextLine().trim();
            if (inputPhone.isEmpty()) break; // Nhấn Enter -> Bỏ qua
            if (ValidationUtils.isValidPhone(inputPhone)) {
                this.phoneNumber = inputPhone;
                break;
            }
            ValidationUtils.NoticeLine("Số điện thoại không hợp lệ!");
        }

        while (true) {
            ValidationUtils.Notice("Nhập Lương mới (Nhấn Enter để giữ nguyên [" + salary + "]): ");
            String inputSalaryStr = sc.nextLine().trim();
            if (inputSalaryStr.isEmpty()) break; // Nhấn Enter -> Bỏ qua
            try {
                double inputSalary = Double.parseDouble(inputSalaryStr);
                if (inputSalary > 0) {
                    this.salary = inputSalary;
                    break;
                }
            } catch (NumberFormatException e) {
                // Xử lý ngoại lệ số
            }
            ValidationUtils.NoticeLine("Lương phải là số lớn hơn 0!");
        }
    }

    // Hiển thị thông tin cơ bản dạng dòng formatted string.
    // Căn chỉnh khoảng cách bằng %-8s, %-18s... giúp bảng thông tin thẳng hàng đẹp mắt.
    public void display() {
        ValidationUtils.NoticeFormat("ID: %-8s | Tên: %-18s | Email: %-22s | SĐT: %-12s | Lương: %,-12.2f",
                id, name, email, phoneNumber, salary);
    }
}