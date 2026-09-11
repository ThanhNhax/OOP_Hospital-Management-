package model;

import java.util.Scanner;
import utils.ValidationUtils;

// Ý nghĩa: Lớp Nhân viên văn phòng kế thừa từ lớp Employee.
// Lý do: Áp dụng Tính kế thừa (Inheritance) để tái sử dụng toàn bộ mã nguồn của lớp cha Employee.
public class OfficeEmployee extends Employee {
    
    // Ý nghĩa: Khai báo thuộc tính chức vụ riêng của nhân viên văn phòng.
    private String position;

    public OfficeEmployee() {
        super(); // Gọi constructor của lớp cha Employee
    }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    // Ý nghĩa: Ghi đè (Override) hàm inputData của lớp cha.
    // Lý do: Áp dụng Tính đa hình (Polymorphism) để vừa nhập thông tin cơ bản, vừa nhập thêm "chức vụ".
    @Override
    public void inputData(Scanner sc) {
        super.inputData(sc); // Gọi phương thức inputData của lớp cha
        while (true) {
            ValidationUtils.Notice("Enter Position: ");
            this.position = sc.nextLine().trim();
            if (!this.position.isEmpty()) break;
            ValidationUtils.NoticeLine("Position cannot be empty!");
        }
    }

    // Ý nghĩa: Ghi đè hàm updateData để hỗ trợ sửa chức vụ.
    @Override
    public void updateData(Scanner sc) {
        super.updateData(sc); // Gọi updateData của lớp cha
        ValidationUtils.Notice("Enter new Position (Press Enter to keep [" + position + "]): ");
        String inputPos = sc.nextLine().trim();
        if (!inputPos.isEmpty()) {
            this.position = inputPos;
        }
    }

    // Ý nghĩa: Ghi đè hàm display để in thêm chức vụ.
    @Override
    public void display() {
        super.display(); // Gọi display của lớp cha
        ValidationUtils.NoticeFormat(" | Position: %s\n", position);
    }
}