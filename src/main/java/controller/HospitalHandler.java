package controller;

import view.*;

public class HospitalHandler {
    // Khai báo 3 hệ thống quản lý dữ liệu
    private DepartmentHandle deptHan = new DepartmentHandle();
    private DoctorHandle docHan = new DoctorHandle();
    private PatientHandle patHan = new PatientHandle();

    // Mỗi lần có thay đổi thực tế thì save vào file
    // === Các hàm handle ===
    // 1. Thêm: Chọn add dept, doc hoặc pat
    public void handleAddNew() {
        while (true) {
            OutputViewer.printSubMenu("ADD NEW");
            // Nhập Choose để chọn số
            String Choose = InputValidator.readNonEmptyString("Choose (0-3): ");
            // Nếu chọn 0 thì return để thoát luôn
            if (Choose.equals("0"))
                return;
            else if (Choose.equals("1"))
                deptHan.addDepartment();
            else if (Choose.equals("2"))
                docHan.addDoctor(deptHan.getDeptMan()); // Truyền tham số lấy deptMan trong deptHan để lấy được deptID
            else if (Choose.equals("3"))
                patHan.addPatient();
            // Nếu không chọn 0-3 thì báo lỗi
            else
                OutputViewer.errChoice(0, 3);
        }
    }

    // 2. Xóa: chọn xóa dept, doc hoặc pat
    public void handleDelete() {
        while (true) {
            OutputViewer.printSubMenu("DELETE");// Nhập Choose để chọn số
            String Choose = InputValidator.readNonEmptyString("Choose (0-3): ");
            // Nếu chọn 0 thì return để thoát luôn
            if (Choose.equals("0"))
                return;
            // 1. Xóa dept -> điều kiện ràng buộc: không được xóa nếu còn doc
            else if (Choose.equals("1"))
                deptHan.deleteDepartment(docHan.getDocMan());
            // 2. Xóa doc -> xác nhận confirm trước khi xóa
            else if (Choose.equals("2"))
                docHan.deleteDoctor(patHan.getPatMan());
            else if (Choose.equals("3"))
                patHan.deletePatient();
            else
                OutputViewer.errChoice(0, 3);
        }
    }

    // 3. Update:
    public void handleUpdate() {
        while (true) {
            OutputViewer.printSubMenu("UPDATE");
            // Nhập Choose để chọn số
            String Choose = InputValidator.readNonEmptyString("Choose (0-3): ");
            // Nếu chọn 0 thì return để thoát luôn
            if (Choose.equals("0"))
                return;
            else if (Choose.equals("1"))
                deptHan.updateDept();
            else if (Choose.equals("2"))
                docHan.updateDoc(deptHan.getDeptMan());
            else if (Choose.equals("3"))
                patHan.updatePat();
            else
                OutputViewer.errChoice(0, 3);
        }
    }

    // 4. Tìm kiếm: searchDeptByID, DocByName, PatAdvance
    public void handleSearch() {
        while (true) {
            OutputViewer.printSubMenu("SEARCH");
            // Nhập Choose để chọn số
            String Choose = InputValidator.readNonEmptyString("Choose (0-3): ");
            // Nếu chọn 0 thì return để thoát luôn
            if (Choose.equals("0"))
                return;
            else if (Choose.equals("1")) {
                // displayDeptID goi findByID -> null bao khong co; else in ra
                // Nhập ID muốn search
                String deptID = InputValidator.readNonEmptyString("Department ID: ");
                deptHan.getDeptMan().displayDeparmtentByID(deptID);
            } else if (Choose.equals("2")) {
                // searchByName có contains tìm chuỗi con
                String namekey = InputValidator.readNonEmptyString("Keyword: ");
                docHan.getDocMan().searchByName(namekey);
            } else if (Choose.equals("3")) {
                String keyword = InputValidator.readNonEmptyString("Keyword: ");
                patHan.getPatMan().searchAdvance(keyword);
            } else
                OutputViewer.errChoice(0, 3);
        }
    }

    // 5. Display a patient: Khi tìm 1 đối tượng bệnh nhân -> dùng while cho tìm
    // liên tục đến khi chọn thoát
    public void handleDisplayAPatient() {
        while (true) {
            String patID = InputValidator.readNonEmptyString("Patient ID (Enter 0 to exit): ");
            // Nếu nhập 0 thì kết thúc
            if (patID.equals("0"))
                return;
            // Nếu nhập thông tin khác -> gọi searchByPatID để kiểm tra và in ra nếu có
            patHan.getPatMan().searchPatientByID(patID);
        }
    }

    // In: Hiển thị toàn bộ danh sách dept/doc
    public void handleDisplayAll() {
        while (true) {
            OutputViewer.printSubMenu("DISPLAY");// Nhập Choose để chọn số
            String Choose = InputValidator.readNonEmptyString("Choose (0-3): ");
            // Nếu chọn 0 thì return để thoát luôn
            if (Choose.equals("0"))
                return;
            else if (Choose.equals("1"))
                deptHan.getDeptMan().showAll();
            else if (Choose.equals("2"))
                docHan.getDocMan().showAll();
            else if (Choose.equals("3"))
                patHan.getPatMan().showAll();
            else
                OutputViewer.errChoice(0, 3);
        }
    }

    // Thêm handleSaveToFile để làm tính năng khi user chọn save ngang dù không có
    // thay đổi.
    public void handleSaveToFile() {
        deptHan.saveToFile();
        docHan.saveToFile();
        patHan.saveToFile();
        OutputViewer.Successfully("Save", "file");
        ;
    }

    // loadFromFile theo opt 8 trong đề mới
    public void handleLoadFromFile() {
        deptHan = new DepartmentHandle();
        docHan = new DoctorHandle();
        patHan = new PatientHandle();
        OutputViewer.Successfully("Load", "All Data from Files");
    }
}