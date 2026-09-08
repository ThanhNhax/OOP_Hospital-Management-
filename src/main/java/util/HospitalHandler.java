package util;

import java.util.Date;
import hospital.*;

public class HospitalHandler {
    // Khai báo 2 hệ thống quản lý dữ liệu
    private DepartmentManager deptMan = new DepartmentManager();
    private DoctorManager docMan = new DoctorManager();

    // Tạo file .dat - dùng final không cho chỉnh sửa
    private final String deptFile = "department.dat";
    private final String docFile = "doctor.dat";

    // Đọc/Ghi File
    // Thêm Constructor nạp file tự động khi mở app
    public HospitalHandler(){
        deptMan.loadFromFile(deptFile);
        docMan.loadFromFile(docFile);
    }
    // Mỗi lần có thay đổi thực tế thì save vào file

    // === Các hàm handle ===
    // Thêm: Chọn add dept hoặc add doc
    public void handleAddNew(){
        while (true){
            Validator.Notice("---ADD NEW---");
            Validator.Notice("1. Add New Department\n2. Add New Doctor\n0. Back to Main Menu");
            // Nhập Choose để chọn số
            String Choose = Validator.readNonEmptyString("Choose (0-2): ");
            // Nếu chọn 0 thì return để thoát luôn
            if (Choose.equals("0")) return;
            else if (Choose.equals("1")){
                // Thêm Department => bắt buộc DeptID không trùng
                String deptID = Validator.readPattenString("Nhap Department ID: ", id -> !deptMan.isDuplicateID(id), "Loi: DepartmentID da ton tai!!");
                // Tiếp tục nhập tên dept
                String deptName = Validator.readNonEmptyString("Nhap ten Khoa: ");
                // Ngày tạo là lúc nhập -> chỉ cần gọi hàm Constructor điền thông tin vào với new Date(), ngày lastUpdate = null vì chưa có Update.
                Department newDept = new Department(deptID, deptName, new Date(), null);
                // Thêm vào deptMan
                deptMan.add(newDept);
                // Thông báo thêm thành công
                Validator.Notice("Them Khoa thanh cong!!");
                // Sau khi thêm vào Khoa thành công thì save trạng thái mới vào file
                deptMan.saveToFile(deptFile);
            }
            else if (Choose.equals("2")){
                // Thêm bác sĩ: yêu cầu không trùng docID và phòng ban đã tồn tại.
                // 1. Nhập docID trước - không trùng docID
                String docID = Validator.readPattenString("Nhap Doctor ID: ", id -> !docMan.isDuplicateID(id), "Loi: Doctor ID da ton tai!!");
                // 2. Nhập các thông tin tên doc, sex, addr
                String docName = Validator.readNonEmptyString("Nhap ten bac si: ");
                String docSex = Validator.readGender("Nhap gioi tinh: ");
                String docAddr = Validator.readNonEmptyString("Nhap dia chi bac si: ");
                // 3. Nhap deptID - bắt buộc đã tồn tại, nếu nhập sai bắt buộc nhập lại deptID đến khi đúng, thay vì bắt nhập lại toàn bộ thông tin
                String deptID = Validator.readPattenString("Nhap Department ID: ", id -> deptMan.findByID(id) != null, "Loi: Department ID khong ton tai!!"); // Nếu ID không tồn tại thì trả về null -> bắt buộc phải != null
                // 4. Dùng hàm khởi tạo để thêm thông tin
                Doctor newDoc = new Doctor(docID, docName, docSex, docAddr, deptID, new Date(), null);
                // Thêm vào docMan
                docMan.add(newDoc);
                // In thông báo thành công
                Validator.Notice("Them bac si thanh cong!!");
                // Tương tự add thành công thì save file vào doc file
                docMan.saveToFile(docFile);
            }
            // Nếu không chọn 0-2 thì báo lỗi
            else Validator.Notice("Loi: Vui long chi nhap tu 0-2!!");
        }
    }

