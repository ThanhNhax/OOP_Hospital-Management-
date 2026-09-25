package model;

import java.util.Date;
import util.Utils;

public class Patient extends BaseEntity {
    private static final long serialVersionUID = 1L; // Khai báo cứng để tránh lỗi mismatch khi thêm method mới
    private String sex;
    private String address;
    private String phone;
    private String dob;
    private String diagnosis;
    private String assignedDoctor;
    private String admissionStatus;

    public Patient() {
    }

    public Patient(String PatID, String fullname, String sex, String addr, String phone, String dob, String diagnosis,
            String assignedDoctor, String status, Date creatDate, Date updateDate) {
        super(PatID, fullname, creatDate, updateDate);
        this.sex = sex;
        this.address = addr;
        this.phone = phone;
        this.dob = dob;
        this.diagnosis = diagnosis;
        this.assignedDoctor = assignedDoctor;
        this.admissionStatus = status;
    }

    public String getPatientID() {
        return getID();
    }

    public String getPatientName() {
        return getName();
    }

    public String getPatSex() {
        return sex;
    }

    public String getPatAddress() {
        return address;
    }

    public String getPatPhone() {
        return phone;
    }

    public String getPatDOB() {
        return dob;
    }

    public String getPatDiagnosis() {
        return diagnosis;
    }

    public String getAssignedDoctor() {
        return assignedDoctor;
    }

    public String getDocID() {
        return assignedDoctor;
    } // alias để tương thích với DoctorManager.hasPatient()

    public String getPatAdmissionStatus() {
        return admissionStatus;
    }

    public void setPatSex(String sex) {
        this.sex = sex;
    }

    public void setPatAddr(String addr) {
        this.address = addr;
    }

    public void setPatPhone(String phone) {
        this.phone = phone;
    }

    public void setPatDOB(String dob) {
        this.dob = dob;
    }

    public void setPatDiag(String diag) {
        this.diagnosis = diag;
    }

    public void setAssignedDoctor(String assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public void setPatStatus(String status) {
        this.admissionStatus = status;
    }

    @Override
    public String toString() {
        String sdfCreateDate = Utils.formatDate(getCreateDate());
        String sdfUpdateDate = Utils.formatDate(getLastUpdateDate());

        return String.format("| %-15s | %-30s | %-7s | %-50s | %-10s | %-12s | %-20s | %-20s | %-15s | %-12s | %-12s |",
                getPatientID(), getPatientName(), sex, address, dob, phone, diagnosis, assignedDoctor, admissionStatus,
                sdfCreateDate, sdfUpdateDate);
    }

    @Override
    public void showInfo() {
        System.out.println(this.toString());
    }
}
