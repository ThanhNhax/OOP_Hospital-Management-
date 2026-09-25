package main;

import controller.HospitalHandler;
import view.InputValidator;
import view.OutputViewer;

public class Main {
    // Gọi hàm main để chạy theo cú pháp public static void main(String[] args) {}
    public static void main(String[] args) {
        HospitalHandler handler = new HospitalHandler(); // Dùng HospitalHandler để tự động load file khi khởi tạo

        while (true) {
            // In menu
            OutputViewer.printMainMenu();

            // Tạo biến chọn, không được rỗng
            String choice = InputValidator.readNonEmptyString("Enter your choice: ");

            switch (choice) {
                case "1":
                    handler.handleAddNew();
                    break;
                case "2":
                    handler.handleDelete();
                    break;
                case "3":
                    handler.handleUpdate();
                    break;
                case "4":
                    handler.handleSearch();
                    break;
                case "5":
                    handler.handleDisplayAPatient();
                    break;
                case "6":
                    handler.handleDisplayAll();
                    break;
                case "7":
                    handler.handleSaveToFile();
                    break;
                case "8":
                    handler.handleLoadFromFile();
                    break;
                case "0":
                    // Xác nhận trước khi thoát
                    boolean isExit = InputValidator.readConfirm("exit");
                    if (isExit) {
                        OutputViewer.Successfully("Exit", "the system");
                        return;
                    }
                    break;
                default:
                    OutputViewer.errChoice(0, 8);
            }
        }
    }
}