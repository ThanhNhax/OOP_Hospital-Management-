package model;
import java.util.Date;
import util.Utils;

// Không để Patient kế thừa từ Doctor vì Patient không IS A Doctor, nếu kế thừa Doctor sẽ dư thừa thuộc tính
public class Patient extends BaseEntity{
    private String sex;
    private String address;
    private String phone;
    private String dob;
    private String diagnosis;
    private String doctorID; // Chỉ lưu docID để search doctor khi cần và dễ dàng lưu file
    private String admissionStatus;

    // === Hàm khởi tạo
    // 1. Không có tham số
    public Patient(){}

    // 2. Constructor đầy đủ
    public Patient(String PatID, String fullname, String sex, String addr, String dob, String phone, String diagnosis, String docID, String status, Date creatDate, Date updateDate){
        super(PatID, fullname, creatDate, updateDate); // Vẫn lấy ngày tạo và lastUpdate, khi controller tạo mới dùng new Date() để tạo ngày truyền vào tham số.
        this.sex = sex;
        this.address = addr;
        this.phone = phone;
        this.dob = dob;
        this.diagnosis = diagnosis;
        this.doctorID = docID;
        this.admissionStatus = status;
    }

    // === Getter/Setter
    // 1. Getter
    public String getPatientID(){return getID();}
    public String getPatientName(){return getName();}
    public String getPatSex(){return sex;}
    public String getPatAddress(){return address;}
    public String getPatPhone(){return phone;}
    public String getPatDOB(){return dob;}
    public String getPatDiagnosis(){return diagnosis;}
    public String getDocID(){return doctorID;}
    public String getPatAdmissionStatus(){return admissionStatus;}

    // 2. Setter: tương tự Doctor
    public void setPatSex(String sex){this.sex = sex;}
    public void setPatAddr(String addr){this.address = addr;}
    public void setPatPhone(String phone){this.phone = phone;}
    public void setPatDOB(String dob){this.dob = dob;}
    public void setPatDiag(String diag){this.diagnosis = diag;}
    public void setPatDocID(String docID){this.doctorID = docID;}
    public void setPatStatus(String status){this.admissionStatus = status;}

    // Chuẩn bị in
    @Override 
    public String toString(){
        // Gọi utils chỉnh form trước
        String sdfCreateDate = Utils.formatDate(getCreateDate());
        String sdfUpdateDate = Utils.formatDate(getLastUpdateDate());

        return String.format("| %-15s | %-30s | %-7s | %-50s | %-10s | %-12s | %-20s | %-15s | %-15s | %-12s | %-12s |", getPatientID(), getPatientName(), sex, address, dob, phone, diagnosis, doctorID, admissionStatus, sdfCreateDate, sdfUpdateDate); // In thêm date
    }

    @Override public void showInfo(){System.out.println(this.toString());}
}
