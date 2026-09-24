package view;

public class OutputViewer {
    // Tạo hàm thông báo dùng chung mọi nơi
    public static void Notice(String text){
        System.out.println(text);
    }

    // 1. Các câu báo lỗi chung - PENDING
    // 1.1 Các câu lặp lại với đối tượng khác nhau
    // a. List trống
    public static void listEmptyErr(String objName){
        Notice("Error: " + objName + " list is empty!!");
    }
    // b. Không tìm thấy dept, doc, pat
    public static void unFind(String objName, String key){
        Notice("Error: Cannot find " + objName + " matching: '" + key + "'");
    }

    // c. Thực hiện thành công / hủy bỏ hành vi với đối tượng
    // 1. Thành công
    public static void Successfully(String function, String objName){
        Notice("Notice: " + function + " " + objName + " successfully!");
    }

    // 2. Hủy bỏ
    public static void Cancelled(String function, String objName){
        Notice("Notice: " + function + " " + objName + " was cancelled!");
    }

    // d. Báo lỗi chỉ được nhập từ 0-3 trong subMenu
    public static void errChoice(int ori, int dest){
        Notice("Error: Invalid choice! Please enter from " + ori + " to " + dest);
    }
    
    // 2. In menu chính
    public static void printMainMenu() {
        Notice("\n========== HOSPITAL MANAGEMENT SYSTEM ==========");
        Notice("1. Add new");
        Notice("2. Delete");
        Notice("3. Edit/Update");
        Notice("4. Search");
        Notice("5. Display a patient");
        Notice("6. Display all");
        Notice("7. Save to file");
        Notice("8. Load from file");
        Notice("0. Exit");
        Notice("Enter your choice: ");
    }

    // 3. In menu con cho đối tượng
    public static void printSubMenu(String title) {
        Notice("\n--- " + title + " ---");
        Notice("1. Department");
        Notice("2. Doctor");
        Notice("3. Patient");
        Notice("0. Back to Main Menu");
    }

    public static void printPatSubMenu(String title){
        Notice("\n--- " + title + " ---");
        Notice("1. Search By ID");
        Notice("2. Search Advance");
        Notice("0. Back to Main Menu");
    }
}
