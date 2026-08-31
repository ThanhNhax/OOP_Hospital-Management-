package oop.do_an_hospital.hospital;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Doctor extends BaseEntity{
    private String sex;
    private String address;
    private String departmentID; // Vì doctorID là chính -> dùng kế thừa từ base -> cần tạo mới deptID (phụ) trong class này.

    // ==== Constructor ====
    // Tạo Constructor trống
    public Doctor(){}

    // Tạo constructor đủ thông tin
    public Doctor(String doctorID, String name, String sex, String address, String departmentID, Date createDate, Date updateDate){
        super(doctorID, name, createDate, updateDate);
        this.departmentID = departmentID;
        this.sex = sex;
        this.address = address;
    }

    // ==== Getter/Setter ====
    // Getter
    public String getDoctorID(){return getID();} // ID chính - để dễ gọi lại ID của doctor
    public String getDoctorName(){return getName();} // Tên của Doctor
    public String getSex(){return sex;}
    public String getAddress(){return address;}
    public String getDepartmentID(){return departmentID;} // DeptID của Doctor

    // Setter
    // docID không thể thay đổi -> private hoặc không có set
    public void setSex(String sex){this.sex = sex;}
    public void setAddress(String addr){this.address = addr;}
    public void setDepartmentID(String deptID) {this.departmentID = deptID;} // deparmentID ở đây có thể sửa, vì doc có thể chuyển khoa -> cần có set

    // ==== Hàm in ====
    @Override public String toString(){
        // Đặt biến chỉnh form ngày in
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String sdfCreateDate = (getCreateDate() != null) ? sdf.format(getCreateDate()) : "";
        String sdfUpdateDate = (getLastUpdateDate() != null) ? sdf.format(getLastUpdateDate()) : "";

        // Vẽ bảng in
        return String.format("| %-15s | %-30s | %-7s | %-50s | %-15s | %-12s | %-12s |", getDoctorID(), getDoctorName(), sex, address, getDepartmentID(), sdfCreateDate, sdfUpdateDate);
    }

    @Override public void showInfo(){
        System.out.println(this.toString());
    }
}