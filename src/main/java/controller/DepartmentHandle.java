package controller;
import manager.DepartmentManager;
import manager.DoctorManager;
import model.Department;
import view.*;
import java.util.Date;

public class DepartmentHandle {
    // Quản lý dữ liệu của Department
    private DepartmentManager deptMan = new DepartmentManager();
    private final String deptFile = "department.dat";

    // Constructor nạp file tự động khi mở app
    public DepartmentHandle(){
        deptMan.loadFromFile(deptFile);
    }

    // Getter nếu HospitalHandler cần lấy deptMan để kiểm tra (ví dụ check Dept tồn tại khi thêm Doctor)
    public DepartmentManager getDeptMan(){
        return deptMan;
    }

    // === Các hàm thêm, xóa, sửa cho Dept
    // 1. Thêm Department => bắt buộc DeptID không trùng
    public void addDepartment(){
        String deptID = InputValidator.readValidString("Department ID: ", null, "add",id -> !deptMan.isDuplicateID(id), "Error: Department ID already exists!!");
        if (deptID == null){
            OutputViewer.Cancelled("Add", "Department");
            return; // Thoát về menu chính
        }      
        // Tiếp tục nhập tên dept (không có định dạng)
        String deptName = InputValidator.readNonEmptyString("Department name: ");
        // Ngày tạo là lúc nhập -> chỉ cần gọi hàm Constructor điền thông tin vào với new Date(), ngày lastUpdate = null vì chưa có Update.
        Department newDept = new Department(deptID, deptName, new Date(), null);
        // Nếu thêm vào deptMan thành công -> in ra thông báo và savetoFile vào deptFile
        if (deptMan.add(newDept)){
            OutputViewer.Successfully("Add", "Department");
            deptMan.saveToFile(deptFile);
        }
    }

    // 2. Xóa Dept => phải không còn Doc trong Dept mới được xóa
    public void deleteDepartment(DoctorManager docMan){
        // Tìm dept ID có tồn tại
        String deptID = InputValidator.readValidString("Department ID: ", null, "delete", id -> deptMan.findByID(id) != null, "Error: Department ID does not exist!!");
        if (deptID == null){
            OutputViewer.Cancelled("Delete", "Department");
            return; // Thoát về menu chính
        }  
        // Nếu không còn doc -> !true thì xác nhận, Y thì xóa
        if (!deptMan.hasDoctor(deptID, docMan)) {
            boolean confirm = InputValidator.readConfirm("delete this Department");
            if (confirm){
                deptMan.delete(deptID);
                OutputViewer.Successfully("Delete", "Department");
                deptMan.saveToFile(deptFile);
            } else OutputViewer.Cancelled("Delete", "Department");
        }
        // Ngược lại thì báo lỗi
        else OutputViewer.Notice("Error: This department still has assigned doctors! Cannot delete.");
    }

    // Cập nhật dept
    public void updateDept(){
        // Nhập id để kiểm tra dept có tồn tại
        String deptID = InputValidator.readNonEmptyString("Department ID: ");
        // Duyệt trong dept cũ kiểm tra và lấy được dept cũ cần upd
        Department oldDept = deptMan.findByID(deptID);
        // Nếu không tồn tại -> báo lỗi + kết thúc
        if (oldDept == null){
            OutputViewer.Notice("Error: Department ID does not exist!!");
            return;
        }
        // Không thì update
        // Nhập tên mới, cho phép rỗng -> rỗng = giữ info cũ
        String newName = InputValidator.readUpdateString("name", oldDept.getName());
        // Dept chỉ có name được thay đổi; lastupdatedate lấy = new Date()
        // Đưa vào Constructor để lấy newDept
        Department updDept = new Department(deptID, newName, oldDept.getCreateDate(), new Date());
        if (deptMan.update(updDept)) {
            OutputViewer.Successfully("Update", "Department");
            deptMan.saveToFile(deptFile);
        }
    }

    // Save To File
    public void saveToFile(){deptMan.saveToFile(deptFile);}
}
