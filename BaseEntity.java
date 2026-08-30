package oop.do_an_hospital.hospital;
import java.io.Serializable;

// Cần implements Serializable để sau này ghi/đọc đối tượng ra file .dat
public abstract class BaseEntity implements Serializable{
    private String departmentID;
    private String createDate;
    private String lastUpdateDate;

    // === Khai báo Constructor ===
    // 1. Tạo constructor không tham số để sau này có thể tạo vỏ rỗng như BaseEntity d = new BaseEntity() và dùng obj d đó, VD: d.setName()
    public BaseEntity(){}
    // 2. Constructor đầy đủ thông tin để nạp vào
    public BaseEntity(String dID, String ngay_tao, String ngayUpdate){
        this.departmentID = dID;
        this.createDate = ngay_tao;
        this.lastUpdateDate = ngayUpdate;
    }

    // ==== Getter/Setter ====
    // departmentID không được đổi -> không set nó hoặc có thì là private.
    // createDate cũng không được đổi -> khóa setter bằng private hoặc không set.
    // 1. Get: lấy thông tin trong private ra dùng
    public String getDepartmentID(){return departmentID;}
    public String getCreateDate(){return createDate;}
    public String getLastUpdateDate(){return lastUpdateDate;}

    // 2. Set: Đưa thông tin mới vào private (sửa thông tin)
    public void setLastUpdateDate(String ngayUpdate){this.lastUpdateDate = ngayUpdate;}

    // === Hàm trừu tượng (abstract) showInfo() để ép các lớp con phải tự định nghĩa cách in thông tin riêng
    public abstract void showInfo();
}
