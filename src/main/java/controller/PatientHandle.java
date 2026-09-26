package controller;

import java.util.Date;

import manager.*;
import model.*;
import view.InputValidator;
import view.OutputViewer;

public class PatientHandle {
    // Quản lý dữ liệu của Patient
    private PatientManager patMan = new PatientManager();
    private final String patFile = "patient.dat";

    // Khởi tạo để load vào khi chạy app
    public PatientHandle(){
        patMan.loadFromFile(patFile);
    }

    // Getter khi cần
    public PatientManager getPatMan(){
        return patMan;
    }

    // === Các hàm thêm, xóa, sửa cho Pat
    // 1. Thêm: Không có field nào được để trống.
    public void addPatient(){
        // 1. PatID phải KHÔNG tồn tại
        String patID = InputValidator.readValidString("Patient ID (Enter 0 to cancel)",null, "add", id -> patMan.findByID(id) == null, "Error: Patient ID must not be empty and must not exist!!"); // phải không tìm thấy ID nào -> null -> không trùng thì mới được nhập
        if (patID == null){
            OutputViewer.Cancelled("Add", "Patient");
            return; // Thoát về menu chính
        }  
        // 2. Nhập các thông tin cơ bản
        String name = InputValidator.readNonEmptyString("Patient Name: ");
        String sex = InputValidator.readGender("Patient Sex (M/F): ", null, "add"); // có định dạng M/F, Male/Female
        String addr = InputValidator.readNonEmptyString("Address: ");
        String dob = InputValidator.readDate("Date of Birth (dd/MM/yyyy): ", null, "add"); // dob là định dạng ngày hợp lệ dd/MM/yyyy (dùng formatDate và parseDate)
        String phone = InputValidator.readPhone("Phone Number: ", null, "add"); // phone phải dúng (chuỗi số 10 số)
        String diagnosis = InputValidator.readNonEmptyString("Diagnosis: ");

        // 3. docID, không ràng buộc vì đề mới chỉ có 1 class
        String doctor = InputValidator.readNonEmptyString("Assigned Doctor");
        // 4. admissionStatus phải là trạng thái hợp lệ
        String status = InputValidator.readAdmissionStatus("Admission Status: ", null, "add");

        // Đưa vào constructor
        Patient newPat = new Patient(patID, name, sex, addr, dob, phone, diagnosis, doctor, status, new Date(), null);

        // Lưu vào list, nếu thành công -> save file
        if (patMan.add(newPat)){
            OutputViewer.Successfully("Add", "Patient");
            patMan.saveToFile(patFile);
        }
    }

    // 2. Xóa theo PatID
    public void deletePatient(){
        // Tìm patID để xóa
        String patID = InputValidator.readValidString("Patient ID (Enter 0 to cancel)",null, "delete", id -> patMan.findByID(id) != null, "Error: Patient ID does not exist!!");
        if (patID == null){
            OutputViewer.Cancelled("Delete", "Patient");
            return; // Thoát về menu chính
        }  
        // Đúng ID -> xác nhận xóa
        boolean confirm = InputValidator.readConfirm("delete this Patient");
        if (confirm) {
            patMan.delete(patID);
            OutputViewer.Successfully("Delete", "Patient");
            patMan.saveToFile(patFile);
        } else OutputViewer.Cancelled("Delete", "Patient");
    }

    // update
    public void updatePat(){
        // Nhập ID tìm Pat muốn upd
        String patID = InputValidator.readValidString("Patient ID (Enter 0 to cancel)", null, "updateWithoutChange",
                id -> !id.isEmpty() && patMan.findByID(id) != null, "Error: Patient ID does not exist!!");
        if (patID == null) {
            OutputViewer.Cancelled("Update", "Patient");
            return;
        }
        Patient oldPat = patMan.findByID(patID);
        // 1. Name (không định dạng)
        String newName = InputValidator.readUpdateString("name", oldPat.getName());
        // 2. Sex (có định dạng)
        String newSex = InputValidator.readGender("gender", oldPat.getPatSex(), "update");
        // 3. Addr
        String newAddr = InputValidator.readUpdateString("address", oldPat.getPatAddress());
        // 4. dob: cần xử lý nhập đúng định dạng
        String newDOB = InputValidator.readDate("Date of Birth (dd/MM/yyyy): ", oldPat.getPatDOB(), "update");
        // 5. phone
        String newPhone = InputValidator.readPhone("phone", oldPat.getPatPhone(), "update");
        String newDiag = InputValidator.readUpdateString("diagnosis", oldPat.getPatDiagnosis());
        // 6. Doctor (không ràng buộc với Pat)
        String newDoc = InputValidator.readUpdateString("assigned doctor", oldPat.getAssignedDoctor());
        // 7. Admission status
        String newStatus = InputValidator.readAdmissionStatus("admission status", oldPat.getPatAdmissionStatus(), "update");

        // Đưa vào Doc Constructor
        Patient updPat = new Patient(patID, newName, newSex, newAddr, newDOB, newPhone, newDiag, newDoc, newStatus, oldPat.getCreateDate(), new Date());

        if (patMan.update(updPat)) {
            OutputViewer.Successfully("Update", "Patient");
            patMan.saveToFile(patFile);
        }
    }

    // Save file
    public void saveToFile(){patMan.saveToFile(patFile);}
}
