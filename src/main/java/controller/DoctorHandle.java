package controller;
import manager.*;
import model.Doctor;
import view.*;
import java.util.Date;

public class DoctorHandle {
    // Quản lý dữ liệu của Doctor
    private DoctorManager docMan = new DoctorManager();
    private final String docFile = "doctor.dat";

    // Constructor nạp file tự động khi mở app
    public DoctorHandle(){
        docMan.loadFromFile(docFile);
    }

    // Getter để dùng khi cần
    public DoctorManager getDocMan(){
        return docMan;
    }

    // === Các hàm thêm, xóa, sửa cho Doc
    // Cho phép thoát khi infor trả về null
    // 1. Thêm bác sĩ: yêu cầu không trùng docID và phòng ban đã tồn tại.
    public void addDoctor(DepartmentManager deptMan){
        // 1. Nhập docID trước - không trùng docID
        String docID = InputValidator.readValidString("Doctor ID: ",null, "add", id -> !docMan.isDuplicateID(id), "Error: Doctor ID already exists!!");
        if (docID == null){
            OutputViewer.Cancelled("Add", "Doctor");
            return; // Thoát về menu chính
        }
        // 2. Nhập các thông tin tên doc, sex, addr
        String docName = InputValidator.readNonEmptyString("Doctor Name: ");
        String docSex = InputValidator.readGender("Doctor Sex (M/F): ", null, "add");
        String docAddr = InputValidator.readNonEmptyString("Doctor Address: ");
        // 3. Nhap deptID - bắt buộc đã tồn tại, nếu nhập sai bắt buộc nhập lại deptID đến khi đúng, thay vì bắt nhập lại toàn bộ thông tin
        String deptID = InputValidator.readValidString("Department ID: ",null, "add", id -> deptMan.findByID(id) != null, "Error: Department ID does not exist!!"); // Nếu ID không tồn tại thì trả về null -> bắt buộc phải != null
        if (deptID == null){
            OutputViewer.Cancelled("Add", "Doctor");
            return; // Thoát về menu chính        
        }
        // 4. Dùng hàm khởi tạo để thêm thông tin
        Doctor newDoc = new Doctor(docID, docName, docSex, docAddr, deptID, new Date(), null);
        // 5. Nếu thêm vào docMan thành công -> in ra thông báo và savetoFile vào docFile
        if (docMan.add(newDoc)){
            OutputViewer.Successfully("Add", "Doctor");
            docMan.saveToFile(docFile);
        }
    }

    // 2. Xóa bác sĩ
    public void deleteDoctor(PatientManager patMan){
        // Tìm docID để xóa
        String docID = InputValidator.readValidString("Doctor ID: ",null, "delete", id -> docMan.findByID(id) != null, "Error: Doctor ID does not exist!!");
        // Nếu user không muốn xóa -> nhập 0 -> thoát nhanh
        if (docID == null){
            OutputViewer.Cancelled("Delete", "Doctor");
            return; // Thoát về menu chính
        }
        // Đúng ID -> nếu doctor không còn phụ trách patient
        if (!docMan.hasPatient(docID, patMan)){
            // Xác nhận xóa
            boolean confirm = InputValidator.readConfirm("delete this Doctor");
            if (confirm) {
                docMan.delete(docID);
                OutputViewer.Successfully("Delete", "Doctor");
                docMan.saveToFile(docFile);
            } else OutputViewer.Cancelled("Delete", "Doctor");
        } else OutputViewer.Notice("Error: This doctor is currently assigned to patients! Cannot delete.");
    }

    public void updateDoc(DepartmentManager depMan){
        // Nhập ID tìm Doc muốn upd
        String docID = InputValidator.readNonEmptyString("Doctor ID: ");
        Doctor oldDoc = docMan.findByID(docID);
        if (oldDoc == null){
            OutputViewer.Notice("Error: Doctor ID does not exist!!");
            return;
        }
        // 1. Name (không có định dạng)
        String newName = InputValidator.readUpdateString("name", oldDoc.getName());
        // 2. Sex (có định dạng)
        String newSex = InputValidator.readGender("gender", oldDoc.getSex(), "update");
        // 3. Addr (không có định dạng)
        String newAddr = InputValidator.readUpdateString("address", oldDoc.getAddress());
        // 4. DeptID - nếu nhập mới thì bắt buộc có trong deptList, nếu không thì bắt nhập lại đến khi đúng.
        // Đặt biến lấy deptID cũ trước
        String oldDeptID = oldDoc.getDepartmentID();
        while(true){
            // Nhập ID mới, nếu trống thì trả về oldDeptID
            String newDeptID = InputValidator.readUpdateString("department ID", oldDeptID);
            // Nếu oldDeptID == newDeptID -> kết thúc
            if (newDeptID.equals(oldDeptID)) break;
            // Nếu không thì kiểm tra, đúng thì lấy newDeptID, không thì báo lỗi -> lặp lại.
            if (depMan.findByID(newDeptID) != null){
                oldDeptID = newDeptID; // thay deptID mới vào deptID cũ
                break;
            }else OutputViewer.Notice("Error: Department ID does not exist!!");
        }
        // Đưa vào Doc Constructor
        Doctor updDoc = new Doctor(docID, newName, newSex, newAddr, oldDeptID, oldDoc.getCreateDate(), new Date());

        if (docMan.update(updDoc)) {
            OutputViewer.Successfully("Update", "Doctor");
            docMan.saveToFile(docFile);
        }
    }

    // Save file
    public void saveToFile(){docMan.saveToFile(docFile);}
}
