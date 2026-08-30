package oop.do_an_hospital.hospital;
import java.io.Serializable;

// Cần implements Serializable để sau này ghi/đọc đối tượng ra file .dat
public class Department implements Serializable{
    private String departmentID;
    private String name;
    private String createDate;
    private String lastUpdateDate; 

    // Đảm báo đơn trách nhiệm -> ở class này chỉ quản lý các thuộc tính -> get/set thuộc tính nào.
    
    // ==== Constructor ====

    // 1. Tạo constructor không tham số để sau này có thể tạo vỏ rỗng như Department d = new Department() và dùng obj d đó, VD: d.setName()
    public Department(){}
    // 2. Tạo constructor đầy đủ tham số để đưa vào attribute (tạo mới / đưa từ data về RAM)
    public Department(String dID, String ten, String ngay_tao, String ngayUpdate){
        this.departmentID = dID;
        this.name = ten;
        this.createDate = ngay_tao;
        this.lastUpdateDate = ngayUpdate;
    }

    // ==== Getter/Setter ====
    // departmentID không được đổi -> không set nó hoặc có thì là private.
    // 1. Get: lấy thông tin trong private ra dùng
    public String getDepartmentID(){
        return departmentID;
    }
    public String getName(){
        return name;
    }
    public String getCreateDate(){
        return createDate;
    }
    public String getLastUpdateDate(){
        return lastUpdateDate;
    }
    // 2. Set: Đưa thông tin mới vào private (sửa thông tin)
    public void setName(String ten){
        this.name = ten;
    }
    public void setCreateDate(String ngay_tao){
        this.createDate = ngay_tao;
    }
    public void setLastUpdateDate(String ngayUpdate){
        this.lastUpdateDate = ngayUpdate;
    }

    // ==== Hàm in một obj ====
    // Dùng toString() có sẵn của Java, nếu không @Override sẽ ra chuỗi ký tự như Person... và ô địa chỉ -> Override để viết lại hàm toString, khi gọi Department d = new Department() và println(d) -> in ra đúng form đã định dạng.
    @Override public String toString(){
        // Dùng formart để vẽ khung tương tự printf("%-15s | %-20s", deparmentID, name) của C++
        return String.format("| %-15s | %-30s | %-12s | %-12s |", departmentID, name, createDate, lastUpdateDate);
    }
}