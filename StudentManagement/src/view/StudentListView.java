package view;

import java.util.List;

import controller.StudentList;
import model.Student;
import utils.Utils;

public class StudentListView {

    public static void main(String[] args) {
        // Tạo SV = new constructor
        StudentList stdList = new StudentList();
        int choice; // tạo biến chọn

        do {
            // Tạo danh sách menu
            Utils.Notice("\n========= MENU QUAN LY SINH VIEN =========");
            Utils.Notice("1. Them sinh vien moi");
            Utils.Notice("2. Xoa sinh vien theo ID");
            Utils.Notice("3. Sua thong tin sinh vien");
            Utils.Notice("4. Tim kiem sinh vien theo ten");
            Utils.Notice("5. Sap xep sinh vien (theo ten / diem)");
            Utils.Notice("6. Hien thi danh sach sinh vien");
            Utils.Notice("0. Thoat");
            // dùng getInt để chọn menu
            choice = Utils.getInt("Nhap lua chon cua ban (0-6): ", 0, 6);

            switch (choice) {
                case 1:
                    Utils.Notice("\n--- Them sinh vien moi ---");
                    String id;
                    // Nhập ID trước, lặp lại cho đến khi không trùng || rỗng
                    while (true) {
                        // Nhập không rỗng
                        id = Utils.readNonEmptyString("Nhap ID sinh vien: ");
                        // Nếu != dup -> chưa có ID -> dừng lặp, đã có ID
                        if (!stdList.isDuplicate(id)) break;
                        // Nếu không thì báo lỗi -> lặp lại nhập.
                        Utils.Notice("Loi: ID da ton tai! Vui long nhap ID khac.");
                    }
                    // Tạo một st
                    Student st = new Student();
                    // Thêm ID vào st
                    st.create(id); // Hàm create giúp thêm name và scrore vào st
                    // Thêm vào stdList, báo thành công hoặc lỗi.
                    if (stdList.add(st)) Utils.Notice("Thanh cong: Da them sinh vien!");
                    else Utils.Notice("Loi: Khong the them sinh vien!");
                    break;

                case 2:
                    Utils.Notice("\n--- Xoa sinh vien ---");
                    // Nhập ID cần xóa không rỗng và phải tồn tại
                    String delId = Utils.readNonEmptyString("Nhap ID sinh vien can xoa: ");
                    // Hàm delete đã có xử lý tìm ID, nếu có -> xóa, không -> null -> báo lỗi.
                    if (stdList.delete(delId)) Utils.Notice("Thanh cong: Da xoa sinh vien!");
                    else Utils.Notice("Loi: Khong tim thay ID!");
                    break;

                case 3:
                    Utils.Notice("\n--- Sua thong tin sinh vien ---");
                    // update thông tin theo ID -> không rỗng và phải tồn tại.
                    String updateId = Utils.readNonEmptyString("Nhap ID sinh vien can sua: ");
                    // Nếu != dup -> không có ID -> báo lỗi và kết thúc
                    if (!stdList.isDuplicate(updateId)) {
                        Utils.Notice("Loi: Khong tim thay ID sinh vien này!");
                        break;
                    }
                    // Nếu không thì xử lý
                    // tạo std chứa newInfo
                    Student newInfo = new Student();
                    newInfo.create(updateId); // updateId đã verify có tồn tại mới xử lý -> dùng create để nhập thông tin mới.
                    // Đưa newInfo vào List
                    if (stdList.update(newInfo)) Utils.Notice("Thanh cong: Cap nhat hoan tat!");
                    break;

                case 4:
                    Utils.Notice("\n--- Tim kiem sinh vien ---");
                    String keyword = Utils.readNonEmptyString("Nhap ten can tim: ");
                    // tạo list chứa các name gần giống trong stdList
                    List<Student> result = stdList.search(keyword);
                    // Nếu result != null -> in header ra trước
                    if (result.isEmpty())Utils.Notice("Khong tim thay sinh vien phu hop");
                    else{
                        // dùng stdList để hiển thị header
                        stdList.printHeader();
                        // Duyệt qua list result
                        for (Student std : result) std.showInfo();
                    }
                    break;

                case 5:
                    Utils.Notice("\n--- Sap xep ---");
                    // Chọn lựa sắp xếp theo tên hoặc theo điểm
                    Utils.Notice("1. Sap xep theo Ten (A-Z)");
                    Utils.Notice("2. Sap xep theo Diem (Giam dan)");
                    Utils.Notice("0. Quay ve Menu");
                    // Tạo biến chọn cho sort
                    int sortChoice = Utils.getInt("Lua chon (0-2): ", 0, 2);
                    if (sortChoice == 0) break;
                    else if (sortChoice == 1) {
                        stdList.sortByName();
                        Utils.Notice("Da sap xep theo ten!");
                    } else {
                        stdList.sortByScore();
                        Utils.Notice("Da sap xep theo diem!");
                    }
                    // Hiển thị lại danh sách sau khi sắp xếp
                    stdList.displayList();
                    break;

                case 6:
                    Utils.Notice("\n--- Danh sach sinh vien ---");
                    // In toàn bộ danh sách
                    stdList.displayList();
                    break;

                case 0:
                    Utils.Notice("Thoat thanh cong!!");
                    break;
            }
        } while (choice != 0);
    }
}