    // Xóa: chọn xóa dept hoặc xóa doc
    public void handleDelete(){
        while (true){
            Validator.Notice("---DELETE---");
            Validator.Notice("1. Delete Department\n2. Delete Doctor\n0. Back to Main Menu");
            // Nhập Choose để chọn số
            String Choose = Validator.readNonEmptyString("Choose (0-2): ");
            // Nếu chọn 0 thì return để thoát luôn
            if (Choose.equals("0")) return;
            // 1. Xóa dept -> điều kiện ràng buộc: không được xóa nếu còn doc
            else if (Choose.equals("1")){
                // Tìm dept ID có tồn tại
                String deptID = Validator.readPattenString("Nhap Department ID can xoa: ", id -> deptMan.findByID(id) != null, "Loi: Department ID khong ton tai!!");
                // Nếu không còn doc -> !true thì xác nhận, Y thì xóa
                if (!deptMan.hasDoctor(deptID, docMan)) {
                    boolean confirm = Validator.readConfirm("Ban xac nhan muon xoa (Bam Y/N hoac Yes/No)?");
                    if (confirm){
                        deptMan.delete(deptID);
                        Validator.Notice("Xoa khoa thanh cong!!");
                        deptMan.saveToFile(deptFile);
                    } else Validator.Notice("Huy xoa khoa!!");
                }
                // Ngược lại thì báo lỗi
                else Validator.Notice("Loi: Van con ton tai bac si trong khoa!! Khong the xoa!!");
            }
            // 2. Xóa doc -> xác nhận confirm trước khi xóa
            else if (Choose.equals("2")){
                // Tìm docID để xóa
                String docID = Validator.readPattenString("Nhap Doctor ID can xoa: ", id -> docMan.findByID(id) != null, "Loi: Doctor ID khong ton tai!!");
                // Đúng ID -> xác nhận xóa
                boolean confirm = Validator.readConfirm("Ban xac nhan muon xoa (Bam Y/N hoac Yes/No)?");
                if (confirm) {
                    docMan.delete(docID);
                    Validator.Notice("Xoa bac si thanh cong!!");
                    docMan.saveToFile(docFile);
                } else Validator.Notice("Huy xoa bac si!!");
            }
            else Validator.Notice("Loi: Vui long chi nhap tu 0-2!!");
        }
    }

    // In: Hiển thị toàn bộ danh sách dept/doc
    public void handleDisplay(){
        while (true){
            Validator.Notice("---DISPLAY---");
            Validator.Notice("1. Display Department List\n2. Display Doctor List\n0. Back to Main Menu");
            // Nhập Choose để chọn số
            String Choose = Validator.readNonEmptyString("Choose (0-2): ");
            // Nếu chọn 0 thì return để thoát luôn
            if (Choose.equals("0")) return;
            else if (Choose.equals("1")) deptMan.showAll();
            else if (Choose.equals("2")) docMan.showAll();
            else Validator.Notice("Loi: Vui long chi nhap tu 0-2!!");
        }
    }

    // Tìm kiếm: searchDeptByID, DocByID, DocByName
    public void handleSearch(){
        while (true){
            Validator.Notice("---DISPLAY---");
            Validator.Notice("1. Search Department By ID\n2. Search Doctor By Name\n0. Back to Main Menu");
            // Nhập Choose để chọn số
            String Choose = Validator.readNonEmptyString("Choose (0-2): ");
            // Nếu chọn 0 thì return để thoát luôn
            if (Choose.equals("0")) return;
            else if (Choose.equals("1")) {
                // displayDeptID goi findByID -> null bao khong co; else in ra
                // Nhập ID muốn search
                String deptID = Validator.readNonEmptyString("Nhap Department ID muon tim: ");
                deptMan.displayDeparmtentByID(deptID);
            }
            else if (Choose.equals("2")){
                // searchByName có contains tìm chuỗi con
                String namekey = Validator.readNonEmptyString("Nhap name keyword muon tim: ");
                docMan.searchByName(namekey);
            }
            else Validator.Notice("Loi: Vui long chi nhap tu 0-2!!");
        }
    }

