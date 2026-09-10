package model;

import java.util.Scanner;
import utils.ValidationUtils;

// Lớp Nhân viên kỹ thuật kế thừa từ lớp Employee.
// Lý do: Áp dụng Tính kế thừa (Inheritance).
public class TechnicalEmployee extends Employee {
    
    // Thuộc tính ngôn ngữ lập trình riêng của nhân viên kỹ thuật.
    private String programmingLanguage;

    public TechnicalEmployee() {
        super();
    }

    public String getProgrammingLanguage() { return programmingLanguage; }
    public void setProgrammingLanguage(String programmingLanguage) { this.programmingLanguage = programmingLanguage; }

    // Ghi đè hàm inputData để nhập thêm ngôn ngữ lập trình.
    @Override
    public void inputData(Scanner sc) {
        super.inputData(sc);
        ValidationUtils.Notice("Nhập Ngôn ngữ lập trình (Language): ");
        this.programmingLanguage = sc.nextLine().trim();
    }

    // Ghi đè hàm updateData để cập nhật ngôn ngữ lập trình.
    @Override
    public void updateData(Scanner sc) {
        super.updateData(sc);
        ValidationUtils.Notice("Nhập Ngôn ngữ lập trình mới (Bấm Enter để giữ nguyên [" + programmingLanguage + "]): ");
        String inputLang = sc.nextLine().trim();
        if (!inputLang.isEmpty()) {
            this.programmingLanguage = inputLang;
        }
    }

    // Ghi đè hàm display để in thêm ngôn ngữ lập trình.
    @Override
    public void display() {
        super.display();
        ValidationUtils.NoticeFormat("Ngôn ngữ: %s\n", programmingLanguage);
    }
}