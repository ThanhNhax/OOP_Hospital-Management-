package main;

import util.HospitalHandler;
import util.Validator;

public class Main {
    // Gọi hàm main để chạy theo cú pháp public static void main(String[] args) {}
    public static void main(String[] args) {
        HospitalHandler handler = new HospitalHandler(); // Dùng HospitalHandler để tự động load file khi khởi tạo

        while (true) {
            // In menu
            Validator.Notice("\n=== HOSPITAL MANAGEMENT ===");
            Validator.Notice("1. Show information");
            Validator.Notice("2. Add new");
            Validator.Notice("3. Update information");
            Validator.Notice("4. Delete");
            Validator.Notice("5. Search information");
            Validator.Notice("6. Store data to file");
            Validator.Notice("0. Quit");
            
            // Tạo biến chọn, không được rỗng
            String choice = Validator.readNonEmptyString("Choose (0-6): ");

            switch (choice) {
                case "1":
                    handler.handleDisplay();
                    break;
                case "2":
                    handler.handleAddNew();
                    break;
                case "3":
                    handler.handleUpdate();
                    break;
                case "4":
                    handler.handleDelete();
                    break;
                case "5":
                    handler.handleSearch();
                    break;
                case "6":
                    handler.handleSaveToFile();
                    break;
                case "0":
                    // Xác nhận trước khi thoát
                    boolean isExit = Validator.readConfirm("Ban co chac chan muon thoat ung dung (Y/N)?");
                    if (isExit) {
                        Validator.Notice("Thoat thanh cong!!!");
                        return;
                    }
                    break;
                default:
                    Validator.Notice("Loi: Vui long chon tu 0 den 6!");
            }
        }
    }
}