    // Update:
    // 1. Update dept
    private void handleUpdateDept(){
        // Nhập id để kiểm tra dept có tồn tại
        String deptID = Validator.readNonEmptyString("Nhập Department ID can update: ");
        // Duyệt trong dept cũ kiểm tra
        Department oldDept = deptMan.findByID(deptID);
        // Nếu không tồn tại -> báo lỗi + kết thúc
        if (oldDept == null){
            Validator.Notice("Loi: Department ID khong ton tai!!");
            return;
        }
        // Không thì update
        // Nhập tên mới, cho phép rỗng -> rỗng = giữ info cũ
        String newName = Validator.readString("Nhập ten moi (blank de bo qua update): ");
        newName = newName.isEmpty() ? oldDept.getName() : newName;
        // Dept chỉ có name được thay đổi; lastupdatedate lấy = new Date()
        // Đưa vào Constructor để lấy newDept
        Department updDept = new Department(deptID, newName, oldDept.getCreateDate(), new Date());
        if (deptMan.update(updDept)) Validator.Notice("Cap nhat Khoa thanh cong!!");
        deptMan.saveToFile(deptFile);
    }
    // 2. Update Doc
    private void handleUpdateDoc(){
        // Nhập ID tìm Doc muốn upd
        String docID = Validator.readNonEmptyString("Nhap Doctor ID can update: ");
        Doctor oldDoc = docMan.findByID(docID);

        if (oldDoc == null){
            Validator.Notice("Loi: Doctor ID khong ton tai!!");
            return;
        }
        
        // 1. Name
        String newName = Validator.readString("Nhập ten moi (blank de bo qua update): ");
        newName = newName.isEmpty() ? oldDoc.getName() : newName;

        // 2. Sex
        String newSex = Validator.readString("Nhập gioi tinh moi (blank de bo qua update): ");
        newSex = newSex.isEmpty() ? oldDoc.getSex() : newSex;

        // 3. Addr
        String newAddr = Validator.readString("Nhập dia chi moi (blank de bo qua update): ");
        newAddr = newAddr.isEmpty() ? oldDoc.getAddress() : newAddr;

        // 4. DeptID - nếu nhập mới thì bắt buộc có trong deptList, nếu không thì bắt nhập lại đến khi đúng.
        // Đặt biến lấy deptID cũ trước
        String oldDeptID = oldDoc.getDepartmentID();
        while(true){
            String newDeptID = Validator.readString("Nhập Department ID moi (blank de bo qua update): ");
            // Nếu trống thì kết thúc luôn
            if (newDeptID.isEmpty()) break;
            // Nếu không thì kiểm tra, đúng thì lấy newDeptID, không thì báo lỗi -> lặp lại.
            if (deptMan.findByID(newDeptID) != null){
                oldDeptID = newDeptID; // thay deptID mới vào deptID cũ
                break;
            }else Validator.Notice("Loi: Department ID khong ton tai!!");
        }
        // Đưa vào Doc COnstructor
        Doctor updDoc = new Doctor(docID, newName, newSex, newAddr, oldDeptID, oldDoc.getCreateDate(), new Date());

        if (docMan.update(updDoc)) Validator.Notice("Cap nhat bac si thanh cong!!");
        docMan.saveToFile(docFile);
    }
    // Đưa vào handleUpdate
    public void handleUpdate(){
        while (true){
            Validator.Notice("---UPDATE---");
            Validator.Notice("1. Update Department\n2. Update Doctor \n0. Back to Main Menu");
            // Nhập Choose để chọn số
            String Choose = Validator.readNonEmptyString("Choose (0-2): ");
            // Nếu chọn 0 thì return để thoát luôn
            if (Choose.equals("0")) return;
            else if (Choose.equals("1")) handleUpdateDept();
            else if (Choose.equals("2")) handleUpdateDoc();
            else Validator.Notice("Loi: Vui long chi nhap tu 0-2!!");
        }
    }
    
    // Thêm handleSaveToFile để làm tính năng khi user chọn save ngang dù không có thay đổi.
    public void handleSaveToFile(){
        deptMan.saveToFile(deptFile);
        docMan.saveToFile(docFile);
        Validator.Notice("Dong bo tat ca du lieu ra file .dat thanh cong!!");
    }